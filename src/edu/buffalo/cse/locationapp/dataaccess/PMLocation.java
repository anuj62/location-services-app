package edu.buffalo.cse.locationapp.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import edu.buffalo.cse.locationapp.dataaccess.AccessPointContract.AccessPointEntry;
import edu.buffalo.cse.locationapp.dataaccess.LocationContract.LocationEntry;
import edu.buffalo.cse.locationapp.entity.AccessPoint;
import edu.buffalo.cse.locationapp.entity.Location;

public class PMLocation extends PersistencyManager{

	/**
	 * 
	 */
    public PMLocation(Context context)
    {
    	super(context);
    }
    
    public boolean AddLocation(Location location)
    {
    	boolean result = false;
    	
    	// Create a new map of values, where column names are the keys
    	ContentValues values = new ContentValues();
    	values.put(LocationEntry.COLUMN_NAME_ID, location.getID());
    	values.put(LocationEntry.COLUMN_NAME_XLOC, location.getXLocation());
    	values.put(LocationEntry.COLUMN_NAME_YLOC, location.getYLocation());
    	values.put(LocationEntry.COLUMN_NAME_MAPID, location.getMapID());
    	values.put(LocationEntry.COLUMN_NAME_PROJECTID, location.getProjectID());
    	

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	
    	try {
    		newRowId = writableDB.insert(
    				LocationEntry.TABLE_NAME,
    				null,
    				values);
    		result = true;
    	} catch (Exception ex) {
    		Log.v("PMLocation", ex.getMessage());
    		result = false;
    	}

        return result;
    }

	public int getLocationID(int xLocation, int yLocation, int mapID) {
		Cursor c = readableDB.rawQuery(
    			"select * from " + LocationEntry.TABLE_NAME
    			+ " where xLocation = " + xLocation
    			+ "and yLocation = " + yLocation
    			+ "and mapid = " + mapID, null);
    		    
    	c.moveToFirst();
    	while (c.isAfterLast() == false) {
    		c.getString(1); //result string
    	    c.moveToNext();
    	}
    	c.close(); //get results from c
		return 0;
	}
}
