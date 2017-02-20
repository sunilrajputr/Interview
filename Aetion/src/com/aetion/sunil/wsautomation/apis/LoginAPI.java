package com.aetion.sunil.wsautomation.apis;

import java.util.HashMap;

import org.apache.http.client.methods.HttpUriRequest;

import com.aetion.sunil.wsautomation.User;
import com.aetion.sunil.wsautomation.util.API;
import com.aetion.sunil.wsautomation.util.HttpType;
import com.aetion.sunil.wsautomation.util.RequestGenerator;
import com.aetion.sunil.wsautomation.util.RequestPaths;
import com.aetion.sunil.wsautomation.util.ResponseObject;

import net.sf.json.JSONObject;

public class LoginAPI extends Base {

	private static HashMap<String, String> hparam = new HashMap<>();

	public static User Login(String Username , String password ){

		String reqparam = "{ \"username\" : \""+Username+"\",\"password\" : \""+password+"\" }";

		System.out.println(" request " + reqparam);

		hparam.put("accept", "application/json");

		HttpUriRequest request  = RequestGenerator.generateRequest(null,HttpType.POST,url+RequestPaths.LOGIN_PATH,hparam,reqparam);

		ResponseObject jsonResponse = API.executeAPI(request);

		JSONObject result = API.getJson(jsonResponse);
		System.out.println(" result " + result);

		String token = result.containsKey("token") ? result.getString("token") : null ;

		User usr = new User();

		usr.setUsername(Username);
		usr.setPassword(password);

		if( token != null ){
			usr.setToken(token);
		}

		if( token == null ){
			usr.setCode(result.containsKey("code") ? result.getString("code") : null );
		}

		return  usr;

	}

	public static String Login() {
		return Login("srajput","srajput123").getToken();
	}
}
