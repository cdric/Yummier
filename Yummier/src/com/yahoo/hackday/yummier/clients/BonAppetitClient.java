package com.yahoo.hackday.yummier.clients;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.hackday.yummier.constants.AppConstants;

public class BonAppetitClient {
	
	private final String API_BASE_URL = "http://legacy.cafebonappetit.com/api/1/";
    private AsyncHttpClient client;
    
    private static BonAppetitClient instance = null;

	public BonAppetitClient(Context context) {
		super();
		this.client = new AsyncHttpClient();
	}
	
    public static BonAppetitClient getInstance(Context context) {
    	if (instance == null) {
    		try {
				instance = new BonAppetitClient(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return instance;
    }
    
    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

	/**
	 * Fetch the menu for a given cafe and a given week
	 * 
	 * @param handler
	 */
	public void getMenuForCafe(AsyncHttpResponseHandler handler, String cafeId, String weekDate) {
		String url = getApiUrl("cafe/" + cafeId + "/date/" + weekDate + "/format/json");
		client.get(url, null, handler);
	}

	public void getDishAttributeForCafe(AsyncHttpResponseHandler handler) {
		String cafeId = AppConstants.BONAPPETIT_CAFE_YAHOO_URL;
		String weekDate = "2014-02-24";
		
		String url = getApiUrl("cafe/" + cafeId + "/date/" + weekDate + "/format/json");
		client.get(url, null, handler);
	}
	
}