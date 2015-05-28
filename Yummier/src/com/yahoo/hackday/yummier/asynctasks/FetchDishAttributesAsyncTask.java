package com.yahoo.hackday.yummier.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.yahoo.hackday.yummier.listeners.AsyncFragmentRefreshListener;
import com.yahoo.hackday.yummier.models.DishAttributeFactory;

/**
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class FetchDishAttributesAsyncTask extends AsyncTask<String, Void, String> {

	AsyncFragmentRefreshListener mListener;
	
	public FetchDishAttributesAsyncTask(AsyncFragmentRefreshListener listener) {
		
		mListener = listener;
		
	}

	@Override
	protected String doInBackground(String... params) {

		Log.d("debug", "Start FetchDishAttributesAsyncTask");
		
		DishAttributeFactory.getInstance();
		return null;
		
	}
	
	@Override
    protected void onPostExecute(String result) {
		
		Log.d("debug", "Done FetchDishAttributesAsyncTask");
		mListener.asyncContentRefreshTaskCompleted();
		
	}

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

}