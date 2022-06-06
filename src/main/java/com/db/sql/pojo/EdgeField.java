/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-26 08:44:18
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-27 13:25:05
 * @FilePath: /db_sync/src/main/java/com/db/sql/pojo/EdgeField.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.pojo;

import lombok.Data;

@Data
public class EdgeField {
    private int field_id;
    private int type_id;
    private int space_id;
    private String field_name;
    private String format ;
    public EdgeField(int type_id, int space_id, String field_name, String format) {
        this.type_id = type_id;
        this.space_id = space_id;
        this.field_name = field_name;
        this.format = format;
    }
    
    
}
