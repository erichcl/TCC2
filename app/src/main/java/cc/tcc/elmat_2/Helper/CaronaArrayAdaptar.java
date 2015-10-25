package cc.tcc.elmat_2.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cc.tcc.elmat_2.R;
import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.model.RIDE;

/**
 * Created by erich on 25/10/2015.
 */
public class CaronaArrayAdaptar  extends ArrayAdapter<Ride> {
    private ArrayList<Ride> rides;

    public CaronaArrayAdaptar(Context context, int textViewResourceId, ArrayList<Ride> objects) {
        super(context, textViewResourceId, objects);
        this.rides = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.carona_item_layout, null);
        }

        Ride r = rides.get(position);

        TextView text1 = (TextView)v.findViewById(R.id.text1);
        TextView text2 = (TextView)v.findViewById(R.id.text2);

        if (text1 != null)
        {
            text1.setText(r.usr.Name);
        }
        if (text2 != null)
        {
            text2.setText("CARONA");
        }

        return v;
    }
}
