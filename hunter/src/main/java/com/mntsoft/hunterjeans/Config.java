package com.mntsoft.hunterjeans;

import com.mntsoft.hunterjeans.utils.AppConstants;

public class Config {

    public static final boolean DEVELOPMENT = false;

    //public static final String BASE_URL = "http://test.mntsoft.co.in";
    //public static final String LOGIN_URL = BASE_URL + "/hunterservices/getlogininfo__V1.htm";
    public static final String LOGIN_URL = AppConstants.BASE_URL + "/hunterservices/getlogininfo__V1.htm";
    public static final String OTP_URL = AppConstants.BASE_URL + "/hunterservices/generateotpforlogin__V1.htm";
    public static final String FORM_SUBMIT_URL = AppConstants.BASE_URL + "/hunterservices/saveappcontactform__V1.htm";
    public static final String APPROVAL_STATUS_CHECK_URL = AppConstants.BASE_URL + "/hunterservices/checkappuserstatus__V1.htm";
    //    public static final String BRANCH_LIST = BASE_URL + "";
    public static final String BRANCH_LIST = AppConstants.BASE_URL + "/hunterservices/getbrancheslist_V1.htm";
    public static final String BRANCH_REPORT_URL = AppConstants.BASE_URL + "/hunterservices/branchreport_V1.htm";
    public static final String BRANCH_INFO_URL = AppConstants.BASE_URL + "/hunterservices/getbrancheslistforbill__V1.htm" ;
    public static final String PRODUCT_DETAILS_URL = AppConstants.BASE_URL + "/hunterservices/addtocart_V1.htm" ;
    public static final String GET_SWIPE_MACHINE_URL = AppConstants.BASE_URL + "/hunterservices/getbranchbankswipemachines__V1.htm" ;
    public static final String GENERATE_BILL_URL = AppConstants.BASE_URL + "/hunterservices/billsave_V1.htm" ;
    public static final String SALES_REPORT_URL = AppConstants.BASE_URL + "/hunterservices/getsalesreport_V1.htm" ;
    public static final String BILL_REPORT_URL = AppConstants.BASE_URL + "/hunterservices/getbillsdetails_V1.htm" ;
    public static final String B_STOCK_REPORT_CATEGORY_URL = AppConstants.BASE_URL + "/hunterservices/getbranchstockinhandcategorywise_V1.htm" ;
    public static final String B_STOCK_REPORT_PRODUCT_URL = AppConstants.BASE_URL + "/hunterservices/getbranchstockinhandproductwise_V1.htm" ;
    public static final String WH1_STOCK_REPORT_CATEGORY_URL = AppConstants.BASE_URL + "/hunterservices/getwh1stockinhandcategorywise_V1.htm" ;
    public static final String WH1_STOCK_REPORT_PRODUCT_URL = AppConstants.BASE_URL + "/hunterservices/getwh1stockinhandproductwise_V1.htm" ;
    public static final String WH2_STOCK_REPORT_CATEGORY_URL = AppConstants.BASE_URL + "/hunterservices/getWH2stockinhandcategorywise_V1.htm" ;
    public static final String WH2_STOCK_REPORT_PRODUCT_URL = AppConstants.BASE_URL + "/hunterservices/getWH2stockinhandproductwise_V1.htm" ;
}
