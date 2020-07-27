package com.hzau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.hzau.entity.*;
import com.hzau.util.DBUtil;

public class BaseDao {
	private Connection conn = null;    //连接对象
	private Statement stmt = null;    //语句对象
	private PreparedStatement pstmt = null; //预编译语句对象 
	private ResultSet rs = null;    //结果对象
	
	
//	通过job_id查dept_id
	public int queryDeptByJob(int job_id){
		int dept_id =0;
		try {
			String sql ="select dept_id from job_info where job_id ="+job_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				dept_id = rs.getInt(1);
			}
			return dept_id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return dept_id;
	}
	
//	通过dept_id查organ_id
	public int queryOrganByDept(int dept_id){
		int organ_id =0;
		try {
			String sql ="select organ_id from dept_info where dept_id ="+dept_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				organ_id = rs.getInt(1);
			}
			return organ_id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return organ_id;
	}
	
//	直接返回organlist
	public List<Organ> queryOrganList(){
		List<Organ> organlist = new ArrayList<Organ>();
		Organ organ = null;
		try {
			String sql ="select * from organization";
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				organ=new Organ();
				organ.setOrgan_id(rs.getInt("organ_id"));
				organ.setOrgan_name(rs.getString("organ_name"));
				organlist.add(organ);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return organlist;
	}
//	通过organ_id返回deptlist
	public List<Dept> queryDeptList(int organ_id){
		List<Dept> deptlist = new ArrayList<Dept>();
		Dept dept =null;
		
		try {
			String sql ="select * from dept_info where organ_id =" +organ_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				dept=new Dept();
				dept.setDept_id(rs.getInt("dept_id"));
				dept.setDept_name(rs.getString("dept_name"));
				dept.setOrgan_id(rs.getInt("organ_id"));
				deptlist.add(dept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return deptlist;
	}
	
//	通过dept_id返回joblist
	public List<Job> queryJobList(int dept_id){
		List<Job> joblist = new ArrayList<Job>();
		Job job =null;
		
		try {
			String sql ="select * from job_info where dept_id =" +dept_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				job=new Job();
				job.setJobid(rs.getInt("job_id"));
				job.setJobname(rs.getString("job_name"));
				job.setDeptid(rs.getInt("dept_id"));
				joblist.add(job);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return joblist;
	}
}
