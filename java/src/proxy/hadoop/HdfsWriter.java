package proxy.hadoop;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class HdfsWriter extends Thread{

	private byte[] data;
	private String path;
	private static final String HADOOP_PATH = "hdfs://cse124demo:9000"; 
	
	public HdfsWriter(byte[] data, String relativePath) {
		this.data = data;
		this.path = HADOOP_PATH + relativePath;
	}
	
	public void run() {
		synchronized(this) {
			write();
			this.notify();
		}
	}
	
	public void write() {
		Configuration conf = new Configuration();
		FileSystem fs = null;
		System.out.println("path = " + path);
		try {
			fs = FileSystem.get(URI.create(path), conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		OutputStream out = null;
		try {
			out = fs.create(new Path(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
