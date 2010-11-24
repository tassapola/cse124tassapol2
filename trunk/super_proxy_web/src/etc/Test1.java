package etc;
import proxy.FrontEnd;
import proxy.FrontEndResult;
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
		FrontEndResult fer1 = f.updateCacheReturn(url);
		System.out.println(fer1);
		FrontEndResult fer2 = f.updateCacheReturn(url);
		System.out.println(fer2);
		
		
		//http://www.thai-real.com/imgs_hotel/lawana_resort.jpg
		//http://www.thailandgolftravel.com/leefiles/image/beach/Samui.jpg
	}

}
