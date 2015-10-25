package cc.tcc.elmat_2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cc.tcc.elmat_2.model.RIDE;
import cc.tcc.elmat_2.model.USER;


public class CaronasActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caronas2);
        USER usr = USER.getUser(this);

        //ArrayList<RIDE> rides = RIDE.SolicitationRides(usr.UserID, this);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        setListAdapter(arrayAdapter);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent data = new Intent();
        data.putExtra("myRideID", l.getItemAtPosition(position).toString());
        l.getItemAtPosition(position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }
}
