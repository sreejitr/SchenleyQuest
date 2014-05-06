package com.example.schenleyquest;

import java.util.List;

import com.example.schenleyquest.Progress.ListViewItem;

import android.app.Activity;  
import android.content.Context;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView; 
import android.widget.TextView;  
  
public class ProgressListAdapter extends BaseAdapter
{  
	
	LayoutInflater inflater;
	List<ListViewItem> items;
	
    public ProgressListAdapter(Activity context, List<ListViewItem> items) {  
        super();
		
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return items.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return 0;  
    }
      
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  

    	//Locate current item by its position
    	ListViewItem item = items.get(position);
    	
    	View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.progress_list_item, null);
            
        ImageView imgThumbnail = (ImageView) vi.findViewById(R.id.answer_type);
        TextView txtTitle = (TextView) vi.findViewById(R.id.question);
        TextView txtSubtitle = (TextView) vi.findViewById(R.id.answer_subtitle);
          
        //Set the data value of each element in the list row 
        imgThumbnail.setImageResource(item.ThumbnailImage);
        txtTitle.setText(item.Title);
        txtSubtitle.setText(item.Subtitle);
        
        return vi;  
    }
}