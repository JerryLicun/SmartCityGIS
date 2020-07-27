package com.hzau.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzau.dto.InformDto;
import com.hzau.entity.Inform;
import com.hzau.service.InfoService;


@WebServlet("/infoserver")
public class InfoServlet extends HttpServlet {
	private InfoService infoService= new InfoService();
	private  Log logger = LogFactory.getLog(this.getClass());
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
		if(method.equals("infoList")){
			infoList(request,response);
		}else if(method.equals("searchBytitle")){
			searchBytitle(request,response);
		}else if(method.equals("searchByauthor")){
			searchByauthor(request,response);
		}else if(method.equals("delInfo")){
			delInfo(request,response);
		}else if(method.equals("delInfolist")){
			delInfolist(request,response);
		}else if(method.equals("addInfo")){
			addInfo(request,response);
		}else if(method.equals("getInfo")){
			getInfo(request,response);
		}else if(method.equals("changeInfo")){
			changeInfo(request,response);
		}
	}
//	查找全部
	private void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<InformDto> infolist=infoService.queryInformAll(page, length);
		int total = infoService.queryInformTotalAll();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", infolist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	两模糊查找
	private void searchBytitle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String title=request.getParameter("title");
		title = new String(title.getBytes("8859_1"), "utf8");
		
		List<InformDto> infolist=infoService.queryInformBytitle(title, page, length);
		int total = infoService.queryTotalBytitle(title);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", infolist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	
	private void searchByauthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String keyword=request.getParameter("keyword");
		keyword = new String(keyword.getBytes("8859_1"), "utf8");
		
		List<InformDto> infolist=infoService.queryInformByname(keyword, page, length);
		int total = infoService.queryTotalByname(keyword);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", infolist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	两删除
	private void delInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int informid = Integer.parseInt(request.getParameter("informid"));
		boolean flag =infoService.deleteInformByid(informid);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("删除公告");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
	private void delInfolist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] informlist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			informlist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =infoService.deleteInformlist(informlist);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("批量删除公告");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	新增
	private void addInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String infostr =request.getParameter("info");
			infostr = new String(infostr.getBytes("8859_1"), "utf8");
			Inform inform =JSON.parseObject(infostr,Inform.class);
			boolean flag =infoService.addInform(inform);
			PrintWriter out = response.getWriter();
			if(flag){
				out.write("添加成功");
				logger.info("添加公告");
			}else{
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "添加失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	编辑单条
	private void getInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int inform_id = Integer.parseInt(request.getParameter("inform_id"));
		Inform info =infoService.queryInformByid(inform_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(info));
	}
//	修改单条
	private void changeInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String infostr =request.getParameter("info");
		infostr = new String(infostr.getBytes("8859_1"), "utf8");
		Inform inform =JSON.parseObject(infostr,Inform.class);
		boolean flag =infoService.modifyInformByid(inform);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("修改成功");
			logger.info("修改公告");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "修改失败");
		}
	}
}
