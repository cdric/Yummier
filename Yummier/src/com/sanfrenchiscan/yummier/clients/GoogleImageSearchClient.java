package com.sanfrenchiscan.yummier.clients;

import android.content.Context;
import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sanfrenchiscan.yummier.constants.AppConstants;
import com.sanfrenchiscan.yummier.models.SearchFilters;

public class GoogleImageSearchClient {
	
	// Google Custom Image Search Reference: https://developers.google.com/custom-search/json-api/v1/reference/cse/list
	// This class was previously using Google Image Search that got deprecated in 2011
	// Previous reference was: https://developers.google.com/image-search/v1/jsondevguide#json-reference
	
	private final String API_BASE_URL = "https://www.googleapis.com/";
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
		filters.setColorFilter("color");
		filters.setImageSize("medium");
		filters.setSearchType("image");
		
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
	 * https://developers.google.com/custom-search/json-api/v1/reference/cse/list
	 */
	private static String buildGoogleImageSearchQuery(String query,
			SearchFilters searchFilters) {
		
		int pageSize = 1;
		
		StringBuffer searchQuery = new StringBuffer();
		searchQuery.append("customsearch/v1?");
		//searchQuery.append("v=" + "1.0"); // TODO: CONFIRM NOT USED ANYMORE & THEN DELETE
		
		// Query
		searchQuery.append("q=" + Uri.encode(query));
		
		// API Key
		searchQuery.append("&key=" + Uri.encode(AppConstants.GOOGLESEARCH_APIKEY_YUMMIER));
		
		// Custom Search Engine (define on the Google Management console at: 
		searchQuery.append("&cs=" + Uri.encode(AppConstants.GOOGLESEARCH_CS_YUMMIER));
		
		if (searchFilters != null) {
			if (searchFilters.getColorFilter() != null) {
				searchQuery.append("&imgColorType=" + searchFilters.getColorFilter());
			}
			if (searchFilters.getImageSize() != null) {
				searchQuery.append("&imgSize=" + searchFilters.getImageSize());
			}
			if (searchFilters.getImageType() != null) {
				searchQuery.append("&imgType=" + searchFilters.getImageType());
			}
			if (searchFilters.getSiteFilter() != null) {
				searchQuery.append("&siteSearch=" + Uri.encode(searchFilters.getSiteFilter()));
			}
		}
		
		// Number of search results to return per page
		searchQuery.append("&num=" + pageSize);
		//searchQuery.append("&start=" + (pageSize * pageIndex));
		
		return searchQuery.toString();
	}
	
}