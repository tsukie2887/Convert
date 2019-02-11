package com.pershing.convert.service.impl;


import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

//import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pershing.convert.entities.ConvertData;
import com.pershing.convert.ConvertApp;
import com.pershing.convert.service.HttpService;

@Service
public class HttpServiceImpl implements HttpService {
	
	@Value("${apServerUrl}")
	private String url;
	
	private static Logger logger = LoggerFactory.getLogger(ConvertApp.class);

	@Override
	public String sendPutDataByJson(ConvertData convertData) {
        String result = "";
		try {
	        	
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	
	        HttpPut httpPut = new HttpPut(url);
	        Gson gson = new Gson();
	        String json = gson.toJson(convertData);
	        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
	        stringEntity.setContentEncoding("utf-8");
	        httpPut.setEntity(stringEntity);
	
	        CloseableHttpResponse response = httpClient.execute(httpPut);
	
	        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	            result = EntityUtils.toString(response.getEntity(), "utf-8");
	        }
	
	        response.close();
        }
		catch(Exception e) {
			logger.error(e.getMessage());
			
		}
		
        return result;
    }
}
