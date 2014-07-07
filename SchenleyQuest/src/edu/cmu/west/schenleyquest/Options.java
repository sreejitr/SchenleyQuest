package edu.cmu.west.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Options extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

	public void optionsButtonClick(View view) {
		switch(view.getId())
    	{
	    	case R.id.button1:
	    		Intent intent1 = new Intent(this, HighScores.class);
	    		//intent1.putExtra(KEY_QUESTION, "easy Start");
	        	startActivity(intent1);
	        	break;
	    	case R.id.button2:
	    		Intent intent2 = new Intent(this, Badges.class);
	    		//intent2.putExtra(KEY_QUESTION, "medium Start");
	        	startActivity(intent2);
	        	break;
	    	default:
	    	throw new RuntimeException("Unknown button ID");
    	}
	}
}
