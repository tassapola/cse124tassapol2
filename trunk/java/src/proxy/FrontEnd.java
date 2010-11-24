package proxy;
import proxy.cache.CacheManager;
import util.MyFileWriter;
import util.MyUtil;


public class FrontEnd {

	private CacheManager cacheMgr;
	
	public FrontEnd() {
		cacheMgr = new CacheManager();
	}

	public byte[] updateCacheReturn(String url) {
		byte[] data = cacheMgr.getData(url);
		return data;
	}
	
	/*
	public void writeFile(String location) {
		MyFileWriter myFileWriter = new MyFileWriter(location);
		myFileWriter.write(dlResult.getData());
		myFileWriter.close();
	}
	*/
}
