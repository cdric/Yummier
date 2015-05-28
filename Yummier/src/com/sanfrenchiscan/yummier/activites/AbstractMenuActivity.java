package com.sanfrenchiscan.yummier.activites;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.sanfrenchiscan.yummier.R;
import com.sanfrenchiscan.yummier.adapters.DishItemsAdapter.DishItemsAdapterInterface;
import com.sanfrenchiscan.yummier.asynctasks.FetchDishAttributesAsyncTask;
import com.sanfrenchiscan.yummier.fragments.DishDetailsDialogFragment;
import com.sanfrenchiscan.yummier.fragments.MenuFragment;
import com.sanfrenchiscan.yummier.listeners.AsyncFragmentRefreshListener;
import com.sanfrenchiscan.yummier.models.Dish;

public abstract class AbstractMenuActivity extends AbstractSlidingMenuActivity implements
							AsyncFragmentRefreshListener,
							DishItemsAdapterInterface {
	
	protected Tab tabMenuBreakfast, tabMenuLunch, tabMenuDinner;
	protected String tagMenuBreakfast, tagMenuLunch, tagMenuDinner;
	protected Bundle bundleMenuBreakfast, bundleMenuLunch, bundleMenuDinner;
	
	private MenuFragment fragmentMenus;
	
	private SearchView searchView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		setUpSlidingMenu(savedInstanceState);
		
		// Pre-fetch DishAttributes 
		// The activity will be setup after this
		new FetchDishAttributesAsyncTask(this).execute();
		
	}

	protected abstract void setUpActivity();
							  
    public void showDishDetailsDialog(Dish d) {
  	   FragmentManager fm = getSupportFragmentManager();
  	   DishDetailsDialogFragment editNameDialog = DishDetailsDialogFragment.newInstance(d);
  	   editNameDialog.show(fm, "fragment_edit_name");
    }
    	
	// ------------------
	// ACTION BAR ACTIONS
	// ------------------

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main_activity, menu);
//		return true;
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main_activity, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    searchView = (SearchView) searchItem.getActionView();
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
	       @Override
	       public boolean onQueryTextSubmit(String query) {
	    	   
	    	    filterOnQuery(query);
	            return true;
	       }

	       @Override
	       public boolean onQueryTextChange(String newText) {
	    	   
	    	   //filterOnQuery(newText);
	           return false;
	       }
	          
	   });
	   return super.onCreateOptionsMenu(menu);
	}
	
	
	public void filterOnQuery(String query) {
 	   
	    // Need to identify which fragment to refresh			
	    MenuFragment fragment = null;
		if (getSupportActionBar().getSelectedTab().getTag().equals(tagMenuBreakfast) ) {
			// BREAKFAST TAB
			fragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(tagMenuBreakfast);
		}
		
		if (getSupportActionBar().getSelectedTab().getTag().equals(tagMenuLunch) ) {
			// LUNCH TAB
			fragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(tagMenuLunch);
		}
		
		if (getSupportActionBar().getSelectedTab().getTag().equals(tagMenuDinner) ) {
			// DINNER TAB
			fragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(tagMenuDinner);
		}
		
		fragment.filterListView(query);

   }
	
	
//	@Override
//	public void onTimePicked(Calendar time) {
//		// display the selected time in the TextView
//		EditText etDriverRideStart = (EditText) fragmentDriverRideDetails.getView().findViewById(R.id.et_driver_time_ride_start);
//		etDriverRideStart.setText(DateFormat.format(AppConstants.DRIVER_RIDE_DATE_FORMAT, time));
//	}
	
	// BUTTON EVENTS
	
	public void onRefreshAction(MenuItem mi) {
		
		filterOnQuery("");
		
	}
		
	// METHODS FROM INTERFACE AsyncFragmentRefreshListener

	@Override
	public void asyncContentRefreshTaskStarted(String refreshStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asyncContentRefreshTaskCompleted() {
		setUpActivity();
		
	}
		
	// METHODS FROM INTERFACE DishItemsAdapterInterface
	
	@Override
	public void onDishItemImageClick(View v, Dish d) {
		
		showDishDetailsDialog(d);
		
	}


}
