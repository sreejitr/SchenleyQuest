package edu.cmu.west.schenleyquest;

import java.io.IOException;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
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
	String questionSet = "";
	String questionId = "";
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
		//ROUTES
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_QUESTION).split("\\s+");
		//featureId = inputParameters[0];
		if (inputParameters.length <= 2) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (inputParameters.length == 2) {
				resetQuest();			
				Main.DIFFICULTY = inputParameters[0];
				// Define a projection that specifies which columns from the database
				// you will actually use after this query.
				String[] projection = {
						Contract.Routes._ID,
					    Contract.Routes.COLUMN_NAME_TIMESTAMP,
					    Contract.Routes.COLUMN_NAME_DIFFICULTY_LEVEL,
					    Contract.Routes.COLUMN_NAME_QUESTIONID_SET,
					    };
				
				
				String selection = Contract.Routes.COLUMN_NAME_DIFFICULTY_LEVEL + "=?";
				
				String[] selectionArgs = {Main.DIFFICULTY};
	
				// How you want the results sorted in the resulting Cursor
				String sortOrder =
						Contract.Routes.COLUMN_NAME_TIMESTAMP + " ASC";
						// Contract.Questions._ID + " ASC";
	
				Cursor cursor = db.query(
					Contract.Routes.TABLE_NAME,               // The table to query
				    projection,                               // The columns to return
				    selection,                                // The columns for the WHERE clause
				    selectionArgs,                            // The values for the WHERE clause
				    null,                                     // don't group the rows
				    null,                                     // don't filter by row groups
				    sortOrder                                 // The sort order
				    );
				
				cursor.moveToFirst();
				
				String rowId = cursor.getString(
					    cursor.getColumnIndexOrThrow(Contract.Routes._ID)
						);
				Main.ROUTE_ID = Integer.parseInt(rowId);
				questionSet = cursor.getString(
					    cursor.getColumnIndexOrThrow(Contract.Routes.COLUMN_NAME_QUESTIONID_SET)
						);
				Main.QUESTIONID_SET = questionSet.split(",");
				
				//SQLiteDatabase db2 = dbHelper.getWritableDatabase();
				
				Time now = new Time();
				now.setToNow();
				ContentValues values = new ContentValues();
				values.put(Contract.Routes.COLUMN_NAME_TIMESTAMP, now.toString());
				
				// Which row to update, based on the ID
				selection = Contract.Routes._ID + " LIKE ?";
				selectionArgs = new String[] { String.valueOf(rowId) };

				int count = db.update(
				    Contract.Routes.TABLE_NAME,
				    values,
				    selection,
				    selectionArgs);
			}
			
			questionId = Main.QUESTIONID_SET[++Main.QINDEX];
			String[] projection = {
			    Contract.Questions._ID,
			    Contract.Questions.COLUMN_NAME_QUESTION,
			    Contract.Questions.COLUMN_NAME_FEATURE_ID
			    };
			
			String selection = Contract.Questions._ID + "=?";
			
			String[] selectionArgs = {questionId};

			// How you want the results sorted in the resulting Cursor
			//String sortOrder =
			//		Contract.Questions._ID + " ASC";

			Cursor cursor = db.query(
					Contract.Questions.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,
			    null                                      // Sort order not required
			    // don't filter by row groups
			    //sortOrder                                 // The sort order
			    );
			
			cursor.moveToFirst();
			
			//qId = Integer.toString(cursor.getInt(
			//	    cursor.getColumnIndexOrThrow(Contract.Questions._ID)));
			qId = questionId;
			question = cursor.getString(
			    cursor.getColumnIndexOrThrow(Contract.Questions.COLUMN_NAME_QUESTION)
			);
			featureId = cursor.getString(
				    cursor.getColumnIndexOrThrow(Contract.Questions.COLUMN_NAME_FEATURE_ID));
			  		
			String[] optionProjection = {
					Contract.Options._ID,
				    Contract.Options.COLUMN_NAME_OPTION,
				    Contract.Options.COLUMN_NAME_FEATURE_ID
				    };
			selection = Contract.Options.COLUMN_NAME_QUESTION_ID + "=?";
			selectionArgs[0] = qId;
			String sortOrder = Contract.Options._ID + " ASC";
			
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
					    cursor.getColumnIndexOrThrow(Contract.Options.COLUMN_NAME_FEATURE_ID))).equals(featureId))
					correctOption = Integer.toString(i+1);
				cursor.moveToNext();
			}
			db.close();
			dbHelper.close();
			
		}
		
		setContentView(R.layout.activity_questions);
		
		TextView scoreText =(TextView)findViewById(R.id.textViewScore);
		scoreText.setText("Score: "+Integer.toString(Main.TOTALSCORE));
		
		questionText = (TextView)findViewById(R.id.text_view_question);
		questionText.setText(question);
		radioOpGroup = (RadioGroup) findViewById(R.id.radioOptions);
		for (int i = 0; i < radioOpGroup .getChildCount(); i++) {
            ((RadioButton) radioOpGroup.getChildAt(i)).setText(option[i]);
        }
		
	}

	private void resetQuest() {
		Main.TOTALSCORE = 0;
		//Main.PROGRESS = new String[10];
		Main.PROGRESS_QUESTION.clear();
		Main.PROGRESS_ANSWER.clear();
		Main.PROGRESS_ANS_CORRECT.clear();
		Main.QUESTIONID_SET  = new String[20];
		Main.QINDEX = -1;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TextView scoreText =(TextView)findViewById(R.id.textViewScore);
		scoreText.setText("");
		scoreText.setText("Score: " + Integer.toString(Main.TOTALSCORE));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}
	
	public void questionRadioButtonClick(View view) {
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setVisibility(View.GONE);
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
			//Add the selected question to PROGRESS
    		Main.PROGRESS_QUESTION.add(question);
    		//Add the selected answer to PROGRESS_ANSWER
    		int selectedAnswerID = Integer.parseInt(selectedOption);
    		Main.PROGRESS_ANSWER.add(option[selectedAnswerID-1]);    		
    		String correctAnswer = (selectedOption.equals(correctOption)) ? "yes" : "no";    	
    		//Add answer correctness flag to PROGRESS_ANS_CORRECT
    		Main.PROGRESS_ANS_CORRECT.add(correctAnswer);    		
    		Intent intentSubmit = new Intent(this, TransitionScreen.class);
    		intentSubmit.putExtra(Main.KEY_TRANSITION, featureId + " " + qId + " " + correctAnswer);
        	startActivity(intentSubmit);
        	this.finish();
        	break;
        	
    	case R.id.hint_button:
    		Intent intentHint = new Intent(this, Hints.class);
    		intentHint.putExtra(Main.KEY_HINT, featureId);
        	startActivity(intentHint);
        	break;
    	case R.id.buttonBackToMenu:
    		Intent intent = new Intent(this, Main.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		startActivity(intent);
    		this.finish();
    		break;
    	case R.id.buttonPhoto:
    		//dispatchTakePictureIntent();
    		Intent intentPhotoSharing = new Intent(this, PhotoSharing.class);
    		startActivity(intentPhotoSharing);
    		break;
    	case R.id.buttonProgress:
    		Intent intentProgress = new Intent(this, Progress.class);
        	startActivity(intentProgress);
        	break;
    	default:
    	throw new RuntimeException("Unknown button ID");
    	}
    }
}