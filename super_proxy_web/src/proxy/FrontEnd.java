package proxy;
import javax.servlet.ServletContext;

import proxy.cache.CacheManager;
import util.MyFileWriter;
import util.MyUtil;


public class FrontEnd {

	private CacheManager cacheMgr;
	
	public FrontEnd() {
		cacheMgr = new CacheManager();
	}

	public FrontEndResult updateCacheReturn(String url) {
		//System.out.println("starting update cache return");
		FrontEndResult result = cacheMgr.getFrontEndResult(url);
		System.out.println("requesting, url = " + url + " - " + result);
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
