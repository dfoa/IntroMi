package com.ad.Intromi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SavedCardsList extends Activity {

	    private ActionBar actionBar;
	    private static final String TAG = "IntroMi/SavedCardsList";
        private static final int RELOAD_LIST = 1;
		private static final boolean D = true;

		private ListView listViewSavedCards;
	
	    private ArrayList<Profile> results;
	    private SavedCards mSaveCards; 
	    private  String fileName = "cards.bin";   
	    private CardsListBaseAdapter listAdapterFav;
	    private Cards cards;
	    private ProgressDialog mProgress;
	    private ProgressDialog pBar;
	    private boolean firstTime = true;
	    private String IMAGE_FILE = "image";
	    private 	GestureDetector	gestureDetector;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_cards_list);
		
		System.out.println("+++On create SavedcardList");
		
		
		gestureDetector = new GestureDetector(
                new SwipeGestureDetector());
	
       
		 
		  results = new ArrayList<Profile>();
	    	listViewSavedCards = (ListView) findViewById(R.id.listViewSavedCards); 
	    	
		listAdapterFav = new CardsListBaseAdapter(getBaseContext(),results);
	
		
	
		 mSaveCards = new SavedCards();
		 cards = new Cards(getBaseContext(),fileName);
	  	 
			//mSaveCards = 
					new LoadSavedcardsAsyncTask().execute();
		
	
   	  listViewSavedCards.setOnItemClickListener(new OnItemClickListener() {

	        public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
	                long arg3) {
	      	Profile p = results.get(pos);
			Intent intent = new Intent(SavedCardsList.this, DetailActivity.class);
			intent.putExtra("url", p.getName());
			intent.putExtra("title", p.getEmail());
			intent.putExtra("desc", p.getMobilePhoneNum());
			intent.putExtra("head_line",p.getProfessionalHeadLine());
			intent.putExtra("site",p.getSite());
			intent.putExtra("mission",p.getMission());
			intent.putExtra("mac", p.getMacHw());
			
			
			//intent.putExtra("photo", getPhotoStringFromBitmap(p.getImg()));
			//save photo to cache
			writeStringAsFile(getPhotoStringFromBitmap(p.getImg()));
			intent.putExtra("name",p.getName());
			intent.putExtra("site", p.getSite());
			intent.putExtra("file_name",fileName);
			
			
			
	startActivity(intent);
	//		startActivityForResult(intent, RELOAD_LIST);
	           


	        }
	    });
		
	}




	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
    }
	
	  public String getPhotoStringFromBitmap(Bitmap bm){
			 
			 String ba1 = null;;
				if (bm!=null)
				{
				Bitmap bitmapOrg = bm;
		     ByteArrayOutputStream bao = new ByteArrayOutputStream();
	
		     bitmapOrg.compress(Bitmap.CompressFormat.PNG, 95, bao);
		     byte[] ba = bao.toByteArray();
		     ba1 = Base64.encodeToString(ba, 0);
		       	return ba1;
				}
				else {
					return null;
				}
				
		 
		 }
	  



private class LoadSavedcardsAsyncTask extends AsyncTask<String, String, SavedCards>{
 	 
	  
  	
  	@Override
  	protected void onPreExecute(){
 		super.onPreExecute();
  		pBar = new ProgressDialog(SavedCardsList.this);
        
		pBar.setMessage("Scanning please wait....");
		
		
		pBar.show();
      
  	}
		@Override
		protected SavedCards doInBackground(String... params) {
			// TODO Auto-generated method stub
		
			
			
		return   cards.loadCards();
		
		}

		protected void onPostExecute(SavedCards result){
			
         //  super.execute(result);
	//		Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
			
	//	 System.out.println("This is the card I am going to  bring information for" + card);
			
			ReturnresultsFromAsync(result);
		if(null!=pBar && pBar.isShowing())
           	  pBar.dismiss();
			
			
			
			
  			
  			

           
		}
		protected void onProgressUpdate(Integer... progress){
			
	
	

	}
}

private  void ReturnresultsFromAsync(SavedCards mSaveCards ){
	
	
if (mSaveCards!=null)
{



 for (int i =0  ;i<mSaveCards.profileArrayList.size();i++){
      if (D) Log.v(TAG,"this is the cards i have" + mSaveCards.profileArrayList.get(i).getName()); 	  ;
      if (mSaveCards.profileArrayList.get(i).getPicture()!= null)
      mSaveCards.profileArrayList.get(i).setImg(mSaveCards.profileArrayList.get(i).getPicture()); 
      results.add(mSaveCards.profileArrayList.get(i));



listViewSavedCards.setAdapter(listAdapterFav);

listAdapterFav.notifyDataSetChanged();

 }
 listAdapterFav.notifyDataSetChanged();
 
}


}

@Override
public  void onResume()
{
	
	 
		  
	  

	System.out.println("++onResume  SavedCardList");

if (!firstTime) {

	results.clear();
	
	
	 new LoadSavedcardsAsyncTask().execute();
//	 
	
  


	
	  }

else 	  firstTime = false;
	 
	  super.onResume();
}




	
	
	
	





@Override
public  void onStop()
{
	super.onStop();
 //   finish();
	
	System.out.println("++onStop  card list activity");
	
}

/*
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Check which request we're responding to
    if (requestCode == RELOAD_LIST) {
        // Make sure the request was successful
        if (resultCode == RESULT_OK) {
        	 System.out.println("Okay going to reload list ");
            // The user picked a contact.
            // The Intent's data Uri identifies which contact was selected.
   
		
	
	
            // Do something with the contact here (bigger example below)

        	results.clear();
        	
        	
        	 new LoadSavedcardsAsyncTask().execute();
        	 listAdapterFav.notifyDataSetChanged();
        }
    }
}


*/	
public  void writeStringAsFile( String imageString) {
    Context context =  getApplicationContext();
    
    try {
        FileWriter out = new FileWriter(new File(context.getFilesDir(), IMAGE_FILE));
        out.write(imageString);
        out.close();
    } catch (IOException e) {
        Log.v(TAG, "cant save image to disk");
        }
}


@Override
public boolean onTouchEvent(MotionEvent event) {
  if (gestureDetector.onTouchEvent(event)) {
    return true;
  }
  return super.onTouchEvent(event);
}

private void onLeftSwipe() {
  // Do something
}

private void onRightSwipe() {
  // Do something
}

// Private class for gestures
private class SwipeGestureDetector 
        extends SimpleOnGestureListener {
  // Swipe properties, you can change it to make the swipe 
  // longer or shorter and speed
  private static final int SWIPE_MIN_DISTANCE = 120;
  private static final int SWIPE_MAX_OFF_PATH = 200;
  private static final int SWIPE_THRESHOLD_VELOCITY = 200;

  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2,
                       float velocityX, float velocityY) {
    try {
      float diffAbs = Math.abs(e1.getY() - e2.getY());
      float diff = e1.getX() - e2.getX();

      if (diffAbs > SWIPE_MAX_OFF_PATH)
        return false;
      
      // Left swipe
      if (diff > SWIPE_MIN_DISTANCE
      && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
      //   DeviceListActivity.this.onLeftSwipe();
      System.out.println("swipe left");
      // Right swipe
      } else if (-diff > SWIPE_MIN_DISTANCE
      && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
    	  
        //HistoryCardslist.this.onRightSwipe();
      System.out.println("Swipr  right");
      }
    } catch (Exception e) {
      Log.e("YourActivity", "Error on gestures");
    }
    return false;
  }
}



}








