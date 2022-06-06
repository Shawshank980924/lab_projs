/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 22:51:31
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-26 08:55:12
 * @FilePath: /db_sync/src/test/java/com/db/sql/dao/TagDaoTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.dao;

import com.db.sql.pojo.Tag;
import com.db.sql.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


public class TagDaoTest {
    @Test
    public void testInsert() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        GraphDao mapper = sqlSession.getMapper(GraphDao.class);
        mapper.insertTag(new Tag(8,0,"reddit_node"));
        mapper.insertTag(new Tag(9,0,"node"));
        mapper.insertTag(new Tag(10,0,"node"));
        mapper.insertTag(new Tag(11,0,"node"));
        mapper.insertTag(new Tag(12,0,"node"));
        mapper.insertTag(new Tag(13,0,"node"));
        sqlSession.commit();
        sqlSession.close();

    }
}
