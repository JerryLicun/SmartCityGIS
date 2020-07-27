package com.hzau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hzau.dto.JobDto;
import com.hzau.entity.Job;
import com.hzau.util.DBUtil;
public class JobDao {
	private Connection conn = null;    //连接对象
	private Statement stmt = null;    //语句对象
	private PreparedStatement pstmt = null; //预编译语句对象 
	private ResultSet rs = null;    //结果对象
	
	public boolean addJob(int job_id,int dept_id,String job_name){
		try {
			String sql ="insert into job_info(dept_id,job_name) values(?,?)";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,dept_id);
			pstmt.setString(2,job_name);
			
			int rows =pstmt.executeUpdate();
			
			if(rows>0){
				return true;
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(null,null,pstmt,conn);
		}
		return false;
		
	}
	/**
	 * 通过job_id分页查询所有岗位
	 * 
	 * @param page
	 * @param length
	 * @return
	 */
	public List<JobDto> queryJobAll(int page, int length) {
		List<JobDto> jobList = new ArrayList<JobDto>();
		int starIndex = length*(page-1);
		int limitData = length;
		try {
			String sql = " select t1.job_id,t1.job_name,t2.dept_name,t3.organ_name "
					+ "from job_info t1,dept_info t2,organization t3 "
					+ "where t1.dept_id=t2.dept_id and t2.organ_id=t3.organ_id limit ?,?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, starIndex);
			pstmt.setInt(2, limitData);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JobDto j = new JobDto();
				j.setJob_id(rs.getInt(1));
				j.setJob_name(rs.getString(2));
				j.setDept_name(rs.getString(3));
				j.setOrgan_name(rs.getString(4));

				jobList.add(j);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}

		return jobList;
	}

	/**
	 * 返回查到条数总量total
	 * 
	 * @return
	 */
	public int queryJobTotal() {

		int total = 0;
		try {
			String sql = " select job_id from job_info";
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
	 * 删除job_id
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletjob(int job_id) {
		try {		
			String sql = "delete from job_info where job_id=" + job_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			int d = stmt.executeUpdate(sql);
			if (d >= 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(null, null, stmt, conn);
		}
		return false;

	}
	
	/**
	 * job_id批量删除
	 */
	public boolean deletjobList(int[] job_id){
	
		for(int jobid:job_id){
			boolean flag =deletjob(jobid);
			if(!flag){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 通过传入job_id,job_name,dept_id新建一条数据
	 */
	public boolean addJob_id(int dept_id,String job_name){
		try {
			String sql="insert into job_info(dept_id,job_name)values(?,?)";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, job_name);
			pstmt.setInt(1, dept_id);
			int d=pstmt.executeUpdate();
			if(d>0){
				return true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(null, pstmt, null, conn);
		}
		return false;
	}
	
	/**
	 * job_id 查询单条数据，返回job_info表单行数据
	 */
	public Job queryJob_Id(int job_id){
		Job job=new Job();
		try {
			String sql="select * from job_info where job_id=?";
			conn=DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, job_id);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				job.setJobid(rs.getInt(1));
				job.setDeptid(rs.getInt(2));
				job.setJobname(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return job;
	}
	
	
	/**
	 * 改，同上传入，并通过job_id修改某条数据
	 */
	public boolean upJob(int job_id,int dept_id,String job_name){
		try {
			String sql="update job_info set dept_id=?,job_name=?  where job_id=?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dept_id);
			pstmt.setString(2, job_name);
			pstmt.setInt(3, job_id);
			int d=pstmt.executeUpdate();
			if(d>0){
				return true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(null, null, stmt, conn);
		}
		return false;
	}
	
	
	/**
	 * 模糊查询岗位并返回list 和total
	 */
	
	
	/**
	 * 岗位名称分页,模糊查询岗位
	 * @param jobname
	 * @param page
	 * @param length
	 * @return
	 */
	public List<JobDto> queryJobListByJobName(String jobname,int page,int length){
		List<JobDto>JobList=new ArrayList<JobDto>();
		int starIndex = length*(page-1);
		int limitData = length;
try {
			
			String sql = "select t1.job_id ,t1.job_name,t2.dept_name,t3.organ_name from " +
					"job_info t1,dept_info t2,organization t3 " +
					"where t1.dept_id=t2.dept_id and t2.organ_id=t3.organ_id " +
					"and t1.job_name like ? limit ?,?";						
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+jobname+"%");
			pstmt.setInt(2,starIndex);
			pstmt.setInt(3,limitData);
			rs=pstmt.executeQuery();
			while(rs.next()){
				JobDto j = new JobDto();
				j.setJob_id(rs.getInt(1));
				j.setJob_name(rs.getString(2));
				j.setDept_name(rs.getString(3));
				j.setOrgan_name(rs.getString(4));
				// 添加集合
				JobList.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return JobList;
	}
	
	//模糊job_name的total
	public int queryJobNameTotal(String job_name){
		int total = 0;
		try {
			String sql = " select t1.job_name,t2.dept_name,t3.organ_name from " +
					"job_info t1,dept_info t2,organization t3 " +
					"where t1.dept_id=t2.dept_id and t2.organ_id=t3.organ_id " +
					"and t1.job_name like ?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+job_name+"%");
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
	 * 部门名称分页,模糊查询岗位
	 */
	
	public List<JobDto>queryJobListByDeptName(String dept_name,int page,int length){
		List<JobDto>JobList=new ArrayList<JobDto>();
		int starIndex = length*(page-1);
		int limitData = length;
try {
			
			String sql = "select t1.job_id ,t1.job_name,t2.dept_name,t3.organ_name from " +
					"job_info t1,dept_info t2,organization t3 " +
					"where t1.dept_id=t2.dept_id and t2.organ_id=t3.organ_id " +
					"and t2.dept_name like ? limit ?,?";						
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+dept_name+"%");
			pstmt.setInt(2,starIndex);
			pstmt.setInt(3,limitData);
			rs=pstmt.executeQuery();
			while(rs.next()){
				JobDto j = new JobDto();
				j.setJob_id(rs.getInt(1));
				j.setJob_name(rs.getString(2));
				j.setDept_name(rs.getString(3));
				j.setOrgan_name(rs.getString(4));
				// 添加集合
				JobList.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return JobList;
	}
	
	//模糊dept_name的total
	
	public int queryDeptNameTotal(String dept_name){
		int total = 0;
		try {
			String sql = " select t1.job_name,t2.dept_name,t3.organ_name from " +
					"job_info t1,dept_info t2,organization t3 " +
					"where t1.dept_id=t2.dept_id and t2.organ_id=t3.organ_id " +
					"and t2.dept_name like ?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+dept_name+"%");
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
}
