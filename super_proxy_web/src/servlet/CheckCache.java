package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;

import proxy.*;

/**
 * Servlet implementation class CheckCache
 */
@WebServlet("/CheckCache")
public class CheckCache extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCache() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {	
		super.init(config);
		getServletContext().setAttribute("front_end", new FrontEnd());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("starting doGet");
		ServletContext sContext = getServletContext();
		System.out.println("sContext = " + sContext);
		FrontEnd f = (FrontEnd) sContext.getAttribute("front_end");
		System.out.println("f = " + f);
		String url = (String) request.getParameter("url");
		System.out.println("url = " + url);
		FrontEndResult fer = null;
		if (url != null) {
			System.out.println("before call updatecachereturn");
			fer = f.updateCacheReturn(url);
		}
		if (fer == null) {
			PrintWriter p = response.getWriter();
			p.print("error!");
			p.close();
		} else {
			ServletOutputStream o = response.getOutputStream();
			response.setContentLength(fer.getContentLength());
			response.setCharacterEncoding(fer.getContentEncoding());
			response.setContentType(fer.getContentType());
			o.write(fer.getData());
			o.close();
		}
	}

}
