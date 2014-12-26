package com.ad.Intromi;




import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

private ProgressBar pb;
private TabHost tabHost;
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_tabs);

		
	        

//		setUpActionBar();  	   
	    
	       
	       
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        
        getOverflowMenu();



		Resources ressources = getResources(); 
		tabHost = getTabHost(); 
		
		// Android tab
		Intent intentAndroid = new Intent().setClass(this,DeviceListActivity.class);
		TabSpec tabSpecAndroid = tabHost
			.newTabSpec("Android")
			.setIndicator("", ressources.getDrawable(R.drawable.app_icon))
			.setContent(intentAndroid);

		// Apple tab
		Intent intentApple = new Intent().setClass(this, SavedCardsList.class);
		TabSpec tabSpecApple = tabHost
			.newTabSpec("Appleeeeeeeee")
			.setIndicator("", ressources.getDrawable(R.drawable.favorite_blue))
			.setContent(intentApple);
		
		// Windows tab
		Intent intentWindows = new Intent().setClass(this, HistoryCardslist.class);
		TabSpec tabSpecWindows = tabHost
			.newTabSpec("Windows")
			.setIndicator("", ressources.getDrawable(R.drawable.clock_blue))
			.setContent(intentWindows);
		
		// Blackberry tab
		Intent intentBerry = new Intent().setClass(this, MyCards.class);
		TabSpec tabSpecBerry = tabHost
			.newTabSpec("Berry")
			.setIndicator("", ressources.getDrawable(R.drawable.edit_blue))
			.setContent(intentBerry);
		

	
		
		// add all tabs 

		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		
		tabHost.addTab(tabSpecWindows);
		tabHost.addTab(tabSpecBerry);
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(4);
	}
	   
    private void setUpActionBar() {
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            

        }
        }
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
 // inflate for action bar

    public void switchTab(int tab){
        tabHost.setCurrentTab(tab);
        
}


}