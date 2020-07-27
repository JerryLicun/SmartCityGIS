package com.hzau.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.hzau.entity.Person;

public class ResFilter implements Filter {
	         
   private final static double DEFAULT_USERID= Math.random()*100000.0;    
  
   public void destroy() {  
    }  
   public void doFilter(ServletRequest request, ServletResponse response,  
       FilterChain chain) throws IOException, ServletException {  
       HttpServletRequest req=(HttpServletRequest)request;  
       HttpSession session= req.getSession();  
        if (session==null){  
            MDC.put("username",DEFAULT_USERID);    
        }  
        else{  
            Person customer=(Person)session.getAttribute("loginPerson");  
            if (customer==null){  
                MDC.put("username",DEFAULT_USERID);  
            }  
            else  
            {  
                MDC.put("username",customer.getUsername());  
                MDC.put("ip", request.getRemoteAddr());
            }  
        }  
        //logger.info("test for MDC.");  
  
       chain.doFilter(request, response);  
    }  
   public void init(FilterConfig Config) throws ServletException {  

    }  
}
