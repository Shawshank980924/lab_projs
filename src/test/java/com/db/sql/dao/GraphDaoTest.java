/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-26 08:29:14
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-26 15:54:09
 * @FilePath: /db_sync/src/test/java/com/db/sql/dao/GraphDaoTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.dao;

import com.db.gql.utils.GqlGen;
import com.db.gql.utils.NebulaTools;
import com.db.sql.pojo.Edge;
import com.db.sql.pojo.EdgeField;
import com.db.sql.utils.MybatisUtil;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.net.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;





public class GraphDaoTest {
    @Test
    public void testInsertEdge() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        mapper.insertEdge(new Edge(7,0,"flow"));
        mapper.insertEdge(new Edge(8,0,"reddit_edge"));
        mapper.insertEdge(new Edge(9,0,"edge"));
        mapper.insertEdge(new Edge(10,0,"directed_edge"));
        mapper.insertEdge(new Edge(11,0,"wiki_edge"));
        mapper.insertEdge(new Edge(12,0,"directed_edge"));
        mapper.insertEdge(new Edge(13,0,"directed_edge"));
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testInsertEdgeField() throws Exception {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        Session ss =  NebulaTools.getSession();
        ResultSet res =  ss.execute(GqlGen.DescEdgeGen("netphy", "`directed_edge`"));
        List<ValueWrapper>fields = res.colValues("Field");
		List<ValueWrapper>types = res.colValues("Type");
        for(int i=0;i<types.size();i++){
            String type = types.get(i).asString();
            String field = fields.get(i).asString();
            mapper.insertEdgeField(new EdgeField(11, 13, field, type));


        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdateMetaByName() throws Exception {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        Session ss =  NebulaTools.getSession();
        ResultSet res =  ss.execute(GqlGen.ShowStatsGen("trans"));
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
            if(type.equals("Space")){
                mapper.updateMetaByName("trans", name, count);
             }
            // if(type.equals("Tag")){
            //     JdbcTools.update(SqlGen.UpdateTagGen(space, name, count)); 
            // }
            // else if(type.equals("Edge")){
            //     JdbcTools.update(SqlGen.UpdateEdgeGen(space, name, count));
            // }
            // else if(type.equals("Space")){
            //     JdbcTools.update(SqlGen.UpdateMetaGen(space, name, count));

            // }
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdateMetaByMap() throws Exception {
        
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        Session ss =  NebulaTools.getSession();
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
                if(type.equals("Space")){
                    Map<String,Object> map = new HashMap<>();
                    map.put(name, count);
                    map.put("space_name",space);
                    mapper.updateMetaByMap(map);
                }
                else if(type.equals("Tag")){
                    Map<String,Object> map = new HashMap<>();
                    int space_id = mapper.selectByName(space).getSpace_id();
                    map.put("tag_name",name);
                    map.put("vertex_num",count);
                    map.put("space_id",space_id);
                    mapper.updateTagByMap(map);

                }
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
        sqlSession.commit();
        sqlSession.close();
        
    }

    @Test
    public void testSelectByName() {
        
    }
}
