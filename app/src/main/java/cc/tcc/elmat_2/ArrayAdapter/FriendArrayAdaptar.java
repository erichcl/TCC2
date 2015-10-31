package cc.tcc.elmat_2.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cc.tcc.elmat_2.R;
import cc.tcc.elmat_2.UserService;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

/**
 * Created by erich on 25/10/2015.
 */
public class FriendArrayAdaptar extends ArrayAdapter<User> {
    private ArrayList<User> friends;

    public FriendArrayAdaptar(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        this.friends = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.amigo_item_layout, null);
        }

        final User r = friends.get(position);

        TextView userName = (TextView)v.findViewById(R.id.listUserName);
        Switch userBlock = (Switch)v.findViewById(R.id.blockUserSwitch);

        if (userName != null)
        {
            userName.setText(r.Name);
        }
        if (userBlock != null)
        {
            if (r.RelationStatus == 1) // Liberado
            {
                userBlock.setChecked(true);
            }
            else if (r.RelationStatus == 2) // Bloqueado
            {
                userBlock.setChecked(false);
            }

            userBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean isBlocked = !isChecked;
                    User friend = r;
                    User me = new User(USER.getUser(getContext()));
                    if (UserService.callBlockFriend(getContext(), me, friend, isBlocked))
                    {
                        Toast.makeText(getContext(), "Operação bem sucedida", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Ocorreu um erro na operação", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return v;
    }
}
