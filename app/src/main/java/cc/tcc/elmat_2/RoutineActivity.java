package cc.tcc.elmat_2;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import cc.tcc.elmat_2.ArrayAdapter.FriendArrayAdaptar;
import cc.tcc.elmat_2.ArrayAdapter.RoutineArrayAdaptar;
import cc.tcc.elmat_2.messages.Routine;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

public class RoutineActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        User usr = new User(USER.getUser(this));


        ArrayList<Routine> routines = UserService.callGetRoutine(this, usr);

        ArrayAdapter<Routine> arrayAdapter = new RoutineArrayAdaptar(this, R.layout.rotina_item_layout, routines);

        setListAdapter(arrayAdapter);
    }
}
