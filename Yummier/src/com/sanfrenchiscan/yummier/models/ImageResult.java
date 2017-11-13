package com.sanfrenchiscan.yummier.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Bean that represents an Image result
 * @author C�dric Lignier <cedric.lignier@free.fr>
 *
 */
public class ImageResult implements Serializable {

	private static final long serialVersionUID = 8309250267482186500L;
	
	private String fullUrl;
	private String thumbUrl;
	
	// Default constructor
	public ImageResult(JSONObject json) {
		
		try {
			this.fullUrl = json.getString("link");
			JSONObject imageJsonResults = json.getJSONObject("image");
			this.thumbUrl = imageJsonResults.getString("thumbnailLink");
			
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
		}
		
	}
	
	public String getFullUrl() {
		return fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}

	/**
	 * Create a list of ImageResult objects from a JSONArray
	 * @param array The JSONArray to parse
	 * @return
	 */
	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int x=0; x < array.length(); x++) {
			try {
				results.add(new ImageResult(array.getJSONObject(x)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	
	@Override
	public String toString() {
		return "ImageResult: \n" +
				" - fullUrl=[" + this.fullUrl + "]\n" 
				+ " - thumbUrl=[" + this.thumbUrl + "]";				
	}
	
}
