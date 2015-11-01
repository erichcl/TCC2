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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

public class MainActivity extends AppCompatActivity implements RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    //region Variables
    protected GoogleMap map;
    protected LocationManager locationManager;
    protected long gpsCheckTime = 60000;

    private Marker myMarker = null;
    private Marker tempMarker = null;
    private Marker caronaOrigem = null;
    private Marker caronaDestino = null;

    private PlaceAutoCompleteAdapter mAdapter;
    private AutoCompleteTextView search_location;
    private ImageView send_button;
    protected GoogleApiClient mGoogleApiClient;

    private int RouteColor = -1;

    static final int PICK_RIDE_REQUEST = 1;  // The request code
    static final int NEW_ROUTINE_REQUEST = 2;  // The request code
    //endregion

    //region Listeners

    private View.OnClickListener sendBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tempMarker == null)
            {
                Toast.makeText(getApplicationContext(), "Encontre uma localização no campo texto", Toast.LENGTH_LONG).show();
            }
            else
            {
                // Cria e posiciona o marcador verde
                MarkerOptions mkOpt = new MarkerOptions();
                mkOpt.position(tempMarker.getPosition());
                mkOpt.title("Destino");
                tempMarker.remove();
                tempMarker = null;
                myMarker = map.addMarker(mkOpt);

                Location local = getBestLocation();
                LatLng currentLatLng = new LatLng(local.getLatitude(), local.getLongitude());
                RouteColor = Color.GREEN;
                TracaRota(currentLatLng, myMarker.getPosition());
            }

        }
    };

    private AdapterView.OnItemClickListener autoCompleteClick = new AdapterView.OnItemClickListener() {
        //Ação tomada ao escolher um lugar da lista de locais
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i("AUTOCOMPLETE", "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (!places.getStatus().isSuccess()) {
                        // Request did not complete successfully
                        Log.e("AUTOCOMPLETE", "Place query did not complete. Error: " + places.getStatus().toString());
                        places.release();
                        return;
                    }
                    // Get the Place object from the buffer.
                    final Place place = places.get(0);

                    if (myMarker != null)
                    {
                        myMarker.remove();
                        myMarker = null;
                    }
                    // Cria e posiciona o marcador verde
                    MarkerOptions mkOpt = new MarkerOptions();
                    mkOpt.position(place.getLatLng());
                    mkOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mkOpt.title("Destino");
                    tempMarker = map.addMarker(mkOpt);

                    //Leva a câmera até o lugar
                    CameraUpdate center = CameraUpdateFactory.newLatLng(place.getLatLng());
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                    map.moveCamera(center);
                    map.animateCamera(zoom);

                    //Fecha o teclado
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            });

        }
    };

    private TextWatcher autoCompleteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int startNum, int before, int count) {
            if (myMarker != null) {
                myMarker.remove();
                myMarker = null;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Limites contendo o RS (southwest, northeast)
        LatLngBounds BOUNDS_POA= new LatLngBounds(new LatLng(-32.224461, -57.647480), new LatLng(-27.169183, -49.363789));
        setContentView(R.layout.activity_user_svc);

        // Elementos da tela (textfield e botão de send)
        search_location = (AutoCompleteTextView)findViewById(R.id.search_location);
        send_button = (ImageView)findViewById(R.id.send_button);

        // inicializando o mapa
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        startLocationManager();
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(myLongClickListener);
        map.setOnMarkerClickListener(myMarkerListener);

        // Posicionando a câmera na localização atual
        Location local = getBestLocation();
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(local.getLatitude(), local.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        map.moveCamera(center);
        map.animateCamera(zoom);


        //Inicializando API de dados de localização (nomes dos lugares)
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(this);
        mGoogleApiClient.connect();

        // Adaptador que exibe a lista de lugares para o autocomplete
        mAdapter = new PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1, mGoogleApiClient, BOUNDS_POA, null);

        //Configurando a caixa de texto de busca para chamar os métodos de autocomplete
        search_location.setAdapter(mAdapter);
        search_location.setOnItemClickListener(autoCompleteClick);
        search_location.addTextChangedListener(autoCompleteTextWatcher);

        send_button.setOnClickListener(sendBtnClick);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mostraAmigos) {
            Intent intent = new Intent(this, FriendsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_mostraRotinas) {
            Intent intent = new Intent(this, RoutineActivity.class);
            startActivityForResult(intent, NEW_ROUTINE_REQUEST);
            return true;
        }
        if (id == R.id.action_addRotina) {

            if (myMarker != null) {
                Intent intent = new Intent(this, AddRoutineActivity.class);
                intent.putExtra("Latitude", myMarker.getPosition().latitude);
                intent.putExtra("Longitude", myMarker.getPosition().longitude);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "É necessário escolher um local para cadastrar a rotina!", Toast.LENGTH_LONG).show();
            }
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
        if (id == R.id.action_pedeCaronas) {
            if (myMarker == null)
            {
                Toast.makeText(getApplicationContext(), "É necessário escolher um destino para pedir carona!", Toast.LENGTH_LONG).show();
                return false;
            }
            Location local = getBestLocation();

            Ride r = new Ride();
            r.usr = new User(USER.getUser(this));
            r.LatOrigem = local.getLatitude();
            r.LonOrigem = local.getLongitude();
            r.LatDestino = myMarker.getPosition().latitude;
            r.LonDestino = myMarker.getPosition().longitude;
            boolean pediu = PedeCarona(r);
            if (pediu)
            {
                Toast.makeText(getApplicationContext(), "Carona solicitada com sucesso!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Não foi possível pedir carona!", Toast.LENGTH_LONG).show();
            }

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
                RouteColor = Color.BLUE;

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
        } else if (requestCode == NEW_ROUTINE_REQUEST)
        {
            Toast.makeText(getApplicationContext(), "Escolha um local e selecione a opção Nova Rotina", Toast.LENGTH_LONG).show();
        }
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
        if (RouteColor != -1)
        {
            polyoptions.color(RouteColor);
        }
        else
        {
            polyoptions.color(Color.BLUE);
        }
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.v("ConnectionFailed", connectionResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    //endregion

    //region Helper Methods
    private void startLocationManager() {
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

    private void TracaRota(LatLng... points) {
        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(points)
                .build();
        routing.execute();
    }

    private String getCityName(Location location) {
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

    private boolean PedeCarona(Ride ride)
    {
        return  UserService.callCadastraCarona(getApplicationContext(), ride);
    }

    //endregion


}
