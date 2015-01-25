package com.ad.Intromi;

import java.util.ArrayList;
import java.util.HashMap;

import android.R.array;
import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

import com.google.code.linkedinapi.schema.Position;


import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.sax.TextElementListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;



public class DeviceListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
	private String IMAGE_FILE = "image";
	private ImageView  seperatorPB;
	private  static boolean isBTRunning;
	//private static int DELAYBT = 300000;
	private static int DELAYBT = 3000;
	//for demo

	// Debugging/BLE
	
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;
	public boolean found = false;
	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private static final int REQUEST_ENABLE_BT = 3;
	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private ArrayList<ItemDetails> results;

	private ItemListBaseAdapter lAdapter;
	private String card;
	private String holdMacs="macacdcdcdcdcdcdcdc";
	private ListView lv1;
	private String DisplayName ;
	private String MobileNumber;
	private String HomeNumber = "";
	private String WorkNumber = "";
	private String emailID ;
	private String company = "";
	private int mFoundDevice;
	String myMac;
	float x1,x2;
	float y1, y2;
	private String selfMac;
	private Button mScanButton;
	private ImageView mBlackBar;
	int mStackLevel  = -1;
	private short mRssi;
	private Intent intent;
	private boolean mBTena;
	private LinearLayout layout ;
	private ProgressBar pBar;
	private HttpResponse response;
	private boolean firstTime = true;
	private BluetoothDevice device;
	private EditText inputSearch;
	private String fileName = "cards.bin";
	private String histoyFileName = "history.bin";
	private SwipeRefreshLayout swipeLayout ;
	private Handler mHandler;
	private BluetoothAdapter  mBluetoothAdapter;//BLE 
	private static final long SCAN_PERIOD = 500000;//BLE
	private static boolean  BLE = false;
	private ArrayList <String> mRssiArray;
	private HashMap mHash; 


	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

//			pBar.setVisibility(View.VISIBLE);
			//    	 seperatorPB.setVisibility(View.VISIBLE);

		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// Setup the window
		// requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.main);

//		mScanButton = (Button) findViewById(R.id.scan);
        mRssiArray = new ArrayList<String>();
        mHash = new  HashMap<String, ArrayList<String>>();
		mBTena = true;

		//swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		//swipeLayout.setOnRefreshListener((OnRefreshListener) this);

		//mBlackBar = (ImageView)(findViewById(R.id.imageViewBlackBar));


		//     seperatorPB = (ImageView)findViewById(R.id.seperator);
		pBar = (ProgressBar) findViewById(R.id.progressBarScan);
		pBar.setProgress(50);	
		pBar.setVisibility(View.GONE);
		mHandler = new Handler();
		
//		if (mBluetoothAdapter.isMultipleAdvertisementSupported()){
//		BluetoothLeAdvertiser mBluetoothLeAdvertiser;
		
//		mBluetoothLeAdvertiser.startAdvertising(settings, advertiseData, callback)
		
//		}
	
		ArrayList<String> a = new ArrayList<String>();
		a.add("doron1");
		a.add("doron2");		
	      
		
        mHash.put("alex", a);
 //       String p = mHash.ge
//        System.out.println("HASH details" + p);
        
        
         
        
		/*       
       if (savedInstanceState == null) {
           FragmentManager fragmentManager = getFragmentManager();
           // Or: FragmentManager fragmentManager = getSupportFragmentManager()
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           tt fragment = new tt();
 //          fragmentTransaction.replace((R.id.fragment_container, fragment);
           fragmentTransaction.commit();
       }


 layout = (LinearLayout) findViewById(R.id.linearLayout1);

 layout.setOnClickListener(new OnClickListener() {




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		 editCard();
	}
});
		 */
		//setUpActionBar();  	   


		/*     
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);

        getOverflowMenu();
		 */   


		// Set result CANCELED in case the user backs out
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		//        Button scanButton = (Button) findViewById(R.id.se);
		//       scanButton.setOnClickListener(new OnClickListener() {
		//           public void onClick(View v) {
		//               doDiscovery();
		//              v.setVisibility(View.GONE);
		//           }
		//       });






		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		//    inputSearch = (EditText)findViewById(R.id.autoCompleteTextViewSearch)
		;
		results = new ArrayList<ItemDetails>();
		lv1 = (ListView) findViewById(R.id.listV_main); 

		/*    	
     	lv1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem == 0) {
  //                  swipeLayout.setEnabled(true);
//                } else swipeLayout.setEnabled(false);

            	 swipeLayout.setEnabled(true);
            }
        });
		 */    	

