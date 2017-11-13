package com.sanfrenchiscan.yummier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sanfrenchiscan.yummier.constants.AppConstants;

public class Dish implements Serializable {
	
	private static final long serialVersionUID = 8058136384941995529L;
	
	private String name;
	private String description;
	private String date;
	private String station;
	private String cafe;
	
	private List<DishAttribute> dishAttributes = new ArrayList<DishAttribute>();
	private String dishServedFor;
	
	private String smallDishImage;
	private String largeDishImage;
	
	
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
	 * @return the isVegetarian
	 */
	public boolean isVegetarian() {
		return DishAttribute.containsDishAttribute(getDishAttributes(), AppConstants.BONAPPETIT_CORICONS_VEGETARIAN);
	}
	/**
	 * @return the isGlutenFree
	 */
	public boolean isGlutenFree() {
		return DishAttribute.containsDishAttribute(getDishAttributes(), AppConstants.BONAPPETIT_CORICONS_GLUTENFREE);
	}
	/**
	 * @return the isOrganic
	 */
	public boolean isOrganic() {
		return DishAttribute.containsDishAttribute(getDishAttributes(), AppConstants.BONAPPETIT_CORICONS_ORGANIC);
	}
	/**
	 * @return the isVegan
	 */
	public boolean isVegan() {
		return DishAttribute.containsDishAttribute(getDishAttributes(), AppConstants.BONAPPETIT_CORICONS_VEGAN);
	}
	
	
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * @return the dishServedFor
	 */
	public String getDishServedFor() {
		return dishServedFor;
	}
	/**
	 * @param dishServedFor the dishServedFor to set
	 */
	public void setDishServedFor(String dishServedFor) {
		this.dishServedFor = dishServedFor;
	}
	
	/**
	 * @return the dishAttributes
	 */
	public List<DishAttribute> getDishAttributes() {
		return dishAttributes;
	}
	
