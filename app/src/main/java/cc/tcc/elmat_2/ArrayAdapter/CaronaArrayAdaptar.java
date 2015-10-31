package cc.tcc.elmat_2.ArrayAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cc.tcc.elmat_2.R;
import cc.tcc.elmat_2.messages.Ride;

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

        TextView userName = (TextView)v.findViewById(R.id.listItemUserName);
        TextView OrgDist = (TextView)v.findViewById(R.id.listItemOrgDist);
        TextView OrgColor = (TextView)v.findViewById(R.id.listItemOrgColor);
        TextView DesDist = (TextView)v.findViewById(R.id.listItemDesDist);
        TextView DesColor = (TextView)v.findViewById(R.id.listItemDesColor);

        if (userName != null)
        {
            userName.setText(r.usr.Name);
        }
        if (OrgDist != null)
        {
            String dist = "Origem: " + String.format("%.2f", r.distanciaOrg) + " KM";
            OrgDist.setText(dist);
        }
        if (OrgColor != null)
        {
            int color;
            switch (r.classOrg)
            {
                case 0:
                    color = Color.GREEN;
                    break;
                case 1:
                    color = Color.YELLOW;
                    break;
                case 2:
                    color = Color.RED;
                    break;
                default:
                    color = Color.GRAY;
                    break;
            }
            OrgColor.setBackgroundColor(color);
        }
        if (DesDist != null)
        {
            String dist = "Destino: " +String.format("%.2f", r.distanciaDes) + " KM";
            DesDist.setText(dist);
        }
        if (DesColor != null)
        {
            int color;
            switch (r.classDes)
            {
                case 0:
                    color = Color.GREEN;
                    break;
                case 1:
                    color = Color.YELLOW;
                    break;
                case 2:
                    color = Color.RED;
                    break;
                default:
                    color = Color.GRAY;
                    break;
            }
            DesColor.setBackgroundColor(color);
        }
        return v;
    }
}
