package com.osdma.milestones.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import com.osdma.milestones.db.Image;
import com.osdma.milestones.db.ImageHandler;

public class util {
	
	//private static String timeStampForWaterMark;
	
	public static String createImageFile(Bitmap imageBitmap, String FOLDER_NAME, String FILE_EXTENSION) throws IOException {
	    Bitmap bitmap_copy = Bitmap.createBitmap(imageBitmap);
		Bitmap mutableBitmap = Bitmap.createScaledBitmap(bitmap_copy, bitmap_copy.getWidth()*2, bitmap_copy.getHeight()*2, true);
		Date date = new Date();
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
	    String timeStampForWaterMark = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
	    //System.out.println("TimeStamp : " + timeStamp);
	    Canvas canvas = new Canvas(mutableBitmap);
	    Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(10);
        paint.setAntiAlias(true);
	    canvas.drawText(timeStampForWaterMark, 50, 190, paint);
	    paint.setColor(Color.BLACK);
	    canvas.drawText(timeStampForWaterMark, 10, 10, paint);
	    if(!Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).exists() && !Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).isDirectory()){
	    	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME).mkdir();	
	    }
	    
	    File image = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+FOLDER_NAME), timeStamp + FILE_EXTENSION);
	    System.out.println("File name : " + image.getPath() + "/" + image.getName());
	    FileOutputStream fileOutputStream = new FileOutputStream(image);
	    mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
	    fileOutputStream.flush();
	    fileOutputStream.close();
	    System.out.println(image.getPath());
	    return image.getPath();
	}
	
	public static void addImage(Context context, String file_location, String latitude, String longitude){
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("Timestamp:"+timestamp);
		ImageHandler db = new ImageHandler(context);
		db.add(new Image(System.currentTimeMillis(), file_location, latitude, longitude, timestamp, "false"));
		
		ArrayList<Image> images = db.getAll();
		 for(Image c: images) {
		    String data = "longitude: "+c.longitude+", lattitude: "+c.lattitude+", date: "+c.datetime;
		    Log.d("Car: ", data);
		 }
	}
	
	public static byte[] hash(String data) throws NoSuchAlgorithmException {
		
		/*byte[] combined = new byte[md5.length + password.length];

    	for (int i = 0; i < combined.length; ++i)
    	{
    	    combined[i] = i < md5.length ? md5[i] : password[i - md5.length];
    	}*/
		System.out.println(data);
	    MessageDigest sha512 = MessageDigest.getInstance("SHA-512");        
	    byte[] passBytes = data.getBytes();
	    byte[] passHash = sha512.digest(passBytes);
	    return passHash;
	}
	
	public static String hash(byte[] md5) throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    //byte[] passBytes = password.getBytes();
	    byte[] passHash = sha256.digest(md5);
	    return bytetostring(passHash);
	}
	
	public static final byte[] md5(String s) {
	    try {
	    	// Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        
	        return messageDigest;

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static String bytetostring(byte[] messageDigest){
		StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String h = Integer.toHexString(0xFF & messageDigest[i]);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();
	}
}