package cc.tcc.elmat_2.messages;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by erich on 23/10/2015.
 */
public class GeoPoint {
    private boolean isValid;
    public double Latitude;
    public double Longitude;

    public GeoPoint(LatLng loc)
    {   this.isValid = false;
        if (loc != null)
        {
            this.isValid = true;
            this.Latitude = loc.latitude;
            this.Longitude = loc.longitude;
        }
    }

    public boolean isValid()
    {
        return isValid;
    }
}

