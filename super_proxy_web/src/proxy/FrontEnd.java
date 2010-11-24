package proxy;
import proxy.cache.CacheManager;
import util.MyFileWriter;
import util.MyUtil;


public class FrontEnd {

	private CacheManager cacheMgr;
	
	public FrontEnd() {
		cacheMgr = new CacheManager();
	}

	public FrontEndResult updateCacheReturn(String url) {
		FrontEndResult result = cacheMgr.getFrontEndResult(url);
		return result;
	}
	
	/*
	public void writeFile(String location) {
		MyFileWriter myFileWriter = new MyFileWriter(location);
		myFileWriter.write(dlResult.getData());
		myFileWriter.close();
	}
	*/
}
