package cc.tcc.elmat_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import cc.tcc.elmat_2.messages.ClassifiCarona;
import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.messages.User;

/**
 * Created by erich on 10/10/2015.
 */
public class RIDE {
    private static final String myTableName = "RIDE";
    private Context ctx;

    public int RideID;
    public int UserID;
    public String UserName;
    public Date Hour;
    public double LatOrg;
    public double LonOrg;
    public double LatDest;
    public double LonDest;
    public int classOrg;
    public double distanciaOrg;
    public int classDes;
    public double distanciaDes;

    public RIDE(int userID, Date hour, double latOrg, double lonOrg, double latDest, double lonDest, Context context) {
        UserID = userID;
        Hour = hour;
        LatOrg = latOrg;
        LonOrg = lonOrg;
        LatDest = latDest;
        LonDest = lonDest;
        ctx = context;
    }

    public RIDE(Ride r, Context context)
    {
        RideID = r.RideID;
        UserID = r.usr.UserID;
        UserName = r.usr.Name;
        Hour = r.Hour;
        LatOrg = r.LatOrigem;
        LonOrg = r.LonOrigem;
        LatDest = r.LatDestino;
        LonDest = r.LonDestino;
        classOrg = r.classOrg.getValue();
        distanciaOrg = r.distanciaOrg;
        classDes = r.classDes.getValue();
        distanciaDes = r.distanciaDes;
        ctx = context;
    }

    public RIDE(Context context)
    {
        ctx = context;
    }

    public boolean DbInsertMe()
    {
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RideID",RideID);
        contentValues.put("UserID",UserID);
        contentValues.put("UserName",UserName);
        contentValues.put("Hour",Hour.getTime());
        contentValues.put("LatOrg",LatOrg);
        contentValues.put("LonOrg" ,LonOrg);
        contentValues.put("LatDest",LatDest);
        contentValues.put("LonDest",LonDest);
        contentValues.put("classOrg",classOrg);
        contentValues.put("distanciaOrg",distanciaOrg);
        contentValues.put("classDes",classDes);
        contentValues.put("distanciaDes",distanciaDes);
        db.insert(myTableName, null, contentValues);
        return true;
    }

    public static ArrayList<RIDE> UserRides (int UserID, Context context)
    {
        Cursor crs = getRidesByUser(UserID, context);
        ArrayList<RIDE> rides = getRidesFromCursor(crs, context);
        return rides;
    }

    public static ArrayList<RIDE> SolicitationRides (int UserID, Context context)
    {
        Cursor crs = getRidesSolicitations(UserID, context);
        ArrayList<RIDE> rides = getRidesFromCursor(crs, context);
        return rides;
    }


    private static Cursor getRidesSolicitations(int UserID, Context context){
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"RideID","UserID","UserName","Hour","LatOrg","LonOrg","LatDest","LonDest","classOrg","distanciaOrg","classDes","distanciaDes"};
        String whereClause = "UserID != ?";
        String[] whereArgs = new String[] {
                String.valueOf(UserID)
        };
        Cursor res =  db.query(myTableName, columns, whereClause, whereArgs, null, null, null, null);
        return res;
    }

    private static Cursor getRidesByUser(int UserID, Context context){
        ELMATDbHelper dbHelper = ELMATDbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"RideID","UserID","UserName","Hour","LatOrg","LonOrg","LatDest","LonDest","classOrg","distanciaOrg","classDes","distanciaDes"};
        String whereClause = "UserID = ?";
        String[] whereArgs = new String[] {
                String.valueOf(UserID)
        };
        Cursor res =  db.query(myTableName, columns, whereClause, whereArgs, null, null, null, null);
        return res;
    }

    private static ArrayList<RIDE>  getRidesFromCursor(Cursor crs, Context context)
    {
        ArrayList<RIDE> rides = new ArrayList<RIDE>();
        if (crs.moveToFirst()) // data?
        {
            do
            {
                int sRideID = Integer.parseInt(crs.getString(crs.getColumnIndex("RideID")));
                int sUserID = Integer.parseInt(crs.getString(crs.getColumnIndex("UserID")));
                String sUserName  = crs.getString(crs.getColumnIndex("UserName"));
                Date sHour = new Date(Long.parseLong(crs.getString(crs.getColumnIndex("Hour"))));
                double sLatOrg = Double.parseDouble(crs.getString(crs.getColumnIndex("LatOrg")));
                double sLonOrg = Double.parseDouble(crs.getString(crs.getColumnIndex("LonOrg")));
                double sLatDest = Double.parseDouble(crs.getString(crs.getColumnIndex("LatDest")));
                double sLonDest = Double.parseDouble(crs.getString(crs.getColumnIndex("LonDest")));
                int sclassOrg = Integer.parseInt(crs.getString(crs.getColumnIndex("classOrg")));
                double sdistanciaOrg = Double.parseDouble(crs.getString(crs.getColumnIndex("distanciaOrg")));
                int sclassDes = Integer.parseInt(crs.getString(crs.getColumnIndex("classDes")));
                double sdistanciaDes = Double.parseDouble(crs.getString(crs.getColumnIndex("distanciaDes")));

                RIDE sRide = new RIDE(sUserID, sHour, sLatOrg,sLonOrg,sLatDest,sLonDest,context);
                sRide.RideID = sRideID;
                sRide.classOrg = sclassOrg;
                sRide.classDes = sclassDes;
                sRide.distanciaDes = sdistanciaDes;
                sRide.distanciaOrg = sdistanciaOrg;
                sRide.UserName = sUserName;

                rides.add(sRide);
            }
            while (crs.moveToNext());
            return rides;
        }
        else
            return null;
    }

}
