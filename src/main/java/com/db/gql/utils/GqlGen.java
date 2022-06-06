/*
 * @Author: your name
 * @Date: 2022-04-25 09:14:11
 * @LastEditTime: 2022-05-26 08:51:01
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /datapipe/src/main/java/com/datapipe/Util.java
 */

package com.db.gql.utils;



public class GqlGen {
       
       public static String JobSubmitGen(String space){
              return  "use "+space+";"+"submit job stats;";
       }
       public static String ShowStatsGen(String space){
              return  "use "+space+";"+"show stats";
       }
       public static String DescTagGen(String space ,String tag){
              return "use "+space+";"+"desc tag "+tag;
       }
       public static String DescEdgeGen(String space ,String edge){
              return "use "+space+";"+"desc edge "+edge;
       }



    
}
