package model;

import java.io.Serializable;

public class OrderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String date;
    private double totalCost;
    private String userEmail;

    public OrderBean() {
        this.id = -1;
        this.date = "";
        this.totalCost = 0.0;
        this.userEmail = "null";
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
    
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

