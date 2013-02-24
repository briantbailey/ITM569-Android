package com.example.bbailey4_androidfinal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class CrimeListActivity extends Activity {

	private double latitude;
	private double longitude;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime_list);
		
		// Get Location from intent extras
		Intent intent = this.getIntent();
        Bundle b = intent.getExtras();     
        latitude = b.getDouble("Latitude");
        longitude = b.getDouble("Longitude");
        
        // Test Output
        TextView tv = (TextView)findViewById(R.id.textCrimeList);
        tv.setText("(" + Double.toString(latitude) + ", " + Double.toString(longitude) + ")");
		
	} //end onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crime_list, menu);
		return true;
	}

}
