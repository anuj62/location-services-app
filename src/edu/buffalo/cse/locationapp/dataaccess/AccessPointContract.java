package edu.buffalo.cse.locationapp.dataaccess;

import android.provider.BaseColumns;

public final class AccessPointContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AccessPointContract() {}

    /* Inner class that defines the table contents */
    public static abstract class AccessPointEntry implements BaseColumns {
        public static final String TABLE_NAME = "AccessPoint";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_MACADDRESS = "macaddress";
        public static final String COLUMN_NAME_SSID = "ssid";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_PROJECTID = "projectid";
    }
}