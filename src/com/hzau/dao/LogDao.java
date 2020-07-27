package com.hzau.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hzau.entity.Log;
import com.hzau.util.DBUtil;

public class LogDao {
	protected Connection conn = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
	protected PreparedStatement pstmt = null;

	/**
	 * 通过log_id分页查询所有岗位
	 * 
	 * @param page
	 * @param length
	 * @return
	 */
	public List<Log> queryLogAll(int page, int length) {
		List<Log> logList = new ArrayList<Log>();
		int starIndex = length * (page - 1);
		int limitData = length;
		try {
			String sql = "select * from logs limit ?,?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, starIndex);
			pstmt.setInt(2, limitData);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Log l = new Log();
				l.setLogid(rs.getString(1));
				l.setUsername(rs.getString(2));
				l.setIp(rs.getString(3));
				l.setClassfn(rs.getString(4));
				l.setMethod(rs.getString(5));
				l.setCreatetime(rs.getString(6));
				l.setLoglevel(rs.getString(7));
				l.setMsg(rs.getString(8));
				logList.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}

		return logList;

	}

	/**
	 * 根据用户名模糊查询
	 */
	public List<Log> queryLogByUserName(String username, int page, int length) {
		List<Log> logList = new ArrayList<Log>();
		int starIndex = length * (page - 1);
		int limitData = length;
		try {
			String sql = " select * from logs where username like ? limit ?,?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + username + "%");
			pstmt.setInt(2, starIndex);
			pstmt.setInt(3, limitData);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Log l = new Log();
				l.setLogid(rs.getString(1));
				l.setUsername(rs.getString(2));
				l.setIp(rs.getString(3));
				l.setClassfn(rs.getString(4));
				l.setMethod(rs.getString(5));
				l.setCreatetime(rs.getString(6));
				l.setLoglevel(rs.getString(7));
				l.setMsg(rs.getString(8));
				logList.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return logList;
	}
	
	
	/**
	 * 根据msg模糊查询
	 */
	public List<Log> queryLogByMsg(String msg, int page, int length){
		List<Log> logList = new ArrayList<Log>();
		int starIndex = length * (page - 1);
		int limitData = length;
		try {
			String sql = " select * from logs where msg like ? limit ?,?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + msg + "%");
			pstmt.setInt(2, starIndex);
			pstmt.setInt(3, limitData);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Log l = new Log();
				l.setLogid(rs.getString(1));
				l.setUsername(rs.getString(2));
				l.setIp(rs.getString(3));
				l.setClassfn(rs.getString(4));
				l.setMethod(rs.getString(5));
				l.setCreatetime(rs.getString(6));
				l.setLoglevel(rs.getString(7));
				l.setMsg(rs.getString(8));
				logList.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return logList;
	}
	

	/**
	 * log_id分页查询返回查到条数总量total
	 */
	public int queryLogTotal() {
		int total = 0;
		try {
			String sql = "select * from logs ";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				total = total + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}

		return total;
	}

	/**
	 * 用户名模糊查询返回条数总量total
	 */
	public int queryLogByUserNameTotal(String username) {
		int total = 0;
		try {
			String sql = " select * from logs where username like ?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+username+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				total = total + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return total;
	}
	
	
	/**
	 * Msg模糊查询返回条数总量total
	 */
	
	public int queryLogByMsgTotal(String msg) {
		int total = 0;
		try {
			String sql = " select * from logs where msg like ?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+msg+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				total = total + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return total;
	}
	
	
	/**
	 * log_id删除
	 */
	
	public boolean deletlog(int logid) {
		try {
			String sql = "delete from logs where logid=" + logid;
			
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			int d = stmt.executeUpdate(sql);
			if (d == 0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, null, stmt, conn);
		}
		return true;
		}
	
	/**
	 * log_id批量是删除
	 */
	
	public boolean deletlogList(int[]logid){
		
		for(int log_id:logid){
			boolean flag;
			flag=deletlog(log_id);
			if(!flag){
				return false;
			}
		}
		return true;
	}
}
