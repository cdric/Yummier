package com.sanfrenchiscan.yummier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DishAttribute implements Serializable {
	
	private static final long serialVersionUID = 654730889508908150L;

	private String bonAppetitCode;
	
	private String name;
	private String description;
	
	private String imageUrl;
	private String color;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the bonAppetitCode
	 */
	public String getBonAppetitCode() {
		return bonAppetitCode;
	}
	/**
	 * @param bonAppetitCode the bonAppetitCode to set
	 */
	public void setBonAppetitCode(String bonAppetitCode) {
		this.bonAppetitCode = bonAppetitCode;
	}
	
	private static DishAttribute fromJson(JSONObject corIcon, String key) {
		DishAttribute attr = new DishAttribute();
		try {				
			attr.bonAppetitCode = key;
			attr.color = corIcon.getString("color");
			attr.description = corIcon.getString("mouseover");
			attr.imageUrl = corIcon.getString("imageSrc");
			attr.name = corIcon.getString("type");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return attr;
	}
	
//	"corIcons":{
//    "_6":{
//       "_table":"menus_items_types_options",
//       "_primaryKey":"type_id",
//       "type_id":"6",
//       "type":"farm to fork",
//       "color":"d58f59",
//       "weight":"1",
//       "mouseover":"Farm to Fork: Contains ingredients that are seasonal and minimally processed and purchased from a local farmer or artisan",
//       "imageSrc":"http://legacy.cafebonappetit.com/images/public/menu-item-type-d58f59.png",
//       "image64Src":"http://legacy.cafebonappetit.com/images/public/menu-item-type-d58f59-64.png"
//    },
	
	public static List<DishAttribute> fromJsonCorIcons(JSONObject jsonObject) {
		ArrayList<DishAttribute> dishAttrs = new ArrayList<DishAttribute>(jsonObject.length());

		Iterator<?> keys = jsonObject.keys();

		try {
			
			while( keys.hasNext() ){
	            String key = (String)keys.next();	            
				JSONObject corIcon = jsonObject.getJSONObject(key);
				
				//Log.d("debug", "processing DishAttribute key = " + key);
            	
            	DishAttribute dishAttribute = DishAttribute.fromJson(corIcon, key);
            	
            	//Log.d("debug", " -> returning DishAttribute obj = " + dishAttribute);
            	
            	if (dishAttribute != null) {
            		dishAttrs.add(dishAttribute);
            	}
	            
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dishAttrs;
	}
	
	/**
	 * Identify if a given attribute exist from the list provided
	 * @param dishAttributes the list of attributes to parse 
	 * @param bonAppetitCode the code to lookup
	 * @return
	 */
	public static boolean containsDishAttribute(
			List<DishAttribute> dishAttributes, String bonAppetitCode) {
		
		for (DishAttribute att: dishAttributes) {
			if (att.getBonAppetitCode().equals(bonAppetitCode)) {
				return true;
			}
		}
		
		return false;
		
	}

}
