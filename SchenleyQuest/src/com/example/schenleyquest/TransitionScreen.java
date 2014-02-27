package com.example.schenleyquest;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
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
		
		TextView answer_Label = (TextView)findViewById(R.id.textView_score);
		TextView answer_Text = (TextView)findViewById(R.id.textView_option);
		
		if (inputParameters.length == 4) {
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
			
			if(inputParameters[2].equals("no"))
			{				
				answer_Label.setVisibility(View.VISIBLE);
				answer_Text.setVisibility(View.VISIBLE);
				message = "Sorry, your answer is incorrect";			
			}
			else
			{
				answer_Label.setVisibility(View.INVISIBLE);
				answer_Text.setVisibility(View.INVISIBLE);
				message = "Congratulations, you have won 10000 points";					
			}
		}		

		answer_Text.setText(answer);
		
		TextView description_text = (TextView)findViewById(R.id.textViewdesc);
		description_text.setText(desc);
		
		TextView message_text = (TextView)findViewById(R.id.textView_win_msg);
		message_text.setText(message);		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transition_screen, menu);
		return true;
	}

    /** Called when the user clicks the Level 1, Level 2, Level 3 buttons or the Settings button */
    public void nextQuestionClick(View view) {		
		
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_TRANSITION).split("\\s+");
		
		if(inputParameters[3].equals("3"))
		{
			Intent intent = new Intent(this, WinScreen.class);		
			startActivity(intent);
			this.finish();
		}
		else
		{
			Intent intent1 = new Intent(this, Questions.class);	
			intent1.putExtra(Main.KEY_QUESTION, inputParameters[3]);		
			startActivity(intent1);
			this.finish();
		}
		
    } 
    
}
