package model;

import java.io.Serializable;

public class PaymentMethodBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String type;
    private String iban;
    private String cardNumber;
    private String userEmail;

    public PaymentMethodBean() {
        this.id = -1;
        this.type = "null"; 
        this.iban = "";
        this.cardNumber = "";
        this.userEmail = "";
    }

    // Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
    
    public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String utenteEmail) {
		this.userEmail = utenteEmail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
