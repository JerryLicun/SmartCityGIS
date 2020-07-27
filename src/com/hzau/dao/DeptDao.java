package com.hzau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hzau.dto.DeptDto;
import com.hzau.dto.JobDto;
import com.hzau.entity.Dept;
import com.hzau.entity.Job;
import com.hzau.util.DBUtil;

public class DeptDao {
	private Connection conn = null;
	private Statement stmt =  null;
	private PreparedStatement pstmt =null;
	private ResultSet rs =null;
	BaseDao baseDao = new BaseDao();
	
	 public boolean addDept(Dept dept){
			try {
				String sql="insert into dept_info(organ_Id,dept_name) values(?,?)";
				conn=DBUtil.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, dept.getOrgan_id());
				pstmt.setString(2,dept.getDept_name());
				int rows=pstmt.executeUpdate();
				if(rows>0){
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
	  * 添加
	  */
	 public boolean addDept(int organ_id ,String dept_name){
			try {
				String sql="insert into dept_info(organ_Id,dept_name) values(?,?)";
				conn=DBUtil.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, organ_id);
				pstmt.setString(2,dept_name);
				int rows=pstmt.executeUpdate();
				if(rows>0){
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
	 
	  public  List<DeptDto> getDeptList(int page,int length){
		  int startIndex=length*(page-1);
			int pageSize=length;
		  List<DeptDto> deptList=new ArrayList<DeptDto>();
		   try {
			String sql="select t1.dept_id,t1.dept_name,t2.organ_id,t2.organ_name from "+
					  " dept_info t1,organization t2 where t1.organ_id=t2.organ_id"+
					  " limit ? , ? ";
			    conn=DBUtil.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,startIndex);
				pstmt.setInt(2, pageSize);
				rs=pstmt.executeQuery();
				while(rs.next()){
					DeptDto dept=new DeptDto();
					dept.setDept_id(rs.getInt(1));
					dept.setDept_name(rs.getString(2));
					dept.setOrgan_name(rs.getString(4));
					dept.setJoblist(baseDao.queryJobList(rs.getInt(1)));
					deptList.add(dept);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			DBUtil.closeAll(rs, null, stmt, conn);
		}  
		return deptList;
	    	 
	     }
	  public  int DeptListTotal(){
		  int total=0;
		  try {
			   String sql="select * from dept_info";
			    conn=DBUtil.getConnection();
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					total++;
				}
				return total;
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }finally{
			   DBUtil.closeAll(rs, null, stmt, conn);
		   }
		   return total;
	   }
	  //根据deptid查询单数据
	  public  Dept queryDeptList(int dept_Id){
		  Dept dept =new Dept();
		  try {
			String sql="select * from dept_info where dept_Id="+dept_Id;
			  conn=DBUtil.getConnection();
			  pstmt=conn.prepareStatement(sql);
			  rs=pstmt.executeQuery();
			  while(rs.next()){
				    dept.setDept_id(rs.getInt(1));
				    dept.setOrgan_id(rs.getInt(2));
				    dept.setDept_name(rs.getString(3));
			  }
			  return dept;
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		  }
		  return dept;
	  }
	  //修改
	  public boolean updataDept(int dept_id,int organ_id,String dept_name){
		  try {
			String sql="update dept_info set organ_Id=?,dept_name=? where dept_Id=?";
			  conn=DBUtil.getConnection();
			  pstmt=conn.prepareStatement(sql);
			  pstmt.setInt(1,organ_id);
			  pstmt.setString(2,dept_name);
			  pstmt.setInt(3, dept_id);
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
	  public boolean deletDeptlist(int [] deptlist){
		 for (int dept:deptlist){
			 boolean flag= deletDept(dept);
			 if(!flag){
				 return false;
			 }	
		 }
		  return true;
	  } 
	  /**
	   * 根据deptid删除
	   */
	  public boolean deletDept(int dept_id){
		  try {
			String sql="delete from dept_info where dept_id=?";
			  conn=DBUtil.getConnection();
			  pstmt=conn.prepareStatement(sql);
			  pstmt.setInt(1, dept_id);  
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
	   * 根据 deptname 模糊查询
	   */
	  public List<DeptDto> queryDeptListByDeptName(String DeptName,int page,int length){
		  List<DeptDto> DeptList=new ArrayList<DeptDto>();
		  int startIndex=length*(page-1);
		  int Pagesize=length;
		  try {
			String sql="select t1.dept_id,t1.dept_name,t2.organ_id,t2.organ_name from "+
					  " dept_info t1,organization t2 where t1.organ_id=t2.organ_id and"+
					  " t1.dept_name like ? limit ? , ? ";
			  conn=DBUtil.getConnection();
			  pstmt=conn.prepareStatement(sql);
			  pstmt.setString(1,"%"+DeptName+"%");
			  pstmt.setInt(2, startIndex);
			  pstmt.setInt(3, Pagesize);
			  rs=pstmt.executeQuery(); 
			  while(rs.next()){		
				  DeptDto dept=new DeptDto();
				  dept.setDept_id(rs.getInt(1));
				  dept.setDept_name(rs.getString(2));
				  dept.setOrgan_name(rs.getString(4));
				  dept.setJoblist(baseDao.queryJobList(rs.getInt(1)));
				  DeptList.add(dept);  
			  }
			  return DeptList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}
		  return DeptList;	  
	  }
	  public int queryBydeptnameTotal(String DeptName){
		  int total=0;
		  try {
				String sql="select t1.dept_id,t1.dept_name,t2.organ_id,t2.organ_name from "+
						  " dept_info t1,organization t2 where t1.organ_id=t2.organ_id and"+
						  " t1.dept_name like ? ";
				  conn=DBUtil.getConnection();
				  pstmt=conn.prepareStatement(sql);
				  pstmt.setString(1,"%"+DeptName+"%");
				  rs=pstmt.executeQuery(); 
				  while(rs.next()){		
					  total++;
				  }
				  return total;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBUtil.closeAll(rs, pstmt, null, conn);
			}
		  return total;
	  }
	  
	  /**
	   * 根据organname 模糊查询 
	   */
	  public List<DeptDto> queryDeptListByOrganName(String OrganName,int page,int length){
		  List<DeptDto> OrganList=new ArrayList<DeptDto>();
		  int startIndex=length*(page-1);
		  int Pagesize=length;
		  try {
			String sql="select t1.dept_id,t1.dept_name,t2.organ_id,t2.organ_name from "+
					  " dept_info t1,organization t2 where t1.organ_id=t2.organ_id and"+
					  " t2.organ_name like ? limit ? , ? ";
			  conn=DBUtil.getConnection();
			  pstmt=conn.prepareStatement(sql);
			  pstmt.setString(1,"%"+OrganName+"%");
			  pstmt.setInt(2, startIndex);
			  pstmt.setInt(3, Pagesize);
			  rs=pstmt.executeQuery();
			  while(rs.next()){
				  DeptDto dept=new DeptDto();
				  dept.setDept_id(rs.getInt(1));
				  dept.setDept_name(rs.getString(2));
	              dept.setOrgan_name(rs.getString(4));
	              dept.setJoblist(baseDao.queryJobList(rs.getInt(1)));
				  OrganList.add(dept);  
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	  
		  return OrganList;  
	  }
	  
	  public int queryOrganNametotal(String OrganName){
		  int total =0;
		  try {
				String sql="select t1.dept_id,t1.dept_name,t2.organ_id,t2.organ_name from "+
						  " dept_info t1,organization t2 where t1.organ_id=t2.organ_id and"+
						  " t2.organ_name like ? ";
				  conn=DBUtil.getConnection();
				  pstmt=conn.prepareStatement(sql);
				  pstmt.setString(1,"%"+OrganName+"%");
				  rs=pstmt.executeQuery();
				  while(rs.next()){
					 total++;
				  }
				  return total;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBUtil.closeAll(rs, pstmt, null, conn);
			}	  
		  return total;
	  }
}
