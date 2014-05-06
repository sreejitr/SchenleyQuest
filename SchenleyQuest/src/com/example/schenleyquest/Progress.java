package com.example.schenleyquest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;


public class Progress extends Activity{

      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
		
		//Locate the list view
		ListView lv = (ListView) findViewById(R.id.listView);
		List<ListViewItem> items = new ArrayList<Progress.ListViewItem>(); 
		
		//Fill in the data for the List View Items
		int l = Main.PROGRESS_QUESTION.size();
		
		for(Main.i=0; Main.i<l; Main.i++)
		{				
			items.add(new ListViewItem()
			{{
				if((Main.PROGRESS_ANS_CORRECT.get(Main.i)).equals("yes"))
					ThumbnailImage = R.drawable.greentick;
				else
					ThumbnailImage = R.drawable.redcross;				
				
				Title = Main.PROGRESS_QUESTION.get(Main.i);
				Subtitle = Main.PROGRESS_ANSWER.get(Main.i);
			}});			   
		}
		
		//Initialize the progress list adapter and attach it to the list view
		ProgressListAdapter adapter = new ProgressListAdapter(this, items);
		lv.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.progress, menu);
		return true;
	}
	
	//Represent the elements in a row of the list
	class ListViewItem
	{
		public int ThumbnailImage;
		public String Title;
		public String Subtitle;
	}

}