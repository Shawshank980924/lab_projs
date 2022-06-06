/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 15:51:38
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-25 21:01:43
 * @FilePath: /db_sync/src/test/java/com/db_sync/AppTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db_sync;

import static org.junit.Assert.assertTrue;

import com.db.sql.dao.GraphDao;
import com.db.sql.pojo.Meta;
import com.db.sql.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void testInsertMetar() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        mapper.insertMeta(new Meta(0,0,"reddit"));
        mapper.insertMeta(new Meta(0,0,"DBLPV13"));
        mapper.insertMeta(new Meta(0,0,"nethept"));
        mapper.insertMeta(new Meta(0,0,"wiki"));
        mapper.insertMeta(new Meta(0,0,"demo_graph"));
        mapper.insertMeta(new Meta(0,0,"netphy"));
        sqlSession.commit();
        sqlSession.close();
    }
}
