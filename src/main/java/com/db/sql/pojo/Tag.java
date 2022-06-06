/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 22:44:54
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-27 13:25:32
 * @FilePath: /db_sync/src/main/java/com/db/sql/pojo/Tag.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.pojo;

import lombok.Data;

@Data
public class Tag {
    private int tag_id;
    private int space_id;
    private int vertex_num ;
    private String tag_name;
    public Tag(int space_id, int vertex_num, String tag_name) {
        this.space_id = space_id;
        this.vertex_num = vertex_num;
        this.tag_name = tag_name;
    }
    
}
