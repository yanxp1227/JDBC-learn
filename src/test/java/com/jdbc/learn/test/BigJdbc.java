package com.jdbc.learn.test;

import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * 大文本数据处理
 *
 * @author xipei.yuan
 * @date 2020/5/15 13:30
 * @Description:
 */
public class BigJdbc {

    @Test
    public void clobTest() throws Exception {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        Reader r = null;//记得关闭try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");

//        插入数据 1
        String sqlinsert = "insert into user (clob_,id) values (?,?)";
        pstat = conn.prepareStatement(sqlinsert);
        pstat.setClob(1, new FileReader(new File("E:/test.txt")));//使用I/O流插入数据
        pstat.setInt(2, 1001);
        pstat.executeUpdate();
//        插入数据 2
        pstat.setClob(1, new BufferedReader(new InputStreamReader(new ByteArrayInputStream("ababababab".getBytes()))));
        pstat.setInt(2, 1002);
        pstat.executeUpdate();

        pstat = conn.prepareStatement("select * from user where id = 1001");
        rs = pstat.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            Clob text_ = rs.getClob("clob_");//这里是根据字段名获取数据，也可以通过字段排序：Clob text_ = rs.getClob(1);

            //以流的方式读取数据
            r = text_.getCharacterStream();
            int temp = 0;
            while ((temp = r.read()) != -1) {
                System.out.print((char) temp);
            }
            System.out.print("\n");

        }
    }

    @Test
    public void blobTest() throws Exception {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os = null;
        // 上面所有记得关闭
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=Asia/Shanghai", "root", "123456");

            //插入数据
            String sqlinsert = "insert into user (blob_,id) values (?,?)";
            pstat = conn.prepareStatement(sqlinsert);
            pstat.setBlob(1, new FileInputStream("e:/test.txt"));//采用字节流读取数据
            pstat.setInt(2, 1005);
            pstat.executeUpdate();

            //查询结果
            pstat = conn.prepareStatement("select * from user where id = 1005");
            rs = pstat.executeQuery();
            while (rs.next()) {
                Blob blob_ = rs.getBlob("blob_");
                is = blob_.getBinaryStream();
                os = new FileOutputStream("e:/newTest.txt");//通过字节流输出一个新的文件
                int temp = 0;
                while ((temp = is.read()) != -1) {
                    os.write(temp);// 文件多出了一个新文件 newTest.txt
                }
            }
        } finally {
            //TODO 关闭连接
        }
    }
}
