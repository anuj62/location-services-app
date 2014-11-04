package edu.buffalo.cse.locationapp.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class PersistencyManager {
	
	LocalizationDbHelper mDbHelper = null;

	// Gets the data repository in write mode
	SQLiteDatabase writableDB = null;
	SQLiteDatabase readableDB = null;
	
	public PersistencyManager(Context context) {
		mDbHelper = new LocalizationDbHelper(context);
		writableDB = mDbHelper.getWritableDatabase();
		readableDB = mDbHelper.getReadableDatabase();
	}
	
	
	
	
	

}
