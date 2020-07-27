package com.hzau.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzau.service.LogService;
import com.hzau.entity.*;

@WebServlet("/loginfoserver")
public class LogInfoServlet extends HttpServlet {
	private LogService logService = new LogService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String method=request.getParameter("method");
		if(method.equals("logList")){
			logList(request,response);
		}else if(method.equals("delLog")){
			delLog(request,response);
		}else if(method.equals("delLoglist")){
			delLoglist(request,response);
		}else if(method.equals("searchByMsg")){
			searchByMsg(request,response);
		}else if(method.equals("searchByUser")){
			searchByUser(request,response);
		}
	}
	
//	分页返回所有
	private void logList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<Log> loglist=logService.queryLogAll(page, length);
		int total = logService.queryLogTotal();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", loglist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	两删除
	private void delLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int logid = Integer.parseInt(request.getParameter("logid"));
		boolean flag =logService.deletlog(logid);
	
		if(flag){
			PrintWriter out = response.getWriter();
			out.write("删除成功！");
		}
	}
	private void delLoglist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] idlist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			idlist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =logService.deletlogList(idlist);
		if(flag){
			PrintWriter out = response.getWriter();
			out.write("删除成功！");
		}
	}
//	两模糊查找
	private void searchByMsg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String msg=request.getParameter("msg");
		msg = new String(msg.getBytes("8859_1"), "utf8");
		List<Log> loglist=logService.queryLogByMsg(msg, page, length);
		int total = logService.queryLogByMsgTotal(msg);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", loglist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	private void searchByUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String username=request.getParameter("username");
		username = new String(username.getBytes("8859_1"), "utf8");
		List<Log> loglist=logService.queryLogByUserName(username, page, length);
		int total = logService.queryLogByUserNameTotal(username);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", loglist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
}
