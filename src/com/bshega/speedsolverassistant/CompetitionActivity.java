package com.bshega.speedsolverassistant;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CompetitionActivity extends Activity {
	
	private ListView listView;
	private GoogleMap map;
	Context mContext;
	
	private String[] competitionNames = {
				"Indiana 2014", 
				"US Nationals 2014", 
				"Keep Austin Weird 2014", 
				"Michigan 2014", 
				"New Albany 2014", 
				"BASC 3 2014",
				"Iowa Corn Lovers 2014",
				"San Diego Summer 2014",
				"Remember The Alamo 2014",
				"Caltech Spring 2014"
	};
	
	private String[] competitionLocations = {
				"Fishers, Indiana",
				"Jersey City, New Jersey",
				"Austin, Texas",
				"Ann Arbor, MI",
				"New Albany, Ohio",
				"Sunnyvale, California",
				"Grinnell, IA",
				"San Diego, California",
				"San Antonio, Texas",
				"Pasadena, California"
				
	};
	
	private String[] competitionVenues = {
				"Fishers Library",
				"Liberty Science Center",
				"Triumphant Love Lutheran Church",
				"University of Michigan",
				"New Albany Middle School Cafeteria",
				"Moose Family Center Lodge",
				"Poweshiek County Fair Exhibit Hall",
				"Wangenheim Middle School",
				"Woodland Baptist Church",
				"California Institute of Technology"
	};
	
	private LatLng[] latlngArray = new LatLng[competitionLocations.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_competition);
		
		mContext = this;
		
		listView = (ListView) findViewById(R.id.competitionsListView);
		
		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, competitionNames));
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		
		Location location = locationManager.getLastKnownLocation(locationProvider);
		
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		
		LatLng USER_LOCATION = new LatLng(latitude, longitude);
		
		Geocoder geoCoder = new Geocoder(mContext, Locale.getDefault());
		
		for (int i=0; i<competitionLocations.length; i++) {
			
			double eventLat = 0;
			double eventLong = 0;
			
			try {
				List<Address> address = geoCoder.getFromLocationName(competitionLocations[i], 1);
				eventLat = address.get(0).getLatitude();
				eventLong = address.get(0).getLongitude();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LatLng eventLatLng = new LatLng(eventLat, eventLong);
			
			latlngArray[i] = eventLatLng;
			
		}
		
		if (map == null) {
			
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
			
			for (int i=0; i<latlngArray.length; i++) {
				
				map.addMarker(new MarkerOptions()
				
					.position(latlngArray[i])
					.title(competitionNames[i])
						
				);
				
			}
			
		}
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(mContext, CompetitionDetailActivity.class);
				intent.putExtra("eventName", competitionNames[arg2]);
				intent.putExtra("eventLocation", competitionLocations[arg2]);
				intent.putExtra("eventVenue", competitionVenues[arg2]);
				LatLng eventLatLng = latlngArray[arg2];
				double eventlat = eventLatLng.latitude;
				double eventlong = eventLatLng.longitude;
				intent.putExtra("eventLat", eventlat);
				intent.putExtra("eventLong", eventlong);
				startActivity(intent);
				
			}
		});
		
	}

}
