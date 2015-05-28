package com.sanfrenchiscan.yummier.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sanfrenchiscan.yummier.adapters.DishItemsAdapter;
import com.sanfrenchiscan.yummier.adapters.DishItemsAdapter.DishItemsAdapterInterface;
import com.sanfrenchiscan.yummier.apps.Yummier;
import com.sanfrenchiscan.yummier.constants.AppConstants;
import com.sanfrenchiscan.yummier.models.Dish;
import com.sanfrenchiscan.yummier.R;

public class MenuFragment extends Fragment implements DishItemsAdapterInterface {
	
//	public interface MenuFragmentListener {
//
//		public void on
//		
//	}

	private ListView lvMenuItems;
	private TextView tvEmptyList, tvMenuDate;
	
	private View mFragment = null;
	private DishItemsAdapterInterface listener;
	
	private int mNbCafes = 0;
	private int mNbCafesMax = 0;
	private List<Dish> mDishes = new ArrayList<Dish>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (mFragment == null) {
		   mFragment = inflater.inflate(R.layout.fragment_menu_items, null);
		} else {
		   // Avoid the view to be recreated upon tab switching!
		   // The following code is required to prevent a Runtime exception
		   ((ViewGroup) mFragment.getParent()).removeView(mFragment);
		}
		
		return mFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof DishItemsAdapterInterface) {
		      listener = (DishItemsAdapterInterface) activity;
	    } else {
	      throw new ClassCastException("Must implement " + DishItemsAdapterInterface.class.getName());
	    }
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		tvEmptyList = (TextView) getActivity().findViewById(R.id.tv_fragment_noResults);
		lvMenuItems = (ListView) getActivity().findViewById(R.id.lv_menu_items);
		
		tvMenuDate = (TextView) getActivity().findViewById(R.id.tv_menu_date);
		
		List<String> menuDates = (List<String>) getArguments().getSerializable(AppConstants.BUNDLE_ATTRIBUTE_MENU_DATES);
		// Assuming only one day provided for now
		String dateCode = menuDates.get(0);
		tvMenuDate.setText("Your menu options for: " + dateCode);
		
		String dishServedFor = getArguments().getString(AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR);
		
		List<String> cafeList = (List<String>) getArguments().getSerializable(AppConstants.BUNDLE_ATTRIBUTE_CAFES_LIST);
		mNbCafesMax = cafeList.size();
		
		if (lvMenuItems.getAdapter() == null) {
			
			for (String cafe: cafeList) {
						
				// Load the menu
				Yummier.getBonAppetitRestClient().getMenuForCafe(
						jsonHttpResponseHandlerForTimeline(dishServedFor), cafe, dateCode);
			
			}
				
		}
	}
	
	/**
	 * Update the timeline by adding a list of tweets
	 *  - The method can add the tweet either at the top or at the bottom of the timeline
	 *  - The method will create the adapter is the timeline doesnt exist yet
	 * @param tweets the List of Tweets to add
	 * @param addAtTheTopOfTimeline if TRUE, tweets will be added at the tope of the timeline
	 */
	public void updateListViewWithMenuItems(List<Dish> dishes) {
		
		DishItemsAdapter adapter = (DishItemsAdapter)lvMenuItems.getAdapter();
		
		if (adapter == null) {
			adapter = new DishItemsAdapter(getActivity().getBaseContext(), dishes, new DishItemsAdapterInterface() {
				
				@Override
				public void onDishItemImageClick(View v, Dish d) {
					// Bubble up the event to the activity
					listener.onDishItemImageClick(v, d);		
				}
			});
			lvMenuItems.setAdapter(adapter);
			lvMenuItems.setEmptyView(tvEmptyList);
		} else {
			adapter.clear();
			adapter.addAll(dishes);
		}

	}

	
	protected JsonHttpResponseHandler jsonHttpResponseHandlerForTimeline(final String dishServedFor) {
		
		return new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int code, JSONObject body) {
				
				try {
					JSONObject cafeObj = body.getJSONObject("cafe");
					JSONArray menuArr = cafeObj.getJSONArray("menu");
					JSONObject menusObj = menuArr.getJSONObject(0);
					JSONArray daysArr = menusObj.getJSONArray("days");
					JSONObject dayArr = daysArr.getJSONObject(0);
					JSONObject stationsObj = dayArr.getJSONObject("stations");
					
					String date = menusObj.getString("start_date");
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

					mDishes.addAll(Dish.fromJsonStations(stationsObj, formatter.parseDateTime(date), dishServedFor, cafeObj.getString("name")));					
					mNbCafes++;
					
					if (mNbCafes == (mNbCafesMax-1)) {
					   updateListViewWithMenuItems(new ArrayList<Dish>(mDishes));
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onFailure(Throwable e, JSONObject error) {

				// Display an error message to the user
				Toast.makeText(getActivity().getBaseContext(), "Can't fetch the menu information: " + e.toString(), Toast.LENGTH_SHORT).show();
				Log.e("ERROR", e.toString());
				e.printStackTrace();
				
			}

		};
	}

	@Override
	public void onDishItemImageClick(View v, Dish d) {
		
		listener.onDishItemImageClick(v, d);
		
	}

	/**
	 * Filter the list view based on a given query
	 * @param query
	 */
	public void filterListView(String query) {
		
		Log.d("debug", "Filter for query = " + query);
		
		//Make a copy of the dishes
		List<Dish> dishes = new ArrayList<Dish>(mDishes);
		
		StringTokenizer st = new StringTokenizer(query, ",");
		while (st.hasMoreElements()) {
			String keyword = (String) st.nextElement();
			Log.d("debug", "Filter on keyword: " + keyword);
			if (keyword.length() >=2 && keyword.startsWith("\"") && keyword.endsWith("\"")) {
				keyword.subSequence(1, keyword.length()-1);
			}
			dishes = Dish.filterByKeyword(dishes, keyword);
		}
		
		updateListViewWithMenuItems(dishes);
	}

}
