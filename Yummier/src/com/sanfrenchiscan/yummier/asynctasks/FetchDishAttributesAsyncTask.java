package com.sanfrenchiscan.yummier.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.sanfrenchiscan.yummier.listeners.AsyncFragmentRefreshListener;
import com.sanfrenchiscan.yummier.models.DishAttributeFactory;

/**
 * @author C�dric Lignier <cedric.lignier@free.fr>
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