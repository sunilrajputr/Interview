package com.aetion.sunil.wsautomation.apis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;

import com.aetion.sunil.wsautomation.User;
import com.aetion.sunil.wsautomation.datahelper.UsrtoModify;
import com.aetion.sunil.wsautomation.util.API;
import com.aetion.sunil.wsautomation.util.HttpType;
import com.aetion.sunil.wsautomation.util.RequestGenerator;
import com.aetion.sunil.wsautomation.util.RequestPaths;
import com.aetion.sunil.wsautomation.util.ResponseObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserAPI extends Base {

	private static HashMap<String, String> hparam = new HashMap<>();

	private static Logger log = Logger.getLogger(UserAPI.class);

	public static User createUser(String token, ArrayList reqParam  ){

		String reqBody = RequestGenerator.generateRequestBody(reqParam, new ArrayList (Arrays.asList("email","first_name","last_name","age")));

		log.debug(" request : " + reqBody);

		hparam.put("accept", "application/json");

		HttpUriRequest request  = RequestGenerator.generateRequest(token,HttpType.POST,url+RequestPaths.CREATE_USER_PATH,hparam,reqBody);

		ResponseObject jsonResponse = API.executeAPI(request);

		JSONObject result = API.getJson(jsonResponse);

		log.debug(" result " + result);

		String id = result.containsKey("id") ? result.getString("id") : null ;

		User usr = new User();

		if( id != null ){
			populateUserDetails(usr,result);

			log.debug( " Created User " + usr );
		}
		return usr;

	}

	public static ArrayList<User> searchUser(String token, int startAge , int endAge ){

		String reqBody = "{ \"start_age\" : "+startAge+", \"end_age\":"+ endAge + "}";              

		log.debug(" Search User request : " + reqBody);

		hparam.put("accept", "application/json");

		HttpUriRequest request  = RequestGenerator.generateRequest(token,HttpType.POST,url+RequestPaths.SEARCH_USER_PATH,hparam,reqBody);

		ResponseObject jsonResponse = API.executeAPI(request);

		JSONArray result = API.getJsonArray(jsonResponse);

		ArrayList<User> foundUsers = new ArrayList();

		log.debug(" Search User result " + result);

		for( int i =0;i<result.size();i++)
		{
			log.debug(" individula search response " + (JSONObject) result.get(i));
			User usr = new User();
			populateUserDetails(usr, (JSONObject) result.get(i));
			foundUsers.add(usr);
		}

		return foundUsers;

	}

	public static User modifyUser (String token ,UsrtoModify userToModify ,String email, String missingDataKey, String missingDataVal, String id ){

		String reqBody = "{ \"email\" : \""+email+"\",\""+ missingDataKey +"\" : \""+missingDataVal+"\" , \"" + userToModify.getKey() +"\": \""+userToModify.getNewVal() + "\" }";              

		log.debug(" Modify request : " + reqBody);

		hparam.put("accept", "application/json");

		HttpUriRequest request  = RequestGenerator.generateRequest(token,HttpType.PUT,url+RequestPaths.GET_USER_PATH+id,hparam,reqBody);

		ResponseObject jsonResponse = API.executeAPI(request);

		JSONObject result = API.getJson(jsonResponse);

		log.debug(" Modify result " + result);

		User usr = new User();

		populateUserDetails(usr,result);
		return usr;

	}

	private static void populateUserDetails(User usr, JSONObject result) {

		usr.setFirst_name(result.getString("first_name"));
		usr.setLast_name(result.getString("last_name"));
		usr.setEmail(result.getString("email"));
		usr.setAge(result.getInt("age"));
		usr.setId(result.getString("id"));

	}

	public static User getUserDetails(String token, String id){

		log.debug(" request by id : " + id);

		hparam.put("accept", "application/json");

		HttpUriRequest request  = RequestGenerator.generateRequest(token,HttpType.GET,url+RequestPaths.GET_USER_PATH+id,hparam,null);

		ResponseObject jsonResponse = API.executeAPI(request);

		JSONObject result = API.getJson(jsonResponse);

		log.debug(" result " + result);

		String receivedid = result.containsKey("id") ? result.getString("id") : null ;

		User usr = new User();

		if( receivedid != null ){
			
			populateUserDetails(usr, result);

			log.debug( " Received user details "+ usr );
		}
		return usr;

	}
}
