package edu.cmu.west.schenleyquest;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TransitionScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transition_screen);
				
		DBHelper dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 
	 		dbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}
		dbHelper.close();
		
		String answer = "";
		String message = "";
		String desc = "";
		
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_TRANSITION).split("\\s+");
		
		//TextView answer_Label = (TextView)findViewById(R.id.textView_score);
		TextView answer_Text = (TextView)findViewById(R.id.textView_option);
		ImageView image = (ImageView)findViewById(R.id.imageView2);
		TextView message_text = (TextView)findViewById(R.id.textView_win_msg);
		
		if (inputParameters.length == 3) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
			    Contract.Options._ID,
			    Contract.Options.COLUMN_NAME_OPTION
			    };
			
			String selection = Contract.Options.COLUMN_NAME_FEATURE_ID + "=? AND " +
					Contract.Options.COLUMN_NAME_QUESTION_ID + "=?";
			
			String[] selectionArgs = {inputParameters[0],inputParameters[1]};

			// How you want the results sorted in the resulting Cursor
			String sortOrder =
					Contract.Options._ID + " ASC";

			
			Cursor cursor = db.query(
					Contract.Options.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
			
			cursor.moveToFirst();
			answer = cursor.getString(
			    cursor.getColumnIndexOrThrow(Contract.Options.COLUMN_NAME_OPTION)
			);					
			
			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection2 = {
			    Contract.Features._ID,
			    Contract.Features.COLUMN_NAME_FEATURE_DESC
			    };
			
			String selection2 = Contract.Features._ID + "=?";
			
			String[] selectionArgs2 = {inputParameters[0]};

			// How you want the results sorted in the resulting Cursor
			String sortOrder2 =
					Contract.Features._ID + " ASC";

			
			Cursor cursor2 = db.query(
					Contract.Features.TABLE_NAME,  // The table to query
			    projection2,                               // The columns to return
			    selection2,                                // The columns for the WHERE clause
			    selectionArgs2,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder2                                 // The sort order
			    );
			
			cursor2.moveToFirst();
			desc = cursor2.getString(
			    cursor2.getColumnIndexOrThrow(Contract.Features.COLUMN_NAME_FEATURE_DESC)
			);
			db.close();
			dbHelper.close();
			
			if(inputParameters[2].equals("no"))
			{
				//RelativeLayout.LayoutParams paramsBannerText = (RelativeLayout.LayoutParams) message_text.getLayoutParams();
				//paramsBannerText.topMargin = getDpAsPixels(70);
				
				//RelativeLayout.LayoutParams paramsAnswer = (RelativeLayout.LayoutParams) answer_Label.getLayoutParams();
				//paramsAnswer.addRule(RelativeLayout.BELOW, R.id.textView_win_msg);
				
				//int topPadAns = getDpAsPixels(30);
				//answer_Label.setPadding(0, topPadAns, 0, 0);
				image.setVisibility(View.GONE);
				//answer_Label.setLayoutParams(paramsAnswer);
				//answer_Label.setVisibility(View.GONE);
				answer_Text.setVisibility(View.VISIBLE);
				message = "Sorry, your answer is incorrect";			
			}
			else
			{
				//answer_Label.setVisibility(View.GONE);
				answer_Text.setVisibility(View.GONE);
				
				if(Main.DIFFICULTY.equals("easy")) {
					Main.TOTALSCORE += 1000;
					message = "Congratulations, you have won 1000 points";
				}
				else if(Main.DIFFICULTY.equals("medium")) {
					Main.TOTALSCORE += 2000;	
					message = "Congratulations, you have won 2000 points";	
				}
				else {
					Main.TOTALSCORE += 3000;
					message = "Congratulations, you have won 3000 points";
				}
			}
		}		

		answer_Text.setText("Answer: " + answer);
		
		TextView description_text = (TextView)findViewById(R.id.textViewdesc);
		description_text.setText(desc);
		
		message_text.setText(message);		
		
	}

	/**
	 * @param dp
	 * @return
	 */
//	private int getDpAsPixels(int dp) {
//		float scale = getResources().getDisplayMetrics().density;
//		int dpAsPixels = (int) (dp*scale + 0.5f);
//		return dpAsPixels;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transition_screen, menu);
		return true;
	}

    /** Called when the user clicks the Level 1, Level 2, Level 3 buttons or the Settings button */
    public void nextQuestionClick(View view) {		
		
		//String[] inputParameters = getIntent().getStringExtra(Main.KEY_TRANSITION).split("\\s+");
		
		if(Main.QINDEX>=20 || Main.QUESTIONID_SET.length == Main.QINDEX +1) 
		{
			Intent intent = new Intent(this, WinScreen.class);		
			startActivity(intent);
			this.finish();	
		}
		else
		{
			Intent intent1 = new Intent(this, Questions.class);	
			intent1.putExtra(Main.KEY_QUESTION, String.valueOf(Main.QINDEX));		
			startActivity(intent1);
			this.finish();
		}
		
    } 
    
}
