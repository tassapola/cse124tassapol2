
public class FrontEnd {

	private String url;
	
	public FrontEnd(String url) {
		this.url = url;
	}

	public void process() {
		DownloadResult dlResult = Downloader.download(url);
		
		///MyUtil.print(dlResult.getData());
	}
}
