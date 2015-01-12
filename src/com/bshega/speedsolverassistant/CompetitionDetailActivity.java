package com.bshega.speedsolverassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CompetitionDetailActivity extends Activity {
	
	TextView eventName;
	TextView eventLocation;
	TextView eventVenue;
	
	GoogleMap map;
	
	String eName;
	String eLocation;
	
	ShareActionProvider sap;
	
	double eventLat;
	double eventLong;
	double longitude;
	double latitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_competition_detail);
		
		Bundle data = getIntent().getExtras();
		
		eventName = (TextView) findViewById(R.id.eventName);
		eventLocation = (TextView) findViewById(R.id.eventLocation);
		eventVenue = (TextView) findViewById(R.id.eventVenue);
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		
		Location location = locationManager.getLastKnownLocation(locationProvider);
		
		eName = data.getString("eventName");
		eLocation = data.getString("eventLocation");
		
		longitude = location.getLongitude();
		latitude = location.getLatitude();
		
		LatLng userLocation = new LatLng(latitude, longitude);
		
		eventName.setText(data.getString("eventName"));
		eventLocation.setText(data.getString("eventLocation"));
		eventVenue.setText(data.getString("eventVenue"));
		
		eventLat = data.getDouble("eventLat");
		eventLong = data.getDouble("eventLong");
		
		LatLng eventLatLng = new LatLng(eventLat, eventLong);
		
		if (map == null) {
			
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap();
			if (map != null) {
				
				map.setMyLocationEnabled(true);
				map.addMarker(new MarkerOptions()
					
					.position(eventLatLng)
					.title(data.getString("eventName"))
				
				);
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 15));
			}
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		getMenuInflater().inflate(R.menu.compdetail_menu, menu);
		
		MenuItem share = menu.findItem(R.id.action_share);
		sap = (ShareActionProvider) share.getActionProvider();
		
		sap.setShareIntent(shareIntent());
		
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()) {
		
		case R.id.action_directions:
			
			String directionsURL = "http://maps.google.com?saddr="+latitude+","+longitude+"&daddr="+eventLat+","+eventLong;
			
			Intent directions = new Intent(Intent.ACTION_VIEW, Uri.parse(directionsURL));
			startActivity(directions);
			
			break;
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public Intent shareIntent() {
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cubing competition I found!" + "\n" + eName + " @ " + eLocation);
		shareIntent.setType("text/plain");
		
		return shareIntent;
		
	}
	
}
