package com.hzau.dto;

public class InformDto {
	private int inform_id;
	private String infotitle;
	private String infoname;
	private String infotime;
	private boolean hot;
	private String keyword;
	public int getInform_id() {
		return inform_id;
	}
	public void setInform_id(int inform_id) {
		this.inform_id = inform_id;
	}
	public String getInfotitle() {
		return infotitle;
	}
	public void setInfotitle(String infotitle) {
		this.infotitle = infotitle;
	}
	public String getInfoname() {
		return infoname;
	}
	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}
	public String getInfotime() {
		return infotime;
	}
	public void setInfotime(String infotime) {
		this.infotime = infotime;
	}
	public boolean isHot() {
		return hot;
	}
	public void setHot(boolean hot) {
		this.hot = hot;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
