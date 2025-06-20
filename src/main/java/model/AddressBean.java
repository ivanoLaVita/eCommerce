package model;

import java.io.Serializable;

public class AddressBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String city;
    private String province;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String utenteEmail;
    private int userId;

    public AddressBean() {
        this.id = -1;
        this.city = "";
        this.province = "";
        this.postalCode = "";
        this.street = "";
        this.streetNumber = "";
        this.userId = -1;
    }

    // Getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

