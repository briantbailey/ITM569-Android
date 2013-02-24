package com.example.bbailey4_androidfinal;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private LocationManager locationManager;
	private String bestLocationProvider;
	private Location myLocation;
	private int selectedDistancePos;
	private int selectedDatePos;
	// The minimum distance change to update location in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BETWEEN_UPDATES = 500;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initialize GPS and start listening for updates
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
		
		// Initialize Distance Spinner
		Spinner distSpinner = (Spinner)findViewById(R.id.distanceSpinner);
		ArrayAdapter<CharSequence> distAdapter = ArrayAdapter.createFromResource(this, 
				R.array.distance_array, android.R.layout.simple_spinner_item);
		distAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distSpinner.setAdapter(distAdapter);
		distSpinner.setOnItemSelectedListener(spinnerDistListener);
		
		// Initialize Date Spinner
		Spinner dateSpinner = (Spinner)findViewById(R.id.dateSpinner);
		ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(this, 
				R.array.date_array, android.R.layout.simple_spinner_item);
		dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dateSpinner.setAdapter(dateAdapter);
		dateSpinner.setOnItemSelectedListener(spinnerDateListener);
		
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
		intent.putExtra("latitude", myLocation.getLatitude());
		intent.putExtra("longitude", myLocation.getLongitude());
		intent.putExtra("selectedDistancePos", selectedDistancePos);
		intent.putExtra("selectedDatePos", selectedDatePos);
		this.startActivity(intent);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} //end onCreateOptionsMenu
	
	
	private OnItemSelectedListener spinnerDistListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			selectedDistancePos = pos;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}; //end spinnerDistListener
	
	
	private OnItemSelectedListener spinnerDateListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			selectedDatePos = pos;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}; //end spinnerDateListener
	
	
	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			myLocation = location;
			Log.d("GPS Update", "(" + Double.toString(myLocation.getLatitude()) + ", " + Double.toString(myLocation.getLongitude()) + ")");
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
