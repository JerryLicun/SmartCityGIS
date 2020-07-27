package com.hzau.dto;

public class NewsDto {
	private int news_id;
	private String newstitle;
	private String newsauthor;
	private String createtime;
	private String keyword;
	private boolean hot;
	public int getNews_id() {
		return news_id;
	}
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	public String getNewstitle() {
		return newstitle;
	}
	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}
	public String getNewsauthor() {
		return newsauthor;
	}
	public void setNewsauthor(String newsauthor) {
		this.newsauthor = newsauthor;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public boolean isHot() {
		return hot;
	}
	public void setHot(boolean hot) {
		this.hot = hot;
	}
}