	/**
	 * @return the smallDishImage
	 */
	public String getSmallDishImage() {
		return smallDishImage;
	}
	/**
	 * @param smallDishImage the smallDishImage to set
	 */
	public void setSmallDishImage(String smallDishImage) {
		this.smallDishImage = smallDishImage;
	}
	/**
	 * @return the largeDishImage
	 */
	public String getLargeDishImage() {
		return largeDishImage;
	}
	/**
	 * @param largeDishImage the largeDishImage to set
	 */
	public void setLargeDishImage(String largeDishImage) {
		this.largeDishImage = largeDishImage;
	}
	private void addDishAttribute(DishAttribute attributeById) {
		dishAttributes.add(attributeById);
	}
	
	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * @return the cafe
	 */
	public String getCafe() {
		return cafe;
	}
	/**
	 * @param cafe the cafe to set
	 */
	public void setCafe(String cafe) {
		this.cafe = cafe;
	}
	/**
	 * Create a Dish object from json
	 * @param jsonObject the JSONObject that represent the dish
	 * @param date the startDate for the week this dish has been extracted for
	 * @param dishServedFor the type of dishes to lookup. Values are "B" for breakfast, "L" for lunch and "D" for dinner
	 * @return
	 */
	public static Dish fromJson(JSONObject jsonObject, DateTime date, String dishServedFor, String cafe, String station) {
		Dish dish = new Dish();
		
		try {			
			
			JSONArray mealTypesArr = jsonObject.getJSONArray("mealTypes");
			JSONObject mealTypeObj = mealTypesArr.getJSONObject(0);
			String mealType = mealTypeObj.getString("abbreviation");
			
			if (mealType.equals(dishServedFor)) {
				
				dish.cafe = cafe;
				dish.station = station;
				dish.dishServedFor = dishServedFor;
				
				dish.name = jsonObject.getString("description");
				dish.description = jsonObject.getString("description_full");
				
				int dayOfWeek = 1;
				
				// "days_of_week" can be either sindle or multiple
				// multiple = "1|2|3|4|5"
				// single = "3"
				// The code below extract the value for both case
				// In the case of a multiple value, we only pick up the first value
				// TODO: Understand why we even need to parse this value. Is this even used?
				
				String dow = jsonObject.getString("days_of_week");
				if (dow.length() == 1) {
				
					try {
					   dayOfWeek = Integer.valueOf(dow);
					} catch (NumberFormatException nfe) {
					   Log.e("error", "Can't process days_of_week: [" + jsonObject.getString("days_of_week") + "]");
					}
					
				} else {
					
					try {
						   dayOfWeek = Integer.valueOf(dow.indexOf(0));
					} catch (NumberFormatException nfe) {
					   Log.e("error", "Can't process days_of_week: [" + jsonObject.getString("days_of_week") + "]");
					}
					
				}
				
				DateTime adjustedDate = date.plusDays(dayOfWeek-1);
				DateTimeFormatter dtf = DateTimeFormat.forPattern("E MM/dd/yyyy");
				dish.date = adjustedDate.toString(dtf);		
				
				JSONArray corIconssArr = jsonObject.getJSONArray("corIcons");
				
				for (int i = 0; i<corIconssArr.length(); i++) {
				   String key = corIconssArr.getString(i);
				   dish.addDishAttribute(DishAttributeFactory.getAttributeById(key));
				}
				
			} else {
				// If the dish doesn't match what we are looking for, 
				// we return and empty object
				return null;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return dish;
	}

//	"cafe":{
//	      "_table":"cafes",
//	      "_primaryKey":"cafe_id",
//	      "account_id":"148",
//	      "location_id":"148",
//	      "cafe_id":"684",
//	      "name":"URL's Caf\u00e9",
//	      "opening_times":"<p><strong>Hours of Operation:</strong></p>\n<p>Open Monday Through Friday</p>\n<p> </p>\n<p><strong>Breakfast</strong> 7:30am - 10:30am <strong></strong></p>\n<p><strong>Breakfast Grill</strong> 7:30am - 10:00 am<strong></strong></p>\n<p><strong>Lunch</strong> 11:30am - 2:00 pm <strong></strong></p>\n<p><strong>Dinner (M-Th)</strong> 7:00pm - 8:30pm</p>",
//	      "menu":[
//	         {
//	            "_table":"menus",
//	            "_primaryKey":"menu_id",
//	            "cafe_id":"684",
//	            "menu_id":"40950",
//	            "start_date":"2014-01-27",
//	            "static":"0",
//	            "days":[
//	               {
//	                  "_table":"menus_days",
//	                  "_primaryKey":"day_id",
//	                  "day_id":"287485",
//	                  "menu_id":"40950",
//	                  "day_date":"2014-01-27",
//	                  "holiday":"N",
//	                  "stations":{
//	                     "4859":{
//	                        "_table":"cafes_stations",
//	                        "_primaryKey":"cafe_station_id",
//	                        "station_id":"4859",
//	                        "station":"breakfast",
//	                        "weight":"3",
//	                        "items":[
//	                           {
//	                             "_table":"menus_items",
//	                             "_primaryKey":"item_id",
//	                             "eni":[ ],
//                                 "nutritionFacts":[
//
//                                 ],
//                                 "isEniPlate":false,
//                                 "corIcons":[
//                                    "_1",
//                                    "_8"
//                                 ],
//                                 "mealTypes":[
//                                    {
//                                       "meal_type_id":"1",
//                                       "meal_type":"Breakfast",
//                                       "abbreviation":"B",
//                                       "weight":"1"
//                                    }
//                                 ],
//                                 "eni_ingredients":null,
//                                 "day_id":"287485",
//                                 "days_of_week":"1",
//                                 "meal_type_id":"1",
//                                 "description":"buttermilk pancakes",
//                                 "description_full":"with roasted bananas and hazelnut whipped cream",
//                                 "nutrition_info":"",
//                                 "item_id":"1969743",
//                                 "eni_connector":"",
//                                 "price":"",
//                                 "weight":"1",
//                                 "station_id":"4859",
//                                 "recipe_reference_number":"0",
//                                 "item_type":"basic_menu_item"
//                              }
//                           ]
//                        },
	    	
	/**
	 * Create a list of Dishes from jSon
	 * @param jsonObject
	 * @return
	 */
	public static ArrayList<Dish> fromJsonStations(JSONObject jsonObject, DateTime date, String dishedServedFor, String cafe) {
		ArrayList<Dish> dishes = new ArrayList<Dish>(jsonObject.length());

		Iterator<?> keys = jsonObject.keys();

		try {
			
			while( keys.hasNext() ){
	            String key = (String)keys.next();
	            
				//Log.d("debug", "processing key: " + key);
	            
	            JSONObject stationObj = jsonObject.getJSONObject(key);
	            JSONArray itemsArr = stationObj.getJSONArray("items");
	            
	            for (int j = 0; j < itemsArr.length(); j++) {
	            	
	            	//Log.d("debug", "processing key: " + key + " iteration=" + j);
	            	
	            	Dish dish = Dish.fromJson(itemsArr.getJSONObject(j), date, dishedServedFor, cafe, stationObj.getString("station"));
	            	if (dish != null) {
	            		dishes.add(dish);
	            	}
	            	
	            }
	            
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dishes;
	}
	public static List<Dish> filterByKeyword(List<Dish> dishes, String keyword) {
		
		List<Dish> results = new ArrayList<Dish>();
		for (Dish d: dishes) {
			if (d.containKeyword(keyword)) {
				results.add(d);
			}
		}
		
		return results;
		
	}
	private boolean containKeyword(String keyword) {
		
		// Query Normalization
		keyword = keyword.toLowerCase();
		Log.d("debug", "keyword: " + keyword);
		
		if (getName().toLowerCase().contains(keyword)) {
			Log.d("debug", "KW: " + keyword + " match on name for dish=" + getName());
			return true;
		}
		if (getDescription().toLowerCase().contains(keyword)) {
			Log.d("debug", "KW: " + keyword + " match on description for dish=" + getName());
			return true;
		}
		
		if (keyword.toLowerCase().contains(AppConstants.BONAPPETIT_CORICONS_GLUTENFREE) && isGlutenFree()) {
			Log.d("debug", "KW: " + keyword + " match on isGlutenFree for dish=" + getName());
			return true;
		}
		if (keyword.toLowerCase().contains(AppConstants.BONAPPETIT_CORICONS_ORGANIC) && isOrganic()) {
			Log.d("debug", "KW: " + keyword + " match on isOrganic for dish=" + getName());
			return true;
		}
		if (keyword.toLowerCase().contains(AppConstants.BONAPPETIT_CORICONS_VEGAN) && isVegan()) {
			Log.d("debug", "KW: " + keyword + " match on isVegan for dish=" + getName());
			return true;
		}
		if (keyword.toLowerCase().contains(AppConstants.BONAPPETIT_CORICONS_VEGETARIAN) && isVegetarian()) {
			Log.d("debug", "KW: " + keyword + " match on isVegetarian for dish=" + getName());
			return true;
		}
		
		if (getCafe().toLowerCase().contains(keyword)) {
			Log.d("debug", "KW: " + keyword + " match on cafe for dish=" + getName());
			return true;
		}
		if (getStation().toLowerCase().contains(keyword)) {
			Log.d("debug", "KW: " + keyword + " match on station for dish=" + getName());
			return true;
		}
		
		return false;
		
	}

}
