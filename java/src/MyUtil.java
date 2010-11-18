
public class MyUtil {

	public static void print(byte[] data) {
		for (int i=0; i < data.length; i++) {
			System.out.print((char) data[i]);
		}
		System.out.println();
	}
	
	public final static String defaultRewriteUrlHost = "http://localhost/check_cache.jsp?url=";
	
	public static String rewriteUrl(String url, String sourceUrl) {
		return rewriteUrl(url, defaultRewriteUrlHost, sourceUrl);	
	}
	
	/**
	 * Given a string of URL, return a string of rewritten URL
	 * @param url
	 * @param rewriteUrlHost
	 * @param sourceUrl
	 * @return
	 */
	public static String rewriteUrl(String url, String rewriteUrlHost, String sourceUrl) {
		String result = null;
		
		if (url.startsWith("http")) {
			String[] tokens = url.split("/");
			result = rewriteUrlHost;
			for (int i=0; i < tokens.length; i++) {
				if (i != 0) {
					result += "/" + tokens[i];
				} else {
					result += tokens[i];
				}
			}
		} else {
			String[] sourceUrlTokens = sourceUrl.split("/");
			result = "";
			
			result = rewriteUrlHost;
			for (int i=0; i < sourceUrlTokens.length-1; i++) {
				result += sourceUrlTokens[i] + "/";
			}
			//System.out.println(sourceUrlTokens[sourceUrlTokens.length-1]);
			if (sourceUrl.charAt(sourceUrl.length()-1) == '/') {
				result += sourceUrlTokens[sourceUrlTokens.length-1] + "/";
			}
			result += url;
		}
		return result;
	}
	
	public static byte[] getDataAfterModifyUrl(byte[] data, String sourceURL) {
		return getDataAfterModifyUrl(data, defaultRewriteUrlHost, sourceURL);
	}
	
	public static byte[] getDataAfterModifyUrl(byte[] data, String rewriteUrlHost, String sourceURL) {
		ByteArrayWrapper bAW = new ByteArrayWrapper();
		boolean inAHref = false;
		StringBuilder aHref = null;
		
		for (int i=0; i < data.length; i++) {
			char c = (char) data[i];
			if (inAHref) {
				aHref.append((char) data[i]);
				StringBuilder w = new StringBuilder();
				//w.append(get(data, i-3));
				//w.append(get(data, i-2));
				w.append(get(data, i-1));
				w.append(get(data, i));
				if (w.toString().compareTo("\">") == 0) {
					//System.out.println(w);
					inAHref = false;
					//System.out.println(aHref);
					String newAHrefString = convertAHrefString(aHref.toString(), sourceURL);
					//System.out.println(newAHrefString);
					bAW.addString(newAHrefString);
				}
			} else {
				StringBuilder w = new StringBuilder();
				for (int j=0; j < 9; j++) {
					w.append(get(data, i+j));
				} 
				if (w.toString().compareTo("<a href=\"") == 0) {
					//System.out.println(w);
					inAHref = true;
					aHref = new StringBuilder();
					aHref.append((char) data[i]);
				} else {
					bAW.addByte(data[i]);	
				}
			}
			
			
		}
		return bAW.getFinalByteArray();
	}
	
	private static char get(byte[] data, int index) {
		if (index >= data.length || index < 0) return ' ';
		else return (char) data[index];
	}
	
	private static String convertAHrefString(String org, String sourceURL) {
		String[] tokens = org.split("\"");
		return tokens[0] + "\"" + rewriteUrl(tokens[1], sourceURL) + "\"" + tokens[2];
	}
}
