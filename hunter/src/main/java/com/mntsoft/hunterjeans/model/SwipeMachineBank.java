package com.mntsoft.hunterjeans.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SwipeMachineBank {

    @SerializedName("bankId")
    @Expose
    private String bankId;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("clientId")
    @Expose
    private Object clientId;
    @SerializedName("bankCode")
    @Expose
    private Object bankCode;
    @SerializedName("bankClientId")
    @Expose
    private Object bankClientId;
    @SerializedName("banksearch")
    @Expose
    private Object banksearch;
    @SerializedName("bedit")
    @Expose
    private Object bedit;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public SwipeMachineBank withBankId(String bankId) {
        this.bankId = bankId;
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public SwipeMachineBank withBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public SwipeMachineBank withMsg(Object msg) {
        this.msg = msg;
        return this;
    }

    public Object getClientId() {
        return clientId;
    }

    public void setClientId(Object clientId) {
        this.clientId = clientId;
    }

    public SwipeMachineBank withClientId(Object clientId) {
        this.clientId = clientId;
        return this;
    }

    public Object getBankCode() {
        return bankCode;
    }

    public void setBankCode(Object bankCode) {
        this.bankCode = bankCode;
    }

    public SwipeMachineBank withBankCode(Object bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public Object getBankClientId() {
        return bankClientId;
    }

    public void setBankClientId(Object bankClientId) {
        this.bankClientId = bankClientId;
    }

    public SwipeMachineBank withBankClientId(Object bankClientId) {
        this.bankClientId = bankClientId;
        return this;
    }

    public Object getBanksearch() {
        return banksearch;
    }

    public void setBanksearch(Object banksearch) {
        this.banksearch = banksearch;
    }

    public SwipeMachineBank withBanksearch(Object banksearch) {
        this.banksearch = banksearch;
        return this;
    }

    public Object getBedit() {
        return bedit;
    }

    public void setBedit(Object bedit) {
        this.bedit = bedit;
    }

    public SwipeMachineBank withBedit(Object bedit) {
        this.bedit = bedit;
        return this;
    }

}
