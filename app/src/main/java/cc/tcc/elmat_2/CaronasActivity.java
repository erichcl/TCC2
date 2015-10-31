package cc.tcc.elmat_2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import cc.tcc.elmat_2.ArrayAdapter.CaronaArrayAdaptar;
import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;


public class CaronasActivity extends ListActivity {
    USER usr = USER.getUser(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caronas2);


        Intent intent = getIntent();

        double LatOrg = intent.getExtras().getDouble("LatOrg");
        double LonOrg = intent.getExtras().getDouble("LonOrg");

        LatLng origem = new LatLng(LatOrg, LonOrg);
        LatLng destino = null;
        if (intent.getExtras().getBoolean("TemDestino"))
        {
            double LatDest = intent.getExtras().getDouble("LatDest");
            double LonDest = intent.getExtras().getDouble("LonDest");
            destino = new LatLng(LatDest, LonDest);
        }

        ArrayList<Ride> rides = ListaCaronas(origem, destino);

        ArrayAdapter<Ride> arrayAdapter = new CaronaArrayAdaptar(
                this,
                R.layout.carona_item_layout,
                rides );

        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final Ride item = (Ride)l.getItemAtPosition(position);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Atender essa carona?");
        alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent data = new Intent();
                AtendeCarona(item);
                data.putExtra("cLatOrg", item.LatOrigem);
                data.putExtra("cLonOrg", item.LonOrigem);
                data.putExtra("cLatDes", item.LatDestino);
                data.putExtra("cLonDes", item.LonDestino);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private ArrayList<Ride> ListaCaronas(LatLng origem, LatLng destino)
    {
        USER modelUser = usr;
        User msgUser = new User();
        msgUser.UserID = modelUser.getUserID();
        msgUser.FacebookID = Math.round(modelUser.getFacebookID());
        ArrayList<Ride> myRides = UserService.callListaCaronas(this, msgUser, origem, destino);
        return myRides;
    }

    private void AtendeCarona(Ride r)
    {
        USER modelUser = usr;
        User msgUser = new User();
        msgUser.UserID = modelUser.getUserID();
        msgUser.FacebookID = Math.round(modelUser.getFacebookID());
        UserService.callAtendeCarona(this, msgUser, r);
    }
}
