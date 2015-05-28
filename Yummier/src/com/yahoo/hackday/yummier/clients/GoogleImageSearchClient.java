package com.yahoo.hackday.yummier.clients;

import android.content.Context;
import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yahoo.hackday.yummier.models.SearchFilters;

public class GoogleImageSearchClient {
	
	private final String API_BASE_URL = "https://ajax.googleapis.com/";
    private AsyncHttpClient client;
    
    private static GoogleImageSearchClient instance = null;

	public GoogleImageSearchClient(Context context) {
		super();
		this.client = new AsyncHttpClient();
	}
	
    public static GoogleImageSearchClient getInstance(Context context) {
    	if (instance == null) {
    		try {
				instance = new GoogleImageSearchClient(context);
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
	public void getImageForDish(AsyncHttpResponseHandler handler, String dishDescription) {
		
		SearchFilters filters = new SearchFilters();
		filters.setImageType("photo");
		
		String url = getApiUrl(buildGoogleImageSearchQuery(dishDescription, filters));
		client.get(url, null, handler);
	}
	
	/**
	 * Build the GoogleImageSearch query based on the user query and SearchFilters
	 * @param query the query from the user
	 * @param searchFilters the searchFilters preference from the user
	 * @param pageIndex the index of the page to return
	 * @return Return a formatter query to the Google Search API
	 * 
	 * Documentation:
	 * https://developers.google.com/image-search/v1/jsondevguide#json_reference
	 */
	private static String buildGoogleImageSearchQuery(String query,
			SearchFilters searchFilters) {
		
		int pageSize = 1;
		
		StringBuffer searchQuery = new StringBuffer();
		searchQuery.append("ajax/services/search/images?");
		searchQuery.append("v=" + "1.0");
		
		if (searchFilters != null) {
			if (searchFilters.getColorFilter() != null) {
				searchQuery.append("&imgcolor=" + searchFilters.getColorFilter());
			}
			if (searchFilters.getImageSize() != null) {
				searchQuery.append("&imgsz=" + searchFilters.getImageSize());
			}
			if (searchFilters.getImageType() != null) {
				searchQuery.append("&imgtype=" + searchFilters.getImageType());
			}
			if (searchFilters.getSiteFilter() != null) {
				searchQuery.append("&as_sitesearch=" + Uri.encode(searchFilters.getSiteFilter()));
			}
		}
		
		searchQuery.append("&rsz=" + pageSize);
		//searchQuery.append("&start=" + (pageSize * pageIndex));
		searchQuery.append("&q=" + Uri.encode(query));
		
		return searchQuery.toString();
	}
	
}