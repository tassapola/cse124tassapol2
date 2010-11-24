package proxy.cache;

public class CacheUtil {

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
		result = url.replaceAll("http[:][/][/]", UrlConstants.HADOOP_RELATIVE_ROOT + "/");
		result = result.replaceAll("https[:][/][/]", UrlConstants.HADOOP_RELATIVE_ROOT  + "/");
		//System.out.println(result);
		if (result.endsWith("/")) {
			result = result.substring(0, result.length()-1) + "_"; 
		}
		return result;
	}
}
