package com.ad.Intromi;


import java.io.Serializable;
import java.util.ArrayList;



public class SavedCards implements Serializable {

   /**
	 * 
	 */
	private static final long serialVersionUID = 8715692378350732713L;
	ArrayList<Profile>  profileArrayList ;

	public SavedCards () {
	profileArrayList  = new ArrayList<Profile>();
  }


public ArrayList<Profile>  addProfile(Profile profile)
{
	profileArrayList.add(profile);
	return profileArrayList;
}

public void removeProfile(Profile profile)
{
 
	profileArrayList.remove(profile);
 	
}

public Profile getProfile(int position)
{
 
	return profileArrayList.get(position);
 	
}



public boolean  profileLookup(Profile profile)
{

	 
		 if (profileArrayList.contains(profile))
	    	 return true;
			 
	 
	return false;

}

public void    push (Profile profile)
{
   
  for (int i = 0 ; i <profileArrayList.size()-1 ; i++)
  {
	  
   Profile temp = new Profile();
   temp = profileArrayList.get(i);
	 profileArrayList.add(i+1,temp);
	
  }
  profileArrayList.add(0,profile);
 
}





}

