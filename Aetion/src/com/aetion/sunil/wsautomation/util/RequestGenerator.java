package com.aetion.sunil.wsautomation.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

public class RequestGenerator {

	/**
	 * @param token
	 * @param httpType
	 * @param requestURL
	 * @param headers
	 * @param requestBody
	 * @return
	 */
	public static HttpUriRequest generateRequest(String token, HttpType httpType,
			String requestURL, HashMap<String, String> headers, String requestBody) {
		HttpUriRequest httpRequest=null;



		switch (httpType) {
		case POST: {

			HttpPost post = new HttpPost(requestURL);
			if (requestBody != null && !requestBody.isEmpty() ) {
				try {
					post.setEntity(new StringEntity(requestBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				post.setHeader("Content-Type", "application/json;charset=UTF-8");
			}
			httpRequest = post;
			if(token!=null){
				httpRequest.setHeader("X-Auth-Token",token);
			}else if(headers != null){
				for (Map.Entry head: headers.entrySet()) {
					httpRequest.setHeader(String.valueOf(head.getKey()), String.valueOf(head.getValue()));
				}
			}
			break;
		}
		case GET: {
			httpRequest = new HttpGet(requestURL);
			if(token!=null){
				httpRequest.setHeader("X-Auth-Token",token);
			}else if(headers != null){
				for (Map.Entry head: headers.entrySet()) {
					httpRequest.setHeader(String.valueOf(head.getKey()), String.valueOf(head.getValue()));
				}
			}
			break;
		}

		case PUT: {
			HttpPut put = new HttpPut(requestURL);

			if (requestBody != null) {
				try {
					put.setEntity(new StringEntity(requestBody, "UTF-8"));
					put.setHeader("Content-Type",
							"application/json;charset=UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
			}
			httpRequest = put;
			if(token!=null){
				httpRequest.setHeader("X-Auth-Token",token);
			}else if(headers != null){
				for (Map.Entry head: headers.entrySet()) {
					httpRequest.setHeader(String.valueOf(head.getKey()), String.valueOf(head.getValue()));
				}
			}
			break;
		}
		}
		return httpRequest;
	}

	public static String generateRequestBody(ArrayList reqParamVal , ArrayList reqParamKey){

		StringBuilder reqBody = new StringBuilder();
		reqBody.append("{");

		if(reqParamVal.size() == reqParamKey.size()){

			for ( int i=0;i<reqParamKey.size();i++){
				reqBody.append("\"").append(reqParamKey.get(i)).append("\" : \"").append(reqParamVal.get(i)).append("\"");
				if( reqParamKey.size() - ( i+1) > 0)
					reqBody.append(",");
			}
		}
		reqBody.append("}");

		return reqBody.toString();
	}
}
