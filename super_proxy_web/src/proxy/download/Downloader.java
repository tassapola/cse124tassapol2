package proxy.download;
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
		//return SingleThreadDownloader.download(url);
		return MultiThreadDownloaderManager.download(url);
	}
}
