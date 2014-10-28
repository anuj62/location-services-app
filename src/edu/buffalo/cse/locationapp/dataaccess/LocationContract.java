package edu.buffalo.cse.locationapp.dataaccess;

import android.provider.BaseColumns;

public final class LocationContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public LocationContract() {}

    /* Inner class that defines the table contents */
    public static abstract class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "Location";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_XLOC = "xlocation";
        public static final String COLUMN_NAME_YLOC = "ylocation";
        public static final String COLUMN_NAME_MAPID = "mapid";
        public static final String COLUMN_NAME_PROJECTID = "projectid";
    }
}