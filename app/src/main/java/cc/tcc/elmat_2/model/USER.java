package cc.tcc.elmat_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by erich on 10/10/2015.
 */
public class USER {
    private static final String myTableName = "USER";
    private Context ctx;

    public int UserID;
    public double FacebookID;
    public String Name;

    //private

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public double getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(double facebookID) {
        FacebookID = facebookID;
    }

    public USER(int userID, double facebookID, Context context) {
        UserID = userID;
        FacebookID = facebookID;
        ctx = context;
    }

    public USER(int userID, double facebookID, String name, Context context) {
        UserID = userID;
        FacebookID = facebookID;
        Name = name;
        ctx = context;
    }


    public USER(Context context) {
        ctx = context;
    }

    public boolean DbInsertMe()
    {
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID", UserID);
        contentValues.put("FacebookID", FacebookID);
        contentValues.put("Name", Name);
        db.insert(myTableName, null, contentValues);
        return true;
    }


    private Cursor getSingle(){
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(ctx);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"UserID", "FacebookID", "Name"};
        //Cursor res =  db.rawQuery("select * from contacts where id=" + id + "", null);
        String whereClause = "UserID = ?";
        String[] whereArgs = new String[] {
                String.valueOf(UserID)
        };
        Cursor res =  db.query(myTableName, columns, whereClause, whereArgs, null, null, null, "1");
        return res;
    }

    public USER DBgetMe()
    {
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(ctx);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"UserID", "FacebookID", "Name"};
        Cursor crs = db.query(myTableName, columns, null, null, null, null, null, "1");
        if (crs.moveToFirst()) // data?
        {
            String ID, FBID, NM;
            ID = crs.getString(crs.getColumnIndex("UserID"));
            FBID = crs.getString(crs.getColumnIndex("FacebookID"));
            NM = crs.getString(crs.getColumnIndex("Name"));
            UserID = Integer.parseInt(ID);
            FacebookID = Double.parseDouble(FBID);
            Name = NM;
            return this;
        }
        else
            return null;
    }

    public static USER getUser (Context context)
    {
        Cursor crs = getSingle(context);
        USER usr;
        if (crs.moveToFirst()) // data?
        {
            String ID, FBID, NM;
            int UsrID;
            double FcbkID;
            ID = crs.getString(crs.getColumnIndex("UserID"));
            FBID = crs.getString(crs.getColumnIndex("FacebookID"));
            NM = crs.getString(crs.getColumnIndex("Name"));
            UsrID = Integer.parseInt(ID);
            FcbkID = Double.parseDouble(FBID);
            usr = new USER(UsrID, FcbkID, context);
            usr.Name = NM;
            return usr;
        }
        else
            return null;
    }

    private static Cursor getSingle(Context context){
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"UserID", "FacebookID", "Name"};
//        String whereClause = "UserID = ?";
//        String[] whereArgs = new String[] {
//                String.valueOf(id)
//        };
        Cursor res =  db.query(myTableName, columns, null, null, null, null, null, "1");
        return res;
    }
}
