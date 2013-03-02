package com.example.bbailey4_androidfinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddressListActivity extends Activity {
	
	private String address;
	private Geocoder geocoder;
	private List<Address> addressList;
	private AddressListAdapter myAddressListAdapter;
	private int selectedDistancePos;
	private String searchDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_list);
		
		// Get address from intent extras
		Intent intent = this.getIntent();
        Bundle b = intent.getExtras();     
        address = b.getString("address");
        selectedDistancePos = b.getInt("selectedDistancePos");
        searchDate = b.getString("searchDate");
        
        // Use Geocoder to get addresses
        geocoder = new Geocoder(this);
        address = address + ", Chicago, IL";
        try {
			addressList = geocoder.getFromLocationName(address, 20);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addressList = new ArrayList<Address>();
			Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
        
        // Fill listview with results list
        ListView lv = (ListView) findViewById(R.id.addressListView);
        myAddressListAdapter = new AddressListAdapter(this, R.layout.address_list_row, addressList);
        lv.setAdapter(myAddressListAdapter);
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				// Launch CrimeListActivity with Address Data
				Intent intent = new Intent(getApplicationContext(), CrimeListActivity.class);
				intent.putExtra("latitude", addressList.get(position).getLatitude());
				intent.putExtra("longitude", addressList.get(position).getLongitude());
				intent.putExtra("selectedDistancePos", selectedDistancePos);
				intent.putExtra("searchDate", searchDate);
				startActivity(intent);	
			}
        	
        });    
        
	} //end onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.address_list, menu);
		return true;
	}
	
	
	private class AddressListAdapter extends ArrayAdapter<Address> {
		
		private List<Address> tmpAddressList;

		public AddressListAdapter(Context context, int textViewResourceId,
				List<Address> objects) {
			super(context, textViewResourceId, objects);
			this.tmpAddressList = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.address_list_row, null);
			}
			Address address = tmpAddressList.get(position);
			if (address != null) {
				TextView tvAddress = (TextView) v.findViewById(R.id.tvAddressGeoResults);
				String line = "";
				for (int i = 0 ; i <= address.getMaxAddressLineIndex() ; i++){
					line = line + address.getAddressLine(i) + ", ";
				}
				tvAddress.setText(line);
			}
			return v;
		}
	
	} //end AddressListAdapter


} //end AddressListActivity
