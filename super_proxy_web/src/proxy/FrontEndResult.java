package proxy;

import proxy.cache.CacheData;

public class FrontEndResult {

	private byte[] data;
	private String contentEncoding;
	private int contentLength;
	private String contentType;
	
	public FrontEndResult(CacheData c, byte[] data) {
		contentEncoding = c.getContentEncoding();
		contentLength = c.getContentLength();
		contentType = c.getContentType();
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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
	
	public String toString() {
		String r = "data.length() = " + data.length;
		r += ",contentEncoding = " + contentEncoding;
		r += ",contentLength = " + contentLength;
		r += ",contentType = " + contentType;
		return r;
	}
	
}
