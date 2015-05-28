package com.sanfrenchiscan.yummier.fragments;

import java.io.Serializable;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sanfrenchiscan.yummier.models.Dish;
import com.sanfrenchiscan.yummier.models.DishAttribute;
import com.sanfrenchiscan.yummier.R;

public class DishDetailsDialogFragment extends DialogFragment {

	private ImageView ivDishLargeImage;
	private TextView tvDishCafe, tvDishDescription, tvDishStation;

	public DishDetailsDialogFragment() {
		// Empty constructor required for DialogFragment
	}
	
	public static DishDetailsDialogFragment newInstance(Dish d) {
		DishDetailsDialogFragment frag = new DishDetailsDialogFragment();
		Bundle args = new Bundle();
		args.putSerializable("dish", (Serializable) d);
		frag.setArguments(args);
		return frag;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dish_details, container);
		
		ImageView ivDisImageView = (ImageView) view.findViewById(R.id.iv_dish_picture_large);
		TextView tvDishDescription = (TextView) view.findViewById(R.id.tv_dish_description);
		TextView tvDishStation = (TextView) view.findViewById(R.id.tv_dish_station);
		TextView tvDishCafe = (TextView) view.findViewById(R.id.tv_dish_cafe);
		
		Dish dish = (Dish) getArguments().getSerializable("dish");
		
		ImageLoader.getInstance().displayImage(dish.getLargeDishImage(), ivDisImageView);
		
		String title = dish.getName();
		getDialog().setTitle("Dish Details");
		
		String description = "<b>" + dish.getName() + "</b> " + dish.getDescription();
		tvDishDescription.setText(Html.fromHtml(description));
		
		String station = "<b>Station: </b>" + dish.getStation();
		tvDishStation.setText(Html.fromHtml(station));
		
		String cafe = "<b>Cafe: </b>" + dish.getCafe();
		tvDishCafe.setText(Html.fromHtml(cafe));
		
		List<DishAttribute> attributes = dish.getDishAttributes();
		
		int i = 1;
		for (DishAttribute attr: attributes) {
			int resivID = view.getContext().getResources().getIdentifier("iv_dish_attribute" + i++, "id", view.getContext().getPackageName());
			int restvID = view.getContext().getResources().getIdentifier("tv_dish_attribute" + i++, "id", view.getContext().getPackageName());	
			ImageView ivDishAttr = (ImageView) view.findViewById(resivID);
			ImageLoader.getInstance().displayImage(attr.getImageUrl(), ivDishAttr);
			TextView tvDishAttr = (TextView) view.findViewById(restvID);
			tvDishAttr.setText(attr.getName());
			
		}
		
		return view;
	}
	
}
