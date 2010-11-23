package etc;
import proxy.FrontEnd;


public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/welcome.htm";
		String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/Banner.htm";
		new FrontEnd(url).process();

	}

}
