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
public class UserService {

    private static final String NAMESPACE = "http://tempuri.org/"; //"http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.elmat.kinghost.net/elmatservices/Services/UserService.svc";


    public static void callRegisterUser(String accesToken) {
        final String sendStuff = accesToken;
        final String SOAP_ACTION = "http://tempuri.org/IUserService/RegisterUser";
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, "RegisterUser");
                    request.addProperty("accessToken", sendStuff);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.dotNet = true;

                    HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
                    ht.call(SOAP_ACTION, envelope);
                    final SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    final String str = response.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
    }


    public static void callGetMessage()
    {
        final String SOAP_ACTION = "http://tempuri.org/IUserService/getMessage";
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, "getMessage");
                    String fValue = "Teste WebService ";
                    request.addProperty("message", fValue);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.dotNet = true;

                    HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
                    ht.call(SOAP_ACTION, envelope);
                    final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                    final String str = response.toString();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
    }
}
