package com.example.android.BluetoothChat;



import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.jar.Attributes.Name;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class MyCards extends Activity {
    // Debugging
    private static final String TAG = "IntroMi";
    private static final boolean D = true;
    private ProgressBar pb;
    private TextView name;
    private TextView mobile; 
    private TextView email; 
    private ImageView photo;
  

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE card details+++ ");
        // Set up the window layout
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.my_cards_view);
          name = (TextView) findViewById(R.id.textViewNamePresent);
          mobile = (TextView) findViewById(R.id.textViewMobilePresent);
          email = (TextView)findViewById(R.id.textViewEmailPresent); 
          photo = (ImageView)findViewById(R.id.imageViewPhotoPresent);
        
       loadCard(); 
        
   //     final EditText site= (EditText)findViewById(R.id.siteEdit);
   //     Button btPickImage = (Button) findViewById(R.id.btImagepPick);
   //     Button saveRecords = (Button) findViewById(R.id.saveButton);
    }

    
    private  void  loadCard()
    {
    	final String filename = "card.bin";
    	 Profile p = new Profile(); 
         FileInputStream fis = null;
    	 ObjectInputStream in = null; 
    		    try {
    		      fis = openFileInput(filename);
    		      in = new ObjectInputStream(fis);
    		      p = (Profile) in.readObject();
    		      in.close();
    		    } catch (Exception ex) {
    		      ex.printStackTrace();
    		      Toast.makeText(this, "cant open file to read" , Toast.LENGTH_SHORT).show();
    		    }
    		    System.out.println("test");
    		    
    		     name.setText(p.name);
    		     mobile.setText(p.MobilePhoneNum);
    		     email.setText(p.email); 
    		     if ( p.picture != null)
    		     photo.setImageBitmap(setImg(p.picture));
    		    
    		   
    		  
    		    System.out.println(p.email + p.name + p.MobilePhoneNum + p.picture);
    		  }
    		
	public Bitmap setImg(String img) {
		//decode from base_64 to real PNG format
                Bitmap bm;
				
				byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
				Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
			//	im.setImageBitmap(decodedByte);
				bm = decodedByte;
				return bm;
				
			    
			    	
    		
	}
    		 
    	 
    
    



   
    
    

}