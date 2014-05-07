package edu.cmu.west.schenleyquest;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class HintText extends Activity {

	String featureId = "";
	String hint = "";
	TextView hintText;
	
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
		
		String[] inputParameters = getIntent().getStringExtra(Main.KEY_HINT).split("\\s+");
		
		featureId = inputParameters[0];
		if (inputParameters.length == 1) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();

			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
			    Contract.Features._ID,
			    Contract.Features.COLUMN_NAME_FEATURE_HINT
			    };
			
			String selection = Contract.Features._ID + "=?";
			
			String[] selectionArgs = {featureId};

			// How you want the results sorted in the resulting Cursor
			String sortOrder =
					Contract.Features._ID + " ASC";

			Cursor cursor = db.query(
					Contract.Features.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
			
			cursor.moveToFirst();
			
			hint = cursor.getString(
			    cursor.getColumnIndexOrThrow(Contract.Features.COLUMN_NAME_FEATURE_HINT));
			
			db.close();
			dbHelper.close();
		}
		
		setContentView(R.layout.activity_hint_text);
		hintText = (TextView) findViewById(R.id.textView_hinttext);
		hintText.setText(hint);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hint_text, menu);
		return true;
	}

}
