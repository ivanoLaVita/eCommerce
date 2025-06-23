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
    private String userEmail;

    public AddressBean() {
        this.city = "";
        this.province = "";
        this.postalCode = "";
        this.street = "";
        this.streetNumber = "";
        this.userEmail = "";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

