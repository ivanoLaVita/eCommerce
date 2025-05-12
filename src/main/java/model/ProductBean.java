package model;

import java.io.Serializable;

public class ProductBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	private int quantity;
	private int price;
	private String gender;
	private String image;
	private String categoryName;
	
	
	public ProductBean() {
		
		this.id = -1;
		this.name = "null";
		this.description = "null";
		this.quantity = -1;
		this.price = -1;
		this.gender = "null";
		this.image = "null";
		this.categoryName = "null";
		
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


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