//		mScanButton.setOnClickListener(new OnClickListener()
//		{
//			public void onClick(View v)
//
//
//			{
//				startDiscovery();
//
//			}
//
//
//		});

		lv1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				if (pos == 0)
				{
					//		 intent = new Intent(DeviceListActivity.this, CardDetails.class);
					// 	startActivity(intent);
					switchTabInActivity(3);
				}
				else {
					ItemDetails  item= results.get(pos);
					intent = new Intent(DeviceListActivity.this, DetailActivity.class);
					intent.putExtra("url", item.getName());
					intent.putExtra("title", item.getPrice());
					intent.putExtra("desc", item.getItemDescription());
					intent.putExtra("head_line",item.getPrfessionalHeadLine());
					intent.putExtra("site",item.getSite());
					intent.putExtra("mission",item.getmission());
					intent.putExtra("site", item.getSite());
					intent.putExtra("file_name", fileName);
					intent.putExtra("mac", item.getMac());

					intent.putExtra("name",item.getName());
					new MyAsyncTask1().execute(item.getImg());
				}



			}
		});

		/*  	 	
     	 inputSearch.addTextChangedListener(new TextWatcher() {

     			@Override
     			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
     				// When user changed the Text
     				System.out.println("Text was chnaged");	
     				setSearchResult(cs.toString());

     			}

     			@Override
     			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
     					int arg3) {
     				// TODO Auto-generated method stub


     			}

     			@Override
     			public void afterTextChanged(Editable arg0) {
     				// TODO Auto-generated method stub	

     			}
     		});

		 */	  



		//       registerForContextMenu(lv1);
		lAdapter = new ItemListBaseAdapter(this,results);

		//        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);




		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		this.registerReceiver(mReceiver, filter);

		loadSelfProfile();

		// Get the local Bluetooth adapter
		if (mBTena)       mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBTena)  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBTena){

          if (BLE) {
        	  scanLeDevice(true);  
          }
          else {
			new Thread(new Runnable() { 
				public void run(){

					{
						while(!Thread.currentThread().isInterrupted()){
							isBTRunning = true;
							Looper.prepare(); 
							//               BluetoothAdapter mBluetoothAdapter =BluetoothAdapter.getDefaultAdapter();

							try { 
								Log.d(TAG, "BT Scanning started");

								while(isBTRunning)
								{ 

									if (!mBtAdapter.isEnabled())
									{
										mBtAdapter.enable();  


									}
									Log.d(TAG,"Register..."); 


									Log.d(TAG,"Start discovery");


									// mBluetoothAdapter.startDiscovery();

									startDiscovery();
									Log.d(TAG,"sleep for " + DELAYBT + " seconds");

									Thread.sleep(DELAYBT);
									mBtAdapter.cancelDiscovery();

									Log.d(TAG,"Unregister..."); 
									//                     unregisterReceiver(mReceiver);
								}
								Looper.loop();
							}
							catch (InterruptedException e) {
								Log.d(TAG, "BT Scanning stopped");
								Looper.myLooper().quit();
							}

						}     

					}
				}
			}).start();

          }

			/////
		}







	}


	@Override
	public  void onStop()
	{
		super.onStop();	
		System.out.println("++++onStop Device ListActivity");
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
			isBTRunning = false;
			//       mBtAdapter.disable();
			Thread.interrupted();
		}
	}
	@Override
	public void onStart() {
		super.onStart();
		if(D) Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		if (mBTena)  {  
			if (!mBtAdapter .isEnabled()) {
				//           Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				//           startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
				mBtAdapter.enable();
				// Otherwise, setup the chat session
			}
			//     mBtAdapter.setName("IntroMi");
			ensureDiscoverable();
			// startDiscovery();

		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
			isBTRunning = false;
			Thread.interrupted();
			//       mBtAdapter.disable();

		}


		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */


	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			int pos ;
			System.out.println("In broadcast reciev");
			found = false;
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				System.out.println("device found");
				// Get the BluetoothDevice object from the Intent


				//IntroMi

				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				System.out.println("This is theMacc and RSSI  of the device "+ device.getAddress() +" " + mRssi);
				// if (device.getName().equalsIgnoreCase("IntroMi")) {
				mRssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);

				if (D) Log.v(TAG, "rssi:"+ mRssi);
				// If it's already paired or already listed, skip it, because it's been listed already
				//           System.out.println("This is the device name" + device.getName());
				//           System.out.println("This is the length of the name :\n" +  device.getName().length());
				//            showToast("Device name is\n " + device.getName() +"length:\n" + device.getName().length());

				if (device.getBondState() != BluetoothDevice.BOND_BONDED  ) {

					if (holdMacs.contains(device.getAddress()))
					{

						if (D) Log.v(TAG, "The device" + device.getAddress() +"is already in the list");		 
						System.out.println("update device  " + device.getAddress() +" with RSSI: "+mRssi );

						found = true;
						updateRssi(mRssi,device.getAddress());      
					}


					for (pos = 0 ; pos< mNewDevicesArrayAdapter.getCount();pos++)
					{

						System.out.println("Thsissssss" +mNewDevicesArrayAdapter.getItem(pos));

						if (mNewDevicesArrayAdapter.getItem(pos).contains(device.getAddress()))
						{
							//found device and need to update RSSI

							if (D) Log.v(TAG,"The device" + device.getAddress() +" is already in the list with "+ mRssi);
							found = true;

						}




					}
					if (!found ) {

						holdMacs = holdMacs+device.getAddress()+ "=";	
						//	 mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
						if (D) Log.v(TAG,"found device and going to look for information Asynctask with " + device.getAddress());
						//Save this card to history file


						new MyAsyncTask().execute(device.getAddress());
						
            		new Thread(new Runnable() { 
            	         public void run(){

            	             {
            	            	 int count =0;
            	                System.out.println("Into thread");

            	               Looper.prepare(); 


            	               try { 
            	                   Log.d(TAG, "BT Scanning started");

            	                   while(true &&count<20)
            	                   { 
            	                	  ++count; 
            	                 		new MyAsyncTask().execute(device.getAddress());
            	                   Log.d(TAG,"Register..."); 



            	                   Log.d(TAG,"sleep for " + DELAYBT + " seconds");

            	                   Thread.sleep(2000);

            	                   Log.d(TAG,"Unregister..."); 

            	                   }
            	                   Looper.loop();
            	               }
            	               catch (InterruptedException e) {
            	                   Log.d(TAG, "BT Scanning stopped");
            	                       Looper.myLooper().quit();
            	               }

            	             }  

            	         }

            	 }).start();	

						 





					}
				}


				//     }
				else {
					if (D) Log.v(TAG,"found blletooth device but dont have IntoMI application"); 

				}

				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				//       setProgressBarIndeterminateVisibility(false);
				//showToast("finish discovery...");

				if (D) Log.v(TAG,"finish discovery");
				pBar.setVisibility(View.GONE);
				//         seperatorPB.setVisibility(View.GONE);
				//        if(null!=pBar && pBar.isShowing()){

				//             	  pBar.dismiss();
				//               }

				setTitle("Below cards were found");
				if (mNewDevicesArrayAdapter.getCount() == 0) {
					String noDevices = getResources().getText(R.string.none_found).toString();
					mNewDevicesArrayAdapter.add(noDevices);
				}

			}

		}

	};



	////




	// put the other two menu on the three dots (overflow)
	private void getOverflowMenu() {

		try {

			ViewConfiguration config = ViewConfiguration.get(this);
			java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// so that we know something was triggered
	public  void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	//////

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(D) Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {

		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session


			} else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void ensureDiscoverable() {
		if(D) Log.d(TAG, "ensure discoverable");


		if (mBtAdapter.getScanMode() !=
				BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,0);
			startActivity(discoverableIntent);



		}
	}



	private void  editCard() {

		if(D) Log.d(TAG, "edit card");

		Intent  intent_card = new Intent(this, CardDetails.class);
		//      Intent  intent_card = new Intent(this, MyCards.class); 
		startActivity(intent_card);

	}



	public void  retrievCardDetails(String mac) throws IOException {



		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mac",mac));


		//      String URL_STRING="http://192.168.0.151:8080/map/jsp/passwordget.jsp";
		String URL_STRING="http://192.168.50.5/cgi-bin/get_card.cgi";

		final HttpClient hc = new DefaultHttpClient();
		final HttpPost post = new HttpPost(URL_STRING);
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

		new Thread(new Runnable() {
			public void run() {

				try {
					HttpResponse rp = hc.execute(post);

					int responseCode = rp.getStatusLine().getStatusCode();
					switch(responseCode)
					{
					case 200:
						HttpEntity entity = rp.getEntity();
						if(entity != null)
						{
							String responseBody = EntityUtils.toString(entity);
							//			  System.out.println("This is cards details " + responseBody);
							show_card_results(responseBody);	             
						}
						break;
					} 
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}




			}
		}).start();




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


	public void show_card_results(String result)
	{
		card = result;

		//	 System.out.println("This is the card I am going to  bring information for" + card);
		mNewDevicesArrayAdapter.add(result);
	}


	private class MyAsyncTask extends AsyncTask<String, Integer, Integer>{



		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			//pBar.setMessage("Found Card " + mFoundDevice + "  Loading profile");

			++mFoundDevice;

		}
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			int  i = 1;
			i = postData(params[0]);

			return i;
		}

		protected void onPostExecute(Integer result){

			super.onPostExecute(result);
			//		Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();

			if (result==1) {
				//    	 pBar.setMessage("Server is not reachable..");

				showToast("Server is not reachable. please check Internet connection");
				System.out.println("Server is not reachable");
				mBtAdapter.cancelDiscovery();
				holdMacs = "a";
			}




			//	 System.out.println("This is the card I am going to  bring information for" + card);
			if (card!=null) {

				Profile p = new Profile();
				String arr[] = card.split("&");

				if (arr.length==8){
					String mac =      arr[0].toString().replace("mac=" , ""        );
					String name =     arr[1].toString().replace("name=" , ""      );
					String phone =    arr[2].toString().replace("phone=" , ""     );
					String email =    arr[3].toString().replace("email=" , ""     );
					String site =     arr[4].toString().replace ("site=" , ""     );
					String head_line =arr[5].toString().replace ("head_line=" , "");
					String mission =  arr[6].toString().replace ("mission=" , ""  );
					String pic =      arr[7].toString().replace ("pic=" , ""      );

					p.setMacHw(arr[0].toString().replace("mac=" , ""        ));
					p.setName(arr[1].toString().replace("name=" , ""        ));
					p.setMobilePhoneNum(arr[2].toString().replace("phone=" , ""     ));
					p.setEmail(arr[3].toString().replace("email=" , ""     ));
					p.setSite(arr[4].toString().replace ("site=" , ""     ));
					p.setProfessionalHeadLine(arr[5].toString().replace ("head_line=" , ""));
					p.setMission(arr[6].toString().replace ("mission=" , ""  ));
					p.setPicture(arr[7].toString().replace ("pic=" , ""      ));

					card = name + "\n" + phone +  "\n" +email + "\n"  + site +"\n" + head_line +"\n" + mission + "\n";

					SaveToHistory(p);
					//	  pBar.setMessage("Scanning please wait....");
					///////////////////////////////////////////////////

					System.out.println("This is the mission:"+mission);
					GetSearchResults(mac,name,phone,email,site,head_line,mission,pic);






					////////////////////////////




					//		     	mNewDevicesArrayAdapter.add(card);
					//			mNewDevicesArrayAdapter.notifyDataSetChanged();
				}
			}
		}
		protected void onProgressUpdate(Integer... progress){


		}

		public int postData(String mac) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			myMac =   BluetoothAdapter.getDefaultAdapter().getAddress();
			//HttpPost httppost = new HttpPost("http://192.168.50.5/cgi-bin/get_card.cgi");
			//		HttpPost httppost = new HttpPost("http://dfoa.ssh22.net/cgi-bin/get_card.cgi");
			HttpPost httppost = new HttpPost("http://31.168.241.149/cgi-bin/get_card.cgi");

			//		  httppost.addHeader("Accept-Encoding", "gzip");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("mac", mac));
				nameValuePairs.add(new BasicNameValuePair("selfmac", myMac));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httppost.addHeader("Accept-Encoding", "gzip");
				// Execute HTTP Post Request
				System.out.println("Print httppost");
				response = httpclient.execute(httppost);

				if (response!=null){
					System.out.println("not Null");

					int responseCode = response.getStatusLine().getStatusCode();
					switch(responseCode)
					{
					case 200:

						HttpEntity entity = response.getEntity();

						InputStream instream = entity.getContent();
						org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");
						if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
							instream = new GZIPInputStream(instream);
						}

						if(entity != null)
						{

							card = convertStreamToString(instream);
							//  card = EntityUtils.toString(entity);







							//  System.out.println("This is cards details " + responseBody);

						}
						break;

					case 500:
						card=null;
						break;

					} 
				}
				else {
					System.out.println("This is a mess");
				}




			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				//   System.out.println("server is not reachable");
				//		    pBar.cancel();
				return 1;



				// TODO Auto-generated catch block
			}
			return 0;
		}

	}




	private void setUpActionBar() {
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


		}
	}


	private void GetSearchResults(String mac,String name,String phone,String email,String site,String head_line, String mission , String pic ){

		//update global variables

		DisplayName = name;
		String img = pic;
		String macHw = mac.toString();
		String url  = "http://dfoa.ssh22.net/photo/photo/" + macHw + ".png";
		emailID = email;
		MobileNumber = phone;

		ItemDetails item_details = new ItemDetails();
		item_details.setName(name);
		item_details.setItemDescription(phone);
		item_details.setPrice(email);
		item_details.setSite(site);
		item_details.setImg(img);
		item_details.setProfessionlaHeadLine(head_line);
		item_details.setMac(mac);

		item_details.setMission(mission);
		item_details.setmRssi(String.valueOf(mRssi));

		results.add(item_details);

		updateList(results.lastIndexOf(item_details));

		//     ArrayList<ItemDetails> image_details = GetSearchResults();








	}

	/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
          super.onCreateContextMenu(menu, v, menuInfo);
          if (v.getId()==R.id.listV_main) {
              MenuInflater inflater = getMenuInflater();
              inflater.inflate(R.menu.menu_list, menu);
          }
    }
	 */

	private void updateList (int pos) 
	{
		
		if(lv1.getAdapter()==null)
			   lv1.setAdapter(lAdapter);
			else{
			
			   lAdapter.updateData(pos); //update your adapter's data
		   lAdapter.notifyDataSetChanged();
			  
			}
//		lAdapter.
//		lv1.setAdapter(lAdapter);
		lAdapter.notifyDataSetChanged();

	}   




	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String getPhotoStringFromBitmap(Bitmap bm){
		String ba1 = null;;
		if (bm!=null)
		{
			Bitmap bitmapOrg = bm;
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			//Resize the image


			bitmapOrg.compress(Bitmap.CompressFormat.PNG, 100, bao);
			byte[] ba = bao.toByteArray();
			ba1 = Base64.encodeToString(ba, 0);

			return ba1;
		}
		else {
			System.out.println("return null");
			return null;
		}


	}



	private class MyAsyncTask1 extends AsyncTask<Bitmap, Void, String>{



		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			//pBar.setMessage("Found Card " + mFoundDevice + "  Loading profile");



		}
		@Override
		protected String  doInBackground(Bitmap... params) {
			// TODO Auto-generated method stub

			return   getPhotoStringFromBitmap(params[0]);

		}

		protected void onPostExecute(String result){

			//  super.execute(result);
			//		Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();

			//	 System.out.println("This is the card I am going to  bring information for" + card);

			//	intent.putExtra("photo", result);

			writeStringAsFile(result);	




			startActivity(intent);

		}
		protected void onProgressUpdate(Integer... progress){




		}
	}
	private void startDiscovery(){

		System.out.println("In start discovery function ++++++++++++++");
		mBtAdapter.startDiscovery();

		Message message = handler.obtainMessage();
		message.obj = "update UI";
		handler.sendMessage(message);

		mFoundDevice =1; 
	}

	private  Profile  loadCard()
	{
		final String filename = "card.bin";
		Profile  p = new Profile(); 
		FileInputStream fis = null;
		ObjectInputStream in = null; 
		try {
			fis = openFileInput(filename);
			in = new ObjectInputStream(fis);
			p = (Profile) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}


		//		     decode p.picture from BASE_64 to bitmap 

		//Bitmap b = decodeSampledBitmapFromPath(p.picture,100,100);
		if ( p.getPicture() != null) {

			Bitmap b = setImg(p.getPicture());
			//    imageView.setImageBitmap(b);
			//	    img = p.picture;
			//	     WebView.setImageBitmap(setImg(p.picture));
		}


		System.out.println("Going to return P with follow " +p.getEmail() + p.getEmail() + p.getMobilePhoneNum() + p.getProfessionalHeadLine());
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


	public   void setSearchResult(String str) {
		CardsListBaseAdapter    mAdapter = new CardsListBaseAdapter(this, null);
		ArrayList<ItemDetails> results1 = new ArrayList<ItemDetails>();
		//   ItemDetails temp = new ItemDetails();
		for (ItemDetails temp  : results) {
			System.out.println("This is temp: " + temp.toString());
			if (temp.getPrfessionalHeadLine().contains(str)){
				System.out.println("match-- this is head line :" +temp.getPrfessionalHeadLine() + "and String is :" + str );


				results1.add(temp);
				//    lAdapter.notifyDataSetChanged();





				//  
			}






		}

		//    results = results1;

		//   lAdapter.notifyDataSetChanged();
	}   


	private void SaveToHistory (Profile p){

		Context c = getApplicationContext();
		SavedCards mHistoryCards = new SavedCards();
		boolean flag = true;
		boolean  reachedMaxProfiles = false;
		int maxProfiles = 50; 		
		Cards cards = new Cards(c,histoyFileName);
		//load list from file;
		mHistoryCards = cards.loadCards();
		if (mHistoryCards !=null) {

			if (mHistoryCards.profileArrayList.size() == maxProfiles-1)    	
				reachedMaxProfiles = true;


			if (D)  Log.v(TAG,"History file reached 50  profiles, need to rotate");



			for (int ind =0 ;ind <mHistoryCards.profileArrayList.size() && flag;ind++) {
				System.out.println("This is the mac i am looking" + p.getMacHw() + "and this is mac in the file "+ mHistoryCards.profileArrayList.get(ind).getMacHw() );
				if    (mHistoryCards.profileArrayList.get(ind).getMacHw().contentEquals(p.getMacHw())){
					//That means that this card already exist in history card so we can ignore, 

					if (D)  Log.v(TAG,"The card with Mac address" + p.getMacHw() + "already exist in history fileis " );
					flag=false;



				}


			}



			if (flag  && reachedMaxProfiles)    cards.saveToFile(p,true);


			//  System.out.println("adding to card this mac address and name anf flag  " + p.getName() +p.getMacHw() + flag  );
			else if(flag)  cards.saveToFile(p,false);







		}
		else    cards.saveToFile(p,false);
	}
	public void switchTabInActivity(int indexTabToSwitchTo){
		MainActivity parentActivity;
		parentActivity = (MainActivity) this.getParent();
		parentActivity.switchTab(indexTabToSwitchTo);
	}

	private void updateView(int index){
		System.out.println("In update view");


		System.out.println("This is getfirstvisability"  +lv1.getFirstVisiblePosition());
		//View v = lv1.getChildAt(0);
		if (lv1.getFirstVisiblePosition()==0){


			View v = lv1.getChildAt(lv1.getFirstVisiblePosition()); 
			Profile p = new Profile();
			p = loadCard(); 


			if(v == null){

				System.out.println("return falseeeeeee1");


				return;
			}

			else {



				TextView   mission   = (TextView) v.findViewById(R.id.tvMission);
				ImageView  picture   = (ImageView) v.findViewById(R.id.photo);
				TextView   name      = (TextView) v.findViewById(R.id.name);
				TextView   position  =(TextView) v.findViewById(R.id.tvHeadLine);

				mission.setText(p.getMission());
				name.setText(p.getName());
				position.setText(p.getProfessionalHeadLine());
				if ( p.getPicture() != null) {


					picture.setImageBitmap(setImg(p.getPicture()));

				}

				results.get(0).setMission(p.getMission());
				results.get(0).setImg(p.getPicture());
				results.get(0).setName(p.getName()); 
				results.get(0).setProfessionlaHeadLine(p.getProfessionalHeadLine());

			}
		}
	}

	@Override

	public void onResume()
	{
		super.onResume();
		System.out.println("++Resume++++    In Devicelistacticity ");

		updateView(0);




	}
	@Override public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override public void run() {
				swipeLayout.setRefreshing(false);
				// updateView(0);
				// View v = swipeLayout.getChildAt(0);


				System.out.println("Restesh is on");

				startDiscovery();

			}
		}, 10);
	}



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

	public void loadSelfProfile()
	{

		final String filename = "card.bin";
		File file = getBaseContext().getFileStreamPath(filename);
		if(file.exists()) {
			//read the card and show  details
			if(D) Log.e(TAG, "+++ Load self card");






			System.out.println("loading card");
			Profile profile = new Profile() ;
			profile = loadCard();



			GetSearchResults(profile.getMacHw() ,profile.getName(),profile.getMobilePhoneNum(),profile.getEmail(),profile.getSite(),profile.getProfessionalHeadLine() ,profile.getMission(),profile.getPicture() );




		}

		else 
		{
			//check if profile exist on server , maybe return user
			if (mBTena){	
				selfMac =   BluetoothAdapter.getDefaultAdapter().getAddress();
				//look selfmac address on server
				new RetrievSelfCardFromServer().execute(selfMac);
				if(file.exists()) {



					Profile profile = new Profile() ;
					profile = loadCard();

					GetSearchResults(profile.getMacHw() ,profile.getName(),profile.getMobilePhoneNum(),profile.getEmail(),profile.getSite(),profile.getProfessionalHeadLine() ,profile.getMission(),profile.getPicture() );
				}
				else {
					System.out.println("Self profile does not exist");
					GetSearchResults("ss" ,"New user ?" ,"ssd","sdsd","sdsds","Click here" ,"To Complete your profile", null);
				}
			}
		}



	}



	private class RetrievSelfCardFromServer extends AsyncTask<String, Integer, Integer>{



		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			//pBar.setMessage("Found Card " + mFoundDevice + "  Loading profile");



		}
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			int  i = 1;
			i = postData(params[0]);

			return i;
		}

		protected void onPostExecute(Integer result){

			super.onPostExecute(result);
			//		Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();

			if (result==1) {
				//    	 pBar.setMessage("Server is not reachable..");

				showToast("Server is not reachable. please check Internet connection");
				System.out.println("Server is not reachable");


			}




			//	 System.out.println("This is the card I am going to  bring information for" + card);
			if (card!=null) {

				Profile p = new Profile();
				String arr[] = card.split("&");
				if (arr.length!=1) {

					String mac =      arr[0].toString().replace("mac=" , ""        );
					String name =     arr[1].toString().replace("name=" , ""      );
					String phone =    arr[2].toString().replace("phone=" , ""     );
					String email =    arr[3].toString().replace("email=" , ""     );
					String site =     arr[4].toString().replace ("site=" , ""     );
					String head_line =arr[5].toString().replace ("head_line=" , "");
					String mission =  arr[6].toString().replace ("mission=" , ""  );
					String pic =      arr[7].toString().replace ("pic=" , ""      );

					p.setMacHw(arr[0].toString().replace("mac=" , ""        ));
					p.setName(arr[1].toString().replace("name=" , ""        ));
					p.setMobilePhoneNum(arr[2].toString().replace("phone=" , ""     ));
					p.setEmail(arr[3].toString().replace("email=" , ""     ));
					p.setSite(arr[4].toString().replace ("site=" , ""     ));
					p.setProfessionalHeadLine(arr[5].toString().replace ("head_line=" , ""));
					p.setMission(arr[6].toString().replace ("mission=" , ""  ));
					p.setPicture(arr[7].toString().replace ("pic=" , ""      ));

					card = name + "\n" + phone +  "\n" +email + "\n"  + site +"\n" + head_line +"\n" + mission + "\n";


					//	  pBar.setMessage("Scanning please wait....");
					///////////////////////////////////////////////////

					System.out.println("This is the mission:"+mission);
					saveCard(p);
				}
				else 
					if (D) Log.v(TAG,"Cant parse profile after retrieve from server");


			}
		}
		protected void onProgressUpdate(Integer... progress){


		}

		public int postData(String mac) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();

			//HttpPost httppost = new HttpPost("http://192.168.50.5/cgi-bin/get_card.cgi");
			//	HttpPost httppost = new HttpPost("http://dfoa.ssh22.net/cgi-bin/get_card.cgi");
			HttpPost httppost = new HttpPost("http://31.168.241.149/cgi-bin/get_card.cgi");

			//	  httppost.addHeader("Accept-Encoding", "gzip");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("mac", mac));
				//		nameValuePairs.add(new BasicNameValuePair("slefmac", selfMac));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httppost.addHeader("Accept-Encoding", "gzip");
				// Execute HTTP Post Request
				System.out.println("Print httppost");
				response = httpclient.execute(httppost);

				if (response!=null){
					System.out.println("not Null");

					int responseCode = response.getStatusLine().getStatusCode();
					switch(responseCode)
					{
					case 200:

						HttpEntity entity = response.getEntity();

						InputStream instream = entity.getContent();
						org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");
						if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
							instream = new GZIPInputStream(instream);
						}

						if(entity != null)
						{

							card = convertStreamToString(instream);
							//  card = EntityUtils.toString(entity);







							//  System.out.println("This is cards details " + responseBody);

						}
						break;

					case 500:
						card=null;
						break;

					} 
				}
				else {
					System.out.println("This is a mess");
				}




			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				//   System.out.println("server is not reachable");
				//		    pBar.cancel();
				return 1;



				// TODO Auto-generated catch block
			}
			return 0;
		}

	}



	public  void saveCard(Profile p){



		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			String filename = "card.bin";
			//	      Toast.makeText(this, "going to open file and write...." , Toast.LENGTH_SHORT).show();
			fos = openFileOutput(filename ,Context.MODE_PRIVATE);
			//      Toast.makeText(this, "opened file...." , Toast.LENGTH_SHORT).show();
			out = new ObjectOutputStream(fos);
			out.writeObject(p);

			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(this, "Cant open file and write" , Toast.LENGTH_SHORT).show();
		}
	}







	private void updateRssi(short rssi,String mac){

		for (int i=0; i < results.size();i++) {
			if (results.get(i).getMac().equalsIgnoreCase(mac))
				results.get(i).setmRssi(String.valueOf(rssi));
			System.out.println("update device" + mac + "and RSSI " +rssi); 

			//         runOnUiThread(new Runnable() {
			//             @Override
			//             public void run() {

			//    	      mBlackBar.getLayoutParams().height = 10;
			//     mLeDeviceListAdapter.addDevice(device);
			//     mLeDeviceListAdapter.notifyDataSetChanged();
			/*  
        	   if (device.getAddress().equalsIgnoreCase("24:0A:11:E7:26:A9")) {
//         		  if (device.getAddress().equalsIgnoreCase("BC:F5:AC:85:90:F7")) {
//    	   if (device.getAddress().equalsIgnoreCase("B8:78:2E:04:B5:FC")) {
//          	   System.out.println("Got BLE device");
                 System.out.println("This is the device " + device+" " +device.getName() + " with RSSI" +  rssi);
                 Item item = new Item();
                 item.setRssi(rssi);
                 item.setTime(System.currentTimeMillis());
                 mArr.add(item);


//                 System.out.println("device added");

//                  System.out.println("Information\n");
//                  System.out.println("Time " + mArr.get(mArr.size() -1).getTime());
///                  System.out.println("Arra0 time " +  mArr.get(0).getTime());
//                 System.out.println("the result is " + (mArr.get(mArr.size() -1).getTime() - mArr.get(0).getTime())); 


                 if ((mArr.get(mArr.size() -1).getTime() - mArr.get(0).getTime()) > 1000)  {

              	    System.out.println("Now im in - delta >1 sec");



              	   for (i = (mArr.size() -1);((mArr.get(mArr.size() -1).getTime() - mArr.get(i).getTime()) < 1000);i--) {


              		   total  = total + mArr.get(i).getRssi() ;


              	   }
              	     System.out.println("I is " + i);  
              	     System.out.println("This is array size" + mArr.size());

              	     total = total/ (mArr.size()-1 - i);

                       System.out.println("Average is " + total);
                       changeBar();
                       mArr.remove(i);
                 }

             total = 0;





                 //                  System.out.println("This is the device address" + device.getAddress());
//                  System.out.println("This is the device  name" + device.getName());
//                  System.out.println("This is the device type" + device.getType());
//                  System.out.println("This is the device address" + device.getUuids());
        	   }

			 */

			//          }
			//         });


		//	updateList();
		}
	}


	private void scanLeDevice(final boolean enable) {
		boolean mScanning = true;;
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					boolean mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
				}
			}, SCAN_PERIOD);

			mScanning  = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);

		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}


	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback =
			new BluetoothAdapter.LeScanCallback() {
		
		
	
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
				
					
					if (device.getBondState() != BluetoothDevice.BOND_BONDED  ) {

						if (holdMacs.contains(device.getAddress()))
						{
							if (D) Log.v(TAG, "The device" + device.getAddress() +"is already in the list");		 
							System.out.println("update device  " + device.getAddress() +" with RSSI: "+mRssi );

							found = true;
							
							
							
							
						//	updateRssi((short) rssi,device.getAddress());      
						}
					for (int pos = 0 ; pos< mNewDevicesArrayAdapter.getCount();pos++)
					{

						System.out.println("Thsissssss" +mNewDevicesArrayAdapter.getItem(pos));

						if (mNewDevicesArrayAdapter.getItem(pos).contains(device.getAddress()))
						{
							
							long time = SystemClock.uptimeMillis();
							Item item = new Item();
							item.setTime(time);
//							mHash.put(device.getAddress(),mRssi.add(item));
							
							
							
							//found device and need to update RSSI

							if (D) Log.v(TAG,"The device" + device.getAddress() +" is already in the list with "+ rssi);
							found = true;
						}

					}
					if (!found ) {
						
												
						long time = SystemClock.uptimeMillis();
						Item item = new Item();
						item.setTime(time);
						item.setRssi(rssi);
//						mHash.put(device.getAddress(),mRssi.add(item));
						
						holdMacs = holdMacs+device.getAddress()+ "=";	
						//	 mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
						if (D) Log.v(TAG,"found device and going to look for information Asynctask with " + device.getAddress());
						//Save this card to history file


						new MyAsyncTask().execute(device.getAddress());
					
					
					
					
					
					//       mLeDeviceListAdapter.addDevice(device);
					//       mLeDeviceListAdapter.notifyDataSetChanged();
				}
				}
				}
			});
		}
	};

}

