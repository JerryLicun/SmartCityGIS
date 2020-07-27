package com.hzau.service;

import java.util.List;

import com.hzau.dao.InformDao;
import com.hzau.dto.InformDto;
import com.hzau.entity.Inform;

public class InfoService {
	InformDao infoDao =new InformDao();
	public List<InformDto> queryInformBytitle(String infotitle ,int page,int length ){
		return infoDao.queryInformBytitle(infotitle, page, length);
	}
	public int queryTotalBytitle(String infotitle){
		return infoDao.queryTotalBytitle(infotitle);
	}
	public boolean deleteInformByid(int informid){
		return infoDao.deleteInformByid(informid);
	}
	public boolean deleteInformlist(int [] informlist){
		return infoDao.deleteInformlist(informlist);
	}
	public boolean addInform(Inform inform){
		return infoDao.addInform(inform);
	}
	public List<InformDto> queryInformAll(int page, int length){
		return infoDao.queryInformAll(page, length);
	}
	public int queryInformTotalAll(){
		return infoDao.queryInformTotalAll();
	}
	public Inform queryInformByid(int inform_id){
		return infoDao.queryInformByid(inform_id);
	}
	public boolean modifyInformByid(Inform inform){
		return infoDao.modifyInformByid(inform);
	}
	public List<InformDto> queryInformByname(String infoname ,int page,int length ){
		return infoDao.queryInformByname(infoname, page, length);
	}
	public int queryTotalByname(String infoname){
		return infoDao.queryTotalByname(infoname);
	}
}
