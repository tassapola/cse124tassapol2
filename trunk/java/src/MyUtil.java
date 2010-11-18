
public class MyUtil {

	public static void print(byte[] data) {
		for (int i=0; i < data.length; i++) {
			System.out.print((char) data[i]);
		}
		System.out.println();
	}
	
	public final static String defaultRewriteUrlHost = "localhost/check_cache.jsp?url=";
	
	public static String rewriteUrl(String url, String rewriteUrlHost) {
		String result = null;
		String[] tokens = url.split("/");
		//for (int i=0; i < tokens.length; i++) {
		//	System.out.println(tokens[i]);
		//}
		result = tokens[0] + "//" + rewriteUrlHost;
		for (int i=2; i < tokens.length; i++) {
			//System.out.println(tokens[i] + " " + result);
			if (i != 2) {
				result += "/" + tokens[i];
			} else {
				result += tokens[i];
			}
		}
		return result;
	}
	
	public static byte[] getDataAfterModifyUrl(byte[] data, String rewriteUrlHost) {
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
					String newAHrefString = convertAHrefString(aHref.toString());
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
	
	private static String convertAHrefString(String org) {
		String[] tokens = org.split("\"");
		return tokens[0] + "\"" + rewriteUrl(tokens[1], defaultRewriteUrlHost) + "\"" + tokens[2];
	}
}
