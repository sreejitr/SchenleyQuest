package edu.cmu.west.schenleyquest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
		if (inputParameters.length <= 2) {
			
			if (inputParameters.length == 2)
				resetQuest();			
			
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
			    Contract.Questions._ID,
			    Contract.Questions.COLUMN_NAME_QUESTION
			    };
			
			String selection = Contract.Questions.COLUMN_NAME_FEATURE_ID + "=?";
			
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
				    Contract.Options.COLUMN_NAME_FEATURE_ID
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
					    cursor.getColumnIndexOrThrow(Contract.Options.COLUMN_NAME_FEATURE_ID))).equals(featureId))
					correctOption = Integer.toString(i+1);
				cursor.moveToNext();
			}
			db.close();
			dbHelper.close();
			
		}
		
		setContentView(R.layout.activity_questions);
		
		TextView scoreText =(TextView)findViewById(R.id.textViewScore);
		scoreText.setText(scoreText.getText()+Integer.toString(Main.TOTALSCORE));
		
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
			//Add the selected question to PROGRESS
    		Main.PROGRESS_QUESTION.add(question);
    		//Add the selected answer to PROGRESS_ANSWER
    		int selectedAnswerID = Integer.parseInt(selectedOption);
    		Main.PROGRESS_ANSWER.add(option[selectedAnswerID-1]);    		
    		String correctAnswer = (selectedOption.equals(correctOption)) ? "yes" : "no";    	
    		//Add answer correctness flag to PROGRESS_ANS_CORRECT
    		Main.PROGRESS_ANS_CORRECT.add(correctAnswer);    		
    		Intent intentSubmit = new Intent(this, TransitionScreen.class);
    		intentSubmit.putExtra(Main.KEY_TRANSITION, featureId + " " + qId + " " + correctAnswer + " " + Integer.toString((Integer.parseInt(featureId) + 1)));
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
    		dispatchTakePictureIntent();
    		break;
    	case R.id.buttonProgress:
    		Intent intentProgress = new Intent(this, Progress.class);
        	startActivity(intentProgress);
        	break;
    	default:
    	throw new RuntimeException("Unknown button ID");
    	}
    }
    
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            	System.out.println(ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}