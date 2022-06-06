/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 22:16:55
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-25 23:44:44
 * @FilePath: /db_sync/src/test/java/com/db/gql/utils/NebulaToolsTest.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.gql.utils;

import com.db.sql.utils.JdbcTools;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import com.vesoft.nebula.client.graph.net.Session;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import org.junit.Test;




public class NebulaToolsTest {
    @Test
    public void testExcuteGql() throws Exception {
        Session ss =  NebulaTools.getSession();
        ResultSet res =  ss.execute(GqlGen.DescTagGen("trans", "`balance`"));
        List<ValueWrapper>fields = res.colValues("Field");
		List<ValueWrapper>types = res.colValues("Type");
        for(int i=0;i<types.size();i++){
            String type = types.get(i).asString();
            String field = fields.get(i).asString();
            Connection cnn =  JdbcTools.getConnection();
            Statement statm =  cnn.createStatement();
            System.out.println(statm.execute("insert into vertex_field(`field_name`,`format`,`tag_id`,`space_id`)values('"+field+"','"+type+"',3,7)")); 
            
        }
        

    }
}
