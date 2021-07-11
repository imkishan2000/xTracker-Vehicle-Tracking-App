package com.victusvir.xtracker;

public class ServiceDetail {
    private String vehicleId;
    private String billNo;
    private String status;
    private String amount;

    public ServiceDetail() {
    }

    public ServiceDetail(String vehicleId, String billNo, String status, String amount) {
        this.vehicleId = vehicleId;
        this.billNo = billNo;
        this.status = status;
        this.amount = amount;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }
}
