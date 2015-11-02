package cc.tcc.elmat_2.messages;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by erich on 31/10/2015.
 */
public class Routine implements Serializable {
    public int RoutineID;
    public String Title;
    public boolean Mon;
    public boolean Tue;
    public boolean Wed;
    public boolean Thu;
    public boolean Fri;
    public boolean Sat;
    public boolean Sun;
    public Date Hour;
    public double Latitude;
    public double Longitude;
}
