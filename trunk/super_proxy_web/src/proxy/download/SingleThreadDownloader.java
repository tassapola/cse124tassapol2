package proxy.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import util.ByteArrayWrapper;

public class SingleThreadDownloader {

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
