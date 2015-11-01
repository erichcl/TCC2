package cc.tcc.elmat_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import cc.tcc.elmat_2.messages.Routine;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.USER;

public class AddRoutineActivity extends AppCompatActivity {

    private Routine myRoutine;

    View.OnClickListener AddBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText title = (EditText)findViewById(R.id.txtRoutineTitle);
            EditText hour = (EditText)findViewById(R.id.txtRoutineHour);
            CheckBox Mon = (CheckBox)findViewById(R.id.chkMon);
            CheckBox Tue = (CheckBox)findViewById(R.id.chkTue);
            CheckBox Wed = (CheckBox)findViewById(R.id.chkWed);
            CheckBox Thu = (CheckBox)findViewById(R.id.chkThu);
            CheckBox Fri = (CheckBox)findViewById(R.id.chkFri);
            CheckBox Sat = (CheckBox)findViewById(R.id.chkSat);
            CheckBox Sun = (CheckBox)findViewById(R.id.chkSun);

            if (title != null)
            {
                myRoutine.Title = title.getText().toString();
            }
            if (hour != null)
            {
                String tmpHora = hour.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                try {
                    myRoutine.Hour = dateFormat.parse(tmpHora);
                }
                catch (ParseException ex)
                {
                    Toast.makeText(getApplicationContext(), "Formato de hora inválido", Toast.LENGTH_LONG).show();
                    Log.d("Nova Rotina", ex.getMessage());
                }
            }
            if (Mon != null)
            {
                myRoutine.Mon = Mon.isChecked();
            }
            if (Tue != null)
            {
                myRoutine.Tue = Tue.isChecked();
            }
            if (Wed != null)
            {
                myRoutine.Wed = Wed.isChecked();
            }
            if (Thu != null)
            {
                myRoutine.Thu = Thu.isChecked();
            }
            if (Fri != null)
            {
                myRoutine.Fri = Fri.isChecked();
            }
            if (Sat != null)
            {
                myRoutine.Sat = Sat.isChecked();
            }
            if (Sun != null)
            {
                myRoutine.Sun = Sun.isChecked();
            }

            User usr = new User(USER.getUser(getApplicationContext()));
            UserService.callAddRoutine(getApplicationContext(), usr, myRoutine);
            Intent intent = new Intent(getApplicationContext(), RoutineActivity.class);
            startActivity(intent);
            finish();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        double Latitude = intent.getExtras().getDouble("Latitude");
        double Longitude = intent.getExtras().getDouble("Longitude");

        setContentView(R.layout.activity_add_routine);

        myRoutine = new Routine();
        myRoutine.Latitude = Latitude;
        myRoutine.Longitude = Longitude;

        Button AddButton = (Button)findViewById(R.id.AddRoutineBtn);
        AddButton.setOnClickListener(AddBtnClick);
    }
}
