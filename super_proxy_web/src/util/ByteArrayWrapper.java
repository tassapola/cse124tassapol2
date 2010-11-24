package util;
import java.util.Arrays;

import proxy.cache.UrlConstants;


public class ByteArrayWrapper {
	private byte[] data; //0..size-1
	int size;
	
	public ByteArrayWrapper() {
		size = 0;
		data = new byte[UrlConstants.STARTING_CONTENT_SIZE];
	}
	
	public void addByte(byte b) {
		if (size == data.length) {
			//byte[] newData = new byte[size*2];
			data = Arrays.copyOf(data, size*2);
		}
		data[size++] = b;
	}

	void addString(String s) {
		for (int i=0; i < s.length(); i++) {
			addByte((byte) s.charAt(i));
		}
	}
	
	public byte[] getData() {
		return data;
	}
	
	public byte[] getFinalByteArray() {
		//byte[] result = new byte[size];
		return Arrays.copyOf(data, size);
	}
}
