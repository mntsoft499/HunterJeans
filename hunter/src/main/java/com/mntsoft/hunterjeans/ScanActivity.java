package com.mntsoft.hunterjeans;

import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.mntsoft.hunterjeans.application.AppController;
import com.mntsoft.hunterjeans.billing.ProductAddActivity;
import com.mntsoft.hunterjeans.model.Product;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;
import com.mntsoft.hunterjeans.zxing.CameraPreview;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import me.dm7.barcodescanner.core.ViewFinderView;

public class ScanActivity extends BaseActivity {

    private static final String TAG = ScanActivity.class.getSimpleName();
    private boolean mFlash;
    private boolean mAutoFocus;
    private int mCameraId = -1;
    private ArrayList<Integer> mSelectedIndices;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private static final int CAMERA_PERMISSION = 1;
    private TextView txt_scanneddetails, txt_validate;
    private FloatingActionButton fab;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText manualEntryValue;
    private SharedPreferenceData preferenceData;
    private AppCompatButton proccedBtn;
    private Gson gson;

    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_scan, frag_frame);
        logout.setVisibility(View.INVISIBLE);
        preferenceData = new SharedPreferenceData(this);

        manualEntryValue = (EditText)findViewById(R.id.manual_entry_value);
        proccedBtn = (AppCompatButton)findViewById(R.id.procced_manualentry_btn);
        proccedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(ScanActivity.this);
                requestProduct(manualEntryValue.getText().toString().trim());
            }
        });
        manualEntryValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    proccedBtn.setVisibility(View.VISIBLE);
                }else {
                    proccedBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        ViewFinderView scannerView = new ViewFinderView(this);
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();
        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, net.sourceforge.zbar.Config.X_DENSITY, 3);
        scanner.setConfig(0, net.sourceforge.zbar.Config.Y_DENSITY, 3);
        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        contentFrame.addView(mPreview);
        contentFrame.addView(scannerView);
        gson = new Gson();
    }
    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCamera.setPreviewCallback(previewCb);
                mCamera.startPreview();
                previewing = true;
                mCamera.autoFocus(autoFocusCB);
            }
        }, 500);
    }

    private void requestProduct(final String barcode) {
        JSONObject jsonObject = new JSONObject();
        if (ProductAddActivity.productResponse == null) {
            try {
                jsonObject.put("branchid", preferenceData.getbranchid());
                jsonObject.put("barcode", barcode);
                LOG.e(TAG, "productResponse is null & new Product request : " + jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ProductAddActivity.productResponse.setBarcode(barcode);
            try {
                jsonObject = new JSONObject(gson.toJson(ProductAddActivity.productResponse));
                LOG.e(TAG, "productResponse is not null & new Product request : " + jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final JSONObject jsonObject1 = jsonObject;
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.PRODUCT_DETAILS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LOG.e(TAG, "requestProduct() : onResponse() : " + response);
                ProductAddActivity.backUpProduct = ProductAddActivity.productResponse;
                ProductAddActivity.productResponse = gson.fromJson(response, Product.class);
                if (ProductAddActivity.productResponse == null || !StringUtils.isEmpty((String) ProductAddActivity.productResponse.getAvailablestateus())) {
                    Util.showToast(AppController.getInstance(), "Quantity not available ", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);
                    ProductAddActivity.backUpProduct = null;
                    ProductAddActivity.productResponse = null;
                }
                dismissProgressDialog();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOG.e(TAG, "requestProduct() : onErrorResponse() : " + error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                LOG.e(TAG, "requestProduct() : getBody() : " + "barcode : "+barcode+".... branchid :"+preferenceData.getUserId());
                try {
                    return jsonObject1.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    LOG.e(TAG, "Unsupported Encoding while trying to get the bytes of %s using %s" + uee.getMessage());
                    return null;
                }
            }
        };

        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    Util.showToast(ScanActivity.this, "Barcode is : " + sym.getData(), AppConstants.COLOR_GREEN, Toast.LENGTH_LONG);
                    try {
//                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                        r.play();
                        ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                    } catch (Exception e) {
                    }
                    requestProduct(sym.getData());
//                    barcodeScanned = true;
                }
            }
        }
    };
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}
