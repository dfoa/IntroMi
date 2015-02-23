package com.ad.Intromi;

public class Item {

 private Long time;
 private int Rssi;	
 private String Mac;

public void setTime(long time)  
	
{
	
	this.time =  time;
}

public void setMac(String Mac)
{
	this.Mac = Mac;
}

public String getMac()
{
	return Mac;
}



public  long  getTime()  
{
	
	return  time;
}


     
public void setRssi(int Rssi)

{
  this.Rssi = Rssi;	
 
}

public int getRssi()

{
   return Rssi;	
 
}


}
