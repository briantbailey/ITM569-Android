package com.example.bbailey4_androidfinal;

import java.io.IOException;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AddressListActivity extends Activity {
	
	private String address;
	private Geocoder geocoder;
	private List<Address> addressList;
	private AddressListAdapter myAddressListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_list);
		
		// Get address from intent extras
		Intent intent = this.getIntent();
        Bundle b = intent.getExtras();     
        address = b.getString("address");
        
        // Use Geocoder to get addresses
        geocoder = new Geocoder(this);
        address = "25 lake shore dr" + ", Chicago, IL";
        try {
			addressList = geocoder.getFromLocationName(address, 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Fill listview with results list
        ListView lv = (ListView) findViewById(R.id.addressListView);
        myAddressListAdapter = new AddressListAdapter(this, R.layout.address_list_row, addressList);
        lv.setAdapter(myAddressListAdapter);
        
        
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
