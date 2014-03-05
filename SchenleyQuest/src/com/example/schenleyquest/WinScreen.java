package com.example.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class WinScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win_screen);
		
		String message = "Congratulations! Your quest is complete!";
				
		String score = "Total score: " + Main.TOTALSCORE;

		TextView message_text = (TextView)findViewById(R.id.textView_win_msg);
		message_text.setText(message);	
		
		TextView score_text = (TextView)findViewById(R.id.textView_score);
		score_text.setText(score);			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.win_screen, menu);
		return true;
	}
	
	public void backToMenu(View view) {
		Intent intent = new Intent(this, Main.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		this.finish();
	}

}
