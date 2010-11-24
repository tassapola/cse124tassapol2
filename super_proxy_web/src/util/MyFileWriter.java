package util;

import java.io.*;

public class MyFileWriter {

	private FileOutputStream fos;
	
	public MyFileWriter(String location) {
		try {
			fos = new FileOutputStream(new File(location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void write(byte[] data) {
		for (int i=0; i < data.length; i++) {
			try {
				fos.write(data[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
