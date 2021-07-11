package com.victusvir.xtracker;

public class Vehicle {
    private String userId;
    private String userEmail;
    private String ownerName;
    private String regNo;
    private String model;
    private String fuelType;
    private String seatingCap;
    private String color;

    public Vehicle() {
    }

    public Vehicle(String userId,String userEmail, String ownerName, String regNo, String model, String fuelType, String seatingCap, String color) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.ownerName = ownerName;
        this.regNo = regNo;
        this.model = model;
        this.fuelType = fuelType;
        this.seatingCap = seatingCap;
        this.color = color;
    }

    public String getUserId() {return userId; }

    public String getUserEmail() {return userEmail; }

    public String getOwnerName() {
        return ownerName;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getModel() {
        return model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getSeatingCap() {
        return seatingCap;
    }

    public String getColor() {
        return color;
    }
}
