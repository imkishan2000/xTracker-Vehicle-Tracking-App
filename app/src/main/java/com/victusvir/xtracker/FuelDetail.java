package com.victusvir.xtracker;

public class FuelDetail {
    private String fuelId;
    private String billNo;
    private String price;
    private String cost;

    public FuelDetail() {
    }

    public FuelDetail(String fuelId, String billNo, String price, String cost) {
        this.fuelId = fuelId;
        this.billNo = billNo;
        this.price = price;
        this.cost = cost;
    }

    public String getFuelId() {
        return fuelId;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getPrice() {
        return price;
    }

    public String getCost() {
        return cost;
    }
}
