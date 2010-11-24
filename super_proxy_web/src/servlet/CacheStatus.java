package servlet;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proxy.*;
import proxy.cache.CacheData;
import proxy.cache.CacheManager;

/**
 * Servlet implementation class CacheStatus
 */
@WebServlet("/CacheStatus")
public class CacheStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CacheStatus() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.print("<html><head><title>Cache status</title></head><body>");
		pw.print("<h1>Cache status</h1>");
		pw.print("<table><tr>");
		pw.print("<th>No.</th><th>URL</th><th>Date</th>");
		pw.print("</tr>");
		FrontEnd f = (FrontEnd) getServletContext().getAttribute("front_end");
		if (f != null) {
			CacheManager c = f.getCacheMgr();
			HashMap<String, CacheData> h = c.getCache();
			Set<String> s = h.keySet();
			int n = 1;
			for (Iterator<String> it = s.iterator(); it.hasNext();) {
				String key = it.next();
				CacheData v = h.get(key);
				pw.print("<tr>");
				pw.print("<td>" + (n++) + "</td>");
				pw.print("<td>" + key + "</td>");
				pw.print("<td>" + v.getDate() + "</td>");
				pw.print("</tr>");
			}
			pw.print("</table>");
		} else {
			pw.print("</table>");
			pw.print("no data");
		}
		
		pw.print("</body></html>");
		pw.close();
	}


}
