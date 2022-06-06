/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-25 20:06:55
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-05-25 22:16:00
 * @FilePath: /db_sync/src/main/java/com/db/gql/utils/NebulaTools.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.db.gql.utils;
import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;



public class NebulaTools {
    private static NebulaPool pool = null;
    private static String[] spaceList = null;
    private static Properties properties = new Properties();
    //初始化
    public static boolean init(){
        try {
            pool = new NebulaPool();
            NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
            
            InputStream inStream  = NebulaTools.class.getClassLoader().getResourceAsStream("nebula.properties");
            properties.load(inStream);
            
            String addr = properties.getProperty("addr");
            int port = Integer.valueOf(properties.getProperty("port")) ;
            String spaces = properties.getProperty("spaces");
            int maxconnSize = Integer.valueOf(properties.getProperty("maxconnSize"));
            spaceList = spaces.split(" ");

            nebulaPoolConfig.setMaxConnSize(maxconnSize);
            List<HostAddress> addresses = Arrays.asList(new HostAddress(addr, port));
            Boolean initResult = pool.init(addresses, nebulaPoolConfig);
			
            if (!initResult) {
                return false;
            }
        }catch (Exception e) {
                e.printStackTrace();
            }
            return true;
    }

    public static Session getSession() throws Exception{
        if(pool==null&&init()==false){
            throw new Exception("nebula init failed");
        }
        else{
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            return pool.getSession(user, password, false);
        }
        
    }
    public static String[] getSpaceList(){
        return spaceList;
    }
    public static void excuteGql(){
        
    }
    
    
    
    
}