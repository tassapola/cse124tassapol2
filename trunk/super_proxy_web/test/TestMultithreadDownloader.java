import java.util.Date;

import proxy.download.DownloadResult;
import proxy.download.Downloader;
import proxy.download.MultiThreadDownloaderManager;

import junit.framework.TestCase;


public class TestMultithreadDownloader extends TestCase {

	public void testDl1() {
		String url = "http://cseweb.ucsd.edu/classes/fa10/cse124/welcome.htm";
		DownloadResult dr = MultiThreadDownloaderManager.download(url);
		System.out.println(dr);
	}
	
}

