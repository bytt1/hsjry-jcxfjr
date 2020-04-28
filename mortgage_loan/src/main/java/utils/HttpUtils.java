package utils;

import java.io.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	private static final int TIMEOUT = 10 * 10000;
	private static final int MAXTOTAL = 300;
	private static final int MAXPERROUTE = 50;
	private static final int MAXROUTE = 300;
	private static String hostName;
	private static int port;
	private static ConnectionSocketFactory plainsf;
	private static LayeredConnectionSocketFactory sslsf;
	private static Registry<ConnectionSocketFactory> registry;
	private static PoolingHttpClientConnectionManager cm;
	private static HttpRequestRetryHandler retry;
	private static CloseableHttpClient client = null;
	private static CloseableHttpResponse response = null;
	private static HttpPost httpPost;
	private static HttpGet httpGet;
	private static HttpEntity entity;
	private static List<NameValuePair> param;
	private static StringBuffer str = null;
	private static BufferedReader br = null;
	private static JSONObject json;
	private static FileInputStream fileInputStream = null;
	private static byte[] bytes = null;
	public static Properties pro = null;
	private static FileOutputStream oFile;
	private static final String proFilePath = "bontop_largerdata/database.properties";
	private static SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	private static Calendar cal = Calendar.getInstance();
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

