package com.example.administrator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class LinktoMysql {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://rm-m5e16nslvj454680qno.mysql.rds.aliyuncs.com:3306/ouc_secondhand";
    private static final String user = "root";
    private static final String pwd = "woshishui123!";
    private static    Connection conn=null;
    private  static Statement stmt=null;
    private  static PreparedStatement preparedStatement=null;

    public static void linkMysql() {
        try {
            Class.forName(driver).newInstance();
            System.out.println("驱动加载成功！！！！！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url,user,pwd);
            System.out.println("连接数据库成功！！！！！！");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void Creat_Table(){
        String sql="create table if not exists users" +
                "(userId varchar(20) primary key," +
                "passWord varchar(20) not null," +
                "name varchar(20)," +
                "subject varchar(20)," +
                "phone varchar(15)," +
                "qq varchar(15)," +
                "address varchar(50))";
        String sql2="create table if not exists things"+
                "(thingId int AUTO_INCREMENT primary key,"+
                "name varchar(20) not null,"+
                "class varchar(20),"+
                "image MediumBlob,"+
                "introduce varchar(200),"+
                "price INT)";
        try{
            stmt=conn.createStatement();
            //stmt.execute(sql);
            stmt.execute(sql2);
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    public static Connection getConn() {
        return conn;
    }

    public static Statement getStmt() {
        return stmt;
    }
}

  /*  public static final String Creat_User="create table if not exists User ("
            +"userId varchar(20) primary key,"
            +"password varchar(20) not null,"
            +"name varchar(20),"
            +"subject varchar(20),"
            +"phone varchar(20),"
            +"qq varchar(20),"
            +"address varchar(20))";*/