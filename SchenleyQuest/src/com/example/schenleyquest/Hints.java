package com.example.schenleyquest;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Hints extends Activity {
	
	String featureId = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_HINT).split("\\s+");
		featureId = inputParameters[0];
		setContentView(R.layout.activity_hints);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hints, menu);
		return true;
	}

	public void hintButtonClick(View view) {
    	
    	switch(view.getId())
    	{
    	case R.id.imageButton1:
    		Intent intenthintText = new Intent(this, HintText.class);
    		intenthintText.putExtra(Main.KEY_HINT, featureId);
        	startActivity(intenthintText);
        	this.finish();
        	break;
    	case R.id.imageButton2:
    		Intent intentDirectionHint = new Intent(this, DirectionHint.class);
    		intentDirectionHint.putExtra(Main.KEY_HINT, featureId);
        	startActivity(intentDirectionHint);
        	this.finish();
        	break;
    	default:
    	throw new RuntimeException("Unknown button ID");
    	}
	}
}
