package com.aetion.sunil.wsautomation.apis;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aetion.sunil.wsautomation.User;
import com.aetion.sunil.wsautomation.datahelper.UsrtoModify;
import com.aetion.sunil.wsautomation.dataprovider.UserData;

public class UserTest extends BaseTest {

	ArrayList<User> insertedUsers = new ArrayList<>();
	private static Logger log = Logger.getLogger(UserTest.class);

	@Test (dataProvider = "CreateUsr" , dataProviderClass = UserData.class  , priority = 1)
	public void CreateUsr(ArrayList testData  , ArrayList expectedResult ) {

		log.debug(" Creating user " );
		User usr = UserAPI.createUser(token,testData);

		Assert.assertEquals(usr.getEmail(), expectedResult.get(0));
		Assert.assertEquals(usr.getFirst_name(), expectedResult.get(1));
		Assert.assertEquals(usr.getLast_name(), expectedResult.get(2));
		Assert.assertEquals(Integer.toString(usr.getAge()),expectedResult.get(3) );
		Assert.assertNotEquals(0, usr.getId());

		insertedUsers.add(usr);

	}

	@Test (dependsOnMethods = {"CreateUsr"} , priority = 2)
	public void getUserDetails() {

		log.info("In Get User Details");
		User actualusr = new User();
		User expectedUsr = new User();

		if ( insertedUsers != null && insertedUsers.size() > 1 )
		{
			expectedUsr = insertedUsers.get(0);
			actualusr = UserAPI.getUserDetails(token,expectedUsr.getId() );
		}

		Assert.assertEquals(actualusr.getEmail(), expectedUsr.getEmail());
		Assert.assertEquals(actualusr.getFirst_name(), expectedUsr.getFirst_name());
		Assert.assertEquals(actualusr.getLast_name(), expectedUsr.getLast_name());
		Assert.assertEquals(actualusr.getAge(),expectedUsr.getAge() );
		Assert.assertEquals(actualusr.getId(),expectedUsr.getId() );

	}

	@Test (dataProvider = "ModifyUsr" , dataProviderClass = UserData.class , dependsOnMethods = {"CreateUsr"} , priority = 3)
	public void ModifyUser(String email , UsrtoModify userToModify , String missingDataKey , String missingDataVal ){

		User originalUsr = new User();

		String id  = retriveUserId_Email(email);

		originalUsr = UserAPI.getUserDetails(token, id);

		User modifiedUser = UserAPI.modifyUser(token,userToModify,email,missingDataKey,missingDataVal,originalUsr.getId());

		Assert.assertEquals(getSpecifiedKeyValue(modifiedUser,userToModify.getKey()), userToModify.getNewVal());

	}

	private String retriveUserId_Email(String email) {

		for( int i =0;i<insertedUsers.size();i++)
		{
			if(email.equalsIgnoreCase(insertedUsers.get(i).getEmail()))
				return insertedUsers.get(i).getId();
		}
		log.debug(" User id not retrived " );

		return null;
	}

	private String getSpecifiedKeyValue(User user , String key) {

		if( key.equalsIgnoreCase("First_name"))
			return user.getFirst_name();

		if( key.equalsIgnoreCase("age"))
			return Integer.toString(user.getAge());

		if( key.equalsIgnoreCase("last_name"))
			return user.getLast_name();

		if( key.equalsIgnoreCase("email"))
			return user.getEmail();

		if( key.equalsIgnoreCase("id"))
			return user.getId();

		return null;

	}

	@Test ( dependsOnMethods = {"CreateUsr"} , priority = 4 )
	public void SearchUser()
	{

		ArrayList<User> searchResponse = UserAPI.searchUser(token, 35, 45);
		Assert.assertNotEquals(0, searchResponse.size());

	}

}
