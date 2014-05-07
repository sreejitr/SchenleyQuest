package com.example.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DirectionHint extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Main.TOTALSCORE -= 300;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direction_hint);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.direction_hint, menu);
		return true;
	}

}
