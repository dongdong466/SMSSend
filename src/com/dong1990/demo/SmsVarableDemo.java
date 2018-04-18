package com.dong1990.demo;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.dong1990.sms.request.SmsVarableRequest;
import com.dong1990.sms.response.SmsVarableResponse;
import com.dong1990.sms.util.ChuangLanSmsUtil;


public class SmsVarableDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2771466";
	// 用户平台API密码(非登录密码)
	public static String pswd = "tynJ0LhYFM7efe";

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		//变量短信发送
		String smsVarableRequestUrl = "http://vsms.253.com/msg/variable/json";
		//短信内容
		int rand_code = (int) (Math.random()*1000000);
		String msg = "【冬雪霓凰】尊敬的{$var}VIP金卡会员,您好,谢谢您的验证码测试："+rand_code+"，VIP金卡会员";
		//参数组																
		String params = "18670306453,张冬晖";// ;18721605772,小红
		// 定时
		//String sendtime = "201704171711";
		SmsVarableRequest smsVarableRequest=new SmsVarableRequest(account, pswd, msg, params);
		
        String requestJson = JSON.toJSONString(smsVarableRequest);
		
		System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsVarableRequestUrl, requestJson);
		
		System.out.println("response after request result is : " + response);
		
		SmsVarableResponse smsVarableResponse = JSON.parseObject(response, SmsVarableResponse.class);
		
		System.out.println("response  toString is : " + smsVarableResponse);
		
		
	
	}
}
