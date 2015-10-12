package cc.tcc.elmat_2.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cc.tcc.elmat_2.model.TABLEBUILDER.USER_TABLE;
import cc.tcc.elmat_2.model.USER;

/**
 * Created by Erich on 11/10/2015.
 */
public class ELMATDbHelper extends SQLiteOpenHelper {

    private static ELMATDbHelper objDbHelper;


    // Tables to initialize
    private USER_TABLE user_table = new USER_TABLE();

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ELMAT.db";

    private ELMATDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ELMATDbHelper getInstance(Context context)
    {
        if (objDbHelper == null)
        {
            objDbHelper = new ELMATDbHelper(context);
        }
        return objDbHelper;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(user_table.CREATE_TABLE());
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(user_table.DROP_TABLE());
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}