package proxy.cache;

import java.util.Date;

public class CacheData {

	/**
	 * GMT time of target server
	 */
	private Date date;
	private String filename;
	
	public CacheData(Date date, String filename) {
		this.date = date;
		this.filename = filename;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isNewerThanCache(Date d) {
		return d.after(date);
	}
}
