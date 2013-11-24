package com.osdma.milestones.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;

public class util {
	public static void createImageFile(Bitmap imageBitmap, String FOLDER_NAME, String FILE_EXTENSION) throws IOException {
	    Bitmap bitmap_copy = Bitmap.createBitmap(imageBitmap);
		Bitmap mutableBitmap = Bitmap.createScaledBitmap(bitmap_copy, bitmap_copy.getWidth()*2, bitmap_copy.getHeight()*2, true);
		Date date = new Date();
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
	    String timeStampForWaterMark = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	    System.out.println("TimeStamp : " + timeStamp);
	    Canvas canvas = new Canvas(mutableBitmap);
	    Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(7);
        paint.setAntiAlias(true);
	    canvas.drawText(timeStampForWaterMark, 50, 190, paint);
	    if(!Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).exists() && !Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).isDirectory()){
	    	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).mkdir();	
	    }
	    
	    File image = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME), timeStamp + FILE_EXTENSION);
	    System.out.println("File name : " + image.getPath() + "/" + image.getName());
	    FileOutputStream fileOutputStream = new FileOutputStream(image);
	    mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
	    fileOutputStream.flush();
	    fileOutputStream.close();
	    //return image;
	}
}
