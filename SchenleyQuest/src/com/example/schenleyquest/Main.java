package com.example.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Main extends Activity {

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
    
    /** Called when the user clicks the Level 1, Level 2 or Level 3 buttons */
    public void startGame(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, Questions.class);
    	startActivity(intent);
    }
    
    public void openOptions(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, Options.class);
    	startActivity(intent);
    }    
    
}
