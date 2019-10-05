package com.mntsoft.hunterjeans.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesReportResponse {

    @SerializedName("billid")
    @Expose
    private String billid;
    @SerializedName("billproxyid")
    @Expose
    private String billproxyid;
    @SerializedName("billoverallamount")
    @Expose
    private String billoverallamount;
    @SerializedName("tradediscount")
    @Expose
    private String tradediscount;
    @SerializedName("smartcarddiscount")
    @Expose
    private String smartcarddiscount;
    @SerializedName("overalldiscount")
    @Expose
    private String overalldiscount;
    @SerializedName("netamount")
    @Expose
    private String netamount;
    @SerializedName("taxablevalue")
    @Expose
    private String taxablevalue;
    @SerializedName("taxvalue")
    @Expose
    private String taxvalue;
    @SerializedName("cgst")
    @Expose
    private String cgst;
    @SerializedName("sgst")
    @Expose
    private String sgst;
    @SerializedName("igst")
    @Expose
    private String igst;
    @SerializedName("branchid")
    @Expose
    private String branchid;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("fromdate")
    @Expose
    private String fromdate;
    @SerializedName("todate")
    @Expose
    private String todate;
    @SerializedName("billdate")
    @Expose
    private String billdate;
    @SerializedName("branchname")
    @Expose
    private String branchname;
    @SerializedName("billstatus")
    @Expose
    private String billstatus;
    @SerializedName("totalquantity")
    @Expose
    private Double totalquantity;
    @SerializedName("totdiscountpercentonbill")
    @Expose
    private String totdiscountpercentonbill;
    @SerializedName("voucheramount")
    @Expose
    private String voucheramount;
    @SerializedName("gstnumber")
    @Expose
    private String gstnumber;
    @SerializedName("clist")
    @Expose
    private String clist;

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getBillproxyid() {
        return billproxyid;
    }

    public void setBillproxyid(String billproxyid) {
        this.billproxyid = billproxyid;
    }

    public String getBilloverallamount() {
        return billoverallamount;
    }

    public void setBilloverallamount(String billoverallamount) {
        this.billoverallamount = billoverallamount;
    }

    public String getTradediscount() {
        return tradediscount;
    }

    public void setTradediscount(String tradediscount) {
        this.tradediscount = tradediscount;
    }

    public String getSmartcarddiscount() {
        return smartcarddiscount;
    }

    public void setSmartcarddiscount(String smartcarddiscount) {
        this.smartcarddiscount = smartcarddiscount;
    }

    public String getOveralldiscount() {
        return overalldiscount;
    }

    public void setOveralldiscount(String overalldiscount) {
        this.overalldiscount = overalldiscount;
    }

    public String getNetamount() {
        return netamount;
    }

    public void setNetamount(String netamount) {
        this.netamount = netamount;
    }

    public String getTaxablevalue() {
        return taxablevalue;
    }

    public void setTaxablevalue(String taxablevalue) {
        this.taxablevalue = taxablevalue;
    }

    public String getTaxvalue() {
        return taxvalue;
    }

    public void setTaxvalue(String taxvalue) {
        this.taxvalue = taxvalue;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public Double getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(Double totalquantity) {
        this.totalquantity = totalquantity;
    }

    public String getTotdiscountpercentonbill() {
        return totdiscountpercentonbill;
    }

    public void setTotdiscountpercentonbill(String totdiscountpercentonbill) {
        this.totdiscountpercentonbill = totdiscountpercentonbill;
    }

    public String getVoucheramount() {
        return voucheramount;
    }

    public void setVoucheramount(String voucheramount) {
        this.voucheramount = voucheramount;
    }

    public String getGstnumber() {
        return gstnumber;
    }

    public void setGstnumber(String gstnumber) {
        this.gstnumber = gstnumber;
    }

    public String getClist() {
        return clist;
    }

    public void setClist(String clist) {
        this.clist = clist;
    }

}
