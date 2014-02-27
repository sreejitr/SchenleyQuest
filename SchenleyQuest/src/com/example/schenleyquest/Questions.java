package com.example.schenleyquest;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Questions extends Activity {
	RadioGroup radioOpGroup;
	RadioButton radioOp;
	TextView questionText;
	String selectedOption;
	String correctOption;
	String featureId = "";
	String qId ="";
	String question = "";
	String[] option = new String[4];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
		
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_QUESTION).split("\\s+");
		featureId = inputParameters[0];
		if (inputParameters.length == 1) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
			    Contract.Questions._ID,
			    Contract.Questions.COLUMN_NAME_QUESTION
			    };
			
			String selection = Contract.Questions.COLUMN_NAME_FEATURES_ID + "=?";
			
			String[] selectionArgs = {featureId};

			// How you want the results sorted in the resulting Cursor
			String sortOrder =
					Contract.Questions._ID + " ASC";

			Cursor cursor = db.query(
					Contract.Questions.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
			
			cursor.moveToFirst();
			
			qId = Integer.toString(cursor.getInt(
				    cursor.getColumnIndexOrThrow(Contract.Questions._ID)));
			question = cursor.getString(
			    cursor.getColumnIndexOrThrow(Contract.Questions.COLUMN_NAME_QUESTION)
			);
			
			String[] optionProjection = {
					Contract.Options._ID,
				    Contract.Options.COLUMN_NAME_OPTION,
				    Contract.Options.COLUMN_NAME_FEATURES_ID
				    };
			selection = Contract.Options.COLUMN_NAME_QUESTION_ID + "=?";
			selectionArgs[0] = qId;
			sortOrder = Contract.Options._ID + " ASC";
			
			cursor = db.query(
					Contract.Options.TABLE_NAME,  // The table to query
				optionProjection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
			cursor.moveToFirst();
			for (int i = 0; i < 4; i ++){
				option[i] = cursor.getString(
					    cursor.getColumnIndexOrThrow(Contract.Options.COLUMN_NAME_OPTION));
				if(Integer.toString(cursor.getInt(
					    cursor.getColumnIndexOrThrow(Contract.Options.COLUMN_NAME_FEATURES_ID))).equals(featureId))
					correctOption = Integer.toString(i);
				cursor.moveToNext();
			}
			
		}
		
		setContentView(R.layout.activity_questions);
		questionText = (TextView)findViewById(R.id.text_view_question);
		questionText.setText(question);
		radioOpGroup = (RadioGroup) findViewById(R.id.radioOptions);
		for (int i = 0; i < radioOpGroup .getChildCount(); i++) {
            ((RadioButton) radioOpGroup.getChildAt(i)).setText(option[i]);
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}
	
	public void questionRadioButtonClick(View view) {
		Button submitButton = (Button)findViewById(R.id.submit_button);
		if (submitButton.getVisibility() == View.INVISIBLE)
			submitButton.setVisibility(View.VISIBLE);
			
		switch(view.getId())
		{
		case R.id.radioButton1:
			selectedOption = "1";
			break;
		case R.id.radioButton2:
			selectedOption = "2";
			break;
		case R.id.radioButton3:
			selectedOption = "3";
			break;
		case R.id.radioButton4:
			selectedOption = "4";
			break;
		default:
	    	throw new RuntimeException("Unknown button ID");
		}
	}
	
	
	
    /** Called when the user clicks the Level 1, Level 2, Level 3 buttons or the Settings button */
    public void questionsButtonClick(View view) {
    	
    	switch(view.getId())
    	{
    	case R.id.submit_button:
    		String correctAnswer = (selectedOption.equals(correctOption)) ? "yes" : "no";
    		Intent intentSubmit = new Intent(this, TransitionScreen.class);
    		intentSubmit.putExtra(Main.KEY_QUESTION, featureId + " " + qId + " " + correctAnswer + " " + Integer.toString((Integer.parseInt(featureId) + 1)));
        	startActivity(intentSubmit);
        	this.finish();
        	break;
    	case R.id.hint_button:
    		Intent intentHint = new Intent(this, Hints.class);
        	startActivity(intentHint);
        	break;

    	default:
    	throw new RuntimeException("Unknown button ID");
    	}
    }  	

}
