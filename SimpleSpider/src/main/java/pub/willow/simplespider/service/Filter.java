package pub.willow.simplespider.service;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Filter {

	/**
	 * 过虑非utf-8字符
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String filterOffUtf8Mb4(String text) {
		String res = "";
		byte[] bytes;
		try {
			bytes = text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		int i = 0;
		while (i < bytes.length) {
			short b = bytes[i];
			if (b > 0) {
				buffer.put(bytes[i++]);
				continue;
			}
			b += 256;
			if ((b ^ 0xC0) >> 4 == 0) {
				buffer.put(bytes, i, 2);
				i += 2;
			} else if ((b ^ 0xE0) >> 4 == 0) {
				buffer.put(bytes, i, 3);
				i += 3;
			} else if ((b ^ 0xF0) >> 4 == 0) {
				i += 4;
			} else { 
                buffer.put(bytes[i++ ]); 
                continue; 
            } 
		}
		buffer.flip();
		try {
			res = new String(buffer.array(), 0, buffer.limit(), "utf-8"); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}
}
