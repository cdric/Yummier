package com.sanfrenchiscan.yummier.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class FoodNotificationService extends IntentService {

	public FoodNotificationService() {
		super("test-service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Extract the receiver passed into the service
	    ResultReceiver rec = intent.getParcelableExtra("receiver");
	    // Extract additional values from the bundle
	    String val = intent.getStringExtra("foo");
	    // To send a message to the Activity, create a pass a Bundle
	    Bundle bundle = new Bundle();
	    bundle.putString("resultValue", "My Result Value. Passed in: " + val);
	    // Here we call send passing a resultCode and the bundle of extras
	    rec.send(Activity.RESULT_OK, bundle);
		
	}

}
