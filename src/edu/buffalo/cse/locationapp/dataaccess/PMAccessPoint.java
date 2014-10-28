package edu.buffalo.cse.locationapp.dataaccess;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import edu.buffalo.cse.algorithm.knearestneighbor.AccessPoint;
import edu.buffalo.cse.locationapp.dataaccess.AccessPointContract.AccessPointEntry;

public class PMAccessPoint extends PersistencyManager
{	
	/**
	 * 
	 */
    public PMAccessPoint(Context context)
    {
    	super(context);
    }
    
    public boolean AddAccessPoint(AccessPoint ap)
    {
    	boolean result = false;
    	
    	// Create a new map of values, where column names are the keys
    	ContentValues values = new ContentValues();
    	values.put(AccessPointEntry.COLUMN_NAME_ID, ap.getID());
    	values.put(AccessPointEntry.COLUMN_NAME_SSID, ap.getSSID());
    	values.put(AccessPointEntry.COLUMN_NAME_MACADDRESS, ap.getMacAddress());
    	values.put(AccessPointEntry.COLUMN_NAME_PROJECTID, ap.getProjectID());
    	values.put(AccessPointEntry.COLUMN_NAME_DESC, ap.getDescription());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	
    	try {
    		newRowId = writableDB.insert(
    				AccessPointEntry.TABLE_NAME,
    				null,
    				values);
    		result = true;
    	} catch (Exception ex) {
    		Log.v("PMAccessPoint", ex.getMessage());
    		result = false;
    	}

        return result;
    }

