package com.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC入门实例
 *
 * @author xipei.yuan
 * @date 2020/5/14 10:51
 * @Description:
 */
public class Demo {
    public static void main(String[] args) {
        // 数据库的连接类
        Connection conn = null;
        // SQL执行器
        PreparedStatement statement = null;
        try {
            //加载特定数据库连接的驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");

            //连接数据库，(连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时！这是Connection对象管理的一个要点！)
            //真正开发中，为了提高效率，都会使用连接池来管理连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");

            // 执行器
            statement = conn.prepareStatement("insert into `user` (name) values(?)");
            statement.setString(1,"张三");  //设置参数

            int row = statement.executeUpdate();//返回影响行数
            System.out.println("影响行数："+row);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
