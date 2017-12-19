package com.github.util;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HttpClientJdk {
	
	public static String postFile(String requestUrl, Map<String, File> fileMap) throws Exception {
		return postForm(requestUrl, null, fileMap);
	}
	/**
     * 
     *  requestUrl = "http://localhost/common/upload/image";
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put("image", new File("c:/1.jpg"));
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("type", "iMiracle");
		parameterMap.put("resize", "true");
		parameterMap.put("size", "240");
		System.err.println(postForm(requestUrl, parameterMap, fileMap));
     */
	public static String postForm(String requestUrl, Map<String, String> parameterMap, Map<String, File> fileMap) throws Exception {  

		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");

		String BOUNDARY = "-----------------------------iMiracle";
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		
		OutputStream outputStream = connection.getOutputStream();
		
		// form patameter
		if (parameterMap != null && parameterMap.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String field = entry.getKey();
				String value = entry.getValue();
				if (value == null) {
					continue;
				}
				stringBuffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
				stringBuffer.append("Content-Disposition: form-data; name=\"" + field + "\"\r\n\r\n");  
				stringBuffer.append(value);
			}
			outputStream.write(stringBuffer.toString().getBytes("UTF-8"));
		}
		
		// file
		String contentType = "text/plain";
		
		InputStream inputStream = null;
		for (Map.Entry<String, File> entry : fileMap.entrySet()) {
			
			String field = entry.getKey();
			File file = entry.getValue();
			inputStream = new FileInputStream(file);
			
			StringBuffer stringBuffer = new StringBuffer();  
			stringBuffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
			stringBuffer.append("Content-Disposition: form-data; name=\"" + field + "\"; filename=\"" + file.getAbsolutePath() + "\"\r\n");  
			stringBuffer.append("Content-Type:" + contentType + "\r\n\r\n");  
	        outputStream.write(stringBuffer.toString().getBytes("UTF-8"));
	        
			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		}
		
		
		byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
		outputStream.write(endData);
        
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		
		// 从输入流读取返回内容
		inputStream = connection.getInputStream();
		String responseContent = IOUtils.toString(inputStream, "UTF-8");
		inputStream.close();
		connection.disconnect();

		return responseContent;
    }
	
	public static void main(String[] args) throws Exception {
		String requestUrl = "http://static.basicedu.chaoxing.com/resource/book/CD00000001.JPG";
//		System.err.println(getContentLength(requestUrl));
//		
//		
//		File file = new File("c:/1.txt");
//		gjh(requestUrl, file, 0, 10);
		
		
		requestUrl = "http://localhost/upload";
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put("uploadFile", new File("/Users/imiracle/Desktop/22f.jpg"));
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("directory", "38");
		System.err.println(postForm(requestUrl, parameterMap, fileMap));
	}
	
	public static void gjh(String requestUrl, File file, long start, long end) throws Exception {
		
		
		RandomAccessFile randomAccessFile= new RandomAccessFile(file, "rw");
		randomAccessFile.seek(start);
		
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(getProxy());
		connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
		int responseCode = connection.getResponseCode();
		if (responseCode == 200 || responseCode == 206) {
			InputStream inputStream = connection.getInputStream();
			
			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = inputStream.read(buffer)) != -1) {
				randomAccessFile.write(buffer, 0, length);
			}
			inputStream.close();
			randomAccessFile.close();
		}
		connection.disconnect();
	}
	public static long getContentLength(String requestUrl) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();
			if (connection.getResponseCode() == 200) {
				return connection.getContentLength();
			}
		} catch (Exception e) {
		} finally {
			connection.disconnect();
		}
		throw new RuntimeException(requestUrl + " can't be reachable nor HttpStatus !200.");
	}
	
	
	
	/**
	 * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
	 */
	public static Proxy getProxy() {
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
	}
	
	
	
	public static void httpDownload(String downloadUrl, String savePath) {
		httpDownload(downloadUrl, new File(savePath));
	}
	public static void httpDownload(String downloadUrl, File savePath) {
		if (savePath.exists()) {
			return;
		}
		
		try {
			URL url = new URL(downloadUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000); // 设置连接主机超时（单位：毫秒）
			connection.setReadTimeout(10000); // 设置从主机读取数据超时（单位：毫秒）
			// 设置请求方式（GET/POST）
			connection.setRequestMethod("GET");
			connection.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			
			InputStream inputStream = connection.getInputStream();
			OutputStream outputStream = new FileOutputStream(savePath);
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void httpsDownload(String requestUrl, String path) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] trustManager = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, trustManager, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sslSocketFactory);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			OutputStream outputStream = new FileOutputStream(path);
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String httpRequestGet(String requestUrl, Map<String, Object> parameterMap) {
		return httpRequest(requestUrl, "GET", buildRequestBody(parameterMap));
	}
	public static String httpRequestPost(String requestUrl, Map<String, Object> parameterMap) {
		return httpRequest(requestUrl, "POST", buildRequestBody(parameterMap));
	}
	public static String httpRequest(String requestUrl, String requestMethod, Map<String, Object> parameterMap) {
		return httpRequest(requestUrl, requestMethod, buildRequestBody(parameterMap));
	}
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}
			
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			String responseContent = IOUtils.toString(inputStream, "UTF-8");
			inputStream.close();
			conn.disconnect();

			return responseContent;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] trustManager = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, trustManager, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sslSocketFactory);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}
			
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			String responseContent = IOUtils.toString(inputStream, "UTF-8");
			inputStream.close();
			conn.disconnect();

			return responseContent;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String caHttpsRequest(String merchantId, String requestUrl, String requestBody) {

		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			InputStream instream = HttpClientJdk.class.getResourceAsStream("/" + merchantId + ".p12");
			keyStore.load(instream, merchantId.toCharArray());
			instream.close();

			// 实例化密钥库
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			// 初始化密钥工厂
			keyManagerFactory.init(keyStore, merchantId.toCharArray());

			SSLContext sslContext = SSLContext.getInstance("TLSv1", "SunJSSE");
			sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sslSocketFactory);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != requestBody) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(requestBody.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			String responseContent = IOUtils.toString(inputStream, "UTF-8");
			inputStream.close();
			conn.disconnect();

			return responseContent;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* 构造请求体 */
	public static String buildRequestBody(Map<String, Object> parameterMap) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			if (StringUtils.isNotEmpty(entry.getValue().toString())) {
				stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		return stringBuffer.toString();
	}
}

class MyX509TrustManager implements X509TrustManager {
	
	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}
	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}
	// 返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}