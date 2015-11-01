package cc.tcc.elmat_2.ArrayAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import cc.tcc.elmat_2.R;
import cc.tcc.elmat_2.UserService;
import cc.tcc.elmat_2.messages.Routine;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

/**
 * Created by erich on 25/10/2015.
 */
public class RoutineArrayAdaptar extends ArrayAdapter<Routine> {
    private ArrayList<Routine> routines;

    public RoutineArrayAdaptar(Context context, int textViewResourceId, ArrayList<Routine> objects) {
        super(context, textViewResourceId, objects);
        this.routines = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.rotina_item_layout, null);
        }

        final Routine r = routines.get(position);

        // linha 1
        TextView title = (TextView)v.findViewById(R.id.listRoutineTitle);
        TextView hour = (TextView)v.findViewById(R.id.listRoutineHour);

        // linha 2
        TextView Mon = (TextView)v.findViewById(R.id.chkMon);
        TextView Tue = (TextView)v.findViewById(R.id.chkTue);
        TextView Wed = (TextView)v.findViewById(R.id.chkWed);
        TextView Thu = (TextView)v.findViewById(R.id.chkThu);
        TextView Fri = (TextView)v.findViewById(R.id.chkFri);
        TextView Sat = (TextView)v.findViewById(R.id.chkSat);
        TextView Sun = (TextView)v.findViewById(R.id.chkSun);


        Switch userBlock = (Switch)v.findViewById(R.id.blockUserSwitch);

        if (title != null)
        {
            title.setText(r.Title);
        }
        if (hour != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(r.Hour);
            String hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
            String minutes = String.format("%02d", calendar.get(Calendar.MINUTE));
            hour.setText( hours + ":" + minutes);
        }

        if (Mon != null)
        {
            if (r.Mon)
            {
                Mon.setTextColor(Color.BLACK);
            }
            else
            {
                Mon.setTextColor(Color.GRAY);
            }
        }
        if (Tue != null)
        {
            if (r.Tue)
            {
                Tue.setTextColor(Color.BLACK);
            }
            else
            {
                Tue.setTextColor(Color.GRAY);
            }
        }
        if (Wed != null)
        {
            if (r.Wed)
            {
                Wed.setTextColor(Color.BLACK);
            }
            else
            {
                Wed.setTextColor(Color.GRAY);
            }
        }
        if (Thu != null)
        {
            if (r.Thu)
            {
                Thu.setTextColor(Color.BLACK);
            }
            else
            {
                Thu.setTextColor(Color.GRAY);
            }
        }
        if (Fri != null)
        {
            if (r.Fri)
            {
                Fri.setTextColor(Color.BLACK);
            }
            else
            {
                Fri.setTextColor(Color.GRAY);
            }
        }
        if (Sat != null)
        {
            if (r.Sat)
            {
                Sat.setTextColor(Color.BLACK);
            }
            else
            {
                Sat.setTextColor(Color.GRAY);
            }
        }
        if (Sun != null)
        {
            if (r.Sun)
            {
                Sun.setTextColor(Color.BLACK);
            }
            else
            {
                Sun.setTextColor(Color.GRAY);
            }
        }

        return v;
    }
}
