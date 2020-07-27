package com.hzau.service;

import java.util.List;

import com.hzau.dao.*;
import com.hzau.dto.*;
import com.hzau.entity.*;

public class LogService {
	LogDao logDao =new LogDao();
	public List<Log> queryLogAll(int page, int length) {
		return logDao.queryLogAll(page, length);
	}
	
	public List<Log> queryLogByUserName(String username, int page, int length) {
		return logDao.queryLogByUserName(username, page, length);
	}
	
	public List<Log> queryLogByMsg(String msg, int page, int length){
		return logDao.queryLogByMsg(msg, page, length);
	}
	public int queryLogTotal() {
		return logDao.queryLogTotal();
	}
	public int queryLogByUserNameTotal(String username) {
		return logDao.queryLogByUserNameTotal(username);
	}
	public int queryLogByMsgTotal(String msg) {
		return logDao.queryLogByMsgTotal(msg);
	}
	public boolean deletlog(int logid) {
		return logDao.deletlog(logid);
	}
	public boolean deletlogList(int[] logid){
		return logDao.deletlogList(logid);
	}
}
