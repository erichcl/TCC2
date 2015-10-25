package cc.tcc.elmat_2.messages;

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
    public ClassifiCarona classOrg;
    public double distanciaOrg;
    public ClassifiCarona classDes;
    public double distanciaDes;
}
