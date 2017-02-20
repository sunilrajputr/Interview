package com.aetion.sunil.wsautomation.apis;

import org.testng.annotations.BeforeClass;

public class BaseTest {

	protected static String token = null;

	public BaseTest() {
		super();
	}

	@BeforeClass
	public void beforeClass() {
		token = LoginAPI.Login();
		
	}

}