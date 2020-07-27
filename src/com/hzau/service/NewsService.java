package com.hzau.service;

import java.util.List;

import com.hzau.dao.NewsDao;
import com.hzau.dto.NewsDto;
import com.hzau.entity.News;



public class NewsService {
	private NewsDao newsDao = new NewsDao();
	public List<NewsDto> queryNewsBytitle(String title,int page,int length){
		return newsDao.queryNewsBytitle(title, page, length);
	}
	public int queryTotalBytitle(String title){
		return newsDao.queryTotalBytitle(title);
	}
	public List<NewsDto> queryNewsBynewsauthor(String keyword ,int page,int length){
		return newsDao.queryNewsBynewsauthor(keyword, page, length);
	}
	public int queryTotalBynewsauthor(String newsauthor){
		return newsDao.queryTotalBynewsauthor(newsauthor);
	}
	public List<NewsDto> queryNews(int page,int length){
		return newsDao.queryNews(page, length);
	}
	public int queryTotal(){
		return newsDao.queryTotal();
	}
	public boolean deleteNewsByid(int newsid){
		return newsDao.deleteNewsByid(newsid);
	}
	public boolean deleteNewslist(int [] newsidlist){
		return newsDao.deleteNewslist(newsidlist);
	}
	public boolean addNews(News news){
		return newsDao.addNews(news);
	}
	public News queryNewsBynewsid(int news_id){
		return newsDao.queryNewsBynewsid(news_id);
	}
	public boolean modifyNewsByid(News news){
		return newsDao.modifyNewsByid(news);
	}
}
