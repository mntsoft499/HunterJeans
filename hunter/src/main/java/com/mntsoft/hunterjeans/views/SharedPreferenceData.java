package com.mntsoft.hunterjeans.views;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mntsoft.hunterjeans.application.AppController;

public class SharedPreferenceData {
    // public static final String KEY_PREFS_NAME = "user_name";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_ROLL_ID = "roll_id";
    public static final String KEY_USER = "user_name";
    public static final String KEY_REGID = "user_regid";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_LOGGED = "is_logged";
    public static final String KEY_FCMID = "fcmid";
    public static final String KEY_DEVICEID = "deviceid";
    public static final String KEY_REG = "is_register";
    public static final String KEY_SESSIONID = "session";
    public static final String KEY_PATIENT_ID = "patient_id";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_SCANNERBRANCHID = "scannerbranchid";
    public static final String KEY_APPROVAL_PROFILE_ID = "approval_profile_id";
    public static final String KEY_APPROVAL_PROFILE_STATUS = "approval_profile_status";




    public static final String KEY_BRANCHID = "branchid";
    public static final String KEY_BRANCHNAME = "branchname";
    public static final String KEY_PRODUCTBATCHID = "productbatchid";
    public static final String KEY_BARCODE = "barcode";
    public static final String KEY_SALESPRICE = "saleprice";
    public static final String KEY_DISCOUNTPERCENT = "discountpercent";
    public static final String KEY_MRPPRICE = "mrpprice";
    public static final String KEY_PURCHASEPRICE = "purchaseprice";
    public static final String KEY_VATPERCENT = "vatpercent";
    public static final String KEY_DISCOUNTVALUE = "discountvalue";
    public static final String KEY_VATVALUE = "vatvalue";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_AMOUNTWITHDISCOUNT = "amountwithdiscount";
    public static final String KEY_AMOUNTWITHOUTDISCOUNT= "amountwithoutdiscount";
    public static final String KEY_AMOUNTWITHOFFER = "amountwithoffer";
    public static final String KEY_PRODUCTDESCRIPTION = "productdescription";
    public static final String KEY_OFFERID = "offerid";
    public static final String KEY_OFFERAMOUNT = "offeramount";
    public static final String KEY_OFFERVALID = "offervalid";
    public static final String KEY_UOM = "uom";
    public static final String KEY_BEFOREOFFERAMOUNTWITHDISCOUNT = "beforeofferamountwithdiscount";
    public static final String KEY_BEFOREOFFERAMOUNTWITHOFFER = "beforeofferamountwithoffer";
    public static final String KEY_BEFOREOFFERDISCOUNT = "beforeofferdiscount";
    public static final String KEY_IMAGEPATH = "imagepath";
    public static final String KEY_PRODUCTID = "productid";
    public static final String KEY_BILLDETAILSID = "billdetailsid";
    public static final String KEY_BILLINFOID = "billinfoid";
    public static final String KEY_RETURNQUANTITY = "returnQuantity";
    public static final String KEY_DISCOUNT = "discount";
    public static final String KEY_CREDITNOTEVALIDOFFER = "creditNoteValidOffer";

    //Edited by Brahma
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String ADDRESS = "address";



    private static final String APP_SHARED_PREFS = SharedPreferenceData.class
            .getSimpleName(); // Name of the file -.xml
    private static SharedPreferences _sharedPrefs;
    private static Editor _prefsEditor;

