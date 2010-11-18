
public class MyUtil {

	public static void print(byte[] data) {
		for (int i=0; i < data.length; i++) {
			System.out.print((char) data[i]);
		}
		System.out.println();
	}
}
