package proxy.cache;

import java.util.*;

import proxy.*;
import proxy.Downloader;

public class CacheManager {

	/**
	 * <URL as a string, CacheData>
	 */
	private HashMap<String, CacheData> cache;
	
	public CacheManager() {
		
	}
	
	public byte[] getData(String url, Downloader downloader) {
		CacheData o = cache.get(url);
		if (o == null) {
			DownloadResult dr = downloader.download(url);
			CacheData c = new CacheData(dr.getDate(), dr.getData());
			cache.put(url, c);
			return dr.getData();
		} else {
			//check if-modified since
			if (Downloader.isModifiedSince(url, o.getDate())) {
				//need to refresh cache
				DownloadResult dr = downloader.download(url);
				CacheData c = new CacheData(dr.getDate(), dr.getData());
				cache.put(url, c);
				return dr.getData();
			} else {
				return o.getData();
			}
		}
	}
}
