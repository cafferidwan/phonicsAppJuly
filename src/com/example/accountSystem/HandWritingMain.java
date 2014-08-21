package com.example.accountSystem;

import java.io.File;
import java.util.ArrayList;

import org.andengine.util.debug.Debug;

import com.example.phonicsapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HandWritingMain extends Activity {

	public static String DEBUG_TAG = HandWritingMain.class.getSimpleName();
    public class ImageAdapter extends BaseAdapter {
    	
    	private Context mContext;
    	ArrayList<String> itemList = new ArrayList<String>();
    	
    	public ImageAdapter(Context c) {
    		mContext = c;	
    	}
    	
    	void add(String path){
    		itemList.add(path);	
    	}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			TextView textView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(0, 0, 0, 0);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 220, 220);

	        imageView.setImageBitmap(bm);
	        return imageView;
		}
		
		public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
			
			Bitmap bm = null;
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
		     
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		     
			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, options); 
		     
			return bm;  	
		}
		
		public int calculateInSampleSize(
				
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
			// Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;
			
			if (height > reqHeight || width > reqWidth) {
				if (width > height) {
					inSampleSize = Math.round((float)height / (float)reqHeight);   	
					Log.d("a", "a:"+inSampleSize);
				} else {
					inSampleSize = Math.round((float)width / (float)reqWidth);   	
					Log.d("a", "b:"+inSampleSize);
				}   
			}
			
			return inSampleSize;   	
		}

	}
    
    ImageAdapter myImageAdapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handwriting_list);
        
        ListView listview = (ListView) findViewById(R.id.listView1);
        myImageAdapter = new ImageAdapter(this);
        listview.setAdapter(myImageAdapter);
        
        Debug.d(DEBUG_TAG, "accountNumber:"+IndividualAccountDescription.accountNumber);
        String targetPath = "/sdcard/PhonicsApp/HandWritingLetters/"+
        		AdminPanel.currentAccount+"/";
        
        //Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);
        
        File[] files = targetDirector.listFiles();
        
        if(files!= null)
        {
        
        	for (File file : files)
        	{
        		myImageAdapter.add(file.getAbsolutePath());
        		
        	} 
        }
        else
    	{
    		Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_LONG).show();
    	}
    }

}
