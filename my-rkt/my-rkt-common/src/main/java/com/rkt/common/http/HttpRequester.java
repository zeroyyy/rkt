package com.rkt.common.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author yangjanjun
 * @Description http请求对象
 * @Copyright 本软件源代码版权归地利集团, 未经许可不得任意复制与传播
 * @Company 地利集团
 * @createTime 2016/7/26 10:46
 */
public class HttpRequester {
	private static String defaultContentEncoding="UTF-8";

	private HttpRequester() {}
	private static HttpRequester httpRequester=null;
	public static HttpRequester getInstance() {
		if (httpRequester == null) {
			synchronized (HttpRequester.class) {
				if(httpRequester == null)httpRequester = new HttpRequester();
			}
		}
		return httpRequester;
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public  HttpResponse sendGet(String urlString) throws IOException {
		return this.send(urlString, "GET", null, null);
	}

	/**
	 * 发送GET请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public  HttpResponse sendGet(String urlString, Map<String, String> params) throws IOException {
		return this.send(urlString, "GET", params, null);
	}

	/**
	 * 发送GET请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public  HttpResponse sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return send(urlString, "GET", params, propertys);
	}

	/**
	 * 发送POST请求
	 *
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public  HttpResponse sendPost(String urlString) throws IOException {
		return send(urlString, "POST", null, null);
	}

	/**
	 * 发送POST请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public  HttpResponse sendPost(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "POST", params, null);
	}

	/**
	 * 发送POST请求
	 *
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	public  HttpResponse sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return send(urlString, "POST", params, propertys);
	}


	/**
	 * 发送post xml请求
	 * @param urlString
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public  HttpResponse sendPostXML(String urlString,String xml) throws IOException {
		return sendXml(urlString, "POST", xml, null, null);
	}
	/**
	 * 发送HTTP请求
	 *
	 * @param urlString
	 * @return 响映对象
	 * @throws java.io.IOException
	 */
	private  HttpResponse send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys) throws IOException {
		HttpURLConnection urlConnection = null;

		if ("GET".equalsIgnoreCase(method) && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (Map.Entry<String,String> entry : parameters.entrySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(entry.getKey()).append("=").append(entry.getValue());
				i++;
			}
			urlString += param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		if (propertys != null)
			for (Map.Entry<String,String> entry : propertys.entrySet()) {
				urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
			}

		if ("POST".equalsIgnoreCase(method) && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (Map.Entry<String,String> entry : parameters.entrySet()) {
				param.append("&");
				param.append(entry.getKey()).append("=").append(entry.getValue());
			}
			urlConnection.getOutputStream().write(param.toString().getBytes(Charset.forName(HttpRequester.this.defaultContentEncoding)));
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		return makeContent(urlString, urlConnection);
	}

	/**
	 * 发送xml到HTTP请求
	 *
	 * @param urlString
	 * @return 响映对象
	 * @throws java.io.IOException
	 */
	private  HttpResponse sendXml(String urlString, String method,String xml, Map<String, String> parameters, Map<String, String> propertys) throws IOException {
		HttpURLConnection urlConnection = null;

		if ("GET".equalsIgnoreCase(method) && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (Map.Entry<String,String> entry : parameters.entrySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(entry.getKey()).append("=").append(entry.getValue());
				i++;
			}
			urlString += param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);

		System.out.println(xml);
		byte[] xmlBytes=xml.getBytes();
		urlConnection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(xmlBytes.length));

		if (propertys != null)
			for (Map.Entry<String,String> entry : propertys.entrySet()) {
				urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
			}

		if ("POST".equalsIgnoreCase(method) && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (Map.Entry<String,String> entry : parameters.entrySet()) {
				param.append("&");
				param.append(entry.getKey()).append("=").append(entry.getValue());
			}
			urlConnection.getOutputStream().write(param.toString().getBytes(Charset.forName(HttpRequester.this.defaultContentEncoding)));
		}
		urlConnection.getOutputStream().flush();
		urlConnection.getOutputStream().close();
		return makeContent(urlString, urlConnection);
	}

	/**
	 * 得到响应对象
	 *
	 * @param urlConnection
	 * @return 响应对象
	 * @throws java.io.IOException
	 */
	private  HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
		HttpResponse httpResponser = new HttpResponse();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,this.defaultContentEncoding));
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = this.defaultContentEncoding;

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();

			httpResponser.content = new String(temp.toString().getBytes(Charset.forName(this.defaultContentEncoding)), ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
	/**
	 * map转成xml
	 *
	 * @param arr
	 * @return
	 */
	public String arrayToXml(TreeMap<String, String> arr) {
		String xml = "<xml>";

		Iterator<Map.Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			xml += "<" + key + ">" + val + "</" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}
	public static void main(String[] args) {
//		String url="https://api.mch.weixin.qq.com/pay/unifiedorder";
//		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx0c06abb5211ec8f8"+ "&secret=e9ddc1b993579eb473fc447d831fbe43" .trim() + "&code=011Zqmqg0rx7eD11tLpg0lGgqg0ZqmqD"  + "&grant_type=authorization_code";

		//获取access_token
		String url = "https://api.weixin.qq.com/cgi-bin/token";     //请求路径
		try {
			TreeMap<String, String> objectObjectHashMap = new TreeMap<String, String>();
			objectObjectHashMap.put("grant_type","client_credential");
			objectObjectHashMap.put("appid","wx0c06abb5211ec8f8");
			objectObjectHashMap.put("secret","e9ddc1b993579eb473fc447d831fbe43");
			HttpRequester httpRequester=HttpRequester.getInstance();

			String xml = httpRequester.arrayToXml(objectObjectHashMap);
			httpRequester.sendPostXML(url,xml);
			HttpResponse httpResponse = httpRequester.sendPost(url,objectObjectHashMap);
			System.out.println(httpResponse.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
