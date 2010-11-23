package etc;
import proxy.FrontEnd;


public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/welcome.htm";
		//String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/Banner.htm";
		String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/ucsd_logo.gif";
		new FrontEnd(url).process();

	}

}
