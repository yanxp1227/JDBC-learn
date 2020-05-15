package com.jdbc.learn.test;

import org.junit.Test;

import java.sql.*;

/**
 * 注入问题示例
 *
 * @author xipei.yuan
 * @date 2020/5/14 10:51
 * @Description:
 */
public class StatementInjectDemo {

    @Test
    public void statementInject() {
        // 数据库的连接类
        Connection conn = null;
        // SQL执行器
        Statement statement = null;
        // 结果集
        ResultSet resultSet = null;
        try {
            //加载特定数据库连接的驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");

            //连接数据库，(连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时！这是Connection对象管理的一个要点！)
            //真正开发中，为了提高效率，都会使用连接池来管理连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");

            // 执行器
            int id = 1;
            String name = "'or 1=1 or name ='";
            String sql = "select * from user where id = "+id +" and name = '"+name+"'";
            statement = conn.createStatement();

            resultSet = statement.executeQuery(sql);// 结果集
            //全表数据都列出来了 发生了sql注入问题
            while (resultSet.next()){
                System.out.println("id="+resultSet.getInt("id")+",name="+resultSet.getString("name"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    //连接建立后必须关闭
                    conn.close();
                }
                if (statement != null) {
                    //statement建立后必须关闭
                    statement.close();
                }
                if (resultSet != null) {
                    //连接建立后必须关闭
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void preparedStatementInject(){
        // 数据库的连接类
        Connection conn = null;
        // SQL执行器
        PreparedStatement statement = null;
        // 结果集
        ResultSet resultSet = null;
        try {
            //加载特定数据库连接的驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");

            //连接数据库，(连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时！这是Connection对象管理的一个要点！)
            //真正开发中，为了提高效率，都会使用连接池来管理连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");

            // 执行器
            int id = 1;
//            String name = "'or 1=1 or name ='";
            String name = "zzzz";
            String sql = "select * from user where id = ?  and name = ? ";
            statement = conn.prepareStatement(sql);

            // 设置参数 索引从 1 开始
            statement.setInt(1,id);
            statement.setString(2,name);

            resultSet = statement.executeQuery();// 结果集
            // 返回 单条 记录
            while (resultSet.next()){
                System.out.println("id="+resultSet.getInt("id")+",name="+resultSet.getString("name"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    //连接建立后必须关闭
                    conn.close();
                }
                if (statement != null) {
                    //statement建立后必须关闭
                    statement.close();
                }
                if (resultSet != null) {
                    //连接建立后必须关闭
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
