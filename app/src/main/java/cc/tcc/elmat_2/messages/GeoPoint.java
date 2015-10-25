package cc.tcc.elmat_2.messages;

import android.location.Location;

/**
 * Created by erich on 23/10/2015.
 */
public class GeoPoint {
    private boolean isValid;
    public double Latitude;
    public double Longitude;

    public GeoPoint(Location loc)
    {   this.isValid = false;
        if (loc != null)
        {
            this.isValid = true;
            this.Latitude = loc.getLatitude();
            this.Longitude = loc.getLongitude();
        }
    }

    public boolean isValid()
    {
        return isValid;
    }
}