    public AccessPoint GetAccessPoint(String macAddress)
    {
    	
    	//These are the columns that are going to be returned
    	String[] projection = {
    		    AccessPointEntry._ID,
    		    AccessPointEntry.COLUMN_NAME_SSID,
    		    AccessPointEntry.COLUMN_NAME_DESC
    	};
    	
    	//These are the columns that are going to be used in WHERE
    	String selection = AccessPointEntry.COLUMN_NAME_MACADDRESS;
    	
    	//These are the values of columns that are going to be used in WHERE
    	String[] selectionArgs = {
    		    macAddress
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder = AccessPointEntry.COLUMN_NAME_PROJECTID + " DESC";

    	Cursor c = readableDB.query(
    		    AccessPointEntry.TABLE_NAME,  // The table to query
    		    projection,                               // The columns to return
    		    selection,                                // The columns for the WHERE clause
    		    selectionArgs,                            // The values for the WHERE clause
    		    null,                                     // don't group the rows
    		    null,                                     // don't filter by row groups
    		    sortOrder                                 // The sort order
    	);
    
    	c.moveToFirst();
    	while (c.isAfterLast() == false) {
    		c.getString(1); //result string
    	    c.moveToNext();
    	}
    	c.close(); //get results from c
		return null;
    }

    public AccessPoint GetAccessPoint(String macAddress, int project)
    {
    	//These are the columns that are going to be returned
    	String[] projection = {
    		    AccessPointEntry._ID,
    		    AccessPointEntry.COLUMN_NAME_SSID,
    		    AccessPointEntry.COLUMN_NAME_DESC
    	};
    	
    	//These are the columns that are going to be used in WHERE
    	String selection = AccessPointEntry.COLUMN_NAME_MACADDRESS;
    	
    	//These are the values of columns that are going to be used in WHERE
    	String[] selectionArgs = {
    		    macAddress
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder = AccessPointEntry.COLUMN_NAME_PROJECTID + " DESC";

    		Cursor c = readableDB.query(
    		    AccessPointEntry.TABLE_NAME,  // The table to query
    		    projection,                               // The columns to return
    		    selection,                                // The columns for the WHERE clause
    		    selectionArgs,                            // The values for the WHERE clause
    		    null,                                     // don't group the rows
    		    null,                                     // don't filter by row groups
    		    sortOrder                                 // The sort order
    		    );
			return null;
    
    		//c. //get results from c
    }

    public AccessPoint GetAccessPoint(int apID)
    {
    	//These are the columns that are going to be returned
    	String[] projection = {
    		    AccessPointEntry._ID,
    		    AccessPointEntry.COLUMN_NAME_SSID,
    		    AccessPointEntry.COLUMN_NAME_DESC
    	};
    	
    	//These are the columns that are going to be used in WHERE
    	String selection = AccessPointEntry.COLUMN_NAME_MACADDRESS;
    	
    	//These are the values of columns that are going to be used in WHERE
    	String[] selectionArgs = {
    		    //macAddress
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder = AccessPointEntry.COLUMN_NAME_PROJECTID + " DESC";

    		Cursor c = readableDB.query(
    		    AccessPointEntry.TABLE_NAME,  // The table to query
    		    projection,                               // The columns to return
    		    selection,                                // The columns for the WHERE clause
    		    selectionArgs,                            // The values for the WHERE clause
    		    null,                                     // don't group the rows
    		    null,                                     // don't filter by row groups
    		    sortOrder                                 // The sort order
    		    );
    
    		//c. //get results from c
    		return null;
    }

    public ArrayList<AccessPoint> GetAccessPointList()
    {
    	//These are the columns that are going to be returned
    	String[] projection = {
    		    AccessPointEntry._ID,
    		    AccessPointEntry.COLUMN_NAME_SSID,
    		    AccessPointEntry.COLUMN_NAME_DESC
    	};
    	
    	//These are the columns that are going to be used in WHERE
    	String selection = AccessPointEntry.COLUMN_NAME_MACADDRESS;
    	
    	//These are the values of columns that are going to be used in WHERE
    	String[] selectionArgs = {
    		//    macAddress
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder = AccessPointEntry.COLUMN_NAME_PROJECTID + " DESC";

    		Cursor c = readableDB.query(
    		    AccessPointEntry.TABLE_NAME,  // The table to query
    		    projection,                               // The columns to return
    		    selection,                                // The columns for the WHERE clause
    		    selectionArgs,                            // The values for the WHERE clause
    		    null,                                     // don't group the rows
    		    null,                                     // don't filter by row groups
    		    sortOrder                                 // The sort order
    		    );
    
    		//c. //get results from c
    		return null;
    }
    
    public ArrayList<AccessPoint> GetAccessPointList(int projectID)
    {
    	//These are the columns that are going to be returned
    	String[] projection = {
    		    AccessPointEntry._ID,
    		    AccessPointEntry.COLUMN_NAME_SSID,
    		    AccessPointEntry.COLUMN_NAME_DESC
    	};
    	
    	//These are the columns that are going to be used in WHERE
    	String selection = AccessPointEntry.COLUMN_NAME_MACADDRESS;
    	
    	//These are the values of columns that are going to be used in WHERE
    	String[] selectionArgs = {
    		    //macAddress
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder = AccessPointEntry.COLUMN_NAME_PROJECTID + " DESC";

    		Cursor c = readableDB.query(
    		    AccessPointEntry.TABLE_NAME,  // The table to query
    		    projection,                               // The columns to return
    		    selection,                                // The columns for the WHERE clause
    		    selectionArgs,                            // The values for the WHERE clause
    		    null,                                     // don't group the rows
    		    null,                                     // don't filter by row groups
    		    sortOrder                                 // The sort order
    		    );
    
    		//c. //get results from c
    		return null;
    }

    public boolean DeleteAccessPoint(ArrayList<AccessPoint> ap)
    {
    	// Define 'where' part of query.
    	String selection = AccessPointEntry.COLUMN_NAME_ID + " LIKE ?";
    	// Specify arguments in placeholder order.
    	//todo
    	String[] selectionArgs = { String.valueOf(ap.get(0)) };
    	// Issue SQL statement.
    	writableDB.delete(AccessPointEntry.TABLE_NAME, selection, selectionArgs);
    	
    	return true;
    }

    
    public boolean DeleteAccessPoint(int apID)
    {
    	// Define 'where' part of query.
    	String selection = AccessPointEntry.COLUMN_NAME_ID + " LIKE ?";
    	// Specify arguments in placeholder order.
    	String[] selectionArgs = { String.valueOf(apID) };
    	// Issue SQL statement.
    	writableDB.delete(AccessPointEntry.TABLE_NAME, selection, selectionArgs);
    
    	return true;
    }

}
