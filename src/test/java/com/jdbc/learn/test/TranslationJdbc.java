package com.jdbc.learn.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 事务示例
 *
 * @author xipei.yuan
 * @date 2020/5/15 12:03
 * @Description:
 */
public class TranslationJdbc {

    @Test
    public void translationgTest() {
        Connection conn = null;
        PreparedStatement pstat = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");
            conn.setAutoCommit(false);

            pstat = conn.prepareStatement("insert into user (id,name) values (789,789)");
            int count = pstat.executeUpdate();//执行sql语句1
            System.out.println(count);// 1

            pstat = conn.prepareStatement("insert into user (id,name) values (1010,1010)");
            count = pstat.executeUpdate();//执行sql语句2
            System.out.println(count);// 1

            conn.commit();//提交事务，若前面语句执行错误，不会执行事务提交，最终也就不会产生改变

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();//失败，则进行回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            //TODO 关闭连接
        }
    }
}
