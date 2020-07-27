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
import com.hzau.dto.NewsDto;
import com.hzau.dto.PersonDto;
import com.hzau.entity.News;
import com.hzau.entity.Organ;
import com.hzau.entity.Person;
import com.hzau.service.NewsService;
import com.hzau.service.UserService;

@WebServlet("/api/newserver")
public class NewsServlet extends HttpServlet {
	private NewsService newService= new NewsService();
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
		if(method.equals("newsList")){
			newsList(request,response);
		}else if(method.equals("searchBytitle")){
			searchBytitle(request,response);
		}else if(method.equals("searchByauthor")){
			searchByauthor(request,response);
		}else if(method.equals("delNews")){
			delNews(request,response);
		}else if(method.equals("delNewslist")){
			delNewslist(request,response);
		}else if(method.equals("addNews")){
			addNews(request,response);
		}else if(method.equals("getNews")){
			getNews(request,response);
		}else if(method.equals("changeNews")){
			changeNews(request,response);
		}
	}
//	返回全部
	private void newsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<NewsDto> newslist=newService.queryNews(page, length);
		int total = newService.queryTotal();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", newslist);
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
		
		List<NewsDto> newslist=newService.queryNewsBytitle(title, page, length);
		int total = newService.queryTotalBytitle(title);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", newslist);
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
		
		List<NewsDto> newslist=newService.queryNewsBynewsauthor(keyword, page, length);
		int total = newService.queryTotalBynewsauthor(keyword);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", newslist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	两删除
	private void delNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int newsid = Integer.parseInt(request.getParameter("newsid"));
		boolean flag =newService.deleteNewsByid(newsid);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("删除新闻");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
	private void delNewslist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] newsidlist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			newsidlist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =newService.deleteNewslist(newsidlist);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("批量删除新闻");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	新增
	private void addNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String newstr =request.getParameter("news");
			newstr = new String(newstr.getBytes("8859_1"), "utf8");
			News news =JSON.parseObject(newstr,News.class);
			boolean flag =newService.addNews(news);
			PrintWriter out = response.getWriter();
			if(flag){
				out.write("添加成功");
				logger.info("添加新闻");
			}else{
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "添加失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	编辑单条
	private void getNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int news_id = Integer.parseInt(request.getParameter("news_id"));
		News news =newService.queryNewsBynewsid(news_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(news));
	}
//	修改单条
	private void changeNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newstr =request.getParameter("news");
		newstr = new String(newstr.getBytes("8859_1"), "utf8");
		News news =JSON.parseObject(newstr,News.class);
		boolean flag =newService.modifyNewsByid(news);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("修改成功");
			logger.info("修改新闻");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "修改失败");
		}
	}
}
