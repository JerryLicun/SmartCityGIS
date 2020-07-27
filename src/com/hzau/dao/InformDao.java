package com.hzau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hzau.dto.InformDto;
import com.hzau.entity.Inform;
import com.hzau.util.DBUtil;

public class InformDao {
	
	private Connection conn = null;
	private Statement stmt =  null;
	private PreparedStatement pstmt =null;
	private ResultSet rs =null;
	/**
	 * 通过公告标题模糊查询公告并分页
	 */

	/**
	 * 通过公告标题模糊查询公告并分页
	 */
	public List<InformDto> queryInformBytitle(String infotitle ,int page,int length ){
		List<InformDto> informlist = new ArrayList<InformDto>();
		int starindex= length*(page-1);
		int maxsize = length;
		try {
			String sql ="select inform_id,infotitle,infotime,infoname,keyword,hot "+
					"from inform_info where infotitle like ? limit ?,?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+infotitle+"%");
			pstmt.setInt(2, starindex);
			pstmt.setInt(3, maxsize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InformDto informdto = new InformDto();
				informdto.setInform_id(rs.getInt(1));
				informdto.setInfotitle(rs.getString(2));
				informdto.setInfoname(rs.getString(4));
				informdto.setInfotime(rs.getString(3));
				informdto.setKeyword(rs.getString(5));
				informdto.setHot(rs.getBoolean(6));
				informlist.add(informdto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return informlist;
	}
	/**
	 * 计算通过公告标题模糊查询到得公告数量
	 */
	public int queryTotalBytitle(String infotitle){
		try {
			String sql ="select count(*) " +
					"from inform_info where infotitle like ?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+infotitle+"%");
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return 0;
	} 
	/**
	 * 通过id删除公告
	 */
	public boolean deleteInformByid(int informid){
		try {
			String sql="delete from inform_info where inform_id=?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, informid);
			int rows = pstmt.executeUpdate();
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
	public boolean deleteInformlist(int [] informlist){
		for(int informid:informlist){
				boolean flag=deleteInformByid(informid);
				if(!flag){
					return false;
				}
		}
		return true;
	}
	/**
	 * 添加公告
	 */
	public boolean addInform(Inform inform){
		try {
			String sql ="insert into inform_info " +
					"(infotitle,infotime,infoname," +
					"infocontext,keyword,hot) " +
					"values(?,?,?,?,?,?)";
			conn=DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
		
			pstmt.setString(1,inform.getInfotitle());
			pstmt.setString(3, inform.getInfoname());
			pstmt.setString(4, inform.getInfocontext());
			pstmt.setString(2, inform.getInfotime());
			pstmt.setString(5, inform.getKeyword());
			pstmt.setBoolean(6, inform.isHot());
			int rows =pstmt.executeUpdate();
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
	 * 全部查询
	 */
	public List<InformDto> queryInformAll(int page, int length){
		List<InformDto> informlist = new ArrayList<InformDto>();
		int starindex= length*(page-1);
		int maxsize = length;
		try {
			String sql ="select inform_id,infotitle,infotime,infoname,keyword,hot "+
					"from inform_info ORDER BY hot DESC,infotime DESC limit ?,?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, starindex);
			pstmt.setInt(2, maxsize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InformDto informdto = new InformDto();
				informdto.setInform_id(rs.getInt(1));
				informdto.setInfotitle(rs.getString(2));
				informdto.setInfoname(rs.getString(4));
				informdto.setInfotime(rs.getString(3));
				informdto.setKeyword(rs.getString(5));
				informdto.setHot(rs.getBoolean(6));
				informlist.add(informdto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return informlist;
	}
	
	
	/**
	 * 全部查询数量total
	 */
	
	public int queryInformTotalAll(){
		try {
			String sql ="select count(*) " +
					"from inform_info";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return 0;
	} 
	
	
	/**
	 * .通过id查单行，返回news实体
	 */
	public Inform queryInformByid(int inform_id){
		Inform inform =new Inform();
		try {
			String sql ="select * from Inform_info where inform_id=?";
			conn = DBUtil.getConnection();
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, inform_id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				inform.setInform_id(rs.getInt(1));
				inform.setInfotitle(rs.getString(2));
				inform.setInfoname(rs.getString(3));
				inform.setInfocontext(rs.getString(4));
				inform.setInfotime(rs.getString(5));
				inform.setKeyword(rs.getString(6));
				inform.setHot(rs.getBoolean(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}		
		return inform;
	}
	
	
	/**
	 * 通过id，修改单行，接受news实体，返回bool
	 */
	public boolean modifyInformByid(Inform inform){
		try {
			String sql ="update inform_info set infotitle = ?,infoname = ? ," +
					"infocontext = ? , infotime =?,keyword=?, hot=? where inform_id=?";
			conn=DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inform.getInfotitle());
			pstmt.setString(2, inform.getInfoname());
			pstmt.setString(3, inform.getInfocontext());
			pstmt.setString(4,inform.getInfotime());
			pstmt.setString(5, inform.getKeyword());
			pstmt.setBoolean(6, inform.isHot());
			pstmt.setInt(7, inform.getInform_id());
			int rows =pstmt.executeUpdate();
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
	
	
	
	
	/*
	 * 通过名字模糊查
	 */
	public List<InformDto> queryInformByname(String infoname ,int page,int length ){
		List<InformDto> informlist = new ArrayList<InformDto>();
		int starindex= length*(page-1);
		int maxsize = length;
		try {
			String sql ="select inform_id,infotitle,infotime,infoname,keyword,hot "+
					"from inform_info where infoname like ? limit ?,?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+infoname+"%");
			pstmt.setInt(2, starindex);
			pstmt.setInt(3, maxsize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				InformDto informdto = new InformDto();
				informdto.setInform_id(rs.getInt(1));
				informdto.setInfotitle(rs.getString(2));
				informdto.setInfoname(rs.getString(4));
				informdto.setInfotime(rs.getString(3));
				informdto.setKeyword(rs.getString(5));
				informdto.setHot(rs.getBoolean(6));
				informlist.add(informdto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return informlist;
	}
	/**
	 * 计算通过公告名字模糊查询到得公告数量
	 */
	public int queryTotalByname(String infoname){
		try {
			String sql ="select count(*) " +
					"from inform_info where infoname like ?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+infoname+"%");
			rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return 0;
	} 
}
