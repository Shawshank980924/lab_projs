/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-26 01:28:35
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-26 08:55:02
 * @FilePath: /db_sync/src/test/java/com/db/sql/dao/TagFieldDaoTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.dao;

import com.db.gql.utils.GqlGen;
import com.db.gql.utils.NebulaTools;
import com.db.sql.pojo.TagField;
import com.db.sql.utils.MybatisUtil;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.net.Session;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;



public class TagFieldDaoTest {
    @Test
    public void testInsertTagField() throws Exception {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        
        Session ss =  NebulaTools.getSession();
        ResultSet res =  ss.execute(GqlGen.DescTagGen("netphy", "node"));
        List<ValueWrapper>fields = res.colValues("Field");
		List<ValueWrapper>types = res.colValues("Type");
        for(int i=0;i<types.size();i++){
            String type = types.get(i).asString();
            String field = fields.get(i).asString();
            mapper.insertTagField(new TagField(9, 13, field, type));


        }
        sqlSession.commit();
        sqlSession.close();

    }
}
