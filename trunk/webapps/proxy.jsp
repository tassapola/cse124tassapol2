<%@ page import="java.util.*, java.io.*, proxy.*, util.*" %>
<%
	OutputStream o = response.getOutputStream();
	String url = request.getParameter("url");
	FrontEnd f = new FrontEnd(url);
	f.process();
	DownloadResult dlResult = f.getDownloadResult();
	
	
%>