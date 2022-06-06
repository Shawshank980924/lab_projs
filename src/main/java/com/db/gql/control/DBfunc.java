/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-26 10:59:31
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-27 15:53:55
 * @FilePath: /db_sync/src/main/java/com/db/gql/control/DBfunc.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.gql.control;
import com.db.gql.utils.GqlGen;
import com.db.gql.utils.NebulaTools;
import com.db.sql.dao.GraphDao;
import com.db.sql.utils.MybatisUtil;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.net.Session;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;


public class DBfunc {
    //同步nebula和mysql的点边数量
    public static void syncDB(){
		
		try {
            //通过工厂类获取sqlsession单例对象
			SqlSession sqlSession = MybatisUtil.getSqlSession();
            //动态代理，mapper指向通过xml实现GraphDao中接口的实现实例
            GraphDao mapper = sqlSession.getMapper(GraphDao.class);
            Session ss =  NebulaTools.getSession();
            //获取所有的spaceList
            String[] spaceList = NebulaTools.getSpaceList();
            for(String space:spaceList){
                ResultSet res =  ss.execute(GqlGen.ShowStatsGen(space));
                List<ValueWrapper>types = res.colValues("Type");
                List<ValueWrapper>names = res.colValues("Name");
                List<ValueWrapper>counts = res.colValues("Count");
                for(int i=0;i<types.size();i++){
                    String type = types.get(i).asString();
                    String name = names.get(i).asString();
                    Long count = counts.get(i).asLong();
                    System.out.println(type);
                    System.out.println(name);
                    System.out.println(count);
                    //首先同步meta表中的数据
                    if(type.equals("Space")){
                        Map<String,Object> map = new HashMap<>();
                        map.put(name, count);
                        map.put("space_name",space);
                        mapper.updateMetaByMap(map);
                    }
                    //然后同步tag表中的数据
                    else if(type.equals("Tag")){
                        Map<String,Object> map = new HashMap<>();
                        int space_id = mapper.selectByName(space).getSpace_id();
                        map.put("tag_name",name);
                        map.put("vertex_num",count);
                        map.put("space_id",space_id);
                        mapper.updateTagByMap(map);

                    }
                    //最后同步edge表中的数据
                    else if(type.equals("Edge")){
                        Map<String,Object> map = new HashMap<>();
                        int space_id = mapper.selectByName(space).getSpace_id();
                        map.put("type_name",name);
                        map.put("edge_num",count);
                        map.put("space_id",space_id);
                        mapper.updateEdgeByMap(map);

                    }
                }

            }
            //提交修改
            sqlSession.commit();
            sqlSession.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
    public static void main(String[] args) throws InterruptedException {
        while(true){
            Thread.sleep(Duration.ofHours(24L).toMillis());
            syncDB();
        }
    }
}
