package uk.ac.stir.cs.yh.unitconvassignment;

import android.provider.BaseColumns;

/**
 * A contract class that allows databases to be defined and referenced elsewhere within the project
 * Web tutorial used and adapted from: https://developer.android.com/training/data-storage/sqlite
 */
public final class FeedReaderContract {
    private FeedReaderContract() {
    }

    /**
     * This inner class method of contracting databases provides a modular, extensible approach to database creation
     * Although we only require a singular database for this project, good practise is implemented here
     */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Conversions";
        public static final String COLUMN_NAME_UNIT_FROM = "unitFrom";
        public static final String COLUMN_NAME_UNIT_TO = "unitTo";
        public static final String COLUMN_NAME_RATIO = "ratio";
    }
}
