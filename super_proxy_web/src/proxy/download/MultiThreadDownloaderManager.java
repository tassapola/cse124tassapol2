package proxy.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import proxy.cache.UrlConstants;

import util.ByteArrayWrapper;

public class MultiThreadDownloaderManager {

	public static String getAcceptRanges(String url) {
		URL netUrl = null;
		try {
			netUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStream in = null;
		URLConnection urlConn = null;
		try {
			urlConn = netUrl.openConnection();
			//in = urlConn.getInputStream();
			String result = null;
			result = urlConn.getHeaderField("Accept-Ranges");
			return result;
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			if (urlConn != null) {
				try {
					in = urlConn.getInputStream();
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static int getContentLength(String url) {
		URL netUrl = null;
		try {
			netUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStream in = null;
		URLConnection urlConn = null;
		try {
			urlConn = netUrl.openConnection();
			//in = urlConn.getInputStream();
			String result = null;
			result = urlConn.getHeaderField("Content-Length");
			if (result == null)
				return -1;
			else
				return Integer.parseInt(result);
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			if (urlConn != null) {
				try {
					in = urlConn.getInputStream();
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return -1;
	}
	
	public static DownloadResult download(String url) {
		String r = getAcceptRanges(url);
		int len = getContentLength(url);
		MultiThreadDownloader[] m = new MultiThreadDownloader[UrlConstants.NUM_THREADS_MULTI_DOWNLOAD];
		if (r != null && r.equals("bytes")) {
			if (len == -1) 
				return SingleThreadDownloader.download(url);
			else {
				//System.out.println("multithread download");
				int nPart = UrlConstants.NUM_THREADS_MULTI_DOWNLOAD;
				//len = 10001
				//part = 2
				
				int bytesPerPart = len / nPart;
				CountDownLatch l = new CountDownLatch(nPart);
				for (int i = 0; i < nPart; i++) {
					int byteStart = i * bytesPerPart;
					int byteEnd;
					if (i == nPart - 1 ) {
						byteEnd = len;
					} else {
						byteEnd = (i+1) * bytesPerPart - 1;
					}
					m[i] = new MultiThreadDownloader(url, byteStart, byteEnd, l);
					m[i].start();
				}
				try {
					l.await();
				} catch (InterruptedException e) {
					//e.printStackTrace();
					return null;
				}
				//merge
				
				DownloadResult dr = new DownloadResult();
				dr.setDate(new Date());
				dr.setContentEncoding(m[0].getDr().getContentEncoding());
				dr.setContentLength(m[0].getDr().getContentLength());
				dr.setContentType(m[0].getDr().getContentType());
				ByteArrayWrapper bAW = new ByteArrayWrapper();
				for (int j=0; j < nPart; j++) {
					byte[] d = m[j].getDr().getData();
					for (int k=0; k < d.length; k++)
						bAW.addByte(d[k]);
				}
				dr.setData(bAW.getFinalByteArray());
				return dr;
			}		
		} else {
			return SingleThreadDownloader.download(url);
		}
		
	}
}
