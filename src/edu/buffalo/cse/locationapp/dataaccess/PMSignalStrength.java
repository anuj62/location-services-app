package edu.buffalo.cse.locationapp.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import edu.buffalo.cse.locationapp.dataaccess.AccessPointContract.AccessPointEntry;
import edu.buffalo.cse.locationapp.dataaccess.FingerprintContract.FingerprintEntry;
import edu.buffalo.cse.locationapp.entity.Fingerprint;
import edu.buffalo.cse.locationapp.entity.Location;

public class PMSignalStrength extends PersistencyManager {
	
	private PMLocation pmLocation = null;
	private PMAccessPoint pmAccessPoint = null;

	public PMSignalStrength(Context context) {
		super(context);
	}

	public boolean addFingerprint(Fingerprint fingerprint) {
		boolean result = false;
    	
    	// Create a new map of values, where column names are the keys
    	ContentValues values = new ContentValues();
    	values.put(FingerprintEntry.COLUMN_NAME_ACCESSPOINTID, getAccessPointID(fingerprint.getMacAddress()));
    	values.put(FingerprintEntry.COLUMN_NAME_LOCATIONID, getLocationID(fingerprint.getLocation()));
    	values.put(FingerprintEntry.COLUMN_NAME_RECORDTIME, fingerprint.getTimestamp());
    	values.put(FingerprintEntry.COLUMN_NAME_SIGNALSTRENGTH, fingerprint.getLevel());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	
    	try {
    		newRowId = writableDB.insert(
    				FingerprintEntry.TABLE_NAME,
    				null,
    				values);
    		result = true;
    	} catch (Exception ex) {
    		Log.v("PMSignalStrength", ex.getMessage());
    		result = false;
    	}

        return result;
	}

	private int getLocationID(Location location) {
		pmLocation = PMFactory.PMLocation();
		
		return pmLocation.getLocationID(location.getXLocation(), location.getYLocation(), 1);
	}

	private int getAccessPointID(String macAddress) {
		pmAccessPoint = PMFactory.PMAccessPoint();
		
		return pmAccessPoint.GetAccessPoint(macAddress).getID();
	}

}
