package com.sanfrenchiscan.yummier.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sanfrenchiscan.yummier.apps.Yummier;
import com.sanfrenchiscan.yummier.constants.AppConstants;

public class DishAttributeFactory {

	private static DishAttributeFactory instance = null;
	
	public static DishAttributeFactory getInstance() {
		if (instance == null) {
			instance = new DishAttributeFactory();
			Log.d("debug", "Initializaing DishAttributeFactory");
		}
		return instance;
	}
	
	List<DishAttribute> dishAttributes = new ArrayList<DishAttribute>();
	
	/**
	 * Main constructor
	 */
	public DishAttributeFactory() {
		
		Yummier.getBonAppetitRestClient().getDishAttributeForCafe(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int code, JSONObject body) {
				
				try {
					JSONObject corIconsObj = body.getJSONObject("corIcons");
					dishAttributes = DishAttribute.fromJsonCorIcons(corIconsObj);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.d("debug", "Done Initializaing DishAttributeFactory");
				
			}

			@Override
			public void onFailure(Throwable e, JSONObject error) {

				// Display an error message to the user
				Log.e("ERROR", e.toString());
				e.printStackTrace();
				
			}
			
		});
		
		
	}
	
	public static DishAttribute getAttributeById(String bonAppetitCode) {
		
		DishAttributeFactory factory = DishAttributeFactory.getInstance();
		List<DishAttribute> attributes = factory.getDishAttributes();
		
		for (DishAttribute att: attributes) {
			if (att.getBonAppetitCode().equals(bonAppetitCode)) {
				return att;
			}
		}
		
		return null;
		
	}

	/**
	 * @return the dishAttributes
	 */
	public List<DishAttribute> getDishAttributes() {
		return dishAttributes;
	}

	/**
	 * @param dishAttributes the dishAttributes to set
	 */
	public void setDishAttributes(List<DishAttribute> dishAttributes) {
		this.dishAttributes = dishAttributes;
	}
	
}
