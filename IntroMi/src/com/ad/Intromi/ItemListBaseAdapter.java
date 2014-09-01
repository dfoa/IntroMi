package com.ad.Intromi;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.ad.Intromi.R;

public class ItemListBaseAdapter extends BaseAdapter    {
private static boolean D = true;	

private Context c;
	
	private static ArrayList<ItemDetails> itemDetailsrrayList;

	
	private Integer[] imgid = {
		    
			R.drawable.app_icon,
			R.drawable.p2,
			R.drawable.bb5,
			R.drawable.bb6,
			R.drawable.d1
			};
	
	private Typeface font;
	
	private LayoutInflater l_Inflater;

	public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.c = context;
	
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
	
		if (convertView == null) {
			System.out.println("this is the position " +position);
			if (position % 2 ==1)
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
			else
				convertView = l_Inflater.inflate(R.layout.item_details_view_left_pic, null);	
			
			
			
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
//			holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
//			holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
//		    holder.txt_site = (TextView) convertView.findViewById(R.id.tv1Site);
		    holder.txt_head_line = (TextView) convertView.findViewById(R.id.tvHeadLine);
		    holder.txt_mission = (TextView) convertView.findViewById(R.id.tvMission);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);
			holder.ch_check = (CheckBox) convertView.findViewById(R.id.star);
			
			
			  // add listener for email 
	        holder.ch_check.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	 
	             System.out.println("checked");
	             Cards cards = new Cards(c);
	             Profile p = new Profile();
	             p.setName("name_test_name");
	             cards.saveToFile(p);
	             
	        
	  
	            }
	             
	        });

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
	    
        holder.itemImage.setImageBitmap(itemDetailsrrayList.get(position).getImg());
        
   

		return convertView;
	}

	static class ViewHolder {
	
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		TextView txt_site;
	    ImageView itemImage;
		Bitmap bm;
		TextView txt_mission;
		TextView txt_head_line;
		CheckBox ch_check;
	}
	
 	          
	
	 
}
	
	

