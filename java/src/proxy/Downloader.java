package proxy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import util.ByteArrayWrapper;


public class Downloader {

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
		try {
			in = netUrl.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int b = 0;
		
		do {
			try {
				b = in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (b != -1) {
				byteAW.addByte((byte) b);
			}
		}
		while (b != -1);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		result.setData(byteAW.getFinalByteArray());
		result.setDate(new Date());
		return result;
	}
}
