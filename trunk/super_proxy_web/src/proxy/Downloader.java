package proxy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import util.ByteArrayWrapper;


public class Downloader {

	public static boolean isModifiedSince(String url, Date d) {
		boolean result = false;
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		URLConnection uc = null;
		try {
			uc = u.openConnection();
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		uc.setIfModifiedSince(d.getTime());
		InputStream in = null;
		try {
			in = uc.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int b = 0;
		//System.out.println(in);
		try {
			b = in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (b == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * return null if error
	 * @param url
	 * @return
	 */
	public static DownloadResult download(String url) {
		DownloadResult result = new DownloadResult();
		ByteArrayWrapper byteAW = new ByteArrayWrapper();
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
			in = urlConn.getInputStream();
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
		int b = 0;
		
		do {
			try {
				b = in.read();
			} catch (IOException e) {
				//e.printStackTrace();
				return null;
			}
			if (b != -1) {
				byteAW.addByte((byte) b);
			}
		}
		while (b != -1);
		try {
			in.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
		result.setData(byteAW.getFinalByteArray());
		result.setDate(new Date());
		result.setContentEncoding(urlConn.getContentEncoding());
		result.setContentLength(urlConn.getContentLength());
		result.setContentType(urlConn.getContentType());
		return result;
	}
}
