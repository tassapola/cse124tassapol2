package util;

import java.io.PrintStream;

import proxy.cache.UrlConstants;

public class MyUtil {

	public static void print(byte[] data) {
		for (int i=0; i < data.length; i++) {
			System.out.print((char) data[i]);
		}
		System.out.println();
	}
	
	public static void print(PrintStream ps, byte[] data) {
		for (int i=0; i < data.length; i++) {
			ps.print((char) data[i]);
		}
		ps.println();
	}
	
	public static String rewriteUrl(String url, String sourceUrl) {
		return rewriteUrl(url, UrlConstants.DEFAULT_TARGET_WEB_HOST_REWRITE_URL, sourceUrl);	
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
			//absolute link
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
			//relative link
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
		return getDataAfterModifyUrl(data, UrlConstants.DEFAULT_TARGET_WEB_HOST_REWRITE_URL, sourceURL);
	}
	
	public static byte[] getDataAfterModifyUrl(byte[] data, String rewriteUrlHost, String sourceURL) {
		ByteArrayWrapper bAW = new ByteArrayWrapper();
		boolean inAHrefImg = false;
		StringBuilder aHrefImg = null;
		
		for (int i=0; i < data.length; i++) {
			char c = (char) data[i];
			if (inAHrefImg) {
				aHrefImg.append((char) data[i]);
				StringBuilder w = new StringBuilder();
				//w.append(get(data, i-3));
				//w.append(get(data, i-2));
				w.append(get(data, i-1));
				w.append(get(data, i));
				if (w.toString().compareTo("\">") == 0) {
					//System.out.println(w);
					inAHrefImg = false;
					//System.out.println(aHref);
					String newAHrefString = convertAHrefString(aHrefImg.toString(), sourceURL);
					//System.out.println(newAHrefString);
					bAW.addString(newAHrefString);
				}
			} else {
				StringBuilder w = new StringBuilder();
				for (int j=0; j < 50; j++) {
					w.append(get(data, i+j));
				} 
				if ((w.toString().substring(0, 9).compareTo("<a href=\"") == 0)
					|| (w.toString().substring(0, 5).compareTo("<img ") == 0)
				    ) {
					//System.out.println(w);
					inAHrefImg = true;
					aHrefImg = new StringBuilder();
					aHrefImg.append((char) data[i]);
				} else {
					bAW.addByte(data[i]);	
				}
			}
			
			
		}
		MyUtil.print(bAW.getFinalByteArray());
		return bAW.getFinalByteArray();
	}
	
	private static char get(byte[] data, int index) {
		if (index >= data.length || index < 0) return ' ';
		else return (char) data[index];
	}
	
	private static String convertAHrefString(String org, String sourceURL) {
		String result = "";
		//System.out.println(org);
		int index1;
		String sToFind = null;
		sToFind = "src=\"";
		index1 = org.indexOf(sToFind);
		if (index1 != -1) {
			StringBuilder s = new StringBuilder();
			int index2 = 0;
			for (int i=index1+sToFind.length(); i < org.length(); i++) {
				if (org.charAt(i) != '"') {
				  s.append(org.charAt(i));
				} else {
				  index2 = i;
			      break;
				}
			}
			//System.out.println("s = " + s);
			result = org.substring(0, index1+sToFind.length()) + rewriteUrl(s.toString(), sourceURL) + org.substring(index2, org.length());
			//System.out.println(result);
			return result;
		} 
		sToFind = "href=\"";
		index1 = org.indexOf(sToFind);
		if (index1 != -1) {
			StringBuilder s = new StringBuilder();
			int index2 = 0;
			for (int i=index1+sToFind.length(); i < org.length(); i++) {
				if (org.charAt(i) != '"') {
				  s.append(org.charAt(i));
				} else {
				  index2 = i;
			      break;
				}
			}
			result = org.substring(0, index1+sToFind.length()) + rewriteUrl(s.toString(), sourceURL) + org.substring(index2, org.length());
			//System.out.println(result);
			return result;
		} 
		return result;
	}
	
}
