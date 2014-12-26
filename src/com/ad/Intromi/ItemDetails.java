package com.ad.Intromi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;

public class ItemDetails {
	

	public void setContext( Context context) {
		
	
	 
	this.context = context;
	
	}
	
	public Context getContext() {
		
		
		 
	 return context;
	
	}
	public String getPrfessionalHeadLine() {
		return professionlHeadLine;
	}
	public void setProfessionlaHeadLine(String  professionlHeadLine) {
		this.professionlHeadLine = professionlHeadLine;
	}	
	
	public String getmission() {
		return mission;
	}
	public void setMission(String  mission) {
		this.mission = mission;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
	
	public Bitmap getImg() {
		
		return bm;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getMac() {
		return mac;
	}
	

	 public String getmRssi() {
		 
			return mRssi;
		}
		public void setmRssi(String rssi) {
			this.mRssi = rssi;
		}
	
	public void setImg(String img) {

    if (img !=null){
	
		
	
		
		
		
		
		
 //    ImageLoader	                    DisplayImage("http://192.168.1.28:8082/ANDROID/images/BEVE.jpeg", holder.itemImage);
		
		
		//decode from base_64 to real PNG format

		
		byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
		//im.setImageBitmap(decodedByte);
		bm = decodedByte;
    }
    else  
        bm = null;
	 

    
    
	}
		
	
	
	




	//private ImageView im = new ImageView(null);
	private Bitmap bm; 
	private String name ;
	private String itemDescription;
	private String price;
	private String site;
	 DownloadManager mDManager;
	private String mission;
    private String professionlHeadLine;
    private String  mRssi; 
    private Context context;
    private String mac;
   
    

	
	 
	    
	public ItemDetails(){
		
	
	}
	    
	    
	
	private Bitmap getBitmap(String url) 
    {
		Context context = null;
		FileCache  fileCache = new FileCache(context); 
        File f=fileCache.getFile(url);

        //from SD cache
       Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
           ex.printStackTrace();
           return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
/*
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
*/
            //decode with inSampleSize
//           BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {}
        return null;
    }
   
	public class FileCache {

        private File cacheDir;

        public FileCache(Context context){
            //Find the dir to save cached images
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TempImages");
            else
                cacheDir=context.getCacheDir();
            if(!cacheDir.exists())
                cacheDir.mkdirs();
        }

        public File getFile(String url){
            String filename=String.valueOf(url.hashCode());
            File f = new File(cacheDir, filename);
            return f;

        }

        public void clear(){
            File[] files=cacheDir.listFiles();
            if(files==null)
                return;
            for(File f:files)
                f.delete();
        }

    }
    public static class Utils {
        public static void CopyStream(InputStream is, OutputStream os)
        {
            final int buffer_size=1024;
            try
            {
                byte[] bytes=new byte[buffer_size];
                for(;;)
                {
                  int count=is.read(bytes, 0, buffer_size);
                  if(count==-1)
                      break;
                  os.write(bytes, 0, count);
                }
            }
            catch(Exception ex){}
        }
    }
    
    
   
		




	

	

	}
	  
	
	
	






