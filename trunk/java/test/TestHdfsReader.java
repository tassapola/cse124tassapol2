import proxy.hadoop.HdfsReader;
import proxy.hadoop.HdfsWriter;
import util.MyUtil;
import junit.framework.TestCase;


public class TestHdfsReader extends TestCase {

	public void test1() {
		
		String path = "/superproxy/f2.txt";
		HdfsReader h = new HdfsReader(path);
		byte[] data = h.read();
		MyUtil.print(data);
	}
}
