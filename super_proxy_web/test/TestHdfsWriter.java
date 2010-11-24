import proxy.hadoop.HdfsWriter;
import junit.framework.TestCase;


public class TestHdfsWriter extends TestCase {

	public void test1() {
		byte[] data = { 65, 65, 80 };
		String path = "/superproxy/f3.txt";
		HdfsWriter h = new HdfsWriter(data, path);
		synchronized(h) {
			h.start();
			try {
				h.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
