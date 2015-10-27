package cc.tcc.elmat_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.text.ParseException;

import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.model.ELMATDbHelper;
import cc.tcc.elmat_2.model.USER;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        String token = getCurrentToken();
        if (token != null )
        {
            User ReturnUser = UserService.callRegisterUser(getApplicationContext(), token);
            Profile profile = Profile.getCurrentProfile();
            try {
                USER usr = USER.getUser(getApplicationContext());
                //USER usr = new USER(Integer.parseInt(teste), Double.parseDouble(profile.getId()), getApplicationContext());
                if (usr == null)
                {
                    usr = new USER(ReturnUser.UserID, Double.parseDouble(profile.getId()), ReturnUser.Name, getApplicationContext());
                    usr.DbInsertMe();
                }
            }
            catch(NumberFormatException e) {
                Log.d("Login OnCreate", "NumberFormatException: " + e.getMessage());
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finishActivity(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;

    }

    public String getCurrentToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null)
            return accessToken.getToken();
        else
            return null;
    }
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
