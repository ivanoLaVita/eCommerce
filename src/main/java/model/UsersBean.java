package model;

import java.io.Serializable;

public class UsersBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private boolean admin;
	
	
	public UsersBean() {
		
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.username = "";
		this.password = "";
		this.admin = false;
		
	}

	//getter and setter
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	
}
