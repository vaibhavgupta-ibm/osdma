package com.osdma.milestones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.osdma.R;
import com.osdma.milestones.utils.util;

import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener{

	private static final int CAMERA_REQUEST = 0;
	private static final String FOLDER_NAME = "/OSDMA";
	private static final String FILE_EXTENSION = ".PNG";
	private ImageView capturedImage;
	private static final Object CAPTURE_TAB_TEXT = "CAPTURE";
	private static final Object GALLERY_TAB_TEXT = "GALLERY";
	private ViewPager viewPager;
	private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);        
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getAllImages();
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager = (ViewPager)findViewById(R.id.pager);
        Tab captureTab = getActionBar().newTab();
        captureTab.setText((CharSequence) CAPTURE_TAB_TEXT);
        ApplicationTabListener applicationTabListener = new ApplicationTabListener();
        captureTab.setTabListener(applicationTabListener);
        getActionBar().addTab(captureTab);
        Tab galleryTab = getActionBar().newTab();
        galleryTab.setText((CharSequence) GALLERY_TAB_TEXT);
        galleryTab.setTabListener(applicationTabListener);
        getActionBar().addTab(galleryTab);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				System.out.println("Inside onPageSelected....");
				if(arg0 == 0){
					getActionBar().setSelectedNavigationItem(0);
				}else if(arg0==1){
					getAllImages();
					getActionBar().setSelectedNavigationItem(1);					
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        setPagerAdapter();
	}

	public void setPagerAdapter(){
    	viewPager.setAdapter(new ViewPagerAdapter());
    }
    
	public class ViewPagerAdapter extends PagerAdapter{
    	@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view==(LinearLayout)object;
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			
			return super.getItemPosition(object);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			LinearLayout linearLayout = null;
			if(position==0){
				linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.capture_layout, null);
				capturedImage = (ImageView)linearLayout.findViewById(R.id.capturedImage);
		        capturedImage.setOnClickListener(MainActivity.this);
				
			}else if(position==1){
				linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.gallery_layout, null);
				setImageGrid(linearLayout);
			}
			((ViewPager)container).addView(linearLayout, 0);
			return linearLayout;
		}
		@Override
		public void destroyItem(ViewGroup container, int position,
				Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView((LinearLayout)object);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		
		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			
		}
    }
    
	private void setImageGrid(View view){
    	GridView gridView = (GridView)view.findViewById(R.id.imageGrid);
    	gridView.setAdapter(new ImageAdapter(this));
    }

    private void getAllImages() {
		// TODO Auto-generated method stub
    	if(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).exists() && Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).isDirectory()){
	    	if(!bitmaps.isEmpty()){
	    		bitmaps.clear();
	    	}
	    	File[] files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).listFiles();
	    	for (int index = 0; index < files.length; index++) {
				if(!files[index].isDirectory()){
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inPreferredConfig = Bitmap.Config.ARGB_8888;
					Bitmap bitmap = BitmapFactory.decodeFile(files[index].getAbsolutePath(), options);
					bitmaps.add(bitmap);
				}
			}
    	}
	}
    
    public class ImageAdapter extends BaseAdapter {
        
    	private Context mContext;
                
        public ImageAdapter(Context c) {
            mContext = c;
            getAllImages();
        }

		public int getCount() {
            return bitmaps.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(bitmaps.get(position));
            return imageView;
        }    
    }

    private class ApplicationTabListener implements ActionBar.TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if(tab.getText().equals(CAPTURE_TAB_TEXT)){
				viewPager.setCurrentItem(0);
			}else if(tab.getText().equals(GALLERY_TAB_TEXT)){
				viewPager.setCurrentItem(1);
			}
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if(tab.getText().equals(CAPTURE_TAB_TEXT)){
				viewPager.setCurrentItem(0);
			}else if(tab.getText().equals(GALLERY_TAB_TEXT)){
				viewPager.setCurrentItem(1);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.capturedImage:
			launchCamera();
			break;

		default:
			break;
		}
	}
	
	private void launchCamera(){
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	} 
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            System.out.println("Width : " + photo.getWidth());
            System.out.println("Height : " + photo.getHeight());
            System.out.println("Pictures path : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            capturedImage.setImageBitmap(Bitmap.createScaledBitmap(photo, photo.getWidth()*2, photo.getHeight()*2, true));
            try {
				util.createImageFile(photo, FOLDER_NAME, FILE_EXTENSION);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
	}
	
}