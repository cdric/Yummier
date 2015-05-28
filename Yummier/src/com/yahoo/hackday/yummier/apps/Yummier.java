package com.yahoo.hackday.yummier.apps;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yahoo.hackday.yummier.clients.BonAppetitClient;
import com.yahoo.hackday.yummier.clients.GoogleImageSearchClient;

/*
 * This is the Android application itself and is used to configure various settings
 *     
 */
public class Yummier extends com.activeandroid.app.Application {
	
	private static Context context;
		
	@Override
	public void onCreate() {
		super.onCreate(); 
		Yummier.context = this;
		
		// -- Initialize ActiveAndroid --
		ActiveAndroid.initialize(this);

		// Create global configuration and initialize ImageLoader with this configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);
		
	}
	
	@Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
	
	public static BonAppetitClient getBonAppetitRestClient() {
		return (BonAppetitClient) BonAppetitClient.getInstance(Yummier.context);
	}
	
	public static GoogleImageSearchClient getGoogleImageSearchRestClient() {
		return (GoogleImageSearchClient) GoogleImageSearchClient.getInstance(Yummier.context);
	}
	

}