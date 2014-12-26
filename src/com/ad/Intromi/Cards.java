package com.ad.Intromi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.ContextWrapper;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;


public class Cards {

private static  String  TAG ="IntroMi/Cards";	
private  String fileName;
private static boolean D = true; 
public SavedCards mSaveCards ;
protected Context context;


			

  public Cards(Context c,String fileName) {
	  mSaveCards  =  new SavedCards();
	  this.context = c;
			// TODO Auto-generated constructor stub
	  this.fileName = fileName;
	  
  }

    
  public boolean chekIfFileExist()
  {
	   
  if (D) Log.v (TAG,"checking if file exist");
   File file = context.getFileStreamPath(fileName);
    if(file.exists()) {
	    	//read the card and show  details

       return true;
    }
  return false;
 }
	  
  
  public    synchronized void  removeProfile (Profile profile)
  {
	
	
	  
  }
  
 public    synchronized SavedCards  saveToFile(Profile profile,boolean push)
 {
   	  
	 File file = context.getFileStreamPath(fileName);
	
	    if(file.exists()) {
		    	//read the card and show  details
			   if(D) Log.v(TAG, "+++ Load saved cards");	
	
	 mSaveCards = loadCards();
	    }
	 if (push) 
		 mSaveCards.push(profile);
	 else
	    mSaveCards.addProfile(profile);
	 
	 
	 FileOutputStream fos = null;
	 ObjectOutputStream out = null;
	   
	    try {
	      fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
          out = new ObjectOutputStream(fos);
	      out.writeObject(mSaveCards);     
	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      Toast.makeText(context, "Cant open file and write" , Toast.LENGTH_SHORT).show();
	    }
	 return mSaveCards;
 }
 public    synchronized void  saveToFileBulk(SavedCards s)
 {
   	  
	 File file = context.getFileStreamPath(fileName);
	
	    if(file.exists()) {
		    	//read the card and show  details
			   if(D) Log.v(TAG, "+++ Load saved cards");	
	
	
	    
	
	 
	 FileOutputStream fos = null;
	 ObjectOutputStream out = null;
	   
	    try {
	      fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
          out = new ObjectOutputStream(fos);
	      out.writeObject(s);     
	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      Toast.makeText(context, "Cant open file and write" , Toast.LENGTH_SHORT).show();
	    
	 
	    }
	    }
 }
 
	    
  public  SavedCards loadCards()
  
 { 
	  if (chekIfFileExist()) {
	  
	  FileInputStream fis = null;
	 	 ObjectInputStream in = null; 
        
	 	 try {
	 		      fis = context.openFileInput(fileName);
	 		      in = new ObjectInputStream(fis);
	 		      mSaveCards = (SavedCards) in.readObject();
	 		      in.close();
	 		    } catch (Exception ex) {
	 		      ex.printStackTrace();
	 		      Toast.makeText(context, "cant open file to read" , Toast.LENGTH_SHORT).show();
	 		    }
	 		   
	 		    
	 
	 	return(mSaveCards);	
 }
	  else return null;
	  
 }
  
	    }
 	




 