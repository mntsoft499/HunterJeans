package com.mntsoft.hunterjeans.volley;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mntsoft.hunterjeans.application.AppController;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.Util;

public class VolleyController {
    
    private static VolleyController mInstance;
    private RequestQueue mRequestQueue;

    private VolleyController() {
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyController getInstance() {
        if (mInstance == null) {
            mInstance = new VolleyController();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(AppController.getInstance());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> request) {
        new NetworkCheckTask(request).execute();
    }

    class NetworkCheckTask extends AsyncTask<Void, Void, Boolean> {
        private Request request;
        NetworkCheckTask(Request request) {
            this.request = request;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return Util.isOnline();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 60000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 0;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            if (!aBoolean) {
                Util.showToast(AppController.getInstance(), "Please check internet connection !", AppConstants.COLOR_RED, Toast.LENGTH_LONG);
                return;
            }
            getRequestQueue().add(request);
        }
    }
}
