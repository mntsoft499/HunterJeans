package com.mntsoft.hunterjeans.views;

/**
 * Created by root on 2/1/18.
 */
public class StockCnts {

    public String getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(String receiveid) {
        this.receiveid = receiveid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getAllocateddate() {
        return allocateddate;
    }

    public void setAllocateddate(String allocateddate) {
        this.allocateddate = allocateddate;
    }

    private String receiveid;
    private String branchid;
    private String allocateddate;

    public String getAllocatedqty() {
        return allocatedqty;
    }

    public void setAllocatedqty(String allocatedqty) {
        this.allocatedqty = allocatedqty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    private String allocatedqty;
    private String barcode;
}
