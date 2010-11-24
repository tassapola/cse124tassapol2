package proxy.cache;

import java.util.Date;

import proxy.DownloadResult;

public class CacheData {

	/**
	 * GMT time of target server
	 */
	private Date date;
	private String filepath;
	private String contentEncoding;
	private int contentLength;
	private String contentType;
	
	public CacheData(DownloadResult dr, String filepath) {
		this.date = dr.getDate();
		this.filepath = filepath;
		this.contentEncoding = dr.getContentEncoding();
		this.contentLength = dr.getContentLength();
		this.contentType = dr.getContentType();
	}
	
	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filename) {
		this.filepath = filename;
	}

	public boolean isNewerThanCache(Date d) {
		return d.after(date);
	}
}
