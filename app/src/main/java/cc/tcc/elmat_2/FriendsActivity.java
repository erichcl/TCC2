package cc.tcc.elmat_2;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import cc.tcc.elmat_2.ArrayAdapter.FriendArrayAdaptar;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

public class FriendsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        User usr = new User(USER.getUser(this));


        ArrayList<User> friends = UserService.callGetAmigos(this, usr);

        ArrayAdapter<User> arrayAdapter = new FriendArrayAdaptar(this, R.layout.amigo_item_layout, friends);

        setListAdapter(arrayAdapter);
    }

}
