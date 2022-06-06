/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 16:50:07
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-26 15:49:54
 * @FilePath: /db_sync/src/main/java/com/db/sql/dao/MetaDao.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.sql.dao;

import com.db.sql.pojo.Edge;
import com.db.sql.pojo.EdgeField;
import com.db.sql.pojo.Meta;
import com.db.sql.pojo.Tag;
import com.db.sql.pojo.TagField;
import java.util.Map;
import org.apache.ibatis.annotations.Param;



public interface GraphDao {
    int insertMeta(Meta meta);
    int insertTag(Tag tag);
    int insertTagField(TagField tagField);
    int insertEdge(Edge edge);
    int insertEdgeField(EdgeField edgeField);
    int updateMetaByName(
        @Param("space_name")String space_name,
        @Param("name")String name,
        @Param("count")Long count);
    int updateMetaByMap(Map<String,Object> map);
    int updateTagByMap(Map<String,Object> map);
    Meta selectByName(String name);
    int updateEdgeByMap(Map<String,Object> map);
    
    
    
}
