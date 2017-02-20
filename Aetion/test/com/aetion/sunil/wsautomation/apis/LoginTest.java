package com.aetion.sunil.wsautomation.apis;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aetion.sunil.wsautomation.User;
import com.aetion.sunil.wsautomation.dataprovider.LoginData;

public class LoginTest {

	@Test (dataProvider = "ValidLogin" , dataProviderClass = LoginData.class , priority=1 )
	public void Login(String Username , String password) {

		User usr = LoginAPI.Login(Username, password);
		Assert.assertNotNull(usr.getToken());
	}

	@Test (dataProvider = "InvalidLogin" , dataProviderClass = LoginData.class , priority=2)
	public void InValidLogin(String Username , String password , String expected) {

		User usr = LoginAPI.Login(Username, password);
		Assert.assertEquals(usr.getCode(), expected);
	}


}
