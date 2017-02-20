package com.aetion.sunil.wsautomation.dataprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.aetion.sunil.wsautomation.datahelper.UsrtoModify;
import com.aetion.sunil.wsautomation.util.CSVReader;

public class UserData {

	@DataProvider(name = "CreateUsr")
	public static Iterator<Object[]> CreateUsr() {

		ArrayList<Object[]> filedata = CSVReader.readFile("resources/create_user.csv");
		ArrayList<Object[]> ar = new ArrayList();

		for( int i =0 ;i < filedata.size() ; i++){

			Object[] tmp1 =  {new ArrayList(Arrays.asList(filedata.get(i))),new ArrayList(Arrays.asList(filedata.get(i)))};

			ar.add(tmp1);
		}
		return ar.iterator();
	}	

	@DataProvider(name = "ModifyUsr")
	public static Object[][] ModifyUsr() {

		return new Object[][] {
			{"george@example.com", new UsrtoModify().key("first_name").oldValue("Goerge").newValue("Jorge"),"last_name","Pearson"},
			{"dan@oprqu.test", new UsrtoModify().key("last_name").oldValue("Ankemah").newValue("Ankomah"),"first_name","Dan"}
		};
	}	

}
