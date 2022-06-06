/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 17:20:09
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-25 20:35:13
 * @FilePath: /db_sync/src/test/java/com/db/sql/dao/MetaDaoTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.dao;

import com.db.sql.pojo.Meta;
import com.db.sql.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


public class MetaDaoTest {
    @Test
    public void testInsertMeta() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        int num = mapper.insertMeta(new Meta(0,0,"trans"));
        System.out.println(num);
        sqlSession.commit();
        sqlSession.close();
    }
}
