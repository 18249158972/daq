package com.cmcc.andedu.microservice.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONObject;

import ytx.org.apache.http.HttpResponse;
import ytx.org.apache.http.client.HttpClient;
import ytx.org.apache.http.client.methods.HttpGet;
import ytx.org.apache.http.impl.client.DefaultHttpClient;

public class WeixinApi {
	private final static String APPID = "wx4475b88a32786954";
	private final static String SECRET = "eaef26d4467b7ebcca5c35f425182838";
//	private final static String WEIXIN_CODE = "smartSafeFood";//公众号

	
	public static JSONObject getOpenid(String js_code){
		JSONObject jsonObj = null;
		String requestUrl ="https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+SECRET+"&js_code="+js_code+"&grant_type=authorization_code";
		try {
			
			HttpGet httpGet = new HttpGet(requestUrl);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpGet);
			
			// 获取响应输入流
            InputStream inStream = response.getEntity().getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
	        StringBuilder strber = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null)
	         strber.append(line + "\n");
	         inStream.close();
	         jsonObj = JSONObject.parseObject(strber.toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj;
	}

}
