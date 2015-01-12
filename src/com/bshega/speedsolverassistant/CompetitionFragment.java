package com.bshega.speedsolverassistant;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class CompetitionFragment extends Fragment {
	
	private MapView mapView;
	private GoogleMap map;
	private ListView listView;
	
	private String[] competitionNames = {"Competition 1", "Competition 2", "Competition 3", "Competition 4", "Competition 5", "Competition 6"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_competition, container, false);
		
		listView = (ListView) view.findViewById(R.id.competitionsListView);
		
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.drawer_item, competitionNames));
		
		if (mapView == null) {
			
			mapView = (MapView) view.findViewById(R.id.map);
			
		}
		
		map = mapView.getMap();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Fragment fragment = new CompetitionDetailFragment();
				
				FragmentManager manager = getActivity().getFragmentManager();
				FragmentTransaction transaction = manager.beginTransaction();
				transaction.replace(R.id.main_layout, fragment);
				transaction.commit();
				
			}
			
		});
		
		return view;
	}
	
}
