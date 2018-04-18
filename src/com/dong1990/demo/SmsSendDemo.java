package com.dong1990.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.dong1990.sms.request.SmsSendRequest;
import com.dong1990.sms.response.SmsSendResponse;
import com.dong1990.sms.util.ChuangLanSmsUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SmsSendDemo{
	
	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2771466";//N2771466
	// 用户平台API密码(非登录密码)
	public static String pswd = "a.123456";//tynJ0LhYFM7efe

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 表情
		String testbytes2 = new String(hexstr2bytes("0xF0 0x9F 0x8C 0xB9"),"utf-8");//玫瑰
		
		int random = (int)(Math.random()*1000000);
		
		// 普通短信地址
		String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
		//手机号码
		String phone = "18670306453";
		// 短信内容 冬雪霓裳
		String msg = "【冬雪霓裳】手机为"+phone+"的用户登录验证码是"+random+"。";
	   //String msg = "【冬雪霓凰】您好"+testbytes2+"，手机为"+phone+"的用户登录验证码是"+random+"，100%请勿将此验证码透漏给第三方。如非本人操作，可不用理会。";
		//String msg="【冬雪霓凰】小家伙，该起床上厕所，快起来，上厕所啦。。。";
	    msg = URLEncoder.encode(msg,"utf-8");
	    // 状态报告
	    String report = "true";
		// 定时
		String sendtime = "201709300535";
	    // 标记ID
	    String uid = "ios";
	    String extend = "520";
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report,sendtime);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		System.out.println("response after request result is :" + response);
				
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
		
		System.out.println("-------------------");
		String code_string = null;
		String errorMsg = null;
		//将json数据转为json对象
				JsonParser parser = new JsonParser();
				try{
					JsonObject object = (JsonObject) parser.parse(response);
					System.out.println("code="+object.get("code").getAsString());
					code_string = object.get("code").getAsString();
					errorMsg = object.get("errorMsg").getAsString();
				}catch(JsonIOException e){
					e.printStackTrace();
				}
		if(code_string.equals("0")){
			System.out.println(phone+"号码的用户短信下发成功");
		}else{
			System.out.println("短信下发失败，\n\t错误码："+code_string+"\n\t错误信息："+errorMsg);
		}
		
	}
	public static byte[] hexstr2bytes(String hexstr){
		String[] hexstrs = hexstr.split(" ");
		byte[] b = new byte[hexstrs.length];
		
		for(int i=0;i<hexstrs.length;i++){
			b[i] = hexStringToByte(hexstrs[i].substring(2))[0];
		}
		return b;
	}
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
}
