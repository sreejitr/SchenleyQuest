package com.example.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Main extends Activity {
	
	public static final String KEY_QUESTION = "com.example.myfirstapp.KeyQuestion";
	
	public static final String KEY_TRANSITION = "com.example.myfirstapp.KeyTransition";
	
	public static final String KEY_HINT = "com.example.myfirstapp.KeyHint";
	
	public static final int TOTALSCORE = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Level 1, Level 2, Level 3 buttons or the Settings button */
    public void mainButtonClick(View view) {
    	
    	switch(view.getId())
    	{
    	case R.id.button1:
    		Intent intent1 = new Intent(this, Questions.class);
    		intent1.putExtra(KEY_QUESTION, "1");
        	startActivity(intent1);
        	break;
    	case R.id.button2:
    		Intent intent2 = new Intent(this, Questions.class);
    		intent2.putExtra(KEY_QUESTION, "2");
        	startActivity(intent2);
        	break;
    	case R.id.button3:
    		Intent intent3 = new Intent(this, Questions.class);
    		intent3.putExtra(KEY_QUESTION, "1");
        	startActivity(intent3);
        	break;
        case R.id.button4:
        	Intent intentSettings = new Intent(this, Options.class);
        	startActivity(intentSettings);
        	break;
    	default:
    	throw new RuntimeException("Unknown button ID");
    	}
    }   
    
}
