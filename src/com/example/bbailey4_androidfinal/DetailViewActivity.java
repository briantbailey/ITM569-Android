package com.example.bbailey4_androidfinal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class DetailViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_view);
		
		// Get Intent and Extras
		Intent thisIntent = this.getIntent();
		Bundle b = thisIntent.getExtras();
		
		// Populate Views
		TextView tv = (TextView) findViewById(R.id.tvDetailTest);
		tv.setText(b.getString("block"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_view, menu);
		return true;
	}

}
