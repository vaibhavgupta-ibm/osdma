package com.osdma.milestones.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImageHandler extends SQLiteOpenHelper {

	private String IMAGE_TABLE = "image";
	private String KEY_ID = "id";
	private String FIELD_IMAGE_LOCATION = "image_location";
	private String FIELD_LATTITUDE = "lattitude";
	private String FIELD_LONGITUDE = "longitude";
	private String FIELD_DATETIME = "datetime";
	private String FIELD_ISSYNC = "issync";

	private static String databaseName = "ImageDB";
	private static int version = 1;

	public ImageHandler(Context context) {
		super(context, databaseName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_IMAGE_TABLE = "CREATE TABLE " + IMAGE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + FIELD_IMAGE_LOCATION + " TEXT,"
                + FIELD_LATTITUDE + " TEXT," 
                + FIELD_LONGITUDE + " TEXT,"
                + FIELD_DATETIME + " TEXT," 
                + FIELD_ISSYNC + " BOOLEAN)";
                db.execSQL(CREATE_IMAGE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + IMAGE_TABLE);
		this.onCreate(db);
	}
	
	public void add(Image image){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(FIELD_IMAGE_LOCATION, image.image_location);
		values.put(FIELD_LATTITUDE, image.lattitude);
		values.put(FIELD_LONGITUDE, image.longitude);
		values.put(FIELD_DATETIME, image.datetime);
		values.put(FIELD_ISSYNC, image.issync);
		
		// Inserting Row
		db.insert(IMAGE_TABLE, null, values);
		db.close(); // Closing database connection
	}

	public Image get(String image_location){
		SQLiteDatabase db = this.getReadableDatabase();
		String location = null;
		
		Cursor cursor = db.query(IMAGE_TABLE, new String[] {KEY_ID, FIELD_IMAGE_LOCATION, FIELD_LATTITUDE, 
				FIELD_LONGITUDE, FIELD_DATETIME, FIELD_ISSYNC }, FIELD_IMAGE_LOCATION + "=?",
	            new String[] { image_location }, 
	            null, // group by 
	            null, // having
	            null, // order by
	            null); // limit
		Image image = new Image();
		if (cursor !=null && cursor.moveToFirst() && cursor.getCount() > 0) {
		//String data = "ID: "+cursor.getString(0)+", Location: "+cursor.getString(1)+", latitude: "+cursor.getString(2)+", longitude: "+cursor.getString(3)+", date: "+cursor.getString(4)+", issync: "+cursor.getString(5);
	    //Log.d("Data: ", data);
		    image = new Image(
		    	Integer.parseInt(cursor.getString(0)),
		    	cursor.getString(1),
		    	cursor.getString(2),
		    	cursor.getString(3),
		    	cursor.getString(4),
		    	cursor.getString(5));
		    cursor.close();
		}
		    db.close();
		    return image;
	}
	
	public int update(Image image) {

	    SQLiteDatabase db = this.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    //values.put(FIELD_IMAGE_LOCATION, image.image_location);
	    //values.put(FIELD_LATTITUDE, image.lattitude);
	    //values.put(FIELD_LONGITUDE, image.longitude);
	    //values.put(FIELD_DATETIME, image.datetime);
	    values.put(FIELD_ISSYNC, image.issync);

	    // updating row
	    return db.update(IMAGE_TABLE, values, FIELD_IMAGE_LOCATION + " = ?",
	            new String[] { String.valueOf(image.image_location) });
	}

	public int delete(String image_location) {

	    SQLiteDatabase db = this.getWritableDatabase();
	    // deleting row
	    return db.delete(IMAGE_TABLE, FIELD_IMAGE_LOCATION + " = ?",
	        new String[] { image_location });

	}
	
	public ArrayList getAll(){

		  ArrayList listImage = new ArrayList();

		  SQLiteDatabase db = this.getReadableDatabase();
		  Cursor cursor = db.rawQuery("SELECT * FROM " + IMAGE_TABLE, null);   try{
		    if (cursor != null){
		      if(cursor.moveToFirst()){
		        do {
		  	  Image image = new Image(
		            Integer.parseInt(cursor.getString(0)),
		            cursor.getString(1),
		            cursor.getString(2),
		            cursor.getString(3),
		            cursor.getString(4),
		            cursor.getString(5));
		            listImage.add(image);
		        } while(cursor.moveToNext());
		      }
		    }
		  } finally {
		    cursor.close();
		    db.close();
		  }
		  return listImage;
	}
}
