<%@ page import="java.util.*, java.io.*, proxy.*, util.*" %>
<%
	String url = request.getParameter("url");
	FrontEnd f = new FrontEnd(url);
	f.process();
	DownloadResult dlResult = f.getDownloadResult();
	byte[] data = dlResult.getData();
	for (int i=0; i < data.length; i++) {
		out.write((char) data[i]);
	}
%>