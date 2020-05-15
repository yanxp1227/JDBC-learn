package com.jdbc.learn.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 批处理
 *
 * @author xipei.yuan
 * @date 2020/5/15 13:16
 * @Description:
 */
public class BlukJdbc {

    @Test
    public void blukTest() throws SQLException {
        Connection conn = null;
        Statement stat = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");
            //关闭自动提交
            conn.setAutoCommit(false);

            stat = conn.createStatement();
            long start = System.currentTimeMillis();

            for (int i = 5; i < 10; i++) {
                stat.addBatch("insert into user (id,name) values ('" + i + "','batchtest000')");
            }//添加sql语句到batch中
            stat.executeBatch();//批量执行
            conn.commit();//执行成功则提交事务

            long end = System.currentTimeMillis();
            System.out.println("执行时间："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            //TODO 关闭连接
        }
    }

}
