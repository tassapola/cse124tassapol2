package proxy.cache;

import java.util.*;

import javax.servlet.ServletContext;

import proxy.*;
import proxy.download.DownloadResult;
import proxy.download.Downloader;
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
		
	/**
	 * return null if error
	 * @param url
	 * @return
	 */
	public synchronized FrontEndResult getFrontEndResult(String url) {
		CacheData o = cache.get(url);
		if (o == null) {
			System.out.println("case 1: new url");
			DownloadResult dr = Downloader.download(url);
			if (dr == null) {
				return null;
			}
			//rewrite url
			byte[] newData = MyUtil.getDataAfterModifyUrl(dr.getData(), url);
			dr.setContentLength(newData.length);
			dr.setData(newData);
			String newFileName = CacheUtil.getRelativeHadoopPath(url);
			CacheData c = new CacheData(dr, newFileName);
			cache.put(url, c);
			HdfsWriter writer = new HdfsWriter(dr.getData(), newFileName);
			//writer.start();
			//this version will block until write finishes
			writer.write();
			return new FrontEndResult(c, dr.getData());
		} else {
			//check if-modified since
			if (Downloader.isModifiedSince(url, o.getDate())) {
				System.out.println("case 2: refresh cache");
				//need to refresh cache
				DownloadResult dr = Downloader.download(url);
				if (dr == null) {
					return null;
				}
				byte[] newData = MyUtil.getDataAfterModifyUrl(dr.getData(), url);
				dr.setContentLength(newData.length);
				dr.setData(newData);
				String filepath = o.getFilepath();
				CacheData c = new CacheData(dr, filepath);
				cache.put(url, c);
				HdfsWriter writer = new HdfsWriter(dr.getData(), filepath);
				//writer.start();
				//this version will block until write finishes
				writer.write();
				return new FrontEndResult(c, dr.getData());
			} else {
				System.out.println("case 3: read data from hadoop fs");
				//read data from hadoop fs
				HdfsReader reader = new HdfsReader(o.getFilepath());
				return new FrontEndResult(o, reader.read());
			}
		}
	}

	public HashMap<String, CacheData> getCache() {
		return cache;
	}

	public void setCache(HashMap<String, CacheData> cache) {
		this.cache = cache;
	}
	
}
