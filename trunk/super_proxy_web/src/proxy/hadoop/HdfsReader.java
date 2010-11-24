package proxy.hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import javax.servlet.ServletContext;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import proxy.cache.UrlConstants;

import util.ByteArrayWrapper;

public class HdfsReader{

	private String path; 
	
	public HdfsReader(String relativePath) {
		this.path = UrlConstants.HADOOP_PATH + relativePath;
	}
	
	/**
	 * 
	 * @return null if cannot read
	 */
	public byte[] read() {
		Configuration conf = new Configuration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(URI.create(path), conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream in = null;
		ByteArrayWrapper bAW = new ByteArrayWrapper();
		int b;
		try {
			in = fs.open(new Path(path));
			do {
				b = in.read();
				if (b != -1) {
					bAW.addByte((byte) b);
				}
			} while (b != -1);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bAW.getFinalByteArray();
	}
}
