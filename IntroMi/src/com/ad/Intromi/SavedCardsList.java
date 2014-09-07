package com.ad.Intromi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SavedCardsList extends Activity {

	    private ListView lv1;
	
	    private ArrayList<ItemDetails> results;
	  
	    private ItemListBaseAdapter lAdapter;
	    private Cards cards;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_cards_list);
		  results = new ArrayList<ItemDetails>();
		lv1 = (ListView) findViewById(R.id.listViewSavedCards); 
	
		lAdapter = new ItemListBaseAdapter(this,results);
		 cards = new Cards(getBaseContext());
		if (cards.loadCards()!=null)
		{
		 System.out.println("tttt" +cards.mSaveCards.profileArrayList.get(0).getName());
		}
	/*
		lv1.setOnItemClickListener(new OnItemClickListener() {

	        public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
	                long arg3) {
	      	Profile  item= results.get(pos);
			Intent intent = new Intent(SavedCardsList.this, DetailActivity.class);
			intent.putExtra("url", item.getName());
//			intent.putExtra("title", item.getPrice());
//			intent.putExtra("desc", item.getItemDescription());
			intent.putExtra("head_line",item.getProfessionalHeadLine());
			intent.putExtra("site",item.getSite());
			intent.putExtra("mission",item.getMission());
//			intent.putExtra("photo", getPhotoStringFromBitmap(item.getImg()));
			intent.putExtra("name",item.getName());
			
			
			
			startActivity(intent);
	             Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();


	        
	        }    
	    });
	*/  
		


    	ItemDetails item_details = new ItemDetails();
    	item_details.setName("name");
    	item_details.setItemDescription("ii");
    	item_details.setPrice("e");
    	item_details.setSite("u");
    	item_details.setImg("");
    	item_details.setProfessionlaHeadLine("he");
    	item_details.setMission("mission");
    	results.add(item_details);
        
  	lv1.setAdapter(lAdapter);
    	lAdapter.notifyDataSetChanged();
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_cards_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
    }
	  
}

