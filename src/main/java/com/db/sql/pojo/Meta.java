/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 16:37:00
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-27 14:14:21
 * @FilePath: /db_sync/src/main/java/com/db/sql/pojo/Meta.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * 
 */
package com.db.sql.pojo;

public class Meta {
    private int space_id;
    private int edges ;
    private int vertices;
    private String space_name;
    public Meta(){};
    public Meta(int edges, int vertices, String space_name) {

        this.edges = edges;
        this.vertices = vertices;
        this.space_name = space_name;
    }
    public int getSpace_id() {
        return space_id;
    }
    public void setSpace_id(int space_id) {
        this.space_id = space_id;
    }
    public int getEdges() {
        return edges;
    }
    public void setEdges(int edges) {
        this.edges = edges;
    }
    public int getVertices() {
        return vertices;
    }
    public void setVertices(int vertices) {
        this.vertices = vertices;
    }
    public String getSpace_name() {
        return space_name;
    }
    public void setSpace_name(String space_name) {
        this.space_name = space_name;
    }
    
    
}
