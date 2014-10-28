package edu.buffalo.cse.locationapp.dataaccess;

import android.provider.BaseColumns;

public final class MapContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MapContract() {}

    /* Inner class that defines the table contents */
    public static abstract class MapEntry implements BaseColumns {
        public static final String TABLE_NAME = "Map";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_FILE = "file";
        public static final String COLUMN_NAME_PROJECTID = "projectid";
    }
}