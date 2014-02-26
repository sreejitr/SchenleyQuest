package com.example.schenleyquest;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class Questions extends Activity {

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
		String question = "";
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_QUESTION).split("\\s+");
		if (inputParameters.length == 1) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
			    Contract.Questions._ID,
			    Contract.Questions.COLUMN_NAME_QUESTION
			    };
			
			String selection = Contract.Questions.COLUMN_NAME_FEATURES_ID + "=?";
			
			String[] selectionArgs = {inputParameters[0]};

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
			question = cursor.getString(
			    cursor.getColumnIndexOrThrow(Contract.Questions.COLUMN_NAME_QUESTION)
			);
		}
		
		setContentView(R.layout.activity_questions);
		TextView questionText = (TextView)findViewById(R.id.text_view_question);
		questionText.setText(question);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}

}
