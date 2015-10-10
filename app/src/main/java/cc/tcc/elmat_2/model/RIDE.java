package cc.tcc.elmat_2.model;

import java.util.Date;

/**
 * Created by erich on 10/10/2015.
 */
public class RIDE {
    private int RideID;
    private int UserID;
    private Date Hour;
    private double LatOrg;
    private double LonOrg;
    private double LatDest;
    private double LonDest;
    private int DriverID;

    public int getRideID() {
        return RideID;
    }

    public void setRideID(int rideID) {
        RideID = rideID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public Date getHour() {
        return Hour;
    }

    public void setHour(Date hour) {
        Hour = hour;
    }

    public double getLatOrg() {
        return LatOrg;
    }

    public void setLatOrg(double latOrg) {
        LatOrg = latOrg;
    }

    public double getLonOrg() {
        return LonOrg;
    }

    public void setLonOrg(double lonOrg) {
        LonOrg = lonOrg;
    }

    public double getLatDest() {
        return LatDest;
    }

    public void setLatDest(double latDest) {
        LatDest = latDest;
    }

    public double getLonDest() {
        return LonDest;
    }

    public void setLonDest(double lonDest) {
        LonDest = lonDest;
    }

    public int getDriverID() {
        return DriverID;
    }

    public void setDriverID(int driverID) {
        DriverID = driverID;
    }

    public RIDE(int userID, Date hour, double latOrg, double lonOrg, double latDest, double lonDest) {
        UserID = userID;
        Hour = hour;
        LatOrg = latOrg;
        LonOrg = lonOrg;
        LatDest = latDest;
        LonDest = lonDest;
    }
}
