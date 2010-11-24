import junit.framework.TestCase;
import proxy.cache.CacheData;
import proxy.cache.CacheUtil;



public class TestCacheUtil extends TestCase {

	public void test1() {
		String url = "http://cseweb.ucsd.edu/classes/fa10/ab.html";
		String result = CacheUtil.getRelativeHadoopPath(url);
		assertEquals("/superproxy/cseweb.ucsd.edu/classes/fa10/ab.html", result);
	}
	
	public void test2() {
		String url = "http://cseweb.ucsd.edu/classes/fa10/";
		String result = CacheUtil.getRelativeHadoopPath(url);
		assertEquals("/superproxy/cseweb.ucsd.edu/classes/fa10_", result);
	}
}
