package com.rkt.common.http;
import java.util.Vector;

/**
 * @author yangianjun
 * @Description http响应对象
 * @Copyright 本软件源代码版权归地利集团, 未经许可不得任意复制与传播
 * @Company 地利集团
 * @createTime 2016/7/26 10:49
 */
public class HttpResponse {

	String urlString;

	int defaultPort;

	String file;

	String host;

	String path;

	int port;

	String protocol;

	String query;

	String ref;

	String userInfo;

	String contentEncoding;

	String content;

	String contentType;

	int code;

	String message;

	String method;

	int connectTimeout;

	int readTimeout;

	Vector<String> contentCollection;

	public String getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Vector<String> getContentCollection() {
		return contentCollection;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public String getMethod() {
		return method;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public String getUrlString() {
		return urlString;
	}

	public int getDefaultPort() {
		return defaultPort;
	}

	public String getFile() {
		return file;
	}

	public String getHost() {
		return host;
	}

	public String getPath() {
		return path;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getQuery() {
		return query;
	}

	public String getRef() {
		return ref;
	}

	public String getUserInfo() {
		return userInfo;
	}

	@Override
	public String toString() {
		return "HttpResponse{" +
				"urlString='" + urlString + '\'' +
				", defaultPort=" + defaultPort +
				", file='" + file + '\'' +
				", host='" + host + '\'' +
				", path='" + path + '\'' +
				", port=" + port +
				", protocol='" + protocol + '\'' +
				", query='" + query + '\'' +
				", ref='" + ref + '\'' +
				", userInfo='" + userInfo + '\'' +
				", contentEncoding='" + contentEncoding + '\'' +
				", content='" + content + '\'' +
				", contentType='" + contentType + '\'' +
				", code=" + code +
				", message='" + message + '\'' +
				", method='" + method + '\'' +
				", connectTimeout=" + connectTimeout +
				", readTimeout=" + readTimeout +
				", contentCollection=" + contentCollection +
				'}';
	}
}
