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
import java.util.Calendar;

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

    View.OnClickListener UpdtBtnClick = new View.OnClickListener() {
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
            UserService.callUpdtRoutine(getApplicationContext(), usr, myRoutine);
            Intent intent = new Intent(getApplicationContext(), RoutineActivity.class);
            startActivity(intent);
            finish();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        Intent intent = getIntent();

        if (intent.hasExtra("Latitude"))
        {
            double Latitude = intent.getExtras().getDouble("Latitude");
            double Longitude = intent.getExtras().getDouble("Longitude");

            myRoutine = new Routine();
            myRoutine.Latitude = Latitude;
            myRoutine.Longitude = Longitude;

            Button AddButton = (Button)findViewById(R.id.AddRoutineBtn);
            AddButton.setOnClickListener(AddBtnClick);
        }
        else if (intent.hasExtra("Routine"))
        {
            Button UpdtButton = (Button)findViewById(R.id.AddRoutineBtn);
            UpdtButton.setText(getResources().getString(R.string.strUpdt));
            UpdtButton.setOnClickListener(UpdtBtnClick);
            myRoutine = (Routine)intent.getSerializableExtra("Routine");

            // Escreve nos campos

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
                title.setText(myRoutine.Title);
            }
            if (hour != null)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(myRoutine.Hour);
                String hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                String minutes = String.format("%02d", calendar.get(Calendar.MINUTE));
                String tmpHora = hours + ":" + minutes;

                hour.setText(tmpHora);
            }
            if (Mon != null)
            {
                 Mon.setChecked(myRoutine.Mon);
            }
            if (Tue != null)
            {
                Tue.setChecked(myRoutine.Tue);
            }
            if (Wed != null)
            {
                Wed.setChecked(myRoutine.Wed);
            }
            if (Thu != null)
            {
                Thu.setChecked(myRoutine.Thu);
            }
            if (Fri != null)
            {
                Fri.setChecked(myRoutine.Fri);
            }
            if (Sat != null)
            {
                Sat.setChecked(myRoutine.Sat);
            }
            if (Sun != null)
            {
                Sun.setChecked(myRoutine.Sun);
            }

        }
    }
}
