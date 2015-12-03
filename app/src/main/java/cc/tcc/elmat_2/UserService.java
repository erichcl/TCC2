package cc.tcc.elmat_2;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import cc.tcc.elmat_2.messages.GeoPoint;
import cc.tcc.elmat_2.messages.Ride;
import cc.tcc.elmat_2.messages.Routine;
import cc.tcc.elmat_2.messages.ServiceResponse;
import cc.tcc.elmat_2.messages.User;
import cc.tcc.elmat_2.messages.VerificaCarona;
import cc.tcc.elmat_2.model.USER;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserService {


    //region Services Usuário

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
        }
        catch (Exception ex)
        {
            Log.d("Error JsonResult", ex.getMessage());
        }
        return retorno;
    }

    public static ArrayList<User> callGetAmigos(Context pCtx, User usr) {
        ArrayList<User> retorno = new ArrayList<User>();
        final Context ctx = pCtx;
        final User sendUsr = usr;
        final StringBuilder sb = new StringBuilder();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    String usrJson = gson.toJson(sendUsr);

                    JSONObject joPV = new JSONObject(usrJson);
                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/GetFriends", joPV);

                    sb.append(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
        try {
            networkThread.join();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<Collection<User>>>() {}.getType();
            ServiceResponse<Collection<User>> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            retorno = new ArrayList<User>(ObjectRetorno.RETORNO);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            Log.d("Error JsonResult", ex.getMessage());
        }
        return retorno;
    }

    public static boolean callBlockFriend(Context pCtx, User usr, User friend, boolean isBlocked) {
        final Context ctx = pCtx;
        final User me = usr;
        final User myFriend = friend;
        final boolean block = isBlocked;
        final StringBuilder sb = new StringBuilder();
        boolean myReturn = false;
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();

                    Gson gson = new Gson();
                    String usrJson = gson.toJson(me);
                    String friendJson = gson.toJson(myFriend);

                    joPV.put("User", usrJson);
                    joPV.put("Friend", friendJson);
                    joPV.put("isBlocked", block);

                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/BlockFriend", joPV);
                    sb.append(result);
                    Log.d("Bloqueia Amigo", result);
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

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<String>>() {}.getType();
            ServiceResponse<String> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            if (ObjectRetorno.SUCCESS)
                myReturn = true;
            else
                myReturn = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }

    //endregion

    //region Services Rotina

    public static boolean callUpdtRoutine(Context pCtx, User usr, Routine rtn) {
        final Context ctx = pCtx;
        final User me = usr;
        final Routine routine = rtn;
        final StringBuilder sb = new StringBuilder();
        boolean myReturn = false;
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();

                    Gson gson = new Gson();
                    String usrJson = gson.toJson(me);
                    String friendJson = gson.toJson(routine);

                    joPV.put("User", usrJson);
                    joPV.put("Routine", friendJson);

                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/UpdtRoutine", joPV);
                    sb.append(result);
                    Log.d("Atualiza Rotina", result);
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
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<String>>() {}.getType();
            ServiceResponse<String> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            if (ObjectRetorno.SUCCESS)
                myReturn = true;
            else
                myReturn = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }

    public static boolean callDelRoutine(Context pCtx, User usr, Routine rtn) {
        final Context ctx = pCtx;
        final User me = usr;
        final Routine routine = rtn;
        final StringBuilder sb = new StringBuilder();
        boolean myReturn = false;
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();

                    Gson gson = new Gson();
                    String usrJson = gson.toJson(me);
                    String friendJson = gson.toJson(routine);

                    joPV.put("User", usrJson);
                    joPV.put("Routine", friendJson);

                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/DelRoutine", joPV);
                    sb.append(result);
                    Log.d("Deleta Rotina", result);
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
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<String>>() {}.getType();
            ServiceResponse<String> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            if (ObjectRetorno.SUCCESS)
                myReturn = true;
            else
                myReturn = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }

    public static boolean callAddRoutine(Context pCtx, User usr, Routine rtn) {
        final Context ctx = pCtx;
        final User me = usr;
        final Routine routine = rtn;
        final StringBuilder sb = new StringBuilder();
        boolean myReturn = false;
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();

                    Gson gson = new Gson();
                    String usrJson = gson.toJson(me);
                    String friendJson = gson.toJson(routine);

                    joPV.put("User", usrJson);
                    joPV.put("Routine", friendJson);

                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/AddRoutine", joPV);
                    sb.append(result);
                    Log.d("Registra Rotina", result);
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
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<Routine>>() {}.getType();
            ServiceResponse<Routine> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            if (ObjectRetorno.SUCCESS)
                myReturn = true;
            else
                myReturn = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }

    public static ArrayList<Routine> callGetRoutine(Context pCtx, User usr) {
        final Context ctx = pCtx;
        final User me = usr;
        final StringBuilder sb = new StringBuilder();
        ArrayList<Routine> myReturn = new ArrayList<>();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {


                    Gson gson = new Gson();
                    String usrJson = gson.toJson(me);

                    JSONObject joPV = new JSONObject(usrJson);

                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/GetRoutines", joPV);
                    sb.append(result);
                    Log.d("Registra Rotina", result);
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
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<ArrayList<Routine>>>() {}.getType();
            ServiceResponse<ArrayList<Routine>> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            myReturn = ObjectRetorno.RETORNO;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }


    //endregion

    //region Services Caronas

    public static ArrayList<Ride> callListaCaronas(Context pCtx, User usr, LatLng Origem, LatLng Destino) {
        ArrayList<Ride> retorno = new ArrayList<Ride>();
        final Context ctx = pCtx;
        final User sendUsr = usr;
        final GeoPoint Org = new GeoPoint(Origem);
        final GeoPoint Dest = new GeoPoint(Destino);
        final StringBuilder sb = new StringBuilder();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    String usrJson = gson.toJson(sendUsr);
                    String orgJson = gson.toJson(Org);

                    String DestJson = "";
                    if (Dest.isValid())
                    {
                        DestJson = gson.toJson(Dest);
                    }

                    JSONObject joPV = new JSONObject();
                    joPV.put("User", usrJson);
                    joPV.put("Origem", orgJson);
                    joPV.put("Destino", DestJson);
                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/ListaSolCaronas", joPV);

                    sb.append(result);
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
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<Collection<Ride>>>() {}.getType();
            ServiceResponse<Collection<Ride>> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            retorno = new ArrayList<Ride>(ObjectRetorno.RETORNO);
        }
        catch (Exception ex)
        {
            Log.d("Error JsonResult", ex.getMessage());
        }
        return retorno;
    }

    public static void callAtendeCarona(Context pCtx, User usr, Ride ride) {
        final Context ctx = pCtx;
        final User sendUsr = usr;
        final Ride sendRide = ride;
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject joPV = new JSONObject();

                    Gson gson = new Gson();
                    String usrJson = gson.toJson(sendUsr);

                    joPV.put("usr", usrJson);
                    joPV.put("RideID", sendRide.RideID);

                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/AtendeSolicitacaoCarona", joPV);
                    Log.d("Atende Carona", result);
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
    }

    public static boolean callCadastraCarona(Context pCtx, Ride ride) {
        final Context ctx = pCtx;
        final Ride sendRide = ride;
        final StringBuilder sb = new StringBuilder();
        boolean myReturn = false;
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {

                    Gson gson = new Gson();
                    String rideJson = gson.toJson(sendRide);

                    JSONObject joPV = new JSONObject(rideJson);


                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/CadastraCarona", joPV);
                    sb.append(result);
                    Log.d("Solicita Carona", result);
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

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<Ride>>() {}.getType();
            ServiceResponse<Ride> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            if (ObjectRetorno.SUCCESS)
                myReturn = true;
            else
                myReturn = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }


    public static VerificaCarona callVerificaCarona(Context pCtx, User usr) {
        final Context ctx = pCtx;
        final User sendUser = usr;
        final StringBuilder sb = new StringBuilder();
        VerificaCarona myReturn = new VerificaCarona();
        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {

                    Gson gson = new Gson();
                    String userJson = gson.toJson(sendUser);

                    JSONObject joPV = new JSONObject(userJson);


                    String result = Utils.postData(ctx, "http://elmat.kinghost.net/elmatServices/Services/UserService.svc/VerificaCarona", joPV);
                    sb.append(result);
                    Log.d("Verifica Carona", result);
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

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Type myType = new TypeToken<ServiceResponse<VerificaCarona>>() {}.getType();
            ServiceResponse<VerificaCarona> ObjectRetorno = gson.fromJson(sb.toString(), myType);
            if (ObjectRetorno.SUCCESS)
                myReturn = ObjectRetorno.RETORNO;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReturn;
    }

    //endregion


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
