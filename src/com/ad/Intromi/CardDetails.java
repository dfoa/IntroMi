package com.ad.Intromi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.MediaStore;
import android.provider.SyncStateContract.Constants;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;


public class CardDetails extends Activity {
    // Debugging
    private static final String TAG = "IntroMi";
    private static final boolean D = true;
    private ProgressBar pb;
    private static int RESULT_LOAD_IMAGE = 1;
    private String img;
    private String filename = "card.bin";
    private EditText name;
    private EditText mobile;
    private EditText email;
    private EditText site;
    private TextView missionPreviewCard;
    private String name_value ;
    private String phone_value;
    private String  email_value;
    private String site_value; 
    private ImageView imageView,choosePhoto,linkedIn;
    private EditText professional_head_line;
    private EditText mission;
    private  Profile p;
    private  TextView nameCardPreview;
    private  TextView headLinePreview;
    private   TextView tvName;
  
    private  boolean editProfile = false;
    private String mission_value;
    private String head_line_value;
    private HttpResponse response;
//Linkedin
    
    private LinkedInOAuthService oAuthService;
    private LinkedInApiClientFactory factory;
    private LinkedInRequestToken liToken;
    private LinkedInApiClient client;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private boolean saveWasPressed = false;
	private ProgressBar pbar;

	//Fonts style
	private Typeface tf,tf1;
    
   
    
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE card details+++ ");
        // Set up the window layout
        
 //       getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.my_card);
         System.out.println("called from create ");
       

        pbar = (ProgressBar) findViewById(R.id.pbar);
        name = (EditText) findViewById(R.id.nameEditText);
        tvName = (TextView) findViewById(R.id.tvName);
        mobile = (EditText) findViewById(R.id.phoneEditText);
        email = (EditText)findViewById(R.id.emailEditText);
        site= (EditText)findViewById(R.id.siteEditText);
        imageView = (ImageView) findViewById(R.id.imgViewPhoto);
        mission = (EditText)findViewById(R.id.etMission);
        professional_head_line = (EditText)findViewById(R.id.edHeadLine);
  //      Button btPickImage = (Button) findViewById(R.id.btImagepPick);
 //      Button saveRecords = (Button) findViewById(R.id.saveButton);
   //     ImageButton imageButton = (ImageButton)findViewById(R.id.imageView1);
        nameCardPreview = (TextView) findViewById(R.id.nameCardPreview);
        headLinePreview = (TextView) findViewById(R.id.HeadLineCardPreview);
        missionPreviewCard = (TextView) findViewById(R.id.missionPreviewCard);
        choosePhoto =  (ImageView) findViewById(R.id.imgViewPhoto);
        linkedIn = (ImageView)findViewById(R.id.LinkedInLogin) ;
        
                pb = (ProgressBar)findViewById(R.id.pbar);	
       pb.setVisibility(View.INVISIBLE);
     
