package pub.willow.simplespider.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleSpiderBean {
	private int taskId; // 任务唯一标识
	private String url; // 任务url
	private String charset; // 网站编码格式
	private String source; // 网页源码
	private Map<String, String> headers;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void loadDefaultHeaders() {
		Map<String, String> defaultHeaders = new HashMap<String, String>() {
			{
				put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0");
				put("Connection", "keep-alive");
				put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
				put("Accept-Encoding", "gzip, deflate");
			}
		};
		this.headers = defaultHeaders;
	}

	public Map<String, String> getDefaultHeaders() {
		Map<String, String> defaultHeaders = new HashMap<String, String>() {
			{
				put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0");
				put("Connection", "keep-alive");
				put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
				put("Accept-Encoding", "gzip, deflate");
			}
		};
		return defaultHeaders;
	}

}
