/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 17:06:29
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-25 17:09:41
 * @FilePath: /db_sync/src/test/java/com/db/sql/utils/MybatisUtilTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.utils;

import org.junit.Test;


public class MybatisUtilTest {
    @Test
    public void testGetSqlSession() {
        System.out.println(MybatisUtil.getSqlSession()); 

    }
}