        //name edittext listener to update card preview
 	  tf = Typeface.createFromAsset(getApplicationContext().getAssets(),
	             "fonts/Lato-Black.ttf");
		   tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(),
	             "fonts/Lato-Regular.ttf");
       
       name.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
            	
            	
                nameCardPreview.setTypeface(tf);
                nameCardPreview.setText(name.getText().toString());
            }
        //update headline  preview card
        }); 
        professional_head_line.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                headLinePreview.setTypeface(tf1);
            	headLinePreview.setText(professional_head_line.getText().toString());
            }
        }); 
        mission.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                
            	missionPreviewCard.setTypeface(tf1);
            	missionPreviewCard.setText(mission.getText().toString());
                
            }
        }); 
        
        
        
        if( Build.VERSION.SDK_INT >= 9){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        }
 

        
       
 
        
      
       
  //check if card already exist , just load the details 
        System.out.println("checking if file exist");
        File file = getBaseContext().getFileStreamPath(filename);
        if(file.exists()) {
   	    	//read the card and show  details
   	     if(D) Log.e(TAG, "+++ Load card details");
   	      
   
      
   	     
   	     
   	  editProfile = true;   
   	  System.out.println("loading card");
   	     loadCard();
   	     
   	     
        }  
 /*       
        imageButton.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  if (D) Log.v(TAG, "Image button linkedin  was pressed");
     
              // This code get the values from edittexts  
              name_value  = name.getText().toString();
              System.out.println("this is the name" + name_value);
              phone_value = mobile.getText().toString();
              email_value = email.getText().toString();
              site_value  = site.getText().toString();
              mission_value = mission.getText().toString();
              head_line_value = professional_head_line.getText().toString();
           
           if (!isNetworkAvailable())
           {
           	   Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
          	finish();
           }
           else {
        	   
        
         
  //      	   pb.setVisibility(View.VISIBLE);
        	   
        	   GoToLD(); 
        	
      
   
            //   finish();
           }
        	
   
          }
        });
       
   	 */


        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
            	Toast.makeText(getApplicationContext(), "Will be available soon", Toast.LENGTH_LONG).show();
    			
    		
              }
          });
        
      
        
        
     choosePhoto.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                pickImage(v);
            }
        });
        
        
          
  
    
  //   }
   }
        
    
    
  
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) 
          getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    
    
  //Body of your click handler

    }    
    
    
 /*   
    private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
    	 
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
//create new profile card
		if 	(createCard(params[0],params[1],params[2],params[3],params[4],params[5])){
	
			System.out.println("card was created");
//			
			
			return null;
		}
 
		protected void onPostExecute(Double result){
//			pb.setVisibility(View.GONE);
			
			
			Toast.makeText(getApplicationContext(), "card details saved sucessfully", Toast.LENGTH_LONG).show();
		}
		protected void onProgressUpdate(Integer... progress){
			
			super.onProgressUpdate(progress);
			//pb.setVisibility(View.VISIBLE);
		//	pb.setProgress(progress[0]);
		}
 

 
	}
	*/
    
   public void pickImage(View v)
   {
    
    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/*");
    
   
    
   
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);     
    
   }
    
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        
       if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
           Uri selectedImage = data.getData();
           String[] filePathColumn = { MediaStore.Images.Media.DATA };

           Cursor cursor = getContentResolver().query(selectedImage,
                   filePathColumn, null, null, null);
           cursor.moveToFirst();

           int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String picturePath = cursor.getString(columnIndex);
           cursor.close();
            
 //          ImageView imageView = (ImageView) findViewById(R.id.imgViewPhoto);
           
     //      Bitmap b = decodeSampledBitmapFromPath(picturePath,200,200);
           
           setPic( picturePath);
    //       imageView.setImageBitmap(b);
           img = picturePath;

          
          
       }
       
 
    
   } 
 
 private  boolean createCard(String mac ,String name,String mobilePhone,String emailAddress,String siteAddress,String  location,String headLine,String mission){
	 
//Create object and fill with data
	 Profile p = new Profile();  
     p.setMacHw(mac);
     p.setName(name);
     p.setMobilePhoneNum(mobilePhone);
     p.setEmail(emailAddress);
     p.setSite(siteAddress);
     p.setMission(mission);
     p.setProfessionalHeadLine(headLine);
 //    p.picture=location;
     
     if (editProfile ){
    	 if (imageView.getDrawable() != null){
    	 Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap() ; //change to image if profile in edit mode  
    
    	 p.setPicture(getPhotoStringFromBitmap(bm)) ;
    	 }
    	 }
	 
  
     
     else {
    	 p.setPicture(getPhoto(location,400,400));
     }
     if (saveCard(p)){
  //now save  profile to web
    	 return true; 
     }
  
    else {
    	return false;
    
    }
 }
 
 public  boolean saveCard(Profile p){
	 


	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	//	      Toast.makeText(this, "going to open file and write...." , Toast.LENGTH_SHORT).show();
	      fos = openFileOutput(filename,Context.MODE_PRIVATE);
//	      Toast.makeText(this, "opened file...." , Toast.LENGTH_SHORT).show();
   out = new ObjectOutputStream(fos);
	      out.writeObject(p);
        
	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      Toast.makeText(this, "Cant open file and write" , Toast.LENGTH_SHORT).show();
	    }
	 
	 
	 
	 return true;
 }
 
 

 private void setPic(String mCurrentPhotoPath) {
	 System.out.println("in setpic");
	    // Get the dimensions of the View
	    int targetW = imageView.getWidth();
	    int targetH = imageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    imageView.setImageBitmap(bitmap);

 }
 
 
 
 
 public String getPhoto(String path, int reqWidth, int reqHeight){
	 
	 String ba1 = null;;
		if (path!=null)
		{
			
			final BitmapFactory.Options options = new BitmapFactory.Options();
		     options.inJustDecodeBounds = true;
		     BitmapFactory.decodeFile(path, options);

		     options.inSampleSize = calculateInSampleSize(options, reqWidth,
		             reqHeight);

		     // Decode bitmap with inSampleSize set
		     options.inJustDecodeBounds = false;
		     Bitmap bmp = BitmapFactory.decodeFile(path, options);
			
			
			
		Bitmap bitmapOrg = bmp;
     ByteArrayOutputStream bao = new ByteArrayOutputStream();
     //Resize the image
     double width = bitmapOrg.getWidth();
     double height = bitmapOrg.getHeight();
     double ratio = 400/width;
     int newheight = (int)(ratio*height);
     bitmapOrg = Bitmap.createScaledBitmap(bitmapOrg, 200, newheight, true);
     System.out.println("———-width" + width);
     System.out.println("———-height" + height);
     bitmapOrg.compress(Bitmap.CompressFormat.PNG, 95, bao);
     byte[] ba = bao.toByteArray();
     ba1 = Base64.encodeToString(ba, 0);
       	return ba1;
		}
		else {
			return null;
		}
		
 
 }
 
 
 
 
 
 
public String getPhotoStringFromBitmap(Bitmap bm){
	 
	 String ba1 = null;;
		if (bm!=null)
		{
		Bitmap bitmapOrg = bm;
     ByteArrayOutputStream bao = new ByteArrayOutputStream();
     //Resize the image
  //   double width = bitmapOrg.getWidth();
  //   double height = bitmapOrg.getHeight();
  //   double ratio = 400/width;
  //   int newheight = (int)(ratio*height);
  //   bitmapOrg = Bitmap.createScaledBitmap(bitmapOrg, 400, newheight, true);
  //   System.out.println("———-width" + width);
  //   System.out.println("———-height" + height);
     bitmapOrg.compress(Bitmap.CompressFormat.PNG, 95, bao);
     byte[] ba = bao.toByteArray();
     ba1 = Base64.encodeToString(ba, 0);
       	return ba1;
		}
		else {
			return null;
		}
		
 
 }
 
 private  Profile  loadCard()
 {
 	final String filename = "card.bin";
 	  p = new Profile(); 
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
 		   
 		  
 		     name.setText(p.getName());
 		     mobile.setText(p.getMobilePhoneNum());
 		     email.setText(p.getEmail());
 		     site.setText(p.getSite());
 		     mission.setText(p.getMission());
 		     professional_head_line.setText(p.getProfessionalHeadLine());
 		     
 //		     decode p.picture from BASE_64 to bitmap 
             
 		    //Bitmap b = decodeSampledBitmapFromPath(p.picture,100,100);
 		    if ( p.getPicture() != null) {
   		
 		      Bitmap b = setImg(p.getPicture());
 		     imageView.setImageBitmap(b);
 		     
 	//	    img = p.picture;
 	//	     WebView.setImageBitmap(setImg(p.picture));
 		    }
 		   
 		    
 		    System.out.println(p.getEmail() + p.getEmail() + p.getMobilePhoneNum() + p.getProfessionalHeadLine());
            return(p);		  
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
 public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth,
         int reqHeight) {

     final BitmapFactory.Options options = new BitmapFactory.Options();
     options.inJustDecodeBounds = true;
     BitmapFactory.decodeFile(path, options);

     options.inSampleSize = calculateInSampleSize(options, reqWidth,
             reqHeight);

     // Decode bitmap with inSampleSize set
     options.inJustDecodeBounds = false;
     Bitmap bmp = BitmapFactory.decodeFile(path, options);
     return bmp;
     }

 public static int calculateInSampleSize(BitmapFactory.Options options,
         int reqWidth, int reqHeight) {

     final int height = options.outHeight;
     final int width = options.outWidth;
     int inSampleSize = 1;

     if (height > reqHeight || width > reqWidth) {
         if (width > height) {
             inSampleSize = Math.round((float) height / (float) reqHeight);
         } else {
             inSampleSize = Math.round((float) width / (float) reqWidth);
          }
      }
      return inSampleSize;
     }
 
 
 private class UploadProfileAsyncTask extends AsyncTask<String, Integer, Double>{
 	 
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub

			postData(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
	
			
			
			return null;
		}

		protected void onPostExecute(Double result){
//			pb.setVisibility(View.GONE);
			
			
//			Toast.makeText(getApplicationContext(), "card details saved sucessfully", Toast.LENGTH_LONG).show();
		}
		protected void onProgressUpdate(Integer... progress){
			
			super.onProgressUpdate(progress);
			//pb.setVisibility(View.VISIBLE);
		//	pb.setProgress(progress[0]);
		}

		public void postData(String mac ,String name_value,String phone_value,String email_value,String site_value,String head_line, String mission,String  pickPath ) {
			// Create a new HttpClient and Post Header
			
          
        
            if(D) Log.i(TAG, "in post data  send to server");
			HttpClient httpclient = new DefaultHttpClient();
	//		HttpPost httppost = new HttpPost("http://192.168.50.5/cgi-bin/register.cgi");
	//		HttpPost httppost = new HttpPost("http://dfoa.ssh22.net/cgi-bin/register.cgi");
			HttpPost httppost = new HttpPost("http://31.168.241.149/cgi-bin/register.cgi");
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	nameValuePairs.add(new BasicNameValuePair("mac",mac));
	        nameValuePairs.add(new BasicNameValuePair("name",name_value));
	        nameValuePairs.add(new BasicNameValuePair("phone",phone_value ));
	        nameValuePairs.add(new BasicNameValuePair("email",email_value ));
	        nameValuePairs.add(new BasicNameValuePair("site",site_value ));
	        nameValuePairs.add(new BasicNameValuePair("head_line",head_line_value ));
	        nameValuePairs.add(new BasicNameValuePair("mission",mission_value));
	        nameValuePairs.add(new BasicNameValuePair("pic",pickPath));
			
			

			try {
				// Add your data
	//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			//	nameValuePairs.add(new BasicNameValuePair("myHttpData", ));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				 response = httpclient.execute(httppost);
				
	            
	            	
					
			p.setCompany("ww");	
				
				
             
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}

	}

private void GoToLD() { 
 oAuthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET);
 factory = LinkedInApiClientFactory.newInstance(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
         liToken = oAuthService.getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);
         Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(liToken.getAuthorizationUrl()));
       
         startActivity(i);
}
/*
Application Details

    Company:

    IntroMi1
    Application Name:

    IntroMi1
    API Key:

    77sfpn66k6o3ot
    Secret Key:

    nJTkiyLEiZZoKLLq
    OAuth User Token:

    5ab04794-eefb-4155-9456-d7515a4b8f4c
    OAuth User Secret:

    a0f26ec6-9a1e-4be1-bd9d-265b50d51947


 */
 public class Constants {

     public static final String CONSUMER_KEY = "77sfpn66k6o3ot"; // your KEY
     public static final String CONSUMER_SECRET = "nJTkiyLEiZZoKLLq"; // your SECRET
     public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
         public static final String OAUTH_CALLBACK_HOST = "litestcalback1";
         public static final String OAUTH_CALLBACK_URL =
                 OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
     }	  
 
 
 /*
 @Override
 protected void onNewIntent(Intent intent) {
         super.onNewIntent(intent);

         if (D) Log.v(TAG,"in  onnewintent");
         String verifier = intent.getData().getQueryParameter("oauth_verifier");

         LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(liToken, verifier);
                 client = factory.createLinkedInApiClient(accessToken);
         // Now you can access login person profile details...

         Person profile = client.getProfileForCurrentUser(EnumSet.of(ProfileField.ID, ProfileField.FIRST_NAME,   ProfileField.LAST_NAME, ProfileField.HEADLINE,
                         ProfileField.SUMMARY,ProfileField.PICTURE_URL,ProfileField.PHONE_NUMBERS));
 System.out.println("going tp print ptofile");
         System.out.println("First Name -" +  profile.getFirstName());
         System.out.println("Last Name  - "+ profile.getLastName());
         System.out.println("Head Line  -" + profile.getHeadline());
         System.out.println("Industry -" + profile.getIndustry());
         System.out.println("Telephone -" + profile.getPhoneNumbers());
         System.out.println("summery  -" + profile.getSummary());
         System.out.println("picurl  -" + profile.getPictureUrl());
         System.out.println("current status  -" + profile.getCurrentStatus());
         loadImageFromURL(profile.getPictureUrl());
         System.out.println("This is the path to profile   -" + profile.getPublicProfileUrl());
         
         
         
         name.setText(profile.getFirstName()+ " " + profile.getLastName());
         professional_head_line.setText(profile.getHeadline());
         site.setText(profile.getPublicProfileUrl());
         
 
         
         
         
         
         

       }
*/
 
 @Override
 public void onDestroy() {
     Log.v(TAG, "onDestroy()");
     super.onDestroy();
 }
 
	private void loadImageFromURL(String url) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.profile)
				.showImageForEmptyUrl(R.drawable.profile).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imageView, options,
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
   

	
    // handle click events for action bar items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        switch (item.getItemId()) {
 
 
            case R.id.saveButton:
            	 if  (D)  Log.v(TAG, "Save detailes pressed") ;
                 saveWasPressed = true;           	 
            	//check id network connection
            	if (!isNetworkAvailable())
                   {
                   	   Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
                
    
                   }
            	saveDetails();
           return true;
           
   
                   
           
                  
               
                
            default:
                return super.onOptionsItemSelected(item);          
                
           
        
              
      
        }
    
    }
    private void saveDetails()
    {

             {
           	 
        
                 // This code get the values from edittexts  
                 name_value  = name.getText().toString();
                 System.out.println("this is the name" + name_value);
                 phone_value = mobile.getText().toString();
                 email_value = email.getText().toString();
                 site_value  = site.getText().toString();
                 mission_value = mission.getText().toString();
                 head_line_value = professional_head_line.getText().toString();
                
                 
              
              if (D) Log.v(TAG,"Thisis the data " + name_value +phone_value + email_value +site_value + mission_value + head_line_value);
    //          BluetoothAdapter bluetoothDefaultAdapter = BluetoothAdapter.getDefaultAdapter();
     //         if ((bluetoothDefaultAdapter != null) &&+ (bluetoothDefaultAdapter.isEnabled())){
             String mac = BluetoothAdapter.getDefaultAdapter().getAddress();
              if (D) Log.v(TAG,"This is the mac address:" + mac);
    //          }
    //check if card already exist ,if yes  show the information , if no, create new card
              
              //check if card exist 
             

      	    
      	       

             
              
              
              if (!isNetworkAvailable())
              {
              	   Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
            // 	finish();
              }
              else {
           	   
           
            
//           	   pb.setVisibility(View.VISIBLE);
           	   
           	
    //       	   new MyAsyncTask().execute(mac,name_value,phone_value,email_value,site_value,img);
                  if ( createCard(mac,name_value,phone_value,email_value,site_value,img,head_line_value,mission_value))
               	      
                  {  
               	    	  
  //              	  Toast.makeText(getApplicationContext(), "Card details saved sucessfully on local phone", Toast.LENGTH_LONG).show();
  //                else
  //             	   Toast.makeText(getApplicationContext(), "Problem saving card", Toast.LENGTH_LONG).show();  
           	   //upload  profile to server
                  Profile profile = loadCard();
          	   new UploadProfileAsyncTask().execute(profile.getMacHw(),profile.getName(),profile.getMobilePhoneNum(),profile.getEmail(),profile.getSite(),profile.getProfessionalHeadLine(),profile.getMission(),profile.getPicture());
   //       	 if (response==null) {
   //    	    	 Toast.makeText(getApplicationContext(), "Server is not reachable, try to save again", Toast.LENGTH_LONG).show();
       	      }  
          	   
          	   //finish();
              }
           	
      
             }
       //    });
              
         
    }
    
    
    @Override
    public void onBackPressed()
    {
 //   	super.onBackPressed();  // optional depending on your needs
    	 
    	if (!saveWasPressed){
    
    		
    		
    		
    		//means that the user  pressed back button but didnt  save information.
    		//open dialog box to tell him to save the information.
    		
    	 }
    		 
         
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        switch(keyCode)
        {
        case KeyEvent.KEYCODE_BACK:
            AlertDialog.Builder ab = new AlertDialog.Builder(CardDetails.this);
            ab.setMessage("Card was not saved").setPositiveButton("Save", dialogClickListener)
            .setNegativeButton("Exit", dialogClickListener).show();
            break;
        }

        return super.onKeyDown(keyCode, event);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                //Save button clicked -- save the card 
                saveDetails();
                break;
                
            case DialogInterface.BUTTON_NEGATIVE:
                //exit was pressed so dont overide back button
            	finish();
                break;
            }
        }
    };
    
    
    @Override
    
    public void onResume()
    {
    	super.onResume();
    	System.out.println("called from resume ");	
    }
    
    
    
  }


 

