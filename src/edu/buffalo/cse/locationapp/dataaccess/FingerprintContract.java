package edu.buffalo.cse.locationapp.dataaccess;

import android.provider.BaseColumns;

public final class FingerprintContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FingerprintContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FingerprintEntry implements BaseColumns {
        public static final String TABLE_NAME = "Fingerprint";
        public static final String COLUMN_NAME_SIGNALSTRENGTH = "signalstrength";
        public static final String COLUMN_NAME_LOCATIONID = "locationid";
        public static final String COLUMN_NAME_ACCESSPOINTID = "accesspointid";
        public static final String COLUMN_NAME_RECORDTIME = "recordtime";
    }
}