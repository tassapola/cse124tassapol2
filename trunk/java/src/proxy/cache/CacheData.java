package proxy.cache;

import java.util.Date;

public class CacheData {

	/**
	 * GMT time of target server
	 */
	private Date date;
	private byte[] data;
	
	public CacheData(Date date, byte[] data) {
		this.date = date;
		this.data = data;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public boolean isNewerThanCache(Date d) {
		return d.after(date);
	}
}
