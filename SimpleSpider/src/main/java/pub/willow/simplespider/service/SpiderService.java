package pub.willow.simplespider.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import pub.willow.simplespider.beans.SimpleSpiderBean;

public class SpiderService {
	// Cookie类型
	public static final int COOKIE_TYPE = 1;
	// Header类型
	public static final int HEADER_TYPE = 3;

	public SimpleSpiderBean spiderHtml(SimpleSpiderBean simpleSpiderBean) throws Exception {
		String url = simpleSpiderBean.getUrl();
		String charset = simpleSpiderBean.getCharset();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
//		httpGet.setHeader("Referer", url);
//		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0");
//		httpGet.setHeader("Connection", "keep-alive");
//		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//		httpGet.setHeader("Accept-Encoding", "gzip");
//
//		httpGet.setHeader("Connection","keep-alive");
//		httpGet.setHeader("Q-UA2","QV=3&PL=ADR&PR=WX&PP=com.tencent.mm&PPVN=6.3.27&TBSVC=36803&CO=BK&COVC=036849&PB=GE&VE=GA&DE=PHONE&CHID=0&LCID=9422&MO= M351 &RL=1080*1800&OS=4.4.4&API=19");
//		httpGet.setHeader("Q-GUID","1950f1c5f51e88e5ace645d7111188cb");
//		httpGet.setHeader("Q-Auth","31045b957cf33acf31e40be2f3e71c5217597676a9729f1b");
//		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		httpGet.setHeader("User-Agent","Mozilla/5.0 (Linux; Android 4.4.4; M351 Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile MQQBrowser/6.8 TBS/036849 Safari/537.36 MicroMessenger/6.3.27.880 NetType/WIFI Language/zh_CN");
//		httpGet.setHeader("x-wechat-uin","MjYxNjgzNjMzNg==");
//		httpGet.setHeader("Accept-Encoding","gzip");
//		httpGet.setHeader("Accept-Language","zh-CN,en-US;q=0.8");
		
		// 抓取参数设置
		Map<String, String> headers = simpleSpiderBean.getHeaders();
		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}

		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);// 连接时间20s
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

		HttpResponse httpResponse = client.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		if (statusCode == HttpStatus.SC_OK) {
			InputStream is = httpResponse.getEntity().getContent();
			
			String source = getResponseBodyAsString(is, charset);
			source = source.replaceAll("\\\\u0001", " ");
			source = source.replaceAll("\\\\u0002", " ");
			source = source.replaceAll("\\\\u0003", " ");
			source = source.replaceAll("\\\\u0004", " ");
			source = source.replaceAll("\\\\u0005", " ");
			source = source.replaceAll("\\\\u0006", " ");
			source = source.replaceAll("\\\\u0007", " ");
			source = source.replaceAll("\\\\u0008", " ");
			source = source.replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", "");
			source = source.replace("�", "");
			source = Filter.filterOffUtf8Mb4(source);
			simpleSpiderBean.setSource(source);
		}

		return simpleSpiderBean;
	}

	/**
	 * 获取网页编码格式
	 * 
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public String getSourceByCharset(InputStream is, String charset) throws IOException {
		byte[] by = new byte[40000];
		List<byte[]> arr = new ArrayList<byte[]>();
		int actlength = 0;
		while ((actlength = is.read(by)) != -1) {
			byte[] b = Arrays.copyOf(by, actlength);
			if (charset == null || "".equals(charset)) {
				BytesEncodingDetect s = new BytesEncodingDetect();
				charset = BytesEncodingDetect.javaname[s.detectEncoding(b)];
			}
			arr.add(b);
		}
		actlength = 0;
		for (byte[] b : arr) {
			actlength += b.length;
		}
		byte[] by1 = new byte[actlength];
		actlength = 0;
		for (byte[] b : arr) {
			System.arraycopy(b, 0, by1, actlength, b.length);
			actlength += b.length;
		}
		return new String(by1, charset).replaceAll("\u0000", "");
	}

	/**
	 * 处理gzip压缩形式的数据
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String getResponseBodyAsString(InputStream is, String charset) throws IOException {
		InputStream gzin = new GZIPInputStream(is);

		return getSourceByCharset(gzin, charset);
	}

	public static void saveFile(String text, String url) {
		File f = new File("d:/" + url + ".txt");
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(f);// 初始化输出流
			bw = new BufferedWriter(fw);// 初始化输出字符流
			bw.write(text);// 写文件
			bw.flush();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * unicode 转换成 中文
	 * 
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("HH");
	}
}
