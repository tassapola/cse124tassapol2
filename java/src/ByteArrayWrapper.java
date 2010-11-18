import java.util.Arrays;


public class ByteArrayWrapper {
	private byte[] data; //0..size-1
	int size;
	
	public ByteArrayWrapper() {
		size = 0;
		data = new byte[1000000];
	}
	
	void addByte(byte b) {
		if (size == data.length) {
			//byte[] newData = new byte[size*2];
			data = Arrays.copyOf(data, size*2);
		}
		data[size++] = b;
	}

	public byte[] getData() {
		return data;
	}
	
	public byte[] getFinalByteArray() {
		//byte[] result = new byte[size];
		return Arrays.copyOf(data, size);
	}
}
