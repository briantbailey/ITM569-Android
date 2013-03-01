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
		TextView tvCase = (TextView) findViewById(R.id.tvDetailCaseNo);
		tvCase.setText(b.getString("caseNum"));
		TextView tvDate = (TextView) findViewById(R.id.tvDetailDateOf);
		tvDate.setText((b.getString("dateOf")).substring(0, 10) + "    " + (b.getString("dateOf")).substring(11));
		TextView tvBlock = (TextView) findViewById(R.id.tvDetailBlock);
		tvBlock.setText((b.getString("block")));
		TextView tvWard = (TextView) findViewById(R.id.tvDetailWard);
		tvWard.setText((b.getString("ward")));
		TextView tvLat = (TextView) findViewById(R.id.tvDetailLat);
		tvLat.setText((b.getString("latitude")));
		TextView tvLong = (TextView) findViewById(R.id.tvDetailLong);
		tvLong.setText((b.getString("longitude")));
		TextView tvDist = (TextView) findViewById(R.id.tvDetailDist);
		tvDist.setText((b.getString("distance")));
		TextView tvLoc = (TextView) findViewById(R.id.tvDetailLocation);
		tvLoc.setText((b.getString("locationDesc")));
		TextView tvPrimary = (TextView) findViewById(R.id.tvDetailPrimary);
		tvPrimary.setText((b.getString("primaryDesc")));
		TextView tvSecondary = (TextView) findViewById(R.id.tvDetailSecondary);
		tvSecondary.setText((b.getString("secondaryDesc")));
		TextView tvBeat = (TextView) findViewById(R.id.tvDetailBeat);
		tvBeat.setText((b.getString("beat")));
		TextView tvDom = (TextView) findViewById(R.id.tvDetailDomestic);
		tvDom.setText((b.getString("domestic")));
		TextView tvArrest = (TextView) findViewById(R.id.tvDetailArrest);
		tvArrest.setText((b.getString("arrest")));
		TextView tvIucr = (TextView) findViewById(R.id.tvDetailIucr);
		tvIucr.setText((b.getString("iucr")));
		TextView tvFbi = (TextView) findViewById(R.id.tvDetailFBI);
		tvFbi.setText((b.getString("fbi_cd")));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_view, menu);
		return true;
	}

}
