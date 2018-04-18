package com.dong1990.demo;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.dong1990.sms.request.SmsBalanceRequest;
import com.dong1990.sms.response.SmsBalanceResponse;
import com.dong1990.sms.util.ChuangLanSmsUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;

public class SmsBalanceDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2771466";//N2771466
	// 用户平台API密码(非登录密码)
	public static String pswd = "tynJ0LhYFM7efe";//tynJ0LhYFM7efe
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
	
		//查询余额地址
       String smsBalanceRequestUrl = "http://smssh1.253.com/msg/balance/json";
	
		SmsBalanceRequest smsBalanceRequest=new SmsBalanceRequest(account, pswd);
		
        String requestJson = JSON.toJSONString(smsBalanceRequest);
		
		System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsBalanceRequestUrl, requestJson);
		
		System.out.println("response after request result is : " + response);
		
		SmsBalanceResponse smsVarableResponse = JSON.parseObject(response, SmsBalanceResponse.class);
		
		System.out.println("response  toString is : " + smsVarableResponse);
		
		JsonParser parser = new JsonParser();
		String code = null;
		String balance = null;
		String errorMsg = null;
		try{
			JsonObject object = (JsonObject)parser.parse(response);
			code = object.get("code").getAsString();
			if(code.equals("0")){
				balance = object.get("balance").getAsString();
			}else{
				errorMsg = object.get("errorMsg").getAsString();
			}
		}catch(JsonIOException e){
			e.printStackTrace();
		}
		
		System.out.println("--------------------");
		if(code.equals("0")){
			System.out.println("余额剩余:"+balance);
		}else{
			System.out.println("查询错误：\n\t错误码："+code+"\n\t错误信息："+errorMsg);
		}
	}
}
