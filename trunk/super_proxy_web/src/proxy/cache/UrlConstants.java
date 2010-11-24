package proxy.cache;

public class UrlConstants {
	public static final String DEFAULT_TARGET_WEB_HOST_REWRITE_URL =
									"http://cse124demo:8080/cse124demo/CheckCache?url=";
	public static final String HADOOP_RELATIVE_ROOT = "/superproxy";
	
	public static final int STARTING_CONTENT_SIZE = 1000000;
	
	public static final String HADOOP_PATH = "hdfs://cse124demo:9000";
	
	public static final int NUM_THREADS_MULTI_DOWNLOAD = 4;
}
