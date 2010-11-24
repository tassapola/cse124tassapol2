package proxy.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import util.ByteArrayWrapper;
import util.MyUtil;

public class MultiThreadDownloader extends Thread{

	private String url;
	private int byteStart;
	private int byteEnd;
	private CountDownLatch l;
	private DownloadResult dr;
	
	public MultiThreadDownloader(String url, int byteStart, int byteEnd, CountDownLatch l) {
		this.url = url;
		this.byteStart = byteStart;
		this.byteEnd = byteEnd;
		this.l = l;
		this.dr = null;
	}
	
	public void run() {
		//System.out.println("starting a thread");
		this.dr = download();
		l.countDown();
	}
	
	public DownloadResult download() {
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
			urlConn.setRequestProperty("Range", "bytes=" + byteStart + "-" + byteEnd);
			//System.out.println(urlConn.getRequestProperties());
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
		//System.out.println("before countdown l = " + l);
		//System.out.println("after countdown l = " + l);
		//MyUtil.print(result.getData());
		return result;
	}

	public DownloadResult getDr() {
		return dr;
	}

	public void setDr(DownloadResult dr) {
		this.dr = dr;
	}
	
	
}
