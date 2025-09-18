package com.labgeek.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthentificationFilter
 */
@WebFilter("/*")
public class AuthentificationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthentificationFilter() {
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
		  HttpServletRequest httpRequest = (HttpServletRequest) request;
	        HttpServletResponse httpResponse = (HttpServletResponse) response;

	        String loginURI = httpRequest.getContextPath() + "/login.jsp";
	        String loginServlet = httpRequest.getContextPath() + "/login";

	        boolean loggedIn = httpRequest.getSession() != null &&
	                           httpRequest.getSession().getAttribute("username") != null;

	        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI) ||
	                               httpRequest.getRequestURI().equals(loginServlet);

	        boolean staticResources = httpRequest.getRequestURI().contains("css") ||
	                                  httpRequest.getRequestURI().contains("js");

	        if (loggedIn || loginRequest || staticResources) {
	            chain.doFilter(request, response); 
	        } else {
	            httpResponse.sendRedirect(loginServlet);
	        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
