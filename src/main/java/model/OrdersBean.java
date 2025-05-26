package model;

import java.io.Serializable;

public class OrdersBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String date;
    private double totalCost;
    private int userID;

    public OrdersBean() {
        this.id = -1;
        this.date = "";
        this.totalCost = 0.0;
        this.userID = -1;
    }

    // Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

