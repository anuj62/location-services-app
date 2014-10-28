package edu.buffalo.cse.locationapp.dataaccess;

import android.provider.BaseColumns;

public final class ProjectContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ProjectContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "Project";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}