import java.util.Date;

import proxy.Downloader;

import junit.framework.TestCase;


public class TestDownloader extends TestCase {

	public void testIsModifiedSince1() {
		String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/welcome.htm";
		Date d = new Date();
		assertFalse(Downloader.isModifiedSince(url, d));
	}
	
	public void testIsModifiedSince() {
		String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/welcome.htm";
		Date d = new Date(1999,10,1);
		assertTrue(Downloader.isModifiedSince(url, d));
	}
}

