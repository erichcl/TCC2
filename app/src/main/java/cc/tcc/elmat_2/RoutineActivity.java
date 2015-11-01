package cc.tcc.elmat_2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import cc.tcc.elmat_2.ArrayAdapter.RoutineArrayAdaptar;
import cc.tcc.elmat_2.messages.Routine;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

public class RoutineActivity extends ListActivity {

    View.OnClickListener addClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent data = new Intent();
            // Activity finished ok, return the data
            setResult(RESULT_OK, data);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        User usr = new User(USER.getUser(this));
        ArrayList<Routine> routines = UserService.callGetRoutine(this, usr);
        ArrayAdapter<Routine> arrayAdapter = new RoutineArrayAdaptar(this, R.layout.rotina_item_layout, routines);
        setListAdapter(arrayAdapter);

        Button addBtn = (Button)findViewById( R.id.add_routine_button);
        addBtn.setOnClickListener(addClick);
    }
}
