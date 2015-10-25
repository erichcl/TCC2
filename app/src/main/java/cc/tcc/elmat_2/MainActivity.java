package cc.tcc.elmat_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.RIDE;
import cc.tcc.elmat_2.model.USER;

public class MainActivity extends AppCompatActivity implements RoutingListener {

    protected GoogleMap map;
    protected LocationManager locationManager;
    protected long gpsCheckTime = 60000;

    private Marker myMarker = null;
    private Marker caronaOrigem = null;
    private Marker caronaDestino = null;

    static final int PICK_RIDE_REQUEST = 1;  // The request code

    private GoogleMap.OnMarkerClickListener myMarkerListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.remove();
            return true;
        }
    };
    private GoogleMap.OnMapLongClickListener myLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            if (myMarker != null)
            {
                myMarker.remove();
                myMarker = null;
            }
            MarkerOptions mkOpt = new MarkerOptions();
            mkOpt.position(latLng);
            mkOpt.title("Destino");
            myMarker = map.addMarker(mkOpt);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_svc);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        startLocationManager();
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(myLongClickListener);
        map.setOnMarkerClickListener(myMarkerListener);

        Location local = getBestLocation();
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(local.getLatitude(), local.getLongitude()));
        //CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(18.015365, -77.499382));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        map.moveCamera(center);
        map.animateCamera(zoom);

    }

    private void startLocationManager()
    {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 5000, 0,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
//                            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
//                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
//
//                            map.moveCamera(center);
//                            map.animateCamera(zoom);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    3000, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
//                            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
//                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
//
//                            map.moveCamera(center);
//                            map.animateCamera(zoom);

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
        }
        catch(SecurityException ex){
            Log.d("startLocationManager", "Security Issue: "+ex.getMessage() );
        }

    }

    private Location getBestLocation() {
        try
        {
            String TAG = "getBestLocation";
            Location gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            // if we have only one location available, the choice is easy
            if (gpslocation == null) {
                Log.d(TAG, "No GPS Location available.");
                return networkLocation;
            }
            if (networkLocation == null) {
                Log.d(TAG, "No Network Location available");
                return gpslocation;
            }
            // a locationupdate is considered 'old' if its older than the configured
            // update interval. this means, we didn't get a
            // update from this provider since the last check
            long old = System.currentTimeMillis() -  gpsCheckTime;
            boolean gpsIsOld = (gpslocation.getTime() < old);
            boolean networkIsOld = (networkLocation.getTime() < old);
            // gps is current and available, gps is better than network
            if (!gpsIsOld) {
                Log.d(TAG, "Returning current GPS Location");
                return gpslocation;
            }
            // gps is old, we can't trust it. use network location
            if (!networkIsOld) {
                Log.d(TAG, "GPS is old, Network is current, returning network");
                return networkLocation;
            }
            // both are old return the newer of those two
            if (gpslocation.getTime() > networkLocation.getTime()) {
                Log.d(TAG, "Both are old, returning gps(newer)");
                return gpslocation;
            } else {
                Log.d(TAG, "Both are old, returning network(newer)");
                return networkLocation;
            }
        }
        catch(SecurityException ex)
        {
            Log.d("getBestLocation", "Security Issue: "+ex.getMessage() );
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_svc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            TesteRota();
            return true;
        }
        if (id == R.id.action_listaCaronas) {
            Location local = getBestLocation();
            Intent intent = new Intent(this, CaronasActivity.class);
            intent.putExtra("LatOrg", local.getLatitude());
            intent.putExtra("LonOrg", local.getLongitude());
            if (myMarker != null)
            {
                intent.putExtra("TemDestino", true);
                intent.putExtra("LatDest", myMarker.getPosition().latitude);
                intent.putExtra("LonDest", myMarker.getPosition().longitude);
            }
            else
            {
                intent.putExtra("TemDestino", false);
            }
            startActivityForResult(intent, PICK_RIDE_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == PICK_RIDE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                double cLatOrg = data.getExtras().getDouble("cLatOrg");
                double cLonOrg = data.getExtras().getDouble("cLonOrg");
                double cLatDes = data.getExtras().getDouble("cLatDes");
                double cLonDes = data.getExtras().getDouble("cLonDes");

                LatLng cOrigem = new LatLng(cLatOrg, cLonOrg);
                LatLng cDestino = new LatLng(cLatDes, cLonDes);

                MarkerOptions mkcOrigem = new MarkerOptions();
                MarkerOptions mkcDestino = new MarkerOptions();

                mkcOrigem.position(cOrigem);
                mkcDestino.position(cDestino);
                mkcOrigem.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                mkcDestino.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                caronaOrigem = map.addMarker(mkcOrigem);
                caronaDestino = map.addMarker(mkcDestino);

                Location local = getBestLocation();
                LatLng dOrigem = new LatLng(local.getLatitude(), local.getLongitude());

                if (myMarker != null)
                {
                    LatLng dDestino = new LatLng(myMarker.getPosition().latitude, myMarker.getPosition().longitude);
                    TracaRota(dOrigem, cOrigem, cDestino, dDestino);
                }
                else
                {
                    TracaRota(dOrigem, cOrigem, cDestino);
                }
            }
        }
    }

    private void TracaRota(LatLng... points)
    {
        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.WALKING)
                .withListener(this)
                .waypoints(points)
                .build();
        routing.execute();
    }

    private void TesteRota()
    {
        LatLng start = new LatLng(18.015365, -77.499382);
        LatLng waypoint= new LatLng(18.01455, -77.499333);
        LatLng end = new LatLng(18.012590, -77.500659);

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.WALKING)
                .withListener(this)
                .waypoints(start, waypoint, end)
                .build();
        routing.execute();
    }

    private String getCityName(Location location)
    {
        List<Address> addresses;
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName = addresses.get(0).getLocality();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    @Override
    public void onRoutingFailure() {
        Toast.makeText(getApplicationContext(), "Ocorreu um erro ao calcular a rota.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(PolylineOptions polylineOptions, Route route) {
        PolylineOptions polyoptions = new PolylineOptions();
        polyoptions.color(Color.BLUE);
        polyoptions.width(10);
        polyoptions.addAll(polylineOptions.getPoints());
        map.addPolyline(polyoptions);
    }

    @Override
    public void onRoutingCancelled() {

    }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Você tem certeza que deseja sair?");
        alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getApplicationContext(), "You clicked yes button", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
