package cc.tcc.elmat_2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserSvcActivityFragment extends Fragment {

    private static final String SOAP_ACTION = "http://tempuri.org/IUserService/getMessage";
    private static final String METHOD_NAME = "getMessage";
    private static final String NAMESPACE = "http://tempuri.org/"; //"http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.elmat.kinghost.net/elmatservices/Services/UserService.svc";

    public UserSvcActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_svc, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnTesteService = (Button) getView().findViewById(R.id.UserSvcBtn);
        btnTesteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGetMessage();
            }
        });
    }

    public void callGetMessage()
    {
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    String fValue = "La Bela Bunda ";
                    request.addProperty("message", fValue);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.dotNet = true;

                    HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
                    ht.call(SOAP_ACTION, envelope);
                    final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                    final String str = response.toString();

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            TextView result;
                            result = (TextView) getView().findViewById(R.id.UserSvcTxt);//Text view id is textView1
                            result.setText(str);
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
    }
}
