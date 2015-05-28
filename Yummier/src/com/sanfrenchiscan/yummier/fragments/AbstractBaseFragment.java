package com.sanfrenchiscan.yummier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Base class for all fragment of this application
 *  - Provide support for progress bar and associated utility methods
 *  
 * @author C�dric Lignier <cedric.lignier@free.fr>
 *
 */
public abstract class AbstractBaseFragment extends Fragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
