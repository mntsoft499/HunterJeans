package com.mntsoft.hunterjeans.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.volley.volleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpCaller implements volleyCallback {

    public Context context;
    private ProgressDialog progressDialog;
    static final String REQ_TAG = "VACTIVITY";
    Activity activity;


    public HttpCaller(Context context) {
        this.context = context;
    }


    public void callServer(String URL, final HashMap<String,String> params, final volleyCallback callback){
        if (!checkInternetConnection(context)) {
            showNoInternetDialogue(context);
            return;
        }

        progressDialog = new ProgressDialog(context);
        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError.toString());
                hideDialog();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Log.e("serverParams", params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return new HashMap<>();
            }

        };
        //setting up the retry policy for slower connections
        int socketTimeout = 120000;//120000 milli seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);
    }


    public void callGetServer(String URL, final boolean show_dialogue, final volleyCallback callback){


        if (!checkInternetConnection(context)) {
            showNoInternetDialogue(context);
            return;
        }

        if (show_dialogue){
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);

            progressDialog.setMessage("Please wait...");
            showDialog();
        }



        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
                if (show_dialogue) {
                    hideDialog();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    hideDialog();
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(context, "Timeout.Please try again", Toast.LENGTH_SHORT).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        Toast.makeText(context, "Timeout.Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        parseVolleyError(error);

                    }
                }
                hideDialog();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

        };
        //setting up the retry policy for slower connections
        int socketTimeout = 120000;//120000 milli seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);
    }


    public void callJsonServer(String URL, final JSONObject json, final volleyCallback callback){

        if (!checkInternetConnection(context)) {
            showNoInternetDialogue(context);
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                callback.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(context, "Timeout.Please try again", Toast.LENGTH_SHORT).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        Toast.makeText(context, "Timeout.Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        parseVolleyError(error);

                    }
                }

                   /*if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                       callback.onError(AppConfig.TIMEOUT_ERROR);

                   } else if (error instanceof AuthFailureError) {
                       callback.onError(error.toString());

                   } else if (error instanceof ServerError) {

                       callback.onError(AppConfig.SERVER_ERROR);

                   } else if (error instanceof NetworkError) {

                       callback.onError(error.toString());

                   } else if (error instanceof ParseError) {

                       callback.onError(error.toString());

                   }*/
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        jsonObjectRequest.setTag(REQ_TAG);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);

        //setting retry policy for slower connections
        int socketTimeout = 120000; //2 mins

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);



    }

    private void parseVolleyError(VolleyError error) {

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String message = data.getString("message");
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } catch (JSONException ignored) {
        } catch (UnsupportedEncodingException ignored) {
        }

    }


    private static void showNoInternetDialogue(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage("Please enable Internet");
        builder.setPositiveButton("WIFI SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });
        builder.setNegativeButton("MOBILE INTERNET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setTitle(R.string.app_name);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void hideDialog() {

        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void showDialog() {

        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(String error) {

    }


    private static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
