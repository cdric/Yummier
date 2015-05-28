package com.sanfrenchiscan.yummier.fragments;

import java.util.ArrayList;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sanfrenchiscan.yummier.activites.AbstractSlidingMenuActivity;
import com.sanfrenchiscan.yummier.activites.TodayMenuActivity;
import com.sanfrenchiscan.yummier.activites.TomorrowMenuActivity;
import com.sanfrenchiscan.yummier.activites.YesterdayMenuActivity;
import com.sanfrenchiscan.yummier.adapters.NavDrawerListAdapter;
import com.sanfrenchiscan.yummier.models.NavDrawerItem;
import com.sanfrenchiscan.yummier.R;

public class NavDrawerListFragment extends ListFragment {

	private ListView mDrawerList;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sliding_menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpDrawer();
	}

	// slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    
    private void setUpDrawer() {
        
    	// mTitle = mDrawerTitle = getTitle();
 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
         
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        
        mDrawerList = (ListView) getActivity().findViewById(android.R.id.list);
        NavDrawerListAdapter adapter = (NavDrawerListAdapter) mDrawerList.getAdapter();
		
		if (adapter == null) {
			adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(),
	                navDrawerItems);
		} 
        
        setListAdapter(adapter);
        
        //mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        // getActionBar().setHomeButtonEnabled(true);
 
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.drawable.ic_drawer, //nav menu toggle icon
//                R.string.app_name, // nav drawer open - description for accessibility
//                R.string.app_name // nav drawer close - description for accessibility 
//        ){
//                
//            public void onDrawerClosed(View view) {
//                getActionBar().setTitle(mTitle);
//                // calling onPrepareOptionsMenu() to show action bar icons
//                invalidateOptionsMenu();
//            }
// 
//            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
//                // calling onPrepareOptionsMenu() to hide action bar icons
//                invalidateOptionsMenu();
//            }
//        };
        
        // mDrawerLayout.setDrawerListener(mDrawerToggle);
 
    }
    
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		//AbstractSlidingMenuActivity newContent = null;
		Class klass = null;
		switch (position) {
		case 0:
			klass= TodayMenuActivity.class;
			break;
		case 1:
			klass= TomorrowMenuActivity.class;
			break;
		case 2:
			klass= YesterdayMenuActivity.class;
			break;
		}
		
//		if (newContent != null) {
////			if (getActivity() instanceof AbstractSlidingMenuActivity) {
////				AbstractSlidingMenuActivity ara = (AbstractSlidingMenuActivity) getActivity();
////				ara.switchContent(newContent);
////			} 
//		}
		
		if (klass != null) {
			((AbstractSlidingMenuActivity) getActivity()).switchContent(klass);
		}
		
	}
    
}
