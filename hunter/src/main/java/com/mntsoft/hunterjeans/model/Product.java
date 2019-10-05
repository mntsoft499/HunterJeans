package com.mntsoft.hunterjeans.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("branchid")
    @Expose
    private String branchid;
    @SerializedName("billinfoid")
    @Expose
    private String billinfoid;
    @SerializedName("billdate")
    @Expose
    private String billdate;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("totalmrp")
    @Expose
    private String totalmrp;
    @SerializedName("totalsaleamount")
    @Expose
    private String totalsaleamount;
    @SerializedName("totalpurchaseamount")
    @Expose
    private String totalpurchaseamount;
    @SerializedName("totaldiscount")
    @Expose
    private String totaldiscount;
    @SerializedName("taxpercent")
    @Expose
    private String taxpercent;
    @SerializedName("totaldiscountpercent")
    @Expose
    private String totaldiscountpercent;
    @SerializedName("totalbilldisocunt")
    @Expose
    private String totalbilldisocunt;
    @SerializedName("netamount")
    @Expose
    private String netamount;
    @SerializedName("paidbycash")
    @Expose
    private String paidbycash;
    @SerializedName("paidbycard")
    @Expose
    private String paidbycard;
    @SerializedName("totalpayment")
    @Expose
    private String totalpayment;
    @SerializedName("billcode")
    @Expose
    private String billcode;
    @SerializedName("grandtotal")
    @Expose
    private String grandtotal;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("totalsaleamountwithoutdiscount")
    @Expose
    private String totalsaleamountwithoutdiscount;
    @SerializedName("totalsaleamountwithdiscount")
    @Expose
    private String totalsaleamountwithdiscount;
    @SerializedName("totalsaleamountwithoutofferamount")
    @Expose
    private String totalsaleamountwithoutofferamount;
    @SerializedName("offeramount")
    @Expose
    private String offeramount;
    @SerializedName("cashpayment")
    @Expose
    private String cashpayment;
    @SerializedName("cardpayment")
    @Expose
    private String cardpayment;
    @SerializedName("billDate")
    @Expose
    private String billDate;
    @SerializedName("totalvatpercent")
    @Expose
    private String totalvatpercent;
    @SerializedName("totalvatamount")
    @Expose
    private String totalvatamount;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("creditUser")
    @Expose
    private String creditUser;
    @SerializedName("returnchange")
    @Expose
    private String returnchange;
    @SerializedName("totalQuantity")
    @Expose
    private String totalQuantity;
    @SerializedName("dobdate")
    @Expose
    private String dobdate;
    @SerializedName("dobmonth")
    @Expose
    private String dobmonth;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("billCode")
    @Expose
    private String billCode;
    @SerializedName("billId")
    @Expose
    private String billId;
    @SerializedName("offeredproductsamount")
    @Expose
    private String offeredproductsamount;
    @SerializedName("withoutofferproductamount")
    @Expose
    private String withoutofferproductamount;
    @SerializedName("totaldisocountpercent")
    @Expose
    private String totaldisocountpercent;
    @SerializedName("billamountwithouttotaldiscount")
    @Expose
    private String billamountwithouttotaldiscount;
    @SerializedName("swipebankid")
    @Expose
    private String swipebankid;
    @SerializedName("cardnumber")
    @Expose
    private String cardnumber;
    @SerializedName("billAutoIncrement")
    @Expose
    private Integer billAutoIncrement;
    @SerializedName("billIdProxy")
    @Expose
    private String billIdProxy;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("totalCgstpercent")
    @Expose
    private String totalCgstpercent;
    @SerializedName("totalSgstpercent")
    @Expose
    private String totalSgstpercent;
    @SerializedName("totalCgstRupees")
    @Expose
    private String totalCgstRupees;
    @SerializedName("totalSgstRupees")
    @Expose
    private String totalSgstRupees;
    @SerializedName("billFrom")
    @Expose
    private String billFrom;
    @SerializedName("remainAmount")
    @Expose
    private String remainAmount;
    @SerializedName("amountwithoffer")
    @Expose
    private String amountwithoffer;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("subListSize")
    @Expose
    private String subListSize;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("billBranchId")
    @Expose
    private String billBranchId;
    @SerializedName("billBranchName")
    @Expose
    private String billBranchName;
    @SerializedName("dobMonth")
    @Expose
    private String dobMonth;
    @SerializedName("dobDate")
    @Expose
    private String dobDate;
    @SerializedName("creditnoteid")
    @Expose
    private String creditnoteid;
    @SerializedName("creitnoteamount")
    @Expose
    private String creitnoteamount;
    @SerializedName("creditNoteValidOffer")
    @Expose
    private String creditNoteValidOffer;
    @SerializedName("avlCreditAmt")
    @Expose
    private String avlCreditAmt;
    @SerializedName("giftvoucher")
    @Expose
    private String giftvoucher;
    @SerializedName("couponamount")
    @Expose
    private String couponamount;
    @SerializedName("customervouchercode")
    @Expose
    private String customervouchercode;
    @SerializedName("customercouponAmount")
    @Expose
    private String customercouponAmount;
    @SerializedName("redeempoints")
    @Expose
    private String redeempoints;
    @SerializedName("smartcardbarcode")
    @Expose
    private String smartcardbarcode;
    @SerializedName("availablepoints")
    @Expose
    private String availablepoints;
    @SerializedName("addedpoints")
    @Expose
    private String addedpoints;
    @SerializedName("gift")
    @Expose
    private String gift;
    @SerializedName("billproxymannualidadmin")
    @Expose
    private String billproxymannualidadmin;
    @SerializedName("bajajpayment")
    @Expose
    private String bajajpayment;
    @SerializedName("couponnumber")
    @Expose
    private String couponnumber;
    @SerializedName("commissionpercent")
    @Expose
    private String commissionpercent;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("productbatchid")
    @Expose
    private String productbatchid;
    @SerializedName("beforeexchangenetamount")
    @Expose
    private String beforeexchangenetamount;
    @SerializedName("exchangestatus")
    @Expose
    private String exchangestatus;
    @SerializedName("externaldiscountpercent")
    @Expose
    private String externaldiscountpercent;
    @SerializedName("maxremainingdiscount")
    @Expose
    private String maxremainingdiscount;
    @SerializedName("availablestateus")
    @Expose
    private String availablestateus;
    @SerializedName("mannualbillnumber")
    @Expose
    private String mannualbillnumber;
    @SerializedName("clist")
    @Expose
    private List<Clist> clist = null;

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getBillinfoid() {
        return billinfoid;
    }

    public void setBillinfoid(String billinfoid) {
        this.billinfoid = billinfoid;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getTotalmrp() {
        return totalmrp;
    }

    public void setTotalmrp(String totalmrp) {
        this.totalmrp = totalmrp;
    }

    public String getTotalsaleamount() {
        return totalsaleamount;
    }

    public void setTotalsaleamount(String totalsaleamount) {
        this.totalsaleamount = totalsaleamount;
    }

    public String getTotalpurchaseamount() {
        return totalpurchaseamount;
    }

    public void setTotalpurchaseamount(String totalpurchaseamount) {
        this.totalpurchaseamount = totalpurchaseamount;
    }

    public String getTotaldiscount() {
        return totaldiscount;
    }

    public void setTotaldiscount(String totaldiscount) {
        this.totaldiscount = totaldiscount;
    }

    public String getTaxpercent() {
        return taxpercent;
    }

    public void setTaxpercent(String taxpercent) {
        this.taxpercent = taxpercent;
    }

    public String getTotaldiscountpercent() {
        return totaldiscountpercent;
    }

    public void setTotaldiscountpercent(String totaldiscountpercent) {
        this.totaldiscountpercent = totaldiscountpercent;
    }

    public String getTotalbilldisocunt() {
        return totalbilldisocunt;
    }

    public void setTotalbilldisocunt(String totalbilldisocunt) {
        this.totalbilldisocunt = totalbilldisocunt;
    }

    public String getNetamount() {
        return netamount;
    }

    public void setNetamount(String netamount) {
        this.netamount = netamount;
    }

    public String getPaidbycash() {
        return paidbycash;
    }

    public void setPaidbycash(String paidbycash) {
        this.paidbycash = paidbycash;
    }

    public String getPaidbycard() {
        return paidbycard;
    }

    public void setPaidbycard(String paidbycard) {
        this.paidbycard = paidbycard;
    }

    public String getTotalpayment() {
        return totalpayment;
    }

    public void setTotalpayment(String totalpayment) {
        this.totalpayment = totalpayment;
    }

    public String getBillcode() {
        return billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(String grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getTotalsaleamountwithoutdiscount() {
        return totalsaleamountwithoutdiscount;
    }

    public void setTotalsaleamountwithoutdiscount(String totalsaleamountwithoutdiscount) {
        this.totalsaleamountwithoutdiscount = totalsaleamountwithoutdiscount;
    }

    public String getTotalsaleamountwithdiscount() {
        return totalsaleamountwithdiscount;
    }

    public void setTotalsaleamountwithdiscount(String totalsaleamountwithdiscount) {
        this.totalsaleamountwithdiscount = totalsaleamountwithdiscount;
    }

    public String getTotalsaleamountwithoutofferamount() {
        return totalsaleamountwithoutofferamount;
    }

    public void setTotalsaleamountwithoutofferamount(String totalsaleamountwithoutofferamount) {
        this.totalsaleamountwithoutofferamount = totalsaleamountwithoutofferamount;
    }

    public String getOfferamount() {
        return offeramount;
    }

    public void setOfferamount(String offeramount) {
        this.offeramount = offeramount;
    }

    public String getCashpayment() {
        return cashpayment;
    }

    public void setCashpayment(String cashpayment) {
        this.cashpayment = cashpayment;
    }

    public String getCardpayment() {
        return cardpayment;
    }

    public void setCardpayment(String cardpayment) {
        this.cardpayment = cardpayment;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getTotalvatpercent() {
        return totalvatpercent;
    }

    public void setTotalvatpercent(String totalvatpercent) {
        this.totalvatpercent = totalvatpercent;
    }

    public String getTotalvatamount() {
        return totalvatamount;
    }

    public void setTotalvatamount(String totalvatamount) {
        this.totalvatamount = totalvatamount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreditUser() {
        return creditUser;
    }

    public void setCreditUser(String creditUser) {
        this.creditUser = creditUser;
    }

    public String getReturnchange() {
        return returnchange;
    }

    public void setReturnchange(String returnchange) {
        this.returnchange = returnchange;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getDobdate() {
        return dobdate;
    }

    public void setDobdate(String dobdate) {
        this.dobdate = dobdate;
    }

    public String getDobmonth() {
        return dobmonth;
    }

    public void setDobmonth(String dobmonth) {
        this.dobmonth = dobmonth;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOfferedproductsamount() {
        return offeredproductsamount;
    }

    public void setOfferedproductsamount(String offeredproductsamount) {
        this.offeredproductsamount = offeredproductsamount;
    }

    public String getWithoutofferproductamount() {
        return withoutofferproductamount;
    }

    public void setWithoutofferproductamount(String withoutofferproductamount) {
        this.withoutofferproductamount = withoutofferproductamount;
    }

    public String getTotaldisocountpercent() {
        return totaldisocountpercent;
    }

    public void setTotaldisocountpercent(String totaldisocountpercent) {
        this.totaldisocountpercent = totaldisocountpercent;
    }

    public String getBillamountwithouttotaldiscount() {
        return billamountwithouttotaldiscount;
    }

    public void setBillamountwithouttotaldiscount(String billamountwithouttotaldiscount) {
        this.billamountwithouttotaldiscount = billamountwithouttotaldiscount;
    }

    public String getSwipebankid() {
        return swipebankid;
    }

    public void setSwipebankid(String swipebankid) {
        this.swipebankid = swipebankid;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Integer getBillAutoIncrement() {
        return billAutoIncrement;
    }

    public void setBillAutoIncrement(Integer billAutoIncrement) {
        this.billAutoIncrement = billAutoIncrement;
    }

    public String getBillIdProxy() {
        return billIdProxy;
    }

    public void setBillIdProxy(String billIdProxy) {
        this.billIdProxy = billIdProxy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getTotalCgstpercent() {
        return totalCgstpercent;
    }

    public void setTotalCgstpercent(String totalCgstpercent) {
        this.totalCgstpercent = totalCgstpercent;
    }

    public String getTotalSgstpercent() {
        return totalSgstpercent;
    }

    public void setTotalSgstpercent(String totalSgstpercent) {
        this.totalSgstpercent = totalSgstpercent;
    }

    public String getTotalCgstRupees() {
        return totalCgstRupees;
    }

    public void setTotalCgstRupees(String totalCgstRupees) {
        this.totalCgstRupees = totalCgstRupees;
    }

    public String getTotalSgstRupees() {
        return totalSgstRupees;
    }

    public void setTotalSgstRupees(String totalSgstRupees) {
        this.totalSgstRupees = totalSgstRupees;
    }

    public String getBillFrom() {
        return billFrom;
    }

    public void setBillFrom(String billFrom) {
        this.billFrom = billFrom;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getAmountwithoffer() {
        return amountwithoffer;
    }

    public void setAmountwithoffer(String amountwithoffer) {
        this.amountwithoffer = amountwithoffer;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSubListSize() {
        return subListSize;
    }

    public void setSubListSize(String subListSize) {
        this.subListSize = subListSize;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBillBranchId() {
        return billBranchId;
    }

    public void setBillBranchId(String billBranchId) {
        this.billBranchId = billBranchId;
    }

    public String getBillBranchName() {
        return billBranchName;
    }

    public void setBillBranchName(String billBranchName) {
        this.billBranchName = billBranchName;
    }

    public String getDobMonth() {
        return dobMonth;
    }

    public void setDobMonth(String dobMonth) {
        this.dobMonth = dobMonth;
    }

    public String getDobDate() {
        return dobDate;
    }

    public void setDobDate(String dobDate) {
        this.dobDate = dobDate;
    }

    public String getCreditnoteid() {
        return creditnoteid;
    }

    public void setCreditnoteid(String creditnoteid) {
        this.creditnoteid = creditnoteid;
    }

    public String getCreitnoteamount() {
        return creitnoteamount;
    }

    public void setCreitnoteamount(String creitnoteamount) {
        this.creitnoteamount = creitnoteamount;
    }

    public String getCreditNoteValidOffer() {
        return creditNoteValidOffer;
    }

    public void setCreditNoteValidOffer(String creditNoteValidOffer) {
        this.creditNoteValidOffer = creditNoteValidOffer;
    }

    public String getAvlCreditAmt() {
        return avlCreditAmt;
    }

    public void setAvlCreditAmt(String avlCreditAmt) {
        this.avlCreditAmt = avlCreditAmt;
    }

    public String getGiftvoucher() {
        return giftvoucher;
    }

    public void setGiftvoucher(String giftvoucher) {
        this.giftvoucher = giftvoucher;
    }

    public String getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(String couponamount) {
        this.couponamount = couponamount;
    }

    public String getCustomervouchercode() {
        return customervouchercode;
    }

    public void setCustomervouchercode(String customervouchercode) {
        this.customervouchercode = customervouchercode;
    }

    public String getCustomercouponAmount() {
        return customercouponAmount;
    }

    public void setCustomercouponAmount(String customercouponAmount) {
        this.customercouponAmount = customercouponAmount;
    }

    public String getRedeempoints() {
        return redeempoints;
    }

    public void setRedeempoints(String redeempoints) {
        this.redeempoints = redeempoints;
    }

    public String getSmartcardbarcode() {
        return smartcardbarcode;
    }

    public void setSmartcardbarcode(String smartcardbarcode) {
        this.smartcardbarcode = smartcardbarcode;
    }

    public String getAvailablepoints() {
        return availablepoints;
    }

    public void setAvailablepoints(String availablepoints) {
        this.availablepoints = availablepoints;
    }

    public String getAddedpoints() {
        return addedpoints;
    }

    public void setAddedpoints(String addedpoints) {
        this.addedpoints = addedpoints;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getBillproxymannualidadmin() {
        return billproxymannualidadmin;
    }

    public void setBillproxymannualidadmin(String billproxymannualidadmin) {
        this.billproxymannualidadmin = billproxymannualidadmin;
    }

    public String getBajajpayment() {
        return bajajpayment;
    }

    public void setBajajpayment(String bajajpayment) {
        this.bajajpayment = bajajpayment;
    }

    public String getCouponnumber() {
        return couponnumber;
    }

    public void setCouponnumber(String couponnumber) {
        this.couponnumber = couponnumber;
    }

    public String getCommissionpercent() {
        return commissionpercent;
    }

    public void setCommissionpercent(String commissionpercent) {
        this.commissionpercent = commissionpercent;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductbatchid() {
        return productbatchid;
    }

    public void setProductbatchid(String productbatchid) {
        this.productbatchid = productbatchid;
    }

    public String getBeforeexchangenetamount() {
        return beforeexchangenetamount;
    }

    public void setBeforeexchangenetamount(String beforeexchangenetamount) {
        this.beforeexchangenetamount = beforeexchangenetamount;
    }

    public String getExchangestatus() {
        return exchangestatus;
    }

    public void setExchangestatus(String exchangestatus) {
        this.exchangestatus = exchangestatus;
    }

    public String getExternaldiscountpercent() {
        return externaldiscountpercent;
    }

    public void setExternaldiscountpercent(String externaldiscountpercent) {
        this.externaldiscountpercent = externaldiscountpercent;
    }

    public String getMaxremainingdiscount() {
        return maxremainingdiscount;
    }

    public void setMaxremainingdiscount(String maxremainingdiscount) {
        this.maxremainingdiscount = maxremainingdiscount;
    }

    public String getAvailablestateus() {
        return availablestateus;
    }

    public void setAvailablestateus(String availablestateus) {
        this.availablestateus = availablestateus;
    }

    public String getMannualbillnumber() {
        return mannualbillnumber;
    }

    public void setMannualbillnumber(String mannualbillnumber) {
        this.mannualbillnumber = mannualbillnumber;
    }

    public List<Clist> getClist() {
        return clist;
    }

    public void setClist(List<Clist> clist) {
        this.clist = clist;
    }

}