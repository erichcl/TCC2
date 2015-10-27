package cc.tcc.elmat_2.messages;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by erich on 23/10/2015.
 */
public class Ride {
    public int RideID;
    public User usr;
    public Date Hour;
    public double LatOrigem;
    public double LonOrigem;
    public double LatDestino;
    public double LonDestino;
    public int classOrg;
    public double distanciaOrg;
    public int classDes;
    public double distanciaDes;
}
