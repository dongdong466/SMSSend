package com.dong1990.demo;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.dong1990.sms.request.SmsPullRequest;
import com.dong1990.sms.response.SmsPullRespnse;
import com.dong1990.sms.util.ChuangLanSmsUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;


public class SmsPullDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2771466";
	// 用户平台API密码(非登录密码)
	public static String pswd = "";
	

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 4、查询上行明细
		String smsPullRequestUrl = "http://vsms.253.com/msg/pull/mo";
		//上行拉取条数
		String count = "10";
		
		SmsPullRequest smsPullRequest = new SmsPullRequest(account, pswd);

		String requestJson = JSON.toJSONString(smsPullRequest);

		System.out.println("before request string is: " + requestJson);

		String response = ChuangLanSmsUtil.sendSmsByPost(smsPullRequestUrl, requestJson);

		System.out.println("response after request result is : " + response);

		SmsPullRespnse smsPullRespnse = JSON.parseObject(response, SmsPullRespnse.class);

		System.out.println("response  toString is : " + smsPullRespnse.getResult());

		/*
		 *  "spCode":"10690364712106",
            "moTime":"1704171547",
            "messageContent":" 少年心事当拏云-祝我们马到成功",
            "destCode":"10690364712106",
            "mobile":"18670306453"
		 * */
		JsonParser parser = new JsonParser();
		try{
			JsonObject object = (JsonObject)parser.parse(response);
			JsonArray array = object.get("result").getAsJsonArray();
			if(!(array == null || array.size() == 0)){
				for(int i = 0;i < array.size();i++){
					System.out.println("-------------");
					JsonObject subObject = array.get(i).getAsJsonObject();
					System.out.println("spCode="+subObject.get("spCode").getAsString());
					System.out.println("moTime="+subObject.get("moTime").getAsString());
					System.out.println("messageContent="+subObject.get("messageContent").getAsString());
					System.out.println("destCode="+subObject.get("destCode").getAsString());
					System.out.println("mobile="+subObject.get("mobile").getAsString());
				}
			}else{
				System.out.println("\n数据查询为null");
			}
			
		}catch(JsonIOException e){
			e.printStackTrace();
		}
	}
}
