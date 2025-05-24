package model;

import java.io.Serializable;

public class PaymentMethodBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum PaymentType {
        CARD,
        IBAN
    }

    private int id;
    private PaymentType type;
    private String iban;
    private String cardNumber;
    private int userId;

    public PaymentMethodBean() {
        this.id = -1;
        this.type = null; // oppure PaymentType.CARD come default
        this.iban = "";
        this.cardNumber = "";
        this.userId = -1;
    }

    // Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
