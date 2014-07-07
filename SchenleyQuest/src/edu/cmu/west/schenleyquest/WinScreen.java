package edu.cmu.west.schenleyquest;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
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
		
		Time timeNow = new Time();
		timeNow.setToNow();
		
		DBHelper dbHelper = new DBHelper(this);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(Contract.HighScores.COLUMN_NAME_ROUTE_ID, Main.ROUTE_ID);
		values.put(Contract.HighScores.COLUMN_NAME_DIFFICULTY_LEVEL, Main.DIFFICULTY);
		values.put(Contract.HighScores.COLUMN_NAME_SCORE, Main.TOTALSCORE);
		values.put(Contract.HighScores.COLUMN_NAME_TIMESTAMP, timeNow.toString());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
		         Contract.HighScores.TABLE_NAME,
		         "null",
		         values);
		
		TextView score_text = (TextView)findViewById(R.id.textView_score);
		score_text.setText(score);		
		
		if(Main.DIFFICULTY.equals("easy"))
		{
			if(Main.TOTALSCORE >= 5400) {
				//int imageResource = getResources().getIdentifier(uri_rookie, null, getPackageName());
				imageview= (ImageView)findViewById(R.id.imageView2); 
				imageview.setImageResource(R.drawable.rookie_badge);
				//Drawable res = getResources().getDrawable(imageResource);
				//imageview.setImageDrawable(res);
				
				values = new ContentValues();
				values.put(Contract.Badges.COLUMN_NAME_BADGE, "easy");
				values.put(Contract.Badges.COLUMN_NAME_ROUTE_ID, Main.ROUTE_ID);
				values.put(Contract.Badges.COLUMN_NAME_DIFFICULTY_LEVEL, Main.DIFFICULTY);
				values.put(Contract.Badges.COLUMN_NAME_TIMESTAMP, timeNow.toString());
				values.put(Contract.Badges.COLUMN_NAME_NO_OF_BADGES, 1);

				// Insert the new row, returning the primary key value of the new row
				newRowId = db.insert(
				         Contract.Badges.TABLE_NAME,
				         "null",
				         values);
			}
		}
		else if(Main.DIFFICULTY.equals("medium"))
		{
			if(Main.TOTALSCORE >= 16200) {
				//int imageResource = getResources().getIdentifier(uri_experienced, null, getPackageName());

				imageview= (ImageView)findViewById(R.id.imageView2); 
				imageview.setImageResource(R.drawable.experienced_badge);
				//Drawable res = getResources().getDrawable(imageResource);
				//imageview.setImageDrawable(res);
				values = new ContentValues();
				values.put(Contract.Badges.COLUMN_NAME_BADGE, "medium");
				values.put(Contract.Badges.COLUMN_NAME_ROUTE_ID, Main.ROUTE_ID);
				values.put(Contract.Badges.COLUMN_NAME_DIFFICULTY_LEVEL, Main.DIFFICULTY);
				values.put(Contract.Badges.COLUMN_NAME_TIMESTAMP, timeNow.toString());
				values.put(Contract.Badges.COLUMN_NAME_NO_OF_BADGES, 1);

				// Insert the new row, returning the primary key value of the new row
				newRowId = db.insert(
				         Contract.Badges.TABLE_NAME,
				         "null",
				         values);
			}
		}
		else if(Main.DIFFICULTY.equals("hard"))
		{
			if(Main.TOTALSCORE >= 32400) {
				//int imageResource = getResources().getIdentifier(uri_expert, null, getPackageName());

				imageview= (ImageView)findViewById(R.id.imageView2); 
				imageview.setImageResource(R.drawable.expert_badge);
				//Drawable res = getResources().getDrawable(imageResource);
				//imageview.setImageDrawable(res);
				
				values = new ContentValues();
				values.put(Contract.Badges.COLUMN_NAME_BADGE, "hard");
				values.put(Contract.Badges.COLUMN_NAME_ROUTE_ID, Main.ROUTE_ID);
				values.put(Contract.Badges.COLUMN_NAME_DIFFICULTY_LEVEL, Main.DIFFICULTY);
				values.put(Contract.Badges.COLUMN_NAME_TIMESTAMP, timeNow.toString());
				values.put(Contract.Badges.COLUMN_NAME_NO_OF_BADGES, 1);

				// Insert the new row, returning the primary key value of the new row
				newRowId = db.insert(
				         Contract.Badges.TABLE_NAME,
				         "null",
				         values);
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
