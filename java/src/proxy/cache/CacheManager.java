package proxy.cache;

import java.util.*;

import proxy.*;
import proxy.hadoop.HdfsReader;
import proxy.hadoop.HdfsWriter;
import util.MyUtil;

public class CacheManager {

	/**
	 * <URL as a string, CacheData>
	 */
	private HashMap<String, CacheData> cache;
	
	public CacheManager() {
		cache = new HashMap<String, CacheData>();
	}
		
	public synchronized byte[] getData(String url) {
		CacheData o = cache.get(url);
		if (o == null) {
			System.out.println("case 1: new url");
			DownloadResult dr = Downloader.download(url);
			//rewrite url
			dr.setData(MyUtil.getDataAfterModifyUrl(dr.getData(), url));
			String newFileName = CacheUtil.getRelativeHadoopPath(url);
			CacheData c = new CacheData(dr.getDate(), newFileName);
			cache.put(url, c);
			HdfsWriter writer = new HdfsWriter(dr.getData(), newFileName);
			//writer.start();
			//this version will block until write finishes
			writer.write();
			return dr.getData();
		} else {
			//check if-modified since
			if (Downloader.isModifiedSince(url, o.getDate())) {
				System.out.println("case 2: refresh cache");
				//need to refresh cache
				DownloadResult dr = Downloader.download(url);
				String filepath = o.getFilepath();
				CacheData c = new CacheData(dr.getDate(), filepath);
				cache.put(url, c);
				HdfsWriter writer = new HdfsWriter(dr.getData(), filepath);
				writer.start();
				return dr.getData();
			} else {
				System.out.println("case 3: read data from hadoop fs");
				//read data from hadoop fs
				HdfsReader reader = new HdfsReader(o.getFilepath());
				return reader.read();
			}
		}
	}
}
