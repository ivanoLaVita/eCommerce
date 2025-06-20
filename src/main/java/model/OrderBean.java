package model;

import java.io.Serializable;

public class OrderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String date;
    private double totalCost;
    private int userId;
    private String utenteEmail;

    public OrderBean() {
        this.id = -1;
        this.date = "";
        this.totalCost = 0.0;
        this.userId = -1;
        this.utenteEmail = "null";
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUtenteEmail() {
        return utenteEmail;
    }

    public void setUtenteEmail(String utenteEmail) {
        this.utenteEmail = utenteEmail;
    }
}

