package edu.buffalo.cse.locationapp.dataaccess;

import edu.buffalo.cse.locationapp.dataaccess.AccessPointContract.AccessPointEntry;
import edu.buffalo.cse.locationapp.dataaccess.FingerprintContract.FingerprintEntry;
import edu.buffalo.cse.locationapp.dataaccess.MapContract.MapEntry;
import edu.buffalo.cse.locationapp.dataaccess.ProjectContract.ProjectEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalizationDbHelper extends SQLiteOpenHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final String SQL_CREATE_PROJECT_ENTRIES =
	    "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
	    		ProjectEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
	    		ProjectEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + 
	    		" )";
	private static final String SQL_CREATE_MAP_ENTRIES =
		    "CREATE TABLE " + MapEntry.TABLE_NAME + " (" +
		    		MapEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
		    		MapEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
		    		MapEntry.COLUMN_NAME_PROJECTID + TEXT_TYPE + COMMA_SEP +
		    		MapEntry.COLUMN_NAME_FILE + TEXT_TYPE +
		    		" )";
	private static final String SQL_CREATE_ACCESSPOINT_ENTRIES =
		    "CREATE TABLE " + AccessPointEntry.TABLE_NAME + " (" +
		    		AccessPointEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
		    		AccessPointEntry.COLUMN_NAME_MACADDRESS + TEXT_TYPE +
		    		AccessPointEntry.COLUMN_NAME_SSID + TEXT_TYPE +
		    		AccessPointEntry.COLUMN_NAME_DESC + TEXT_TYPE +
		    		AccessPointEntry.COLUMN_NAME_PROJECTID + TEXT_TYPE +
		    		" )";
	private static final String SQL_CREATE_LOCATION_ENTRIES =
		    "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
		    		ProjectEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
		    		ProjectEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
		    		" )";
	private static final String SQL_CREATE_FINGERPRINT_ENTRIES =
		    "CREATE TABLE " + FingerprintEntry.TABLE_NAME + " (" +
		    		FingerprintEntry.COLUMN_NAME_SIGNALSTRENGTH + TEXT_TYPE + COMMA_SEP +
		    		FingerprintEntry.COLUMN_NAME_LOCATIONID + TEXT_TYPE + COMMA_SEP +
		    		FingerprintEntry.COLUMN_NAME_ACCESSPOINTID + TEXT_TYPE + COMMA_SEP +
		    		FingerprintEntry.COLUMN_NAME_RECORDTIME + TEXT_TYPE + COMMA_SEP +
		    		" )";
	
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + ProjectEntry.TABLE_NAME;

	
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Localization.db";

    public LocalizationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROJECT_ENTRIES);
        db.execSQL(SQL_CREATE_MAP_ENTRIES);
        db.execSQL(SQL_CREATE_ACCESSPOINT_ENTRIES);
        db.execSQL(SQL_CREATE_LOCATION_ENTRIES);
        db.execSQL(SQL_CREATE_FINGERPRINT_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
