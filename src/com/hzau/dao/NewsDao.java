package com.hzau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.hzau.dto.NewsDto;
import com.hzau.entity.News;
import com.hzau.util.DBUtil;

public class NewsDao {
	private Connection conn = null;
	private Statement stmt =  null;
	private PreparedStatement pstmt =null;
	private ResultSet rs =null;
	public List<NewsDto> queryNewsBytitle(String title,int page,int length){
		int starindex= length*(page-1);
		int maxsize = length;
		List<NewsDto> newsdtolist = new ArrayList<NewsDto>();
		try {
			String sql ="select news_id,newstitle ,newsauthor,keyword,createtime,hot " +
					"from news_info where newstitle like ? limit ?,?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+title+"%");
			pstmt.setInt(2, starindex);
			pstmt.setInt(3, maxsize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				NewsDto newsdto = new NewsDto();
				newsdto.setNews_id(rs.getInt(1));
				newsdto.setNewstitle(rs.getString(2));
				newsdto.setNewsauthor(rs.getString(3));
				newsdto.setKeyword(rs.getString(4));
				newsdto.setCreatetime(rs.getString(5));
				newsdto.setHot(rs.getBoolean(6));
				newsdtolist.add(newsdto);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return newsdtolist;
	} 
	/**
	 * 计算通过新闻标题模糊查找到得新闻总数
	 */
	public int queryTotalBytitle(String title){
		try {
			String sql ="select count(*) " +
					"from news_info where newstitle like ?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+title+"%");
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
	 * 通过新闻作者名查找新闻
	 */
	public List<NewsDto> queryNewsBynewsauthor(String keyword ,int page,int length){
		int starindex= length*(page-1);
		int maxsize = length;
		List<NewsDto> newsdtolist = new ArrayList<NewsDto>();
		try {
			String sql ="select news_id,newstitle ,newsauthor,keyword,createtime,hot " +
					"from news_info where newsauthor like ? limit ?,?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, starindex);
			pstmt.setInt(3, maxsize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				NewsDto newsdto = new NewsDto();
				newsdto.setNews_id(rs.getInt(1));
				newsdto.setNewstitle(rs.getString(2));
				newsdto.setNewsauthor(rs.getString(3));
				newsdto.setKeyword(rs.getString(4));
				newsdto.setCreatetime(rs.getString(5));
				newsdto.setHot(rs.getBoolean(6));
				newsdtolist.add(newsdto);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return newsdtolist;
	} 
	/**
	 * 计算通过新闻作者名模糊查找到得新闻总数
	 */
	public int queryTotalBynewsauthor(String newsauthor){
		try {
			String sql ="select count(*) " +
					"from news_info where newsauthor like ?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+newsauthor+"%");
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
	 * 查找全部新闻
	 */
	public List<NewsDto> queryNews(int page,int length){
		int starindex= length*(page-1);
		int maxsize = length;
		List<NewsDto> newsdtolist = new ArrayList<NewsDto>();
		try {
			String sql ="select news_id,newstitle ,newsauthor,keyword,createtime,hot " +
					"from news_info ORDER BY hot DESC,createtime DESC limit ?,?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, starindex);
			pstmt.setInt(2, maxsize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				NewsDto newsdto = new NewsDto();
				newsdto.setNews_id(rs.getInt(1));
				newsdto.setNewstitle(rs.getString(2));
				newsdto.setNewsauthor(rs.getString(3));
				newsdto.setKeyword(rs.getString(4));
				newsdto.setCreatetime(rs.getString(5));
				newsdto.setHot(rs.getBoolean(6));
				newsdtolist.add(newsdto);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}	
		return newsdtolist;
	} 
	/**
	 * 计算新闻总数
	 */
	public int queryTotal(){
		try {
			String sql ="select count(*) " +
					"from news_info ";
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
	 * 根据新闻id删除新闻
	 */
	public boolean deleteNewsByid(int newsid){
		try {
			String sql="delete from news_info where news_id=?";
			conn =DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newsid);
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
	 * 批量删除新闻
	 */
	public boolean deleteNewslist(int [] newsidlist){
		for(int newsid:newsidlist){
				boolean flag=deleteNewsByid(newsid);
				if(!flag){
					return false;
				}
		}
		return true;
	}
	/**
	 * 增加新闻
	 */
	public boolean addNews(News news){
		try {
			String sql ="insert into news_info " +
					"(newstitle,newsauthor," +
					"context,createtime,keyword,hot) " +
					"values(?,?,?,?,?,?)";
			conn=DBUtil.getConnection();
			pstmt=conn.prepareStatement(sql);	
			pstmt.setString(1,news.getNewstitle());
			pstmt.setString(2, news.getNewsauthor());
			pstmt.setString(3, news.getContext());
			pstmt.setString(4, news.getCreatetime());
			pstmt.setString(5, news.getKeyword());
			pstmt.setBoolean(6, news.isHot());
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
	 * 根据id查单行数据
	 */
	public News queryNewsBynewsid(int news_id){
		News news =new News();
		try {
			String sql ="select * from news_info where news_id=?";
			conn = DBUtil.getConnection();
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				news.setNews_id(rs.getInt(1));
				news.setNewstitle(rs.getString(2));
				news.setNewsauthor(rs.getString(3));
				news.setContext(rs.getString(4));	
				news.setCreatetime(rs.getString(5));
				news.setKeyword(rs.getString(6));
				news.setHot(rs.getBoolean(7));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			DBUtil.closeAll(rs, pstmt, null, conn);
		}		
		return news;
	}
	/**
	 * 通过id修改，返回boolean
	 */
	public boolean modifyNewsByid(News news){
		try {
			String sql ="update news_info set newstitle = ?,newsauthor = ? ," +
					"context = ? , createtime =?,keyword=?, hot=? where news_id=?";
			conn=DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, news.getNewstitle());
			pstmt.setString(2, news.getNewsauthor());
			pstmt.setString(3, news.getContext());
			pstmt.setString(4,news.getCreatetime());
			pstmt.setString(5, news.getKeyword());
			pstmt.setBoolean(6, news.isHot());
			pstmt.setInt(7, news.getNews_id());
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
}