//	static  {
//		FileInputStream in = null;
//		try {
//			in = new FileInputStream("bontop_largerdata/domain.properties");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		pro = new Properties();
//
//		try {
//			pro.load(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	//在当前时间增加指定天数
	public static String addDay(int num) throws Exception {
		cal.setTime(new Date());
		cal.add(Calendar.DATE,num);
		return format.format(cal.getTime());
	}

	//在当前时间增加指定小时
	public static String addHour(int num) throws Exception {
		cal.setTime(new Date());
		cal.add(Calendar.HOUR,num);
		return format.format(cal.getTime());
	}

	public static String addMinute(int num){
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE,num);
		return format.format(cal.getTime());
	}

	public static String addSecond(int num){
		cal.setTime(new Date());
		cal.add(Calendar.SECOND,num);
		return format.format(cal.getTime());
	}

	public static String currentDateFormat(){
		return format.format(new Date());
	}

	//写入配置文件
	public static void setKey(String key,String value) throws Exception {
		Properties p = new Properties();
		oFile = new FileOutputStream(proFilePath, true);//true表示追加打开
		p.setProperty(key,value);
		p.store(oFile,"DATABASE SEARCH VALUE");
		log.info("写入成功");
		if(oFile != null){
			oFile.close();
		}
	}
	//获取指定键的值
	public static String proGetVal(String key) throws Exception {
		Properties val = new Properties();
		val.load(new FileInputStream(proFilePath));
		return (String) val.get(key);
	}

	//删除配置文件所有数据
	public static void removeAll() throws Exception {
		Properties rm = new Properties();
		rm.load(new FileInputStream(proFilePath));
		Iterator<String> it = rm.stringPropertyNames().iterator();
		while(it.hasNext()){
			rm.remove(it.next());
		}
		rm.store(new FileOutputStream(proFilePath),"remove all data");
		log.info("配置文件已清空!");
	}



	/**
	 * @param url 接口地址
	 * @param parameters 请求参数
	 * post请求方式
	 * */
	
	public static String doPost(String url,Map<String,Object> parameters) throws Exception {

		getHost(url);
		httpPost = new HttpPost(url);
		addHeader("post");
		httpConfig(httpPost);
		setParameter(parameters);
		httpPost.setEntity(new UrlEncodedFormEntity(param,Consts.UTF_8));
		response = httpClient().execute(httpPost);
		if(response.getStatusLine().getStatusCode() != 200 || response ==null) {
			httpPost.abort();
		}
		consumeResource();
		return str.toString();
	}

	public static String doPost(String url,String data) throws Exception {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
		post.setHeader("Accept","application/json, text/plain, */*");
		post.setHeader("Accept-Encoding","gzip, deflate, br");
		post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
		post.setHeader("content-type","application/x-ndjson");
		post.setHeader("Host","kibana-dev.corp.jccfc.com");
		post.setHeader("kbn-version","7.2.0");
		post.setHeader("Origin","https://kibana-dev.corp.jccfc.com");
		post.setHeader("Referer","https://kibana-dev.corp.jccfc.com/app/kibana");
		post.setHeader("Sec-Fetch-Dest","empty");
		post.setHeader("Sec-Fetch-Mode","cors");
		post.setHeader("Sec-Fetch-Site","same-origin");

		StringEntity strEntity = new StringEntity(data,ContentType.TEXT_PLAIN);
		post.setEntity(strEntity);
		CloseableHttpResponse resp = client.execute(post);
		if(resp.getStatusLine().getStatusCode() != 200 || resp ==null) {
			post.abort();
		}

		return EntityUtils.toString(resp.getEntity());
	}

	/**
	 * get请求方式
	 * */
	
	public static String doGet(String url,Map<String,Object> parameters) throws Exception {
		getHost(url);
		if(parameters == null) {
			httpGet = new HttpGet(url);
		}else if(parameters != null) {
			setParameter(parameters);
			URIBuilder uri = new URIBuilder();
			uri.setParameters(param);
			httpGet = new HttpGet(uri.build());
		}
		addHeader("get");
		httpConfig(httpGet);
		response = httpClient().execute(httpGet);

		if(response.getStatusLine().getStatusCode() != 200 || response == null) {
			httpGet.abort();
		}
		consumeResource();
		return str.toString();

	}
	
	/**
	 * requestPayload 提交数据请求方式
	 * */
	
	public static JSONObject requestPayload(String url,String json) throws Exception {
		getHost(url);
		httpPost = new HttpPost(url);
		addHeader("post");
		httpConfig(httpPost);
		StringEntity strEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(strEntity);
		response = httpClient().execute(httpPost);
		if(response.getStatusLine().getStatusCode() != 200 || response == null) {
			httpPost.abort();
		}
		
		consumeResource();
		return JSON.parseObject(str.toString());
	}

	/**
	 *  将文件转成base64加密
	 * **/
	public static String bs64FileConvert(File file) throws Exception {
		fileInputStream = new FileInputStream(file);
		bytes = new byte[fileInputStream.available()];
		fileInputStream.read(bytes);

		return new Base64().encodeAsString(bytes);
	}
	
	/**
	 * 上传文件 请求方式  携带文件上传
	 * */
	
	public static String upLoadFile(String url,Map<String,Object> parameters,String filePath) throws Exception {
		File file = new File(filePath);
		getHost(url);
		httpPost = new HttpPost(url);
		addHeader("post");
		httpConfig(httpPost);
		MultipartEntityBuilder multi = MultipartEntityBuilder.create();
		/**
		 * file 接口参数字段名
		 * new FileInputStream(file)  已流传输   file文件地址
		 * ContentType.DEFAULT_BINARY 解码
		 * file.getName()  文件名 
		 * */
		multi.addBinaryBody("file", new FileInputStream(file), ContentType.DEFAULT_BINARY, file.getName());
		if(parameters != null) {
			for(Map.Entry<String, Object> entry : parameters.entrySet()) {
				String key = entry.getKey();
				StringBody val = new StringBody(entry.getValue().toString(), ContentType.TEXT_PLAIN);
				multi.addPart(key, val);
			}
		}
		HttpEntity reqEntity = multi.build();
		httpPost.setEntity(reqEntity);
		response = httpClient().execute(httpPost);
		consumeResource();
		return str.toString();
		
	}
	
	
	
	/**
	 * 读取response返回数据
	 * */
	
	public static void consumeResource() throws UnsupportedOperationException, IOException {
		
		int len = 0;
		char[] c = new char[1024 * 1024 *3];
		str = new StringBuffer();
		entity = response.getEntity();
		InputStream in = entity.getContent();
		br = new BufferedReader(new InputStreamReader(in));
		while((len = br.read(c)) > 0) {
			str.append(c,0,len);
		}
		
		
	}
	
	/**
	 *  close char stream ， response client
	 * */
	
	public static void close() throws IOException {
		if(br != null) {
			br.close();
		}
		if(response != null) {
			response.close();
		}
		if(client != null) {
			client.close();
		}
	}
	
	/**
	 *  json 拼接
	 * */
	
	public static String toJson(JSONObject headers,JSONObject bodys) {
		JSONObject fullJson = new JSONObject();
		fullJson.put("head", headers);
		fullJson.put("body", bodys);
		return fullJson.toString();
	}
	
	public static String JsonAnalysis(JSONObject res,String msg) {

		JSONObject obj = (JSONObject) res.get("head");
		return obj.get(msg).toString();
	}
	
	public static String getLetter(int num) {
		String str = "";
		for(int i = 0;i < num;i++) {
			str += letter().get(new Random().nextInt(26)).toString();
		}
		return str;
	}
	
	public static Integer getInt() {
		return new Random().nextInt(90000) + 1000 ;
	}
	
	private static List<Character> letter() {
		List<Character> upCase = new ArrayList<>();
		int j = 0;
		for(int i = 0;i < 26;i++) {
			j = i + 65;
			upCase.add((char)j);	
		}
		return upCase;
	}
	
	private static void addHeader(String type) throws Exception {
		String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36";

		if(type.equals("post")) {
			httpPost.setHeader("User-Agent", agent);
			httpPost.setHeader("Accept","application/json, text/plain, */*");
			httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
			httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
			httpPost.setHeader("Connection","keep-alive");
			httpPost.setHeader("Content-Length","858");
			httpPost.setHeader("content-type","application/x-ndjson");
			httpPost.setHeader("Host","kibana-dev.corp.jccfc.com");
			httpPost.setHeader("kbn-version","7.2.0");
			httpPost.setHeader("Origin","https://kibana-dev.corp.jccfc.com");
			httpPost.setHeader("Referer","https://kibana-dev.corp.jccfc.com/app/kibana");
			httpPost.setHeader("Sec-Fetch-Dest","empty");
			httpPost.setHeader("Sec-Fetch-Mode","cors");
			httpPost.setHeader("Sec-Fetch-Site","same-origin");
		}else if(type.equals("get")) {
			httpGet.setHeader("User-Agent", agent);
		}else {
			throw new Exception("type cloud not found");
		}
	}
	

	private static void httpConfig(HttpRequestBase base) {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(TIMEOUT) //设置请求超时时间
				.setConnectTimeout(TIMEOUT) //设置连接超时时间
				.build();
		base.setConfig(config);
	}


	/**
	 *  获取url地址中 主机名 和 端口号
	 * */
	public static void getHost(String url)  {
		hostName = url.split("/")[2];

		if(hostName.contains(":")) {
			String[] arr = hostName.split(":");
			hostName = arr[0];
			port = Integer.parseInt(arr[1]);
		}

	}


	
	private static CloseableHttpClient httpClient() {
		
		client = HttpClients.custom()
				.setConnectionManager(pgccm()) //设施连接池管理
				.setRetryHandler(httpRetry())  //设施请求重试处理
				.build();
				
		return client;	
		
	}
	
	private static List<NameValuePair> setParameter(Map<String,Object> params) throws Exception{
		param = new ArrayList<>();
		if(params == null){
		    throw new Exception("post请求参数为空");
        }
		for(Map.Entry<String, Object> entry: params.entrySet()) {
			if(entry.getKey().equals("")) {
				throw new Exception("map键不能为空");
			}
			param.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}
		return param;
	}
	
	private static HttpRequestRetryHandler httpRetry() {
		
		retry = new HttpRequestRetryHandler() {
			
			@Override
			public boolean retryRequest(IOException e, int count, HttpContext context) {
				if(count >= 5) { //重试5次之后放弃
					return false;
				}
				if(e instanceof NoHttpResponseException) { //如果服务器丢掉连接，那么就重试
					return true;
				}
				if(e instanceof SSLHandshakeException) { //不重试SSL握手异常
					return false;
				}
				if(e instanceof InterruptedIOException) { //超时 不重试
					return false;
				}
				if(e instanceof UnknownHostException) { //目标服务器不可达
					return false;
				}
				if(e instanceof ConnectTimeoutException) { //请求被拒绝
					return false;
				}
				if(e instanceof SSLException) { //SSL握手异常
					return false;
				}
				HttpClientContext hContext = HttpClientContext.adapt(context);
				HttpRequest request = hContext.getRequest();
				//如果请求时幂等就再次尝试
				if(!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};
		return retry;
		
	}
	
	private static Registry<ConnectionSocketFactory> connectRegistry(){
		plainsf = PlainConnectionSocketFactory.getSocketFactory();
		sslsf = SSLConnectionSocketFactory.getSocketFactory();
		registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf).register("https",sslsf).build();
		return registry;
	}
	
	private static PoolingHttpClientConnectionManager pgccm() {
		cm = new PoolingHttpClientConnectionManager(connectRegistry());
		//将最大连接数增加
		cm.setMaxTotal(MAXTOTAL);
		//将每个路由基础连接增加
		cm.setDefaultMaxPerRoute(MAXPERROUTE);
		HttpHost host = new HttpHost(hostName, port);
		//将目标主机的最大连接数增加
		cm.setMaxPerRoute(new HttpRoute(host), MAXROUTE);
		return cm;
	}
	
	private static String font() {
		String str = "";
	    int hightPos;
	    int lowPos;
	    Random random = new Random();
	    hightPos = (176 + Math.abs(random.nextInt(39)));
	    lowPos = (161 + Math.abs(random.nextInt(93)));
	    byte[] b = new byte[2];
	    b[0] = (Integer.valueOf(hightPos)).byteValue();
	    b[1] = (Integer.valueOf(lowPos)).byteValue();
	    try {
	      str = new String(b, "GBK");
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	      System.out.println("错误");
	    }
	    return str;
	}
	
	public static String getFont(int num) {
		String s = "";
		for(int i = 0; i < num;i++) {
			s += font();
		}
		return s;
	}

}
