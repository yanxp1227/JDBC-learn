package com.jdbc.learn.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * c3p0编程式方式
 * @author xipei.yuan
 * @date 2020/5/15 14:39
 * @Description:
 */
public class JdbcC3p0ForJava {

    private static ComboPooledDataSource dataSource;

    private static Connection connection;
    @Before
    public void datasource() throws Exception {
        dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai");
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setInitialPoolSize(5);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(100);
        dataSource.setMaxStatements(50);
        dataSource.setMaxIdleTime(60);
    }

    /**
     * 从连接池中获取connect
     * @return
     */
    public static Connection getConn(){
        try {
            connection=dataSource.getConnection();
            return connection;
        }catch (SQLException e){
            throw new  RuntimeException(e);
        }

    }

    @Test
    public void getConnect(){
        Connection conn = getConn();
        //验证是否能正常再连接池中获取连接
        System.out.println(conn);
    }
}
