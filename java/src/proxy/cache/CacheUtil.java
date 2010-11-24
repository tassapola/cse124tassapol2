package proxy.cache;

public class CacheUtil {

	private static final String RELATIVE_ROOT = "/superproxy";
	/**
	 * url == "http://cseweb.ucsd.edu/classes/fa10/ab.html"
	 * url == "http://cseweb.ucsd.edu/classes/fa10/"
	 * 
	 * return "/superproxy/cseweb.ucsd.edu/classes/fa10/ab.html"
	 * return "/superproxy/cseweb.ucsd.edu/classes/fa10_"
	 * @param url
	 * @return
	 */
	public static String getRelativeHadoopPath(String url) {
		String result;
		result = url.replaceAll("http[:][/][/]", RELATIVE_ROOT + "/");
		result = result.replaceAll("https[:][/][/]", RELATIVE_ROOT + "/");
		//System.out.println(result);
		if (result.endsWith("/")) {
			result = result.substring(0, result.length()-1) + "_"; 
		}
		return result;
	}
}
