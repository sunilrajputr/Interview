package com.aetion.sunil.wsautomation.util;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.aetion.sunil.wsautomation.apis.UserAPI;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class API {	
	
	private static Logger log = Logger.getLogger(API.class);

	public static ResponseObject executeAPI(HttpUriRequest request) {

		ResponseObject jsonResponse = new ResponseObject();
		log.debug(" in executeAPI ");
		
		try
		{

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpResponse response = httpClient.execute(request);

//			if (response.getStatusLine().getStatusCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatusLine().getStatusCode());
//			}

			if( response != null){

				jsonResponse.setHeaders(response.getAllHeaders());
				jsonResponse.setStatusCode(response.getStatusLine().getStatusCode());

				if(response.getEntity()!=null)
					jsonResponse.setBody(EntityUtils.toString(response.getEntity()));

			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return jsonResponse;
	}

	public static JSONObject getJson(ResponseObject response) {
		JSONObject result = new JSONObject();
		if (response != null && response.getBody() != null) {
			if (!response.getBody().equals("")) {
				try{
					result = JSONObject.fromObject(response.getBody());
				}catch (JSONException e){
					log.debug("Unable to convert to JSONObject:" + response.toString());
				}
				return result;
			} else {
				return JSONObject.fromObject("{result:'no response'}");
			}
		}
		return result;
	}


	public static JSONArray getJsonArray(ResponseObject response) {
        return JSONArray.fromObject(response.getBody());

    }


}
