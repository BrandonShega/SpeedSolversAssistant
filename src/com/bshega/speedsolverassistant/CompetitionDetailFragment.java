package com.bshega.speedsolverassistant;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

public class CompetitionDetailFragment extends Fragment {
	
	GoogleMap map;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_competition_detail, container, false);
		
		//map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map2)).getMap();
		
		return view;
	}

}
