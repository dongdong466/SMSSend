package com.dong1990.sms.request;

public class SmsReportRequest {

	/**
	 * 用户账号，必填
	 */
	private String account;
	/**
	 * 用户密码，必填
	 */
	private String password;
	/**
	 * 拉取个数（最大100，默认20），选填
	 */
	private String count;
	
	private String key;
	
	public SmsReportRequest() {
		
	}
	public SmsReportRequest(String account, String password) {
		super();
		this.account = account;
		this.password = password;
	}
	public SmsReportRequest(String account, String password,String count,String key) {
		super();
		this.account = account;
		this.password = password;
		this.count = count;
		this.key = key;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}
