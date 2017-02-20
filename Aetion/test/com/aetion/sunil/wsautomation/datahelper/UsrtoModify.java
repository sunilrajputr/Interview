package com.aetion.sunil.wsautomation.datahelper;

public class UsrtoModify {

	private String key;
	private String oldValue;
	private String newValue;
	
	public UsrtoModify key(String Key) {
		this.key = Key;
		return this;
	}

	public UsrtoModify oldValue(String oldVal) {
		this.oldValue = oldVal;
		return this;
	}

	public UsrtoModify newValue(String newVal) {
		this.newValue = newVal;
		return this;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public String getNewVal(){
		return this.newValue;
	}
	
	public String oldValue(){
		return this.oldValue;
	}
}
