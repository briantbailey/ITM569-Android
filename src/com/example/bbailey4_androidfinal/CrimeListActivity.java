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
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrimeListActivity extends Activity {

	private double latitude;
	private double longitude;
	private Location myLocation;
	private int selectedDistancePos;
	private String searchDate;
	private List<CrimeRecord> resultsList = new ArrayList<CrimeRecord>();
	private CrimeListAdapter myListAdapter;
	// Distance Array Values In Meters
	public static final float[] DISTANCE_ARRAY_METERS = {30.48f, 76.2f, 152.4f, 304.8f, 402.336f, 804.672f, 1609.344f};
	// Distance Array Values In Degrees Latitude
	public static final double[] DISTANCE_ARRAY_DEGREES_LATITUDE = {0.000274, 0.000686, 0.001372, 0.002745, 0.003623, 0.007246, 0.014493};
	// Distance Array Values In Degrees Longitude
	public static final double[] DISTANCE_ARRAY_DEGREES_LONGITUDE = {0.000451, 0.001127, 0.002255, 0.004509, 0.005952, 0.011905, 0.023810};

	
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
        searchDate = b.getString("searchDate");
        
        // Bind ListView to Adapter
        ListView crimeListView = (ListView) findViewById(R.id.crimeListView);
        myListAdapter = new CrimeListAdapter(this, R.layout.list_row, resultsList);
        crimeListView.setAdapter(myListAdapter);
        crimeListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent detailIntent = new Intent(getApplicationContext(), DetailViewActivity.class);
				detailIntent.putExtra("caseNum", resultsList.get(position).getCaseNum());
				detailIntent.putExtra("dateOf", resultsList.get(position).getDateOf());
				detailIntent.putExtra("block", resultsList.get(position).getBlock());
				detailIntent.putExtra("iucr", resultsList.get(position).getIucr());
				detailIntent.putExtra("primaryDesc", resultsList.get(position).getPrimaryDesc());
				detailIntent.putExtra("secondaryDesc", resultsList.get(position).getSecondaryDesc());
				detailIntent.putExtra("locationDesc", resultsList.get(position).getLocationDesc());
				detailIntent.putExtra("arrest", resultsList.get(position).getArrest());
				detailIntent.putExtra("domestic", resultsList.get(position).getDomestic());
				detailIntent.putExtra("beat", resultsList.get(position).getBeat());
				detailIntent.putExtra("ward", resultsList.get(position).getWard());
				detailIntent.putExtra("fbi_cd", resultsList.get(position).getFbi_cd());
				detailIntent.putExtra("latitude", Double.toString(resultsList.get(position).getLatitude()));
				detailIntent.putExtra("longitude", Double.toString(resultsList.get(position).getLongitude()));
				startActivity(detailIntent);
			}
        	
        });
                   
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
			//$where=within_box(location,+42.00,+-88.55,+41.55,+-87.35)+AND+date_of_occurrence%3E='2013-02-10T00:00:00'&$order=date_of_occurrence+DESC
			String queryParam = "?$where=within_box(location,+" 
					+ Double.toString((latitude + DISTANCE_ARRAY_DEGREES_LATITUDE[selectedDistancePos])) + ",+" 
					+ Double.toString((longitude - DISTANCE_ARRAY_DEGREES_LONGITUDE[selectedDistancePos])) + ",+" 
					+ Double.toString((latitude - DISTANCE_ARRAY_DEGREES_LATITUDE[selectedDistancePos])) + ",+" 
					+ Double.toString((longitude + DISTANCE_ARRAY_DEGREES_LONGITUDE[selectedDistancePos])) + ")+AND+"
					+ "date_of_occurrence%3E='" + searchDate + "'&$order=date_of_occurrence+DESC";
			
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
			myListAdapter.clear();
			//myListAdapter.addAll(resultsList); doesn't work in 2.3.3 need to loop
			for (int i = 0; i < resultsList.size(); i++) {
				myListAdapter.add(resultsList.get(i));
			}
			Toast.makeText(getApplicationContext(), ("Found " 
					+ Integer.toString(resultsList.size()) + " Records"), Toast.LENGTH_SHORT).show();
		}
		
	} //end CrimeListAsyncTask
	
	
	private class CrimeListAdapter extends ArrayAdapter<CrimeRecord> {
		
		private List<CrimeRecord> crimeList;

		public CrimeListAdapter(Context context, int textViewResourceId,
				List<CrimeRecord> objects) {
			super(context, textViewResourceId, objects);
			this.crimeList = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.list_row, null);
			}
			CrimeRecord cr = crimeList.get(position);
			if (cr != null) {
				TextView tv = (TextView) v.findViewById(R.id.crimeAddress);
				tv.setText(cr.getBlock());
			}
			return v;
		}
	
	} //end CrimeListAdapter

} //end CrimeListActivity
