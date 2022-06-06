/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-26 08:20:36
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-27 13:24:01
 * @FilePath: /db_sync/src/main/java/com/db/sql/pojo/Edge.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.pojo;

import lombok.Data;

@Data
public class Edge {
    private int type_id;
    private int space_id;
    private int edge_num ;
    private String type_name;
    public Edge(int space_id, int edge_num, String type_name) {
        this.space_id = space_id;
        this.edge_num = edge_num;
        this.type_name = type_name;
    }
    
    
}
