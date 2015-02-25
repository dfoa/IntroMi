package com.ad.Intromi;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.ad.Intromi.R;

public class ItemListBaseAdapter extends BaseAdapter    {
private static boolean D = true;	

private Context c;
private Typeface tf;
private Typeface tf1;
	
	private static ArrayList<ItemDetails> itemDetailsrrayList;

	


	private Typeface font;
	
	private LayoutInflater l_Inflater;

	public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.c = context;
		  tf = Typeface.createFromAsset(c.getAssets(),
		             "fonts/Lato-Black.ttf");
			   tf1 = Typeface.createFromAsset(c.getAssets(),
		             "fonts/Lato-Regular.ttf");			   
	
	}

	public int getCount() {
		
		System.out.println("Holder, size  " + itemDetailsrrayList.size());
		return itemDetailsrrayList.size();
		
	}

	public Object getItem(int position) {
		System.out.println("Hodler, position " +  itemDetailsrrayList.get(position));
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		
		System.out.println("Hodler, ItemID " + position);
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
	
		System.out.println("Holder,Position " + position);
		
		if (convertView == null) {
//			System.out.println("this is the position " +position);
//		if (position % 2 ==1)
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
//		else
//				convertView = l_Inflater.inflate(R.layout.item_details_view_left_pic, null);	
			
			
			
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.txt_itemName.setTypeface(tf);
//			holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
//			holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
//		    holder.txt_site = (TextView) convertView.findViewById(R.id.tv1Site);
		    holder.txt_head_line = (TextView) convertView.findViewById(R.id.tvHeadLine);
		    holder.txt_head_line.setTypeface(tf1);
		    holder.txt_mission = (TextView) convertView.findViewById(R.id.tvMission);
		    holder.txt_mission.setTypeface(tf1);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);
			holder.bar = (ImageView) convertView.findViewById(R.id.imageViewBlackBar);
//			holder.ch_check = (CheckBox) convertView.findViewById(R.id.star);
			holder.txt_rssi = (TextView) convertView.findViewById(R.id.rssi);
         	holder.bar = (ImageView) convertView.findViewById(R.id.imageViewBlackBar);
         	
         	
         	
			  // add listener for email 
	 /*       holder.ch_check.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	 
	             System.out.println("checked");
	             Cards cards = new Cards(c);
	             Profile p = new Profile();
	             p.setName("name_test_name");
	             cards.saveToFile(p);
	             
	        
	  
	            }
	             
	        });
*/
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_itemName.setText(itemDetailsrrayList.get(position).getName());
//		holder.txt_itemDescription.setText(itemDetailsrrayList.get(position).getItemDescription());
//		holder.txt_itemPrice.setText(itemDetailsrrayList.get(position).getPrice());
//	    holder.txt_site.setText(itemDetailsrrayList.get(position).getSite());
	    holder.txt_head_line.setText(itemDetailsrrayList.get(position).getPrfessionalHeadLine());
	    holder.txt_mission.setText(itemDetailsrrayList.get(position).getmission());
        holder.txt_rssi.setText(itemDetailsrrayList.get(position).getmRssi());
 //       Drawable t = c.getResources().getDrawable(R.id.imageViewBlackBar);
//        holder.bar.setImageResource(R.id.imageViewBlackBar);
        int i = Integer.parseInt(itemDetailsrrayList.get(position).getmRssi())*(-1);
//        int s = holder.bar.getHeight();
        if (i!=0){
            System.out.println("Height is RSSI " + i);
 //           System.out.println("GetHe is RSSI " + s);
        i =  (300*(0+i))/(0+100);
   
        
        System.out.println("The heightis " + i);
        holder.bar.getLayoutParams().height=i;
  

        }
     //   holder.bar.setBackground(t);
     
      
        
	    
	    if (itemDetailsrrayList.get(position).getImg() ==null)
	    	holder.itemImage.setImageBitmap(BitmapFactory.decodeResource(c.getResources(),R.drawable.profile));
	    else
        holder.itemImage.setImageBitmap(itemDetailsrrayList.get(position).getImg());
        
    

		return convertView;
	}

	static class ViewHolder {
	
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		TextView txt_site;
	    ImageView itemImage,bar ;
		Bitmap bm;
		TextView txt_mission;
		TextView txt_head_line;
		CheckBox ch_check;
		TextView  txt_rssi;
		 
		
	}

	public void updateData(int pos) {
		
		
		
		// TODO Auto-generated method stub
		
		
	}
	
 	          
	
	 
}
	
	

