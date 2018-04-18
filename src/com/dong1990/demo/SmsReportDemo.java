package com.dong1990.demo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.dong1990.sms.request.SmsReportRequest;
import com.dong1990.sms.response.SmsReportRespnse;
import com.dong1990.sms.util.ChuangLanSmsUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SmsReportDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2771466";//N2771466
	// 用户平台API密码(非登录密码)
	public static String pswd = "a.123456";//tynJ0LhYFM7efe
	

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 查询状态报告地址
		String smsReportRequestUrl = "http://smssh1.253.com/msg/pull/report";
		//状态报告拉取条数
		String count = "10";
		String key ="du64-90we";
		SmsReportRequest smsReportRequest = new SmsReportRequest(account, pswd);

		String requestJson = JSON.toJSONString(smsReportRequest);

		System.out.println("before request string is: " + requestJson);

		String response = ChuangLanSmsUtil.sendSmsByPost(smsReportRequestUrl, requestJson);

		System.out.println("response after request result is : " + response);

		SmsReportRespnse smsReportRespnse = JSON.parseObject(response, SmsReportRespnse.class);

		System.out.println("response  toString is : " + smsReportRespnse.getResult());

		/**
		 * {
            "uid":"ios",
            "reportTime":"1704251141",
            "notifyTime":"1493091673093",
            "status":"DELIVRD",
            "msgId":"17042511411025394",
            "statusDesc":"短信发送成功",
            "mobile":"18670306453"
        },
		 * */
		JsonParser parser = new JsonParser();
		try{
			JsonObject obj = (JsonObject) parser.parse(response);
			System.out.println("ret="+obj.get("ret").getAsString());
			JsonArray array = obj.get("result").getAsJsonArray();
			if(!(array == null || array.size() == 0)){
				for(int i = 0;i < array.size();i++){
					System.out.println("---------------------");
					JsonObject obj_arr = array.get(i).getAsJsonObject();
					System.out.println("reportTime= "+obj_arr.get("reportTime").getAsString());
					System.out.println("status= "+obj_arr.get("status").getAsString());
					System.out.println("msgId= "+obj_arr.get("msgId").getAsString());
					System.out.println("mobile= "+obj_arr.get("mobile").getAsString());
					System.out.println("statusDesc= "+obj_arr.get("statusDesc").getAsString());
					System.out.println("notifyTime= "+obj_arr.get("notifyTime").getAsString());
					System.out.println("uid= "+obj_arr.get("uid").getAsString());
					String notifyTime = obj_arr.get("notifyTime").getAsString();
					//System.out.println("notifyTime to date:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Long(notifyTime)));
				}
			}else{
				System.out.println("数据查询为null");
			}
		}catch(JsonIOException e){
			e.printStackTrace();
		}
	}
}
