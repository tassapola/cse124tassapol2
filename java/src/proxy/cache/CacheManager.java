package proxy.cache;

import java.util.*;

import proxy.*;
import proxy.hadoop.HdfsWriter;

public class CacheManager {

	/**
	 * <URL as a string, CacheData>
	 */
	private HashMap<String, CacheData> cache;
	private int numCacheData;
	
	public CacheManager() {
		numCacheData = 0;
	}
	
	/**
	 * before insertion of new file, get filename
	 * @return
	 */
	public String getFileName() {
		String result = "file" +  Integer.toString(numCacheData);
		return result;
	}
	
	public synchronized byte[] getData(String url, Downloader downloader) {
		CacheData o = cache.get(url);
		if (o == null) {
			DownloadResult dr = Downloader.download(url);
			String newFileName = getFileName();
			CacheData c = new CacheData(dr.getDate(), newFileName);
			cache.put(url, c);
			numCacheData++;
			HdfsWriter writer = new HdfsWriter(dr.getData(), newFileName);
			writer.start();
			return dr.getData();
		} else {
			//check if-modified since
			if (Downloader.isModifiedSince(url, o.getDate())) {
				//need to refresh cache
				DownloadResult dr = Downloader.download(url);
				String filename = o.getFilename();
				CacheData c = new CacheData(dr.getDate(), filename);
				cache.put(url, c);
				numCacheData++;
				HdfsWriter writer = new HdfsWriter(dr.getData(), filename);
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
