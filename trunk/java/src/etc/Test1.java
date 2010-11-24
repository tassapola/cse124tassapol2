package etc;
import proxy.FrontEnd;
import util.MyUtil;


public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/welcome.htm";
		//String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/Banner.htm";
		//String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/ucsd_logo.gif";
		FrontEnd f = new FrontEnd();
		byte[] d1 = f.updateCacheReturn(url);
		if (d1 != null) System.out.println(d1.length);
		byte[] d2 = f.updateCacheReturn(url);
		if (d2 != null) {
			System.out.println(d2.length);
			MyUtil.print(d2);
		}
	}

}
