
public class FrontEnd {

	private String url;
	
	public FrontEnd(String url) {
		this.url = url;
	}

	public void process() {
		DownloadResult dlResult = Downloader.download(url);
		MyUtil.print(MyUtil.getDataAfterModifyUrl(dlResult.getData(), MyUtil.defaultRewriteUrlHost, url));
		///MyUtil.print(dlResult.getData());
	}
}
