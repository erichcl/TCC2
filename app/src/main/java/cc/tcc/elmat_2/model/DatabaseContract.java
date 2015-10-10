package cc.tcc.elmat_2.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by erich on 10/10/2015.
 */
public final class  DatabaseContract {
    public DatabaseContract(){}

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BIGINT_TYPE = " BIGINT";
    private static final String COMMA_SEP = ",";

    private static class ColumnType
    {
        public static String ColumnName;
        public static String ColumnType;

        public ColumnType() {
        }

        public ColumnType(String name, String type) {
            this.ColumnName = name;
            this.ColumnType = type;
        }
    }

    private static class TABLE_BUILDER
    {
        public static String TABLE_NAME;
        public static LinkedList<ColumnType> Columns;
    }

    private static String gera_CREATE_TABLE(TABLE_BUILDER TB)
    {
        String SQL = "CREATE TABLE " +TB.TABLE_NAME + " (";
        for (ColumnType C: TB.Columns)
        {
            SQL += C.ColumnName + C.ColumnType + COMMA_SEP;
        }
        SQL = SQL.substring(0, SQL.length()-1);
        SQL += " )";
        return SQL;
    }

    private static String gera_DROP_TABLE(TABLE_BUILDER TB)
    {
        String SQL = "DROP TABLE IF EXISTS " + TB.TABLE_NAME;
        return SQL;
    }

    public static class USER extends TABLE_BUILDER implements BaseColumns {

        private static void init()
        {
            TABLE_NAME = "USER";
            LinkedList<ColumnType> lista = new LinkedList<ColumnType>();
            lista.add(new ColumnType("UserID", INTEGER_TYPE));
            lista.add(new ColumnType("FacebookID", BIGINT_TYPE));
            Columns = lista;
        }

        public static String CREATE_TABLE()
        {
            USER usr = new USER();
            return gera_CREATE_TABLE(usr);
        }

        public USER() {
            TABLE_NAME = "USER";
            LinkedList<ColumnType> lista = new LinkedList<ColumnType>();
            lista.add(new ColumnType("UserID", INTEGER_TYPE));
            lista.add(new ColumnType("FacebookID", BIGINT_TYPE));
            Columns = lista;
        }

    }

//    public class FeedReaderDbHelper extends SQLiteOpenHelper {
//        // If you change the database schema, you must increment the database version.
//
//        // Logcat tag
//        private static final String LOG = "DatabaseHelper";
//
//        public static final int DATABASE_VERSION = 1;
//        public static final String DATABASE_NAME = "ELMAT.db";
//
//        public FeedReaderDbHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(USER.CREATE_TABLE());
//        }
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // This database is only a cache for online data, so its upgrade policy is
//            // to simply to discard the data and start over
//            db.execSQL(USER.DROP_TABLE());
//            onCreate(db);
//        }
//        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            onUpgrade(db, oldVersion, newVersion);
//        }
//    }
}
