package com.hzau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hzau.dto.PersonDto;
import com.hzau.entity.Person;
import com.hzau.util.DBUtil;

public class UserDao {
	private Connection conn = null;    //连接对象
	private Statement stmt = null;    //语句对象
	private PreparedStatement pstmt = null; //预编译语句对象 
	private ResultSet rs = null;    //结果对象
	
//	验证用户名
	public boolean checkUsername(String username){
		try {
			String sql ="select * from person_info where username = ?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs= pstmt.executeQuery();
			if (rs.next()){
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
	
	public Person querPersonByUsername(String username) {

		Person person = null;
		try {
			String sql = "select * from person_info where username=?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				person = new Person();
				person.setPersonid(rs.getInt(1));
				person.setJobid(rs.getInt(2));
				person.setUsername(rs.getString(3));
				person.setPassword(rs.getString(4));
				person.setRealname(rs.getString(5));
				person.setGender(rs.getString(6));
				person.setTelephone(rs.getString(7));
				person.setEmail(rs.getString(8));
				person.setIsadmin(rs.getBoolean(9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, pstmt, null, conn);
		}

		return person;
	}

	/**
	 * 添加人员
	 * @param p
	 * @return
	 */
	public boolean addPerson(Person p) {
		// TODO Auto-generated method stub
		try {
			String sql="insert into person_info(job_id,username," +
					"password,realname,gender,telephone,email,isadmin) values(?,?,?,?,?,?,?,?)";
			conn=DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, p.getJobid());
			pstmt.setString(2, p.getUsername());
			pstmt.setString(3, p.getPassword());
			pstmt.setString(4, p.getRealname());
			pstmt.setString(5, p.getGender());
			pstmt.setString(6, p.getTelephone());
			pstmt.setString(7, p.getEmail());
			pstmt.setBoolean(8, p.isIsadmin());
			//执行插入SQL
			int rows=pstmt.executeUpdate();
			if(rows>=0){
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
	 * 批量删除
	 */

	public boolean deletePersonlist(int []personidlist){	
		for (int personid:personidlist){
			boolean flag=deletePersonByPersonId(personid);
			if(!flag){
				return false;
			}
		}
	return true;
	}
	/**
	 * 根据personid删除单行数据
	 */
	
	public boolean deletePersonByPersonId(int person_id){
		try {
			String sql ="delete from person_info where person_id = ?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, person_id);
			int rows = pstmt.executeUpdate();
			if (rows >= 0){
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
	/*
	 * 通过person_id 查找数据
	 * 
	 */
	
	public Person queryPersonByPersonId(int personid){
		Person person = null;
		try {
			String sql ="select * from person_info where person_id=?";
			conn = DBUtil.getConnection();
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, personid);
			rs =pstmt.executeQuery();
			if(rs.next()){
				person=new Person();
				person.setPersonid(rs.getInt(1));
				person.setJobid(rs.getInt(2));
				person.setUsername(rs.getString(3));
				person.setPassword(rs.getString(4));
				person.setRealname(rs.getString(5));
				person.setGender(rs.getString(6));
				person.setTelephone(rs.getString(7));
				person.setEmail(rs.getString(8));
				person.setIsadmin(rs.getBoolean(9));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return person;
	}
	/*
	 * 通过person_id 查找并更新数据
	 */
	
	public boolean modifyPersonByPersonId(Person person , int personid){
		try {
			String sql="update person_info set person_id = ? ,job_id=?,username=? , "+
   "password =? ,realname=? ,gender =?,telephone = ?,email=?,isadmin=? where person_id=?";
			conn =DBUtil.getConnection();
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, person.getPersonid());
			pstmt.setInt(2, person.getJobid());
			pstmt.setString(3, person.getUsername());
			pstmt.setString(4, person.getPassword());
			pstmt.setString(5, person.getRealname());
			pstmt.setString(6, person.getGender());
			pstmt.setString(7, person.getTelephone());
			pstmt.setString(8, person.getEmail());
			pstmt.setBoolean(9, person.isIsadmin());
			pstmt.setInt(10, personid);
			int rows =pstmt.executeUpdate();
			if (rows >0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * 根据用户名模糊查询分页
	 */
	public List<PersonDto> queryPersonListByUserName(String username,int page,int length) {
		List<PersonDto> personList = new ArrayList<PersonDto>();
		int starIndex = length*(page-1);
		int limitData = length;
		try {
			
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"+
					"concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,t1.telephone,t1.email,t1.isadmin "+
					"from person_info t1,organization t2,dept_info t3,job_info t4 " +
					"where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id "+
					"and t3.Organ_Id=t2.Organ_Id and t1.username like ? limit ?,?";		
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+username+"%");
			pstmt.setInt(2,starIndex);
			pstmt.setInt(3,limitData);
			rs=pstmt.executeQuery();
			while(rs.next()){
				PersonDto pd = new PersonDto();
				pd.setPersonid(rs.getInt(1));
				pd.setUsername(rs.getString(2));
				pd.setRelname(rs.getString(3));
				pd.setGender(rs.getString(4));
				pd.setJobDescr(rs.getString(5));
				pd.setTelephone(rs.getString(6));
				pd.setEmail(rs.getString(7));
				pd.setIsadmin(rs.getBoolean(8) ? "是" : "否");
				// 添加集合
				personList.add(pd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		
		return personList;
	}
	
	/**
	 * 根据用户名模糊查找的记录总数
	 */
	public int  queryTotalByUsername(String username){
		List<PersonDto> personList = new ArrayList<PersonDto>();
		int count =0;
		try {
			
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"+
					"concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,t1.telephone,t1.email,t1.isadmin "+
					"from person_info t1,organization t2,dept_info t3,job_info t4 " +
					"where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id "+
					"and t3.Organ_Id=t2.Organ_Id and t1.username like ?";		
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+username+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return count;
	}
	
	
	/**
	 * 根据真实姓名模糊查询 分页
	 */
	public List<PersonDto> queryPersonListByRealName(String realname ,int page,int length) {
		List<PersonDto> personList = new ArrayList<PersonDto>();
		int starIndex = length*(page-1);
		int limitData = length;
		try {
			
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"+
					"concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,t1.telephone,t1.email,t1.isadmin "+
					"from person_info t1,organization t2,dept_info t3,job_info t4 " +
					"where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id "+
					"and t3.Organ_Id=t2.Organ_Id and t1.realname like ? limit ?,?";		
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+realname+"%");
			pstmt.setInt(2,starIndex);
			pstmt.setInt(3,limitData);
			rs=pstmt.executeQuery();
			while(rs.next()){
				PersonDto pd = new PersonDto();
				pd.setPersonid(rs.getInt(1));
				pd.setUsername(rs.getString(2));
				pd.setRelname(rs.getString(3));
				pd.setGender(rs.getString(4));
				pd.setJobDescr(rs.getString(5));
				pd.setTelephone(rs.getString(6));
				pd.setEmail(rs.getString(7));
				pd.setIsadmin(rs.getBoolean(8) ? "是" : "否");
				// 添加集合
				personList.add(pd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		
		return personList;
}
	/**
	 * 根据真实姓名模糊查找的记录总数
	 */
	public int  queryTotalByRealname(String realname){
		List<PersonDto> personList = new ArrayList<PersonDto>();
		int count =0;
		try {
			
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"+
					"concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,t1.telephone,t1.email,t1.isadmin "+
					"from person_info t1,organization t2,dept_info t3,job_info t4 " +
					"where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id "+
					"and t3.Organ_Id=t2.Organ_Id and t1.realname like ?";		
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+realname+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				count ++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return count;
	}
	
	
	/**
	 * 根据岗位名称模糊查询 分页
	 */
	public List<PersonDto> queryPersonListByJobName(String job_name ,int page,int length) {
		
		List<PersonDto> personList = new ArrayList<PersonDto>();
		int starIndex = length*(page-1);
		int limitData = length;
		try {
			
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"+
					"concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,t1.telephone,t1.email,t1.isadmin "+
					"from person_info t1,organization t2,dept_info t3,job_info t4 " +
					"where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id "+
					"and t3.Organ_Id=t2.Organ_Id and concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name) like ? limit ?,?";		
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+job_name+"%");
			pstmt.setInt(2,starIndex);
			pstmt.setInt(3,limitData);
			rs=pstmt.executeQuery();
			while(rs.next()){
				PersonDto pd = new PersonDto();
				pd.setPersonid(rs.getInt(1));
				pd.setUsername(rs.getString(2));
				pd.setRelname(rs.getString(3));
				pd.setGender(rs.getString(4));
				pd.setJobDescr(rs.getString(5));
				pd.setTelephone(rs.getString(6));
				pd.setEmail(rs.getString(7));
				pd.setIsadmin(rs.getBoolean(8) ? "是" : "否");
				// 添加集合
				personList.add(pd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		
		return personList;
}
	/**
	 * 根据岗位名模糊查找的记录总数
	 */
	public int  queryTotalByjobname(String job_name){
		List<PersonDto> personList = new ArrayList<PersonDto>();
		int count =0;
		try {
			
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"+
					"concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,t1.telephone,t1.email,t1.isadmin "+
					"from person_info t1,organization t2,dept_info t3,job_info t4 " +
					"where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id "+
					"and t3.Organ_Id=t2.Organ_Id and concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name) like ?";		
			conn= DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+job_name+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				count ++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return count;
	}
	
	
	/**
	 * 分页查询 ，page为当前页，length为每页条数
	 * 
	 */
	public List<PersonDto> queryPersonDtoList(int page ,int length) {
		int starIndex = length*(page-1);
		int limitData = length;
		List<PersonDto> personList = new ArrayList<PersonDto>();
		try {
			String sql = "select t1.person_id personid,t1.username,t1.realname,t1.gender,"
					+ "concat(t2.Organ_Name,t3.Dept_Name,t4.Job_Name)jobDescr,"
					+ "t1.telephone,t1.email,t1.isadmin "
					+ "from person_info t1,organization t2,dept_info t3,job_info t4 "
					+ "where t1.job_id=t4.job_id and t4.dept_id=t3.dept_id and "
					+ "t3.Organ_Id=t2.Organ_Id limit ?,?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,starIndex);
			pstmt.setInt(2,limitData);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PersonDto pd = new PersonDto();
				pd.setPersonid(rs.getInt(1));
				pd.setUsername(rs.getString(2));
				pd.setRelname(rs.getString(3));
				pd.setGender(rs.getString(4));
				pd.setJobDescr(rs.getString(5));
				pd.setTelephone(rs.getString(6));
				pd.setEmail(rs.getString(7));
				pd.setIsadmin(rs.getBoolean(8) ? "是" : "否");
				// 添加集合
				personList.add(pd);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, pstmt,null, conn);
		}
		return personList;
	}
	
	/**
	 * 返回分页查到的总条数
	 */

	public int queryTotal() {
		// TODO Auto-generated method stub
		int count =0;
		try {
			String sql="select count(*) from person_info";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		return count;
	}
	
}
