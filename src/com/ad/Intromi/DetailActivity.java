package com.ad.Intromi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.R.color;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.SyncStateContract.Constants;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.net.Uri;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

public class DetailActivity extends Activity {
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private static Boolean  D = true;
    private static String TAG = "IntroMi/detailedActivity";
	private ProgressBar pbar;
	private TextView tvMobilePhone , tvemail,tvSite,tvMission,tvHeadline,tvName;
	
	String test;
	private ImageView imgView,mImgViewShare,mImgSite;
	private ImageView imagePhone;
	private ImageView imageEmail ;
	private ImageView btSaveToFavourites;
	private  Context c;
    private  String name;
    private String email;
    private String mission,mac;
    private String head_line;
    private String photo,mSite;
    private SavedCards mSavedCards ;
    private String  fileName;
    private static String favFile = "cards.bin";
    


    private ImageView mIvPhonebook;
    
    private static int mBlue = 1;
    private static int mBlueFull = 2;
 //   private String MobileNumber;
  

    private ArrayList<ContentProviderOperation> ops;
    private Cards cards;
    boolean flag;
    private int ind ;
    private static String IMAGE_FILE = "image";

    String HomeNumber = "";
    String WorkNumber = "";
    String company = "";
    String jobTitle = "";
	
	
	Bitmap bm;
	
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_card);
		
		
	
	    
		 
		pbar = (ProgressBar) findViewById(R.id.pbar);
		tvMobilePhone = (TextView) findViewById(R.id.tvPhone);
		tvemail = (TextView) findViewById(R.id.tvEmail);
		tvHeadline = (TextView) findViewById(R.id.tvHeadLine);
		tvMission = (TextView) findViewById(R.id.tvMission);
		tvName = (TextView) findViewById(R.id.tvName);
		 tvSite = (TextView) findViewById(R.id.tv1Site);
		 
