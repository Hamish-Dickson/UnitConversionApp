package uk.ac.stir.cs.yh.unitconvassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * The main database class that provides functionality for working with the database
 * Web tutorial used and adapted from: https://developer.android.com/training/data-storage/sqlite
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    /**
     * SQL statement to create our database
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO + " REAL)";

    /**
     * SQL statement to delete our database
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "YHDatabase.db";

    /**
     * Helper constructor
     *
     * @param context application context, used to locate paths to the the database
     */
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method is called only when the database is created for the first time
     * Prevents duplicate rows from being inserted as this is only called if no existing database exists
     *
     * @param db the database instance to use
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        initializeDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * @param db       database to be used
     * @param unitFrom the String name of the unit to be converted from
     * @param unitTo   the String name of the unit to be converted To
     * @return the ratio of conversion between the two specified units
     */
    public double getRatio(SQLiteDatabase db, String unitFrom, String unitTo) {
        double ratio;

        //Simple SQL statement that selects rows that match unitFrom and unitTo
        SQLiteStatement statement = db.compileStatement("SELECT " + FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO +
                " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME +
                " WHERE " + FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM + " == '" + unitFrom + "' AND " +
                FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO + " == '" + unitTo + "';");
        String result = statement.simpleQueryForString();

        ratio = Double.valueOf(result);//must convert to a double as a string is returned from the database
        return ratio;
    }

    /**
     * This method populates the database with the required rows
     *
     * @param db the database to be used
     */
    private void initializeDB(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        /*
        set up distance rates
         */
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Kilometres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Miles");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.621");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Kilometres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Metres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1000");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Kilometres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Yards");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1093.61");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Miles");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Kilometres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1.609");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Miles");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Metres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1609.34");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Miles");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Yards");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1760");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Metres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Kilometres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.001");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Metres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Miles");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.0006");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Metres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Yards");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1.09361");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Yards");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Kilometres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.0009");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Yards");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Miles");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.00057");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Yards");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Metres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.9144");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        /*
        set up weight rates
         */
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Kilograms");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Pounds");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "2.205");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Kilograms");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Grams");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1000");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Kilograms");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Stone");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.157");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Pounds");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Kilograms");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.453592");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Pounds");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Grams");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "453.592");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Pounds");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Stone");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.0714");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Grams");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Pounds");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.0022");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Grams");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Kilograms");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.001");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Grams");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Stone");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.000157");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Stone");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Pounds");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "14");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Stone");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Grams");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "6350.29");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Stone");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Kilograms");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "6.35029");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        /*
        set up volume rates
         */
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Litres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Millilitres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1000");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Litres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Pints");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1.76");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Litres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Gallons");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.264172");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Millilitres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Litres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.001");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Millilitres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Pints");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.00175975");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Millilitres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Gallons");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.000264172");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Pints");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Litres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.568");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Pints");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Millilitres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "568.261");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Pints");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Gallons");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.150");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Gallons");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Litres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "4.54609");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Gallons");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Pints");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "6.66139");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Gallons");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Millilitres");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "3785.41");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        /*
        set up currency rates
         */
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "GBP (£)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Euros (€)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1.16");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "GBP (£)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "USD ($)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1.28");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "GBP (£)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "CNY (¥)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "9.03");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Euros (€)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "GBP (£)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.86");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Euros (€)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "USD ($)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "1.10");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "Euros (€)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "CNY (¥)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "7.76");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "USD ($)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "GBP (£)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.78");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "USD ($)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Euros (€)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.91");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "USD ($)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "CNY (¥)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "7.04");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "CNY (¥)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "GBP (£)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.11");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "CNY (¥)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "USD ($)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.14");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_FROM, "CNY (¥)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_UNIT_TO, "Euros (€)");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATIO, "0.13");
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }
}
