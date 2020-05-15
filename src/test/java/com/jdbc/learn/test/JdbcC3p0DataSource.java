package com.jdbc.learn.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * c3p0配置方式
 * @author xipei.yuan
 * @date 2020/5/15 14:39
 * @Description:
 */
public class JdbcC3p0DataSource {

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    private static Connection connection;

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
