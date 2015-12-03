package cc.tcc.elmat_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.messages.VerificaCarona;
import cc.tcc.elmat_2.model.USER;

public class VerificaCaronaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User usr = new User(USER.getUser(this));
        VerificaCarona vc = UserService.callVerificaCarona(this, usr);
        setContentView(R.layout.activity_verifica_carona);

        if (vc.Ride == null || vc.Ride.RideID == 0)
        {
            LinearLayout layout = (LinearLayout)findViewById(R.id.layoutVerticalCaronaAtendida);
            layout.setVisibility(View.GONE);
        }
        else
        {
            TextView HoraCarona = (TextView)findViewById(R.id.caronaHoraAtendida);
            TextView Motorista = (TextView)findViewById(R.id.caronaMotoristaAtendida);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(vc.Ride.Hour);
            String hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
            String minutes = String.format("%02d", calendar.get(Calendar.MINUTE));
            HoraCarona.setText( hours + ":" + minutes);

            if (vc.Ride.DriverID != 0)
            {
                Motorista.setText(vc.User.Name);
            }
            else
            {
                Motorista.setText("Ninguém");
            }

        }
    }
}
