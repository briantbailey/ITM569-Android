package com.example.bbailey4_androidfinal;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private LocationManager locationManager;
	private String bestLocationProvider;
	private Location myLocation;
	// The minimum distance change to update location in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BETWEEN_UPDATES = 500;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initialize GPS
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		bestLocationProvider = locationManager.getBestProvider(new Criteria(), false);
		myLocation = locationManager.getLastKnownLocation(bestLocationProvider);
		if (myLocation == null) {
			myLocation = new Location(bestLocationProvider);
			myLocation.setLatitude(0.0);
			myLocation.setLongitude(0.0);
		}
		locationManager.requestLocationUpdates(bestLocationProvider, MIN_TIME_BETWEEN_UPDATES, 
				MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
		
		// Test Output
		TextView tv1 = (TextView) findViewById(R.id.textview1);
		tv1.setText(bestLocationProvider);
		TextView tv2 = (TextView) findViewById(R.id.textview2);
		tv2.setText("(" + Double.toString(myLocation.getLatitude()) + ", " + Double.toString(myLocation.getLongitude()) + ")");
		
	} //end onCreate

	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}


	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(bestLocationProvider, MIN_TIME_BETWEEN_UPDATES, 
				MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
	}
	
	
	// Search Button Handler
	public void searchCrimeData(View view){
		// Launch CrimeListActivity with Location Data
		Intent intent = new Intent(this, CrimeListActivity.class);
		intent.putExtra("Latitude", myLocation.getLatitude());
		intent.putExtra("Longitude", myLocation.getLongitude());
		this.startActivity(intent);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} //end onCreateOptionsMenu
	
	
	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			myLocation = location;
			TextView tv2 = (TextView) findViewById(R.id.textview2);
			tv2.setText("(" + Double.toString(myLocation.getLatitude()) + ", " + Double.toString(myLocation.getLongitude()) + ")");
		}

		@Override
		public void onProviderDisabled(String string) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String string) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String string, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}; //end locationListener
	

} //end MainActivity
