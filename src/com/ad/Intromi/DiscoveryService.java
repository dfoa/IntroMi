package com.ad.Intromi;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DiscoveryService extends Service {
	
    private static final String TAG = "DiscoveryService";
    private BluetoothAdapter mBtAdapter;
    private boolean D = true;
    private DeviceListActivity d ;
	public DiscoveryService() {
	
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		
	}

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
  //      Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
        if (D) Log.v(TAG, "Discovery service onCreate");
        if (!checkIfBTAvailable(mBtAdapter))
        {
         
 //        Toast.makeText(this, "Bluetooth is not enabled ", 3).show();
        }

    }

    @Override
    public void onStart(Intent intent, int startId) {
    	// For time consuming an long tasks you can launch a new thread here...
        //Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
        if (D) Log.v(TAG, "Discovery service onStart");
        
  
    

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        if (D) Log.d(TAG, "Service destroyed");

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    		
    	Log.i(TAG, "Service onStartCommand " + startId);
    	
   //    doDiscovery();
	    
   
    	
       
    	return Service.START_STICKY;
    }
    private void doDiscovery() {
     
     
                
                Toast.makeText(getApplicationContext(), "Start discovery", Toast.LENGTH_LONG).show();

    	
    	if (D) Log.d(TAG, "doDiscovery()");
        
        // Indicate scanning in the title
//      setProgressBarIndeterminateVisibility(true);
 //       setTitle(R.string.scanning);

        // Turn on sub-title for new devices
 //       findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
            System.out.println("discovery still running, cannot start ...");

        }

        // Request discover from BluetoothAdapter
        
        mBtAdapter.startDiscovery();
        
        
       
    



} 
    
    
    
  
 
private boolean checkIfBTAvailable(BluetoothAdapter mBluetoothAdapter){
	

    // If the adapter is null, then Bluetooth is not supported
    if (mBluetoothAdapter == null) {
        Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        
        return false;
        
    }
    
    else return true;
    
}


}