package com.yahoo.hackday.yummier.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class FoodNotificationReceiver extends ResultReceiver {
	  private Receiver receiver;

	  // Constructor takes a handler
	  public FoodNotificationReceiver(Handler handler) {
	      super(handler);
	  }

	  // Setter for assigning the receiver
	  public void setReceiver(Receiver receiver) {
	      this.receiver = receiver;
	  }

	  // Defines our event interface for communication
	  public interface Receiver {
	      public void onReceiveResult(int resultCode, Bundle resultData);
	  }

	  // Delegate method which passes the result to the receiver if the receiver has been assigned
	  @Override
	  protected void onReceiveResult(int resultCode, Bundle resultData) {
	      if (receiver != null) { // The app is running
	        receiver.onReceiveResult(resultCode, resultData);
	      } else {
	    	  // You might implement a persistent notification here
	      }
	  }
	}