    @SuppressWarnings("static-access")
    public SharedPreferenceData(Context context) {
        this._sharedPrefs = AppController.getInstance().getSharedPreferences(APP_SHARED_PREFS,
                Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public static SharedPreferenceData getInstance() {
        return (SharedPreferenceData) _sharedPrefs;
    }



    public String getscannerbranchid() {
        return _sharedPrefs.getString(KEY_SCANNERBRANCHID, "");
    }

    public void setscannerbranchid(String scannerbranchid) {
        _prefsEditor.putString(KEY_SCANNERBRANCHID, scannerbranchid);
        _prefsEditor.commit();
    }

    public String getbranchid() {
        return _sharedPrefs.getString(KEY_BRANCHID, "");
    }

    public void setMobileNumber(String mobileNumber){
        _prefsEditor.putString(MOBILE_NUMBER,mobileNumber);
        _prefsEditor.commit();
    }

    public String getMobileNumber(){
        return _sharedPrefs.getString(MOBILE_NUMBER,"");
    }

    public void setAddress(String address){
        _prefsEditor.putString(ADDRESS,"");
        _prefsEditor.commit();
    }

    public String getAddress(){
        return _sharedPrefs.getString(ADDRESS,"");
    }

    public void setbranchid(String branchid) {
        _prefsEditor.putString(KEY_BRANCHID, branchid);
        _prefsEditor.commit();
    }
    public void setbranchName(String branchName) {
        _prefsEditor.putString(KEY_BRANCHNAME, branchName);
        _prefsEditor.commit();
    }
    public String getBranchName(){
        return _sharedPrefs.getString(KEY_BRANCHNAME, "");
    }

    public String getimagepath() {
        return _sharedPrefs.getString(KEY_IMAGEPATH, "");
    }

    public void setimagepath(String imagepath) {
        _prefsEditor.putString(KEY_IMAGEPATH, imagepath);
        _prefsEditor.commit();
    }

    public String getproductbatchid() {
        return _sharedPrefs.getString(KEY_PRODUCTBATCHID, "");
    }

    public void setproductbatchid(String productbatchid) {
        _prefsEditor.putString(KEY_PRODUCTBATCHID, productbatchid);
        _prefsEditor.commit();
    }

    public String getbarcode() {
        return _sharedPrefs.getString(KEY_BARCODE, "");
    }

    public void setbarcode(String barcode) {
        _prefsEditor.putString(KEY_BARCODE, barcode);
        _prefsEditor.commit();
    }
    public String getKeyApprovalProfileId() {
        return _sharedPrefs.getString(KEY_APPROVAL_PROFILE_ID, "");
    }

    public void setKeyApprovalProfileId(String id) {
        _prefsEditor.putString(KEY_APPROVAL_PROFILE_ID, id);
        _prefsEditor.commit();
    }
    public String getKeyApprovalProfileStatus() {
        return _sharedPrefs.getString(KEY_APPROVAL_PROFILE_STATUS, "");
    }

    public void setKeyApprovalProfileStatus(String status) {
        _prefsEditor.putString(KEY_APPROVAL_PROFILE_STATUS, status);
        _prefsEditor.commit();
    }

    public String getsaleprice() {
        return _sharedPrefs.getString(KEY_SALESPRICE, "");
    }

    public void setsaleprice(String saleprice) {
        _prefsEditor.putString(KEY_SALESPRICE, saleprice);
        _prefsEditor.commit();
    }

    public String getdiscountpercent() {
        return _sharedPrefs.getString(KEY_DISCOUNTPERCENT, "");
    }

    public void setdiscountpercent(String discountpercent) {
        _prefsEditor.putString(KEY_DISCOUNTPERCENT, discountpercent);
        _prefsEditor.commit();
    }

    public String getmrpprice() {
        return _sharedPrefs.getString(KEY_MRPPRICE, "");
    }

    public void setmrpprice(String mrpprice) {
        _prefsEditor.putString(KEY_MRPPRICE, mrpprice);
        _prefsEditor.commit();
    }

    public String getpurchaseprice() {
        return _sharedPrefs.getString(KEY_PURCHASEPRICE, "");
    }

    public void setpurchaseprice(String purchaseprice) {
        _prefsEditor.putString(KEY_PURCHASEPRICE, purchaseprice);
        _prefsEditor.commit();
    }

    public String getvatpercent() {
        return _sharedPrefs.getString(KEY_VATPERCENT, "");
    }

    public void setvatpercent(String vatpercent) {
        _prefsEditor.putString(KEY_VATPERCENT, vatpercent);
        _prefsEditor.commit();
    }

    public String getdiscountvalue() {
        return _sharedPrefs.getString(KEY_DISCOUNTVALUE, "");
    }

    public void setdiscountvalue(String discountvalue) {
        _prefsEditor.putString(KEY_DISCOUNTVALUE, discountvalue);
        _prefsEditor.commit();
    }

    public String getvatvalue() {
        return _sharedPrefs.getString(KEY_VATVALUE, "");
    }

    public void setvatvalue(String vatvalue) {
        _prefsEditor.putString(KEY_VATVALUE, vatvalue);
        _prefsEditor.commit();
    }

    public String getquantity() {
        return _sharedPrefs.getString(KEY_QUANTITY, "");
    }

    public void setquantity(String quantity) {
        _prefsEditor.putString(KEY_QUANTITY, quantity);
        _prefsEditor.commit();
    }

    public String getamount() {
        return _sharedPrefs.getString(KEY_AMOUNT, "");
    }

    public void setamount(String amount) {
        _prefsEditor.putString(KEY_AMOUNT, amount);
        _prefsEditor.commit();
    }

    public String getamountwithdiscount() {
        return _sharedPrefs.getString(KEY_AMOUNTWITHDISCOUNT, "");
    }

    public void setamountwithdiscount(String amountwithdiscount) {
        _prefsEditor.putString(KEY_AMOUNTWITHDISCOUNT, amountwithdiscount);
        _prefsEditor.commit();
    }

    public String getamountwithoutdiscount() {
        return _sharedPrefs.getString(KEY_AMOUNTWITHOUTDISCOUNT, "");
    }

    public void setamountwithoutdiscount(String amountwithoutdiscount) {
        _prefsEditor.putString(KEY_AMOUNTWITHOUTDISCOUNT, amountwithoutdiscount);
        _prefsEditor.commit();
    }

    public String getamountwithoffer() {
        return _sharedPrefs.getString(KEY_AMOUNTWITHOFFER, "");
    }

    public void setamountwithoffer(String amountwithoffer) {
        _prefsEditor.putString(KEY_AMOUNTWITHOFFER, amountwithoffer);
        _prefsEditor.commit();
    }

    public String getproductdescription() {
        return _sharedPrefs.getString(KEY_PRODUCTDESCRIPTION, "");
    }

    public void setproductdescription(String productdescription) {
        _prefsEditor.putString(KEY_PRODUCTDESCRIPTION, productdescription);
        _prefsEditor.commit();
    }

    public String getofferid() {
        return _sharedPrefs.getString(KEY_OFFERID, "");
    }

    public void setofferid(String offerid) {
        _prefsEditor.putString(KEY_OFFERID, offerid);
        _prefsEditor.commit();
    }

    public String getofferamount() {
        return _sharedPrefs.getString(KEY_OFFERAMOUNT, "");
    }

    public void setofferamount(String offeramount) {
        _prefsEditor.putString(KEY_OFFERAMOUNT, offeramount);
        _prefsEditor.commit();
    }

    public String getoffervalid() {
        return _sharedPrefs.getString(KEY_OFFERVALID, "");
    }

    public void setoffervalid(String offervalid) {
        _prefsEditor.putString(KEY_OFFERVALID, offervalid);
        _prefsEditor.commit();
    }

    public String getuom() {
        return _sharedPrefs.getString(KEY_UOM, "");
    }

    public void setuom(String uom) {
        _prefsEditor.putString(KEY_UOM, uom);
        _prefsEditor.commit();
    }

    public String getbeforeofferamountwithdiscount() {
        return _sharedPrefs.getString(KEY_BEFOREOFFERAMOUNTWITHDISCOUNT, "");
    }

    public void setbeforeofferamountwithdiscount(String beforeofferamountwithdiscount) {
        _prefsEditor.putString(KEY_BEFOREOFFERAMOUNTWITHDISCOUNT, beforeofferamountwithdiscount);
        _prefsEditor.commit();
    }

    public String getbeforeofferamountwithoffer() {
        return _sharedPrefs.getString(KEY_BEFOREOFFERAMOUNTWITHOFFER, "");
    }

    public void setbeforeofferamountwithoffer(String beforeofferamountwithoffer) {
        _prefsEditor.putString(KEY_BEFOREOFFERAMOUNTWITHOFFER, beforeofferamountwithoffer);
        _prefsEditor.commit();
    }

    public String getbeforeofferdiscount() {
        return _sharedPrefs.getString(KEY_BEFOREOFFERDISCOUNT, "");
    }

    public void setbeforeofferdiscount(String beforeofferdiscount) {
        _prefsEditor.putString(KEY_BEFOREOFFERDISCOUNT, beforeofferdiscount);
        _prefsEditor.commit();
    }

    public String getproductid() {
        return _sharedPrefs.getString(KEY_PRODUCTID, "");
    }

    public void setproductid(String productid) {
        _prefsEditor.putString(KEY_PRODUCTID, productid);
        _prefsEditor.commit();
    }

    public String getbilldetailsid() {
        return _sharedPrefs.getString(KEY_BILLDETAILSID, "");
    }

    public void setbilldetailsid(String billdetailsid) {
        _prefsEditor.putString(KEY_BILLDETAILSID, billdetailsid);
        _prefsEditor.commit();
    }

    public String getbillinfoid() {
        return _sharedPrefs.getString(KEY_BILLINFOID, "");
    }

    public void setbillinfoid(String billinfoid) {
        _prefsEditor.putString(KEY_BILLINFOID, billinfoid);
        _prefsEditor.commit();
    }

    public String getreturnQuantity() {
        return _sharedPrefs.getString(KEY_RETURNQUANTITY, "");
    }

    public void setreturnQuantity(String returnQuantity) {
        _prefsEditor.putString(KEY_RETURNQUANTITY, returnQuantity);
        _prefsEditor.commit();
    }

    public String getdiscount() {
        return _sharedPrefs.getString(KEY_DISCOUNT, "");
    }

    public void setdiscount(String discount) {
        _prefsEditor.putString(KEY_DISCOUNT, discount);
        _prefsEditor.commit();
    }

    public String getcreditNoteValidOffer() {
        return _sharedPrefs.getString(KEY_CREDITNOTEVALIDOFFER, "");
    }

    public void setcreditNoteValidOffer(String creditNoteValidOffer) {
        _prefsEditor.putString(KEY_CREDITNOTEVALIDOFFER, creditNoteValidOffer);
        _prefsEditor.commit();
    }

    public String getUser() {
        return _sharedPrefs.getString(KEY_USER, "");
    }

    public void setUser(String user) {
        _prefsEditor.putString(KEY_USER, user);
        _prefsEditor.commit();
    }

    public String getFCMID() {
        return _sharedPrefs.getString(KEY_FCMID, "");
    }

    public void setFCMID(String fcmid) {
        _prefsEditor.putString(KEY_FCMID, fcmid);
        _prefsEditor.commit();
    }

    public String getDEVICEID() {
        return _sharedPrefs.getString(KEY_DEVICEID, "");
    }

    public void setDEVICEID(String deviceid) {
        _prefsEditor.putString(KEY_DEVICEID, deviceid);
        _prefsEditor.commit();
    }

    public String getRegId() {
        return _sharedPrefs.getString(KEY_REGID, "");
    }

    public void setRegId(String user) {
        _prefsEditor.putString(KEY_REGID, user);
        _prefsEditor.commit();
    }

    public String getPassword() {
        return _sharedPrefs.getString(KEY_PASSWORD, "");
    }

    public void setPassword(String pass) {
        _prefsEditor.putString(KEY_PASSWORD, pass);
        _prefsEditor.commit();
    }

    public String getLastName() {
        return _sharedPrefs.getString(KEY_LAST_NAME, "");
    }

    public void setLastName(String id) {
        _prefsEditor.putString(KEY_LAST_NAME, id);
        _prefsEditor.commit();
    }

    public String getFirstName() {
        return _sharedPrefs.getString(KEY_FIRST_NAME, "");
    }

    public void setFirstName(String id) {
        _prefsEditor.putString(KEY_FIRST_NAME, id);
        _prefsEditor.commit();
    }

    public void setSessionId(String id) {
        _prefsEditor.putString(KEY_SESSIONID, id);
        _prefsEditor.commit();
    }



    public String getSessionId() {
        return _sharedPrefs.getString(KEY_SESSIONID, "");
    }

    public void patientId(String id) {
        _prefsEditor.putString(KEY_PATIENT_ID, id);
        _prefsEditor.commit();
    }


    public String getUserId() {
        return _sharedPrefs.getString(KEY_USER_ID, "");
    }

    public void setUserId(String userId) {
        _prefsEditor.putString(KEY_USER_ID, userId);
        _prefsEditor.commit();
    }

    public String getRollId() {
        return _sharedPrefs.getString(KEY_ROLL_ID, "");
    }

    public void setRollId(String rollId) {
        _prefsEditor.putString(KEY_ROLL_ID, rollId);
        _prefsEditor.commit();
    }

    public boolean isLogged() {
        return _sharedPrefs.getBoolean(KEY_LOGGED, false);
    }

    public void setLogged(boolean key) {
        _prefsEditor.putBoolean(KEY_LOGGED, key);
        _prefsEditor.commit();
    }

    public boolean isRegistered() {
        return _sharedPrefs.getBoolean(KEY_REG, false);
    }

    public void setRegistered(boolean key) {
        _prefsEditor.putBoolean(KEY_REG, key);
        _prefsEditor.commit();
    }

}
