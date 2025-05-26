package model;

import java.io.Serializable;

public class OrderItemBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int productId;
    private int orderId;
    private int quantity;
    private String image;
    private String name;
    private double price;

    public OrderItemBean() {
        this.id = -1;
        this.productId = -1;
        this.orderId = -1;
        this.quantity = 0;
        this.image = "";
        this.name = "";
        this.price = 0.0;
    }

    // Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
