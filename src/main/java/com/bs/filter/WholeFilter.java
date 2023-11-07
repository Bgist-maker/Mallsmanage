package com.bs.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class WholeFilter
 */
public class WholeFilter implements Filter {
       
    /**
     * @see Filter#Filter()
     */
    public WholeFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest requesth = (HttpServletRequest) request;
        HttpServletResponse responseh = (HttpServletResponse) response;
        String servletPath = requesth.getServletPath();
        if(servletPath.endsWith(".css") || servletPath.endsWith(".xls") || servletPath.endsWith(".gif") 
        		|| servletPath.endsWith(".jpg") || servletPath.endsWith(".png") || servletPath.endsWith(".js")|| servletPath.endsWith(".txt") 
        		|| servletPath.equals("/login")|| servletPath.equals("/loginValidate")) {
        	chain.doFilter(request, response);
        }else{
        	HttpSession session = requesth.getSession(true);
    		if(session.getAttribute("user") == null) {
        		responseh.sendRedirect(requesth.getContextPath()+"/login");
        	} else{
        		chain.doFilter(request, response);
        	}
    	
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
