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
import util.MyUtil;

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
		ServletContext sContext = getServletContext();
		FrontEnd f = (FrontEnd) sContext.getAttribute("front_end");
		String url = (String) request.getParameter("url");
		FrontEndResult fer = null;
		if (url != null) {
			fer = f.updateCacheReturn(url);
		}
		if (fer == null) {
			PrintWriter p = response.getWriter();
			p.print("error!");
			p.close();
		} else {
			response.setContentLength(fer.getContentLength());
			response.setCharacterEncoding(fer.getContentEncoding());
			response.setContentType(fer.getContentType());
			ServletOutputStream o = response.getOutputStream();
			o.write(fer.getData());
			o.close();
		}
		
	}

}
