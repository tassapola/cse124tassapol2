package proxy.hadoop;

public class HdfsWriter extends Thread{

	private byte[] data;
	private String path;
	private static final String HADOOP_PATH = ""; //TODO
	
	public HdfsWriter(byte[] data, String path) {
		this.data = data;
		this.path = path;
	}
	
	public void run() {
		write();
	}
	
	public void write() {
		
	}
}
