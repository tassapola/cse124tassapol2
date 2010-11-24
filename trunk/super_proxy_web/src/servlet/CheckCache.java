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
		//System.out.println("starting doGet");
		ServletContext sContext = getServletContext();
		//System.out.println("sContext = " + sContext);
		FrontEnd f = (FrontEnd) sContext.getAttribute("front_end");
		//System.out.println("f = " + f);
		String url = (String) request.getParameter("url");
		//System.out.println("url = " + url);
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
			response.setContentLength(fer.getContentLength());
			response.setCharacterEncoding(fer.getContentEncoding());
			response.setContentType(fer.getContentType());
			
			System.out.println("content type = " + fer.getContentType());
			byte[] data = fer.getData();
			if (fer.getContentType().equals("text/html; charset=UTF-8")) {
				System.out.println("text/html");
				PrintWriter p = response.getWriter();
				for (int i=0; i < data.length; i++) {
					p.print((char) fer.getData()[i]);
					System.out.print((char) fer.getData()[i]);
				}
				System.out.println("end of printing out");
				//o.write(fer.getData());
				//System.out.println("=servlet=");
				//MyUtil.print(fer.getData());
				p.flush();
				p.close();
			} else {
				
				ServletOutputStream o = response.getOutputStream();
				
				
				System.out.println("start writing out");
				for (int i=0; i < data.length; i++) {
					o.write(fer.getData()[i]);
					System.out.print((char) fer.getData()[i]);
				}
				//o.write(fer.getData());
				//System.out.println("=servlet=");
				//MyUtil.print(fer.getData());
				o.flush();
				o.close();
				}
		}
	}

}
