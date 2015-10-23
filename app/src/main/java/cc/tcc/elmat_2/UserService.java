package cc.tcc.elmat_2;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.HashMap;

import cc.tcc.elmat_2.messages.ServiceResponse;
import cc.tcc.elmat_2.messages.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserService {

    private static final String NAMESPACE = "http://tempuri.org/"; //"http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.elmat.kinghost.net/elmatservices/Services/UserService.svc";


    public static User callRegisterUser(Context pCtx, String paccesToken) {
        User retorno = new User();
        final Context ctx = pCtx;
        final String accessToken = paccesToken;
        final StringBuilder sb = new StringBuilder();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();
                    joPV.put("accessToken", accessToken);
                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/RegisterUser", joPV);
                    sb.append(result);
                }
                catch (JSONException ex)
                {
                    Log.d("Error JSON", ex.getMessage());
                }
                catch (Exception ex)
                {
                    Log.d("Error Post", ex.getMessage());
                }
            }
        };
        networkThread.start();
        try {
            networkThread.join();
        } catch (InterruptedException e) {
            Log.d("Error Thread", e.getMessage());
        }
        try {
            Gson gson = new Gson();
            Type myType = new TypeToken<ServiceResponse<User>>() {}.getType();
            ServiceResponse<User> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            retorno = ObjectRetorno.RETORNO;
            Log.d("retorno", String.valueOf(retorno.UserID));
        }
        catch (Exception ex)
        {
            Log.d("Error JsonResult", ex.getMessage());
        }
        return retorno;
    }

    public static String callListaCaronas(User usr, Location Origem, Location Destino) {
        final String methodName = "ListaSolCaronas";
        final User sendUsr = usr;
        final  Location Org = Origem;
        final  Location Dest = Destino;
        final String SOAP_ACTION = "http://tempuri.org/IUserService/"+methodName;
        final StringBuilder sb = new StringBuilder();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, methodName);

                    PropertyInfo sendUsrObject = new PropertyInfo();
                    sendUsrObject.setName("usr");
                    sendUsrObject.setValue(sendUsr);
                    sendUsrObject.setType(sendUsr.getClass());

                    request.addProperty(sendUsrObject);
                    request.addProperty("LatOrg", Org.getLatitude());
                    request.addProperty("LonOrg", Org.getLongitude());
                    if (Dest != null)
                    {
                        request.addProperty("LatDes", Dest.getLatitude());
                        request.addProperty("LonDes", Dest.getLongitude());
                    }
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.dotNet = true;

                    envelope.addMapping(NAMESPACE, "usr", sendUsr.getClass());

                    HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
                    ht.debug = true;
                    ht.call(SOAP_ACTION, envelope);

                    Log.d("dump Request: ", ht.requestDump);

                    SoapObject response = (SoapObject)envelope.bodyIn;
                    sb.append(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
        try {
            networkThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String callAtendeCarona(User usr, int RideID) {
        final User sendUsr = usr;
        final int sendRideID = RideID;
        final String SOAP_ACTION = "http://tempuri.org/IUserService/AtendeSolicitacaoCarona";
        final StringBuilder sb = new StringBuilder();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, "AtendeSolicitacaoCarona");
                    request.addProperty("usr", sendUsr);
                    request.addProperty("RideID", sendRideID);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
                    ht.call(SOAP_ACTION, envelope);
                    final SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    sb.append(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
        try {
            networkThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static void callGetMessage(Context pCtx) {
        final Context ctx = pCtx;
        final String SOAP_ACTION = "http://tempuri.org/IUserService/RegisterUser";
        final StringBuilder sb = new StringBuilder();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();
                    joPV.put("message", "teste JSON Android");
                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/getMessage", joPV);
                    sb.append(result);
                }
                catch (JSONException ex)
                {
                    Log.d("Error JSON", ex.getMessage());
                }
                catch (Exception ex)
                {
                    Log.d("Error Post", ex.getMessage());
                }
            }
        };
        networkThread.start();
        try {
            networkThread.join();
        } catch (InterruptedException e) {
            Log.d("Error Thread", e.getMessage());
        }
    }
}
