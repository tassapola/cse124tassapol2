package proxy;
import util.MyUtil;


public class FrontEnd {

	private String url;
	private DownloadResult dlResult;
	
	public FrontEnd(String url) {
		this.url = url;
	}

	public void process() {
		dlResult = Downloader.download(url);
		byte[] newData = MyUtil.getDataAfterModifyUrl(dlResult.getData(), url);
		dlResult.setData(newData);
		//MyUtil.print(MyUtil.getDataAfterModifyUrl(dlResult.getData(), MyUtil.defaultRewriteUrlHost, url));
		///MyUtil.print(dlResult.getData());
	}
	
	public void writeFile(String location) {
		MyFileWriter myFileWriter = new MyFileWriter(location);
		myFileWriter.write(dlResult.getData());
		myFileWriter.close();
	}
}
