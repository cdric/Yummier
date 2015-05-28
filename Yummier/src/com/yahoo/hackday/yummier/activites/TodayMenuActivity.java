package com.yahoo.hackday.yummier.activites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.yahoo.hackday.yummier.R;
import com.yahoo.hackday.yummier.constants.AppConstants;
import com.yahoo.hackday.yummier.fragments.MenuFragment;
import com.yahoo.hackday.yummier.listeners.FragmentTabListener;

public class TodayMenuActivity extends AbstractMenuActivity {
	
	protected void setUpActivity() {
		
		// Update action bar title & icon
		updateActionBarTitle("Yummier");
		
		// Setup action bar properties
		ActionBar actionBar = getSupportActionBar(); 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		List<String> cafeList = new ArrayList<String>();
		cafeList.add(AppConstants.BONAPPETIT_CAFE_YAHOO_URL);
		cafeList.add(AppConstants.BONAPPETIT_CAFE_YAHOO_BUILDING_E);
		cafeList.add(AppConstants.BONAPPETIT_CAFE_YAHOO_BUILDING_F);
		cafeList.add(AppConstants.BONAPPETIT_CAFE_YAHOO_BUILDING_G);
		
		List<String> menuDates = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		String dateCode = formatter.print(DateTime.now());
		menuDates.add(dateCode);
		
		// Define action bar tabs
		bundleMenuBreakfast = new Bundle();
		bundleMenuBreakfast.putString(AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR, AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR_VALUE_BREAKFAST);
		bundleMenuBreakfast.putSerializable(AppConstants.BUNDLE_ATTRIBUTE_CAFES_LIST, (Serializable) cafeList);
		bundleMenuBreakfast.putSerializable(AppConstants.BUNDLE_ATTRIBUTE_MENU_DATES, (Serializable) menuDates);
		tagMenuBreakfast = getResources().getString(R.string.tab_menu_breakfast);
		tabMenuBreakfast = actionBar.newTab().setText(R.string.tab_menu_breakfast).setTabListener(		
				new FragmentTabListener(this, tagMenuBreakfast, MenuFragment.class, bundleMenuBreakfast))
					.setIcon(R.drawable.ic_action_meal_breakfast)
					.setTag(tagMenuBreakfast);
		
		bundleMenuLunch = new Bundle();
		bundleMenuLunch.putString(AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR, AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR_VALUE_LUNCH);
		bundleMenuLunch.putSerializable(AppConstants.BUNDLE_ATTRIBUTE_CAFES_LIST, (Serializable) cafeList);
		bundleMenuLunch.putSerializable(AppConstants.BUNDLE_ATTRIBUTE_MENU_DATES, (Serializable) menuDates);
		tagMenuLunch = getResources().getString(R.string.tab_menu_lunch);
		tabMenuLunch = actionBar.newTab().setText(R.string.tab_menu_lunch).setTabListener(		
				new FragmentTabListener(this, tagMenuLunch, MenuFragment.class, bundleMenuLunch))
					.setIcon(R.drawable.ic_action_meal_breakfast)
					.setTag(tagMenuLunch);
		
		bundleMenuDinner = new Bundle();
		bundleMenuDinner.putString(AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR, AppConstants.BUNDLE_ATTRIBUTE_DISHSERVEDFOR_VALUE_DINNER);
		bundleMenuDinner.putSerializable(AppConstants.BUNDLE_ATTRIBUTE_CAFES_LIST, (Serializable) cafeList);
		bundleMenuDinner.putSerializable(AppConstants.BUNDLE_ATTRIBUTE_MENU_DATES, (Serializable) menuDates);
		tagMenuDinner = getResources().getString(R.string.tab_menu_dinner);
		tabMenuDinner = actionBar.newTab().setText(R.string.tab_menu_dinner).setTabListener(		
				new FragmentTabListener(this, tagMenuDinner, MenuFragment.class, bundleMenuDinner))
					.setIcon(R.drawable.ic_action_meal_breakfast)
					.setTag(tagMenuDinner);
		
		actionBar.addTab(tabMenuBreakfast);
		actionBar.addTab(tabMenuLunch);
		actionBar.addTab(tabMenuDinner);
		
	}
							  
}
