package proxy.cache;

import java.util.Date;

public class CacheData {

	/**
	 * GMT time of target server
	 */
	private Date date;
	private String filepath;
	
	public CacheData(Date date, String filename) {
		this.date = date;
		this.filepath = filename;
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
