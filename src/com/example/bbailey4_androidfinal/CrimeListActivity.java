package com.example.bbailey4_androidfinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bbailey4_androidfinal.model.CrimeRecord;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class CrimeListActivity extends Activity {

	private double latitude;
	private double longitude;
	private Location myLocation;
	private int selectedDistancePos;
	private int selectedDatePos;
	private List<CrimeRecord> resultsList;
	// Distance Array Values In Meters
	public static final float[] DISTANCE_ARRAY_METERS = {30.48f, 76.2f, 152.4f, 304.8f, 402.336f, 804.672f, 1609.344f};
	// Distance Array Values In Degrees Latitude
	public static final double[] DISTANCE_ARRAY_DEGREES_LATITUDE = {0.000274, 0.000686, 0.001372, 0.002745, 0.003623, 0.007246, 0.014493};
	// Distance Array Values In Degrees Longitude
	public static final double[] DISTANCE_ARRAY_DEGREES_LONGITUDE = {0.000451, 0.001127, 0.002255, 0.004509, 0.005952, 0.011905, 0.023810};
	// Date Array Values
	public static final String[] DATE_VALUES = {};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime_list);
		
		// Get Location from intent extras
		Intent intent = this.getIntent();
        Bundle b = intent.getExtras();     
        latitude = b.getDouble("latitude");
        longitude = b.getDouble("longitude");
        myLocation = new Location(LocationManager.PASSIVE_PROVIDER);
        myLocation.setLatitude(latitude);
        myLocation.setLongitude(longitude);
        selectedDistancePos = b.getInt("selectedDistancePos");
        selectedDatePos = b.getInt("selectedDatePos");
        
        // Test Output
        TextView tv = (TextView)findViewById(R.id.textCrimeList);
        tv.setText("(" + Double.toString(latitude) + ", " + Double.toString(longitude) + ")");
        TextView tv2 = (TextView)findViewById(R.id.textCrimeListDist);
        tv2.setText(Integer.valueOf(selectedDistancePos).toString());
        TextView tv3 = (TextView)findViewById(R.id.textCrimeListDate);
        tv3.setText(Integer.valueOf(selectedDatePos).toString());
        
        new CrimeListAsyncTask().execute();
		
	} //end onCreate

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crime_list, menu);
		return true;
	}
	
	
	private class CrimeListAsyncTask extends AsyncTask<String, Integer, List<CrimeRecord>> {

		@Override
		protected List<CrimeRecord> doInBackground(String... params) {
			
			List<CrimeRecord> crimeList = new ArrayList<CrimeRecord>();			
			StringBuilder builder = new StringBuilder();
			
			// Chicago Socrata Data Resource Endpoint
			String stringURL = "https://data.cityofchicago.org/resource/x2n5-8w5q.json";
			//String queryParam = "?$where=within_box(location,+42.00,+-88.55,+41.55,+-87.35)";
			String queryParam = "?$where=within_box(location,+" 
					+ Double.toString((latitude + DISTANCE_ARRAY_DEGREES_LATITUDE[selectedDistancePos])) + ",+" 
					+ Double.toString((longitude - DISTANCE_ARRAY_DEGREES_LONGITUDE[selectedDistancePos])) + ",+" 
					+ Double.toString((latitude - DISTANCE_ARRAY_DEGREES_LATITUDE[selectedDistancePos])) + ",+" 
					+ Double.toString((longitude + DISTANCE_ARRAY_DEGREES_LONGITUDE[selectedDistancePos])) + ")";
			
			// Create a new HttpClient and GET query
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet(stringURL + queryParam);
		    
		    // Execute HttpGet Request
		    try {
				HttpResponse response = httpclient.execute(httpget);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				// If response is successful process data
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					// Create JSON Array from result data
					JSONArray jsonCrimesArray = (new JSONArray(builder.toString()));
					// Create CrimeRecord Objects from JSON Array
					for (int i = 0; i < jsonCrimesArray.length(); i++) {
						JSONObject aJSONRecord = jsonCrimesArray.getJSONObject(i);
						CrimeRecord crimerecord = new CrimeRecord();
						crimerecord.setCaseNum(aJSONRecord.getString("case_"));
						crimerecord.setDateOf(aJSONRecord.getString("date_of_occurrence"));
						crimerecord.setBlock(aJSONRecord.getString("block"));
						crimerecord.setIucr(aJSONRecord.getString("_iucr"));
						crimerecord.setPrimaryDesc(aJSONRecord.getString("_primary_decsription"));
						crimerecord.setSecondaryDesc(aJSONRecord.getString("_secondary_description"));
						crimerecord.setLocationDesc(aJSONRecord.getString("_location_description"));
						crimerecord.setArrest(aJSONRecord.getString("arrest"));
						crimerecord.setDomestic(aJSONRecord.getString("domestic"));
						crimerecord.setBeat(aJSONRecord.getString("beat"));
						crimerecord.setWard(aJSONRecord.getString("ward"));
						crimerecord.setFbi_cd(aJSONRecord.getString("fbi_cd"));
						crimerecord.setLatitude(aJSONRecord.getString("latitude"));
						crimerecord.setLongitude(aJSONRecord.getString("longitude"));
						Location location = new Location(LocationManager.PASSIVE_PROVIDER);
						location.setLatitude(crimerecord.getLatitude());
						location.setLongitude(crimerecord.getLongitude());
						if (location.distanceTo(myLocation) <= DISTANCE_ARRAY_METERS[selectedDistancePos]) {
							crimeList.add(crimerecord);
						}
					}
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
			return crimeList;
		}

		@Override
		protected void onPostExecute(List<CrimeRecord> result) {
			super.onPostExecute(result);
			resultsList = result;
			Toast.makeText(getApplicationContext(), "json loaded", Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), ("ListCount: " + Integer.toString(resultsList.size())), Toast.LENGTH_SHORT).show();
		}
		
	} //end CrimeListAsyncTask

} //end CrimeListActivity
