package com.aetion.sunil.wsautomation.dataprovider;

import org.testng.annotations.DataProvider;

public class LoginData {

	@DataProvider(name = "ValidLogin")
	public static Object[][] ValidLogin() {

		return new Object[][] {
			{ "srajput", "srajput123"}
		};
	}
	
	@DataProvider(name = "InvalidLogin")
	public static Object[][] InvalidLogin() {

		return new Object[][] {
			{ "srajput1", "srajput123" ,"Invalid Credentials"}
		};
	}
	

}
