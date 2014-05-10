package edu.cmu.west.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WinScreen extends Activity {
	String uri_rookie = "@drawable/rookie_badge.png";
	String uri_experienced = "@drawable/experienced_badge.png";
	String uri_expert = "@drawable/expert_badge.png";
	ImageView imageview;
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
		
		if(Main.difficulty.equals("easy"))
		{
			if(Main.TOTALSCORE >= 5400) {
				//int imageResource = getResources().getIdentifier(uri_rookie, null, getPackageName());
				imageview= (ImageView)findViewById(R.id.imageView2); 
				imageview.setImageResource(R.drawable.rookie_badge);
				//Drawable res = getResources().getDrawable(imageResource);
				//imageview.setImageDrawable(res);
			}
		}
		else if(Main.difficulty.equals("medium"))
		{
			if(Main.TOTALSCORE >= 16200) {
				//int imageResource = getResources().getIdentifier(uri_experienced, null, getPackageName());

				imageview= (ImageView)findViewById(R.id.imageView2); 
				imageview.setImageResource(R.drawable.experienced_badge);
				//Drawable res = getResources().getDrawable(imageResource);
				//imageview.setImageDrawable(res);
			}
		}
		else if(Main.difficulty.equals("hard"))
		{
			if(Main.TOTALSCORE >= 32400) {
				//int imageResource = getResources().getIdentifier(uri_expert, null, getPackageName());

				imageview= (ImageView)findViewById(R.id.imageView2); 
				imageview.setImageResource(R.drawable.expert_badge);
				//Drawable res = getResources().getDrawable(imageResource);
				//imageview.setImageDrawable(res);
			}
		}
		
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
