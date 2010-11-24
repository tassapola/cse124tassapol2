package proxy.cache;

import java.util.*;

import proxy.*;
import proxy.hadoop.HdfsWriter;

public class CacheManager {

	/**
	 * <URL as a string, CacheData>
	 */
	private HashMap<String, CacheData> cache;
	
	public CacheManager() {
		
	}
		
	public synchronized byte[] getData(String url, Downloader downloader) {
		CacheData o = cache.get(url);
		if (o == null) {
			DownloadResult dr = Downloader.download(url);
			String newFileName = CacheUtil.getRelativeHadoopPath(url);
			CacheData c = new CacheData(dr.getDate(), newFileName);
			cache.put(url, c);
			HdfsWriter writer = new HdfsWriter(dr.getData(), newFileName);
			writer.start();
			return dr.getData();
		} else {
			//check if-modified since
			if (Downloader.isModifiedSince(url, o.getDate())) {
				//need to refresh cache
				DownloadResult dr = Downloader.download(url);
				String filepath = o.getFilepath();
				CacheData c = new CacheData(dr.getDate(), filepath);
				cache.put(url, c);
				HdfsWriter writer = new HdfsWriter(dr.getData(), filepath);
				writer.start();
				return dr.getData();
			} else {
				//read data from hadoop fs
				//TODO
				return null;
			}
		}
	}
}
