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

import com.hzau.dto.OrganDto;
import com.hzau.entity.*;
import com.hzau.util.DBUtil;


public class OrganDao {
	private Connection conn = null;
	private Statement stmt =  null;
	private PreparedStatement pstmt =null;
	private ResultSet rs =null;
	BaseDao baseDao = new BaseDao();
	
//	分页查询organlist
	public List<OrganDto> queryOrganList(int page,int length){
		List<OrganDto> organlist = new ArrayList<OrganDto>();
		OrganDto organdto = null;
		try {
			String sql ="select * from organization limit "+(page-1)*length+","+length;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				organdto=new OrganDto();
				organdto.setOrgan_id(rs.getInt("organ_id"));
				organdto.setOrgan_name(rs.getString("organ_name"));
				organdto.setDeptlist(baseDao.queryDeptList(rs.getInt("organ_id")));
				organlist.add(organdto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return organlist;
	}
//	返回total
	public int OrganlistTotal(){
		int total = -1;
		try {
			String sql ="select COUNT(*)  from organization";
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return total;
		
	}
	
//	通过组织名模糊查询organlist
	public List<OrganDto> queryOrganListByOgname(int page,int length,String organ_name){
		List<OrganDto> organlist = new ArrayList<OrganDto>();
		OrganDto organdto = null;
		try {
			String sql ="select * from organization  where organ_name like ? limit "+(page-1)*length+","+length;
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+organ_name+"%"); 
			rs=pstmt.executeQuery();
			while(rs.next()){
				organdto=new OrganDto();
				organdto.setOrgan_id(rs.getInt("organ_id"));
				organdto.setOrgan_name(rs.getString("organ_name"));
				organdto.setDeptlist(baseDao.queryDeptList(rs.getInt("organ_id")));
				organlist.add(organdto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,pstmt,null,conn);
		}
		return organlist;
	}
//	模糊total
	public int searchlistTotal(String organ_name){
		int total = -1;
		try {
			String sql ="select COUNT(*)  from organization  where organ_name like ?";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+organ_name+"%"); 
			rs=pstmt.executeQuery();
			if(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return total;
		
	}
//	通过organ_id查询单条数据
	public Organ queryOrganById(int organ_id){
		Organ organ = new Organ();
		try {
			String sql ="select * from organization where organ_id ="+organ_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				organ.setOrgan_id(rs.getInt("organ_id"));
				organ.setOrgan_name(rs.getString("organ_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,null,stmt,conn);
		}
		return organ;
	}
//	增加一条数据
	public boolean addOrgan(String organ_name){
		try {
			String sql ="INSERT INTO organization(organ_name)  values(?)";
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, organ_name);
			int rows =pstmt.executeUpdate();
			if(rows>0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,pstmt,null,conn);
		}
		return false;
	}
//	修改一条数据
	public boolean changeOrgan(int organ_id,String organ_name){
		Organ organ = new Organ();
		try {
			String sql ="update organization set organ_id=?,organ_name=? where organ_id =" +organ_id;
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, organ_id);
			pstmt.setString(2, organ_name);
			int rows =pstmt.executeUpdate();
			if(rows>0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,pstmt,null,conn);
		}
		return false;
	}
//	删除一条数据
	public boolean delOrgan(int organ_id){
		try {
			String sql ="delete from organization where organ_id =" +organ_id;
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			int res=stmt.executeUpdate(sql);
			
			if(res!=0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs,pstmt,null,conn);
		}
		return false;
	}
//	批量删除
	public boolean delOrganlist(int[] idlist){
		
		for(int organ_id:idlist){
			boolean flag =delOrgan(organ_id);
			if(!flag){
				return false;
			}
		}
		return true;
	}
}
