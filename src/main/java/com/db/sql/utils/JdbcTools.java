package com.db.sql.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcTools {
    /**
     * 1、获取数据库连接
     */
    public static  Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        InputStream inStream  = 
                JdbcTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(inStream);
        
        //1、准备连接数据库的四个字符串：user password,jdbcUrl,driverClass
        // 从配置文件中得到
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("jdbcUrl");
        String driverClass = properties.getProperty("driver");
        
        
        //2、加载驱动：Class.forName(driverClass)
        Class.forName(driverClass);
        
        //3、调用：DriverManager.getConnection(jdbcUrl,user,password)
        //3.1、获取数据库的连接
        Connection connection =
                DriverManager.getConnection(url,user,password);
        return connection;
    }
    /**
     * 2、释放数据库资源
     */
    public static void releaseDB(ResultSet resultSet,Statement statement,Connection connection){
        
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 3、更新方法（update add delete）没有 select
     * 执行 SQL 的方法
     */
    public static void update(String sql){
        System.out.println("update");
        //1.获取数据连接
        Connection connection = null;
        Statement statement = null;
        
        try {
            //2.调用Connection 对象的 【createStatement()】 方获取 Statement 对象
            connection = getConnection();
            statement = connection.createStatement();
                
            //3.准备 SQL 语句
//          sql = "update customer set name = 'cadddda' where id = 3";
            
            //4.发送 SQL 语句：调用 Statement 对象的 【executeUpdate(sql)】 方法
            System.out.println(statement.executeUpdate(sql)); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭数据库资源：由里向外关闭
            releaseDB(null, statement, connection);
        }
        // System.out.println("update");
    }
    /*
    查询方法，返回ResultSet
    */
    public static ResultSet Select(String sql) {
        System.out.println("select");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
		try {
            connection=getConnection();
            statement=connection.createStatement();
		    resultset=  statement.executeQuery(sql);
            return resultset;
        } catch (ClassNotFoundException | IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        finally{
            releaseDB(resultset, statement, connection);
            
        }
		
    }
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        Connection cnn = JdbcTools.getConnection();
        System.out.println(cnn);
    }
}
