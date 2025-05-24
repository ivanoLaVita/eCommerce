package model;

import java.io.Serializable;

public class ProductBean implements Serializable {
	
    public enum ProductGender {
        M,
        W
    }
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String description;
	private int quantity;
	private double price;
	private ProductGender gender;
	private String image;
	private String categoryName;
	
	
	public ProductBean() {
		
		this.id = -1;
		this.name = "";
		this.description = "";
		this.quantity = -1;
		this.price = 0.0;
		this.gender = null;
		this.image = "";
		this.categoryName = "";
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public ProductGender getGender() {
		return gender;
	}


	public void setGender(ProductGender gender) {
		this.gender = gender;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