//		imgLeftBrackets  = (ImageView)findViewById(R.id.leftBrackets);
//		imgRightBrackets = (ImageView)findViewById(R.id.rightBrackets);
		 mIvPhonebook      = (ImageView)findViewById(R.id.imageViewPhoneBook);
		 mImgViewShare = (ImageView) findViewById(R.id.imgViewShare);
        

	    Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Black.ttf");
	    Typeface tf1 = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");

        tvName.setTypeface(tf);
       
        tvMission.setTypeface(tf);
        tvHeadline.setTypeface(tf1);
        tvMobilePhone.setTypeface(tf1);
        tvemail.setTypeface(tf1);
   //     tvLeftBrackets.setTypeface(tf);
   //     tvRightBrackets.setTypeface(tf);

		imgView = (ImageView) findViewById(R.id.photo);
		imagePhone = (ImageView) findViewById(R.id.imagePhone);
		imageEmail= (ImageView) findViewById(R.id.imageEmail);
		btSaveToFavourites = (ImageView)findViewById(R.id.btSaveToFavourites);
		mImgSite =  (ImageView)findViewById(R.id.imageSite);
      
		
		Bundle b = getIntent().getExtras();
         fileName = b.getString("file_name");
		 email = b.getString("title");
		final String mobilePhone = b.getString("desc");
		
		//if phonenumber is blank
		if (mobilePhone.equalsIgnoreCase(""))
		mIvPhonebook.setEnabled(false);	
		//photo = b.getString("photo");
        photo = readImageFile();		
		 
		
		mac = b.getString("mac");
		head_line = b.getString("head_line");
	    mission = b.getString("mission");
	    mSite = b.getString("site");
		name = b.getString("name");
		
		tvName.setText(name.toUpperCase());
		tvMobilePhone.setText(mobilePhone);
	    tvemail.setText(email);
	    if (head_line!=null)
	    tvHeadline.setText(head_line);
	    tvMission.setText(mission);
	    tvSite.setText(mSite);
	    if (photo !=null)
	            new MyAsyncTask().execute(photo);
	    pbar.setVisibility(View.INVISIBLE);
	    this.c = getApplicationContext();
	    flag = true;
	   
	    
	     cards = new Cards(c,favFile);
         //load list from file;
          mSavedCards = cards.loadCards();
          if (mSavedCards !=null) {
   	 for (ind =0 ;ind <mSavedCards.profileArrayList.size() && flag;ind++) {
  //   if    (mSavedCards.profileArrayList.get(ind).getMobilePhoneNum().contentEquals(mobilePhone)){
   		if    (mSavedCards.profileArrayList.get(ind).getMacHw().contentEquals(mac)){  
       	  System.out.println("match");
       	  flag=false;
       	  //save the user in case we want to delete
       	  btSaveToFavourites.setImageResource((int)R.drawable.favorite_blue_full);
       	  btSaveToFavourites.setId(mBlueFull);
          btSaveToFavourites.refreshDrawableState();
          --ind;
     }
   	 }
     if (flag)
     {
    	 System.out.println("mot found in saved card");
    	 btSaveToFavourites.setImageResource((int)R.drawable.favorite_blue);
         btSaveToFavourites.setId(mBlue);
         btSaveToFavourites.refreshDrawableState();
     }
     
       	  
     
      
        }
         else {
        	 
         	 btSaveToFavourites.setImageResource((int)R.drawable.favorite_blue);
             btSaveToFavourites.setId(mBlue);
             btSaveToFavourites.refreshDrawableState();
          }
		
          

          
	    btSaveToFavourites.setOnClickListener(new OnClickListener()
	     {
	    	 
	       public void onClick(View v)
	       {
	    	  
	    	   
	//    	   btSaveToFavourites.setImageResource((int)R.drawable.favorite_blue_full);
	     	  if (D) Log.v(TAG, "Image button  btSaveToFavorits  was pressed");
	     	  System.out.println("v.getID  "  + v.getId());
	        	 
	    
	        


  if ((v.getId() == mBlueFull)) {
//need to remove contact
	 
	   System.out.println("v.getID  "  + v.getId());
	   btSaveToFavourites.setImageResource((int)R.drawable.favorite_blue);
	   btSaveToFavourites.refreshDrawableState();
	   btSaveToFavourites.setId(mBlue);
	   
//		   for (ind =0 ;ind <mSavedCards.profileArrayList.size() && flag;ind++) {
//			     if    (mSavedCards.profileArrayList.get(ind).getMobilePhoneNum().contentEquals(mobilePhone)){
	              
			     
		   
	   
	 System.out.println("the ind is :" + ind);		    	 
	 mSavedCards.profileArrayList.remove(ind);
	
	
//			     }
	//	   }
	 

	 
      try { 
		flushFile();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      cards.saveToFileBulk(mSavedCards);
     
      flag =	 true;
	 
     
	   Intent returnIntent = new Intent();
   	
        setResult(RESULT_OK,returnIntent);
	   
 }

  else   if ((v.getId() == mBlue)) {

  
	  
     if (flag){

	    	
	    	flag = false;

	        	  

	        	
	          Profile p = new Profile();
	             p.setName(name);
	             p.setEmail(email);
	             p.setMission(mission);
	             p.setProfessionalHeadLine(head_line);
	             p.setPicture(photo);
	             p.setMobilePhoneNum(mobilePhone);
	             p.setMacHw(mac);
	             
	             
	           mSavedCards = cards.saveToFile(p,false);
	    	   btSaveToFavourites.setImageResource((int)R.drawable.favorite_blue_full);
	    	   btSaveToFavourites.refreshDrawableState();
	    	   btSaveToFavourites.setId(mBlueFull);
	       
               
	   

	        
     }
	    }
	  
	       }
	    
	       
	     });
		 
	    
	    mImgSite.setOnClickListener(new OnClickListener()
	     {
	       public void onClick(View v)
	       {
	    	
	    	
	    	   
	     	  if (D) Log.v(TAG, "pressed site link");
	     	
	     	 String strUrl = mSite;
	
	     	 if (strUrl != null){
	     		 
	     	 
	     	 
	     	if (!strUrl.startsWith("http://") && !strUrl.startsWith("https://")){
	     	    strUrl.replace(" ", ""); 
	     		strUrl= "http://"+strUrl;
	     	     
	     	 }
	     	
	     	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
	     	startActivity(browserIntent);
	     
	     	 

	       }
	       }
	     }); 
	    
	    
	    
	    mImgViewShare.setOnClickListener(new OnClickListener()
	     {
	       public void onClick(View v)
	       {
	    	
	    	
	    	   
	     	  if (D) Log.v(TAG, "Shared Icon was pressed");
	     	 Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
	     	sharingIntent.setType("text/plain");
	     	String shareBody = "\nShared by IntroMi";

	     	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact of" +name);
	     	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, name + mobilePhone +email + shareBody);
	     	startActivity(Intent.createChooser(sharingIntent, "Share via"));
	     	 

	       }
	      
	     }); 
	    

			

			
		 
	    	
	
			
	
	    
		 imagePhone.setOnClickListener(new OnClickListener()
	     {
	       public void onClick(View v)
	       {
	     	  if (D) Log.v(TAG, "Imagephone pressed no need to send intent to dialer");
	     	 tvMobilePhone.getText().toString();
	     	 Intent intent = new Intent(Intent.ACTION_DIAL);
	     	intent.setData(Uri.parse("tel:"+mobilePhone));
	     	startActivity(intent); 

	        

	     	

	       }
	     });

		 
		 imageEmail.setOnClickListener(new OnClickListener()
	     {
	       public void onClick(View v)
	       {
	     	  if (D) Log.v(TAG, "ImageEmail  pressed no need to send intent to default mail application");
	     	 tvemail.getText().toString();
	     	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
//	     	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");

	     	startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
         
	        

	     	

	       }
	     });
		 
		 mIvPhonebook.setOnClickListener(new OnClickListener()
	     {
	       public void onClick(View v)
	       {
	    	   
	    	   if (!contactExists(getBaseContext(), mobilePhone)) 
	    	   {
	    	   if (D) Log.v(TAG, "saveToContact  pressed no need to send intent to default contacts application");
	     	 ArrayList<ContentProviderOperation> ops = 
	                 new ArrayList<ContentProviderOperation>();

	             ops.add(ContentProviderOperation.newInsert(
	                 ContactsContract.RawContacts.CONTENT_URI)
	                 .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
	                 .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
	                 .build()
	             );

	             //------------------------------------------------------ Names
	             if(name != null)
	             {           
	                 ops.add(ContentProviderOperation.newInsert(
	                     ContactsContract.Data.CONTENT_URI)              
	                     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                     .withValue(ContactsContract.Data.MIMETYPE,
	                         ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
	                     .withValue(
	                         ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,     
	                         name).build()
	                 );
	             } 

	             //------------------------------------------------------ Mobile Number                      
	             if(mobilePhone != null)
	             {
	                 ops.add(ContentProviderOperation.
	                     newInsert(ContactsContract.Data.CONTENT_URI)
	                     .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                     .withValue(ContactsContract.Data.MIMETYPE,
	                     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
	                     .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobilePhone)
	                     .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 
	                     ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
	                     .build()
	                 );
	             }

	                                 //------------------------------------------------------ Home Numbers
	                                 if(HomeNumber != null)
	                                 {
	                                     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                                             .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                                             .withValue(ContactsContract.Data.MIMETYPE,
	                                                     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
	                                             .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
	                                             .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 
	                                                     ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
	                                             .build());
	                                 }

	                                 //------------------------------------------------------ Work Numbers
	                                 if(WorkNumber != null)
	                                 {
	                                     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                                             .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                                             .withValue(ContactsContract.Data.MIMETYPE,
	                                                     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
	                                             .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
	                                             .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 
	                                                     ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
	                                             .build());
	                                 }

	                                 //------------------------------------------------------ Email
	                                 if(email != null)
	                                 {
	                                      ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                                                 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                                                 .withValue(ContactsContract.Data.MIMETYPE,
	                                                         ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
	                                                 .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
	                                                 .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
	                                                 .build());
	                                 }

	                                 //------------------------------------------------------ Organization
	                                 if(!company.equals("") && !jobTitle.equals(""))
	                                 {
	                                     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                                             .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                                             .withValue(ContactsContract.Data.MIMETYPE,
	                                                     ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
	                                             .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
	                                             .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
	                                             .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
	                                             .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
	                                             .build());
	                                 }

	                                 // Asking the Contact provider to create a new contact                  
	                                 try 
	                                 {
	                                     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	                                 } 
	                                 catch (Exception e) 
	                                 {               
	                                     e.printStackTrace();
	                                   //  Toast.makeText(myContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
	                                 }
	            
	                                 Toast.makeText(getApplicationContext(), "Contact has been saved successfully", Toast.LENGTH_SHORT).show();
	         }
	    	   else Toast.makeText(getApplicationContext(), "Contact Already exist", Toast.LENGTH_SHORT).show();
	       }
	       
	     });
		
	}
	
	

	private void loadImageFromURL(String url) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.profile)
				.showImageForEmptyUrl(R.drawable.profile).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imgView, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingComplete() {
						pbar.setVisibility(View.INVISIBLE);

					}

					@Override
					public void onLoadingFailed() {

						pbar.setVisibility(View.INVISIBLE);
					}

					@Override
					public void onLoadingStarted() {
						pbar.setVisibility(View.VISIBLE);
					}
				});

	}
	
	 public Bitmap StringToBm(String img) {
			//decode from base_64 to real PNG format
	             Bitmap bm;
					
					byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
				//	im.setImageBitmap(decodedByte);
					bm = decodedByte;
					return bm;
	 }
	 
	    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap>{
	    	 
	    	  
	    	
	    	@Override
	    	protected void onPreExecute(){
	    		super.onPreExecute();
	    		//pBar.setMessage("Found Card " + mFoundDevice + "  Loading profile");
	    	
	    		
	    	
	    	}
			@Override
			protected Bitmap  doInBackground(String... params) {
				// TODO Auto-generated method stub
			
			return StringToBm(params[0]);
			}
	 
			protected void onPostExecute(Bitmap result){
				
	           //  super.execute(result);
		//		Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
				
		//	 System.out.println("This is the card I am going to  bring information for" + card);
	             imgView.setImageBitmap(result);
			}
			protected void onProgressUpdate(Integer... progress){
				
		
		
	 
		}
	    }
	    
	    public void flushFile() throws FileNotFoundException
	    {
	     String fileName = "cards.bin";
	   		  
	   		  FileOutputStream fis = null;
	   		 	

	   		 	
	   		 		      fis = getApplicationContext().openFileOutput(fileName, 2);
	   		 		      try {
	   						fis.flush();
	   					} catch (IOException e) {
	   						// TODO Auto-generated catch block
	   						e.printStackTrace();
	   					}
	   		 		      try {
	   						fis.close();
	   					} catch (IOException e) {
	   						// TODO Auto-generated catch block
	   						e.printStackTrace();
	   					}
	   		 		     
	   		 		   
	   		 		    
	   		 //		    System.out.println(p.email + p.name + p.MobilePhoneNum + p.professionalHeadLine);
	   		 
	   	 
	   	 
	    }
	    
	    
	    @SuppressWarnings("resource")
	    public String readImageFile() {
	        Context context = getApplicationContext();
	        StringBuilder stringBuilder = new StringBuilder();
	        String line;
	        BufferedReader in = null;

	        try {
	            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), IMAGE_FILE)));
	            while ((line = in.readLine()) != null) stringBuilder.append(line);

	        } catch (FileNotFoundException e) {
	            Log.v(TAG, "image file not found");
	        } catch (IOException e) {
	            Log.v(TAG,"open file IO prolem");
	        } 

	        return stringBuilder.toString();
	    }


	    public boolean contactExists(Context context, String number) {
	    	/// number is the phone number
	    	
	    	
	    	if (!number.equalsIgnoreCase("")) {
	    		System.out.println("This is the number _"+number+"_");
	    	Uri lookupUri = Uri.withAppendedPath(
	    	PhoneLookup.CONTENT_FILTER_URI, 
	    	Uri.encode(number));
	    	String[] mPhoneNumberProjection = { PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME };
	    	Cursor cur = context.getContentResolver().query(lookupUri,mPhoneNumberProjection, null, null, null);
	    	try {
	    	   if (cur.moveToFirst()) {
	    	      return true;
	    	}
	    	} finally {
	    	if (cur != null)
	    	   cur.close();
	    	}
	    	return false;
	    	}
	    
	    else return false;
	    }
	    
	    
}
	 
	 
	 
	 

					

