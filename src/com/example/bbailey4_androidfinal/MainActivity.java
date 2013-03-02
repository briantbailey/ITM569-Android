package com.example.bbailey4_androidfinal;

import java.util.Calendar;
import java.util.Locale;

import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private LocationManager locationManager;
	private String bestLocationProvider;
	private Location myLocation;
	private int selectedDistancePos;
	private int selectedDatePos;
	// The minimum distance change to update location in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0L;
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BETWEEN_UPDATES = 250L;
	private TextView tvGpsLocation;
	private TextView tvProviderAccuracy;

	
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
		distSpinner.setSelection(2);
		
		// Initialize Date Spinner
		Spinner dateSpinner = (Spinner)findViewById(R.id.dateSpinner);
		ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(this, 
				R.array.date_array, android.R.layout.simple_spinner_item);
		dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dateSpinner.setAdapter(dateAdapter);
		dateSpinner.setOnItemSelectedListener(spinnerDateListener);
		dateSpinner.setSelection(1);
		
		// Set Output in TextViews
		tvGpsLocation = (TextView)findViewById(R.id.tvGPSLocation);
		tvGpsLocation.setText("(" + Double.toString(myLocation.getLatitude()) + ", " 
								+ Double.toString(myLocation.getLongitude()) + ")");
		tvProviderAccuracy = (TextView)findViewById(R.id.tvProviderAccuracy);
		tvProviderAccuracy.setText("Accuracy of " + Float.toString(myLocation.getAccuracy()) + " meters, Provided by "
				+ bestLocationProvider);
		
		// Test For Geocoder service and show UI if present
		if (Geocoder.isPresent()) {
			LinearLayout geoLayout = (LinearLayout) findViewById(R.id.geoLayout);
			geoLayout.setVisibility(View.VISIBLE);
		}
			
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
		intent.putExtra("searchDate", this.getSearchDate(selectedDatePos));
		this.startActivity(intent);
	}
	
	
	// Search Address Handler
	public void searchGeoAddress(View view){
		// Launch CrimeListActivity with Location Data
		Intent intent = new Intent(this, AddressListActivity.class);
		intent.putExtra("address", ( (EditText)findViewById(R.id.geoInput)).getText().toString() );
		intent.putExtra("selectedDistancePos", selectedDistancePos);
		intent.putExtra("searchDate", this.getSearchDate(selectedDatePos));
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
			Log.d("GPS Update", "(" + Double.toString(myLocation.getLatitude()) + ", " 
					+ Double.toString(myLocation.getLongitude()) + ", " + Float.toString(myLocation.getAccuracy()) + ")");
			tvGpsLocation.setText("(" + Double.toString(myLocation.getLatitude()) + ", " 
					+ Double.toString(myLocation.getLongitude()) + ")");
			tvProviderAccuracy.setText("Accuracy of " + Float.toString(myLocation.getAccuracy()) + " meters, Provided by "
					+ bestLocationProvider);
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
	
	
	private String getSearchDate(int datePos){
		Calendar now = Calendar.getInstance();
		String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		switch (datePos) {
			case 0:
				now.add(Calendar.DAY_OF_MONTH, -14);
				break;
			case 1:
				now.add(Calendar.MONTH, -1);
				break;
			case 2:
				now.add(Calendar.MONTH, -2);
				break;
			case 3:
				now.add(Calendar.MONTH, -3);
				break;
			case 4:
				now.add(Calendar.MONTH, -6);
				break;
			case 5:
				now.add(Calendar.YEAR, -1);
				break;
		}
		String year = Integer.toString(now.get(Calendar.YEAR));
		String month = months[now.get(Calendar.MONTH)];
		String day = String.format(Locale.US, "%02d", now.get(Calendar.DAY_OF_MONTH));
		return year + "-" + month + "-" + day + "T00:00:00";
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_gps_default:
				//load GPS default IIT Tower Coordinates
				myLocation.setLatitude(41.83128);
				myLocation.setLongitude(-87.62697);
				tvGpsLocation.setText("(" + Double.toString(myLocation.getLatitude()) + ", " 
						+ Double.toString(myLocation.getLongitude()) + ")");
			default:
				return super.onOptionsItemSelected(item);
		}
		
	} //end onOptionsItemSelected
	

} //end MainActivity
