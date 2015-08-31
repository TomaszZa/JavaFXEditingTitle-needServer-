package com.capgemini.starterkit.javafx.dataprovider.data;

public class AuthorVO {
	private String firstName;
	private String lastName;

	public AuthorVO(String name, String lastName) {
		this.firstName = name;
		this.lastName = lastName;
	}

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

	@Override
	public String toString() {
		return "Person [name=" + firstName + ", lastName=" + lastName + "]";
	}

}
