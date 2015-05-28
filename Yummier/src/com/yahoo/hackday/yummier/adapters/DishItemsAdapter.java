package com.yahoo.hackday.yummier.adapters;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.hackday.yummier.R;
import com.yahoo.hackday.yummier.apps.Yummier;
import com.yahoo.hackday.yummier.models.Dish;
import com.yahoo.hackday.yummier.models.DishAttribute;
import com.yahoo.hackday.yummier.models.ImageResult;

/**
 * Custom adapter for the Passenger list view
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class DishItemsAdapter extends ArrayAdapter<Dish> {
		
	public interface DishItemsAdapterInterface {
		public void onDishItemImageClick(View v, Dish d);
	}

	private DishItemsAdapter mAdapter;
	private Context mContext;
	private DishItemsAdapterInterface mListener;
	
	public DishItemsAdapter(Context context, List<Dish> dishes, DishItemsAdapterInterface listener) {
		super(context, 0, dishes);
		mContext = context;
		mListener = listener;
		
	}
	
	@Override
	public View getView(int position, View mView, ViewGroup parent) {
		mAdapter = this;
		
		if (mView == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflatter.inflate(R.layout.listview_dishes, null);

		}

		final Dish dish = getItem(position);
		String formattedName;
		
		TextView tvDishDate = (TextView) mView.findViewById(R.id.tv_dish_date);
		formattedName = dish.getDate();
		tvDishDate.setText(Html.fromHtml(formattedName));
		tvDishDate.setVisibility(TextView.GONE);
		
		TextView tvDishName = (TextView) mView.findViewById(R.id.tv_dish_name);
		formattedName = "<b>" + dish.getName() + "</b>" + " " + dish.getDescription();
		tvDishName.setText(Html.fromHtml(formattedName));
		
		int i = 1;
		for (DishAttribute attr: dish.getDishAttributes()) {
			int resID = mView.getContext().getResources().getIdentifier("iv_dish_attribute" + i++, "id", mView.getContext().getPackageName());
			ImageView ivDishAttr = (ImageView) mView.findViewById(resID);
			if (attr != null && attr.getImageUrl() != null) {
				ImageLoader.getInstance().displayImage(attr.getImageUrl(), ivDishAttr);
			}
		}
		
		final ImageView imageView = (ImageView) mView.findViewById((R.id.iv_dish_image));
		imageView.setTag(dish);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Dish dish = (Dish) v.getTag();
				mListener.onDishItemImageClick(v, dish);
				
			}
		});
		
		Yummier.getGoogleImageSearchRestClient()
				.getImageForDish(new JsonHttpResponseHandler() {
					
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							
							imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
							List<ImageResult> results = ImageResult.fromJSONArray(imageJsonResults);
							
							//Log.d("DEBUG", results.toString());
							
							if (results != null && !results.isEmpty()) {
							   ImageResult image = results.get(0);
							   ImageLoader.getInstance().displayImage(image.getThumbUrl(), imageView);
							   
							   dish.setSmallDishImage(image.getThumbUrl());
							   dish.setLargeDishImage(image.getFullUrl());
							}
							
						} catch (JSONException e) { 
							e.printStackTrace();
						}
					}
					
				}, dish.getName());
		
		return mView;
	}
	
}
