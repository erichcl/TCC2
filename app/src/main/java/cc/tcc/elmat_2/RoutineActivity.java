package cc.tcc.elmat_2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cc.tcc.elmat_2.ArrayAdapter.RoutineArrayAdaptar;
import cc.tcc.elmat_2.messages.Routine;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

public class RoutineActivity extends ListActivity {

    private Context context;

    //region Listeners

    View.OnClickListener addClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent data = new Intent();
            // Activity finished ok, return the data
            setResult(RESULT_OK, data);
            finish();
        }
    };

    AdapterView.OnItemLongClickListener RoutineLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int row, long arg3) {
            final Routine rtn = ((Routine) arg0.getItemAtPosition(row));
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Deseja deletar a rotina?");
            alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    User usr = new User(USER.getUser(context));
                    UserService.callDelRoutine(context, usr, rtn);
                    finish();
                    startActivity(getIntent());
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
            return true;
        }
    };

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        context = this;

        User usr = new User(USER.getUser(this));
        ArrayList<Routine> routines = UserService.callGetRoutine(this, usr);
        ArrayAdapter<Routine> arrayAdapter = new RoutineArrayAdaptar(this, R.layout.rotina_item_layout, routines);
        setListAdapter(arrayAdapter);

        ListView lv = getListView();
        lv.setOnItemLongClickListener(RoutineLongClick);

        Button addBtn = (Button)findViewById( R.id.add_routine_button);
        addBtn.setOnClickListener(addClick);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Routine rtn = ((Routine) l.getItemAtPosition(position));
        Intent intent = new Intent(this, AddRoutineActivity.class);
        intent.putExtra("Routine", rtn);
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
