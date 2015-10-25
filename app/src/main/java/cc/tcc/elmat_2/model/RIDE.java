package cc.tcc.elmat_2.model;

import java.util.Date;

import cc.tcc.elmat_2.messages.ClassifiCarona;
import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.messages.User;

/**
 * Created by erich on 10/10/2015.
 */
public class RIDE {
    private static final String myTableName = "RIDE";

    public int RideID;
    public int UserID;
    public Date Hour;
    public double LatOrg;
    public double LonOrg;
    public double LatDest;
    public double LonDest;
    public int classOrg;
    public double distanciaOrg;
    public int classDes;
    public double distanciaDes;

    public RIDE(int userID, Date hour, double latOrg, double lonOrg, double latDest, double lonDest) {
        UserID = userID;
        Hour = hour;
        LatOrg = latOrg;
        LonOrg = lonOrg;
        LatDest = latDest;
        LonDest = lonDest;
    }

    public RIDE(Ride r)
    {
        RideID = r.RideID;
        UserID = r.usr.UserID;
        Hour = r.Hour;
        LatOrg = r.LatOrigem;
        LonOrg = r.LonOrigem;
        LatDest = r.LatDestino;
        LonDest = r.LonDestino;
        classOrg = r.classOrg.getValue();
        distanciaOrg = r.distanciaOrg;
        classDes = r.classDes.getValue();
        distanciaDes = r.distanciaDes;
    }
}
