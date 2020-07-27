package com.hzau.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBUtil {
private static Connection conn = null;
	
	/**
	 * 获取数据库连接对象
	 * @return Connection
	 */
	public static Connection getConnection(){
		try {
			//载入驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("没有找到驱动类....");
		}
		
		try {
			//获取连接对象
			//URL统一资源定位符  主协议:子协议://服务器地址:端口/数据库名...;
			String url = "jdbc:mysql://localhost:3306/wugis?useUnicode=true&characterEncoding=UTF8";
			conn = DriverManager.getConnection(url, "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库服务器异常,连接失败!");
		}
		return conn;
	}
	
	/**
	 * 释放内存资源
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs,PreparedStatement pstmt,Statement stmt,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			
			if(pstmt!=null){
				pstmt.close();
			}
			
			if(stmt!=null){
				stmt.close();
			}
			
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
