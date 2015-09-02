package com.capgemini.starterkit.javafx.dataprovider.data;

public class AuthorVO {
	private Long id;
	private String firstName;
	private String lastName;

	public AuthorVO(String name, String lastName) {
		this.firstName = name;
		this.lastName = lastName;
	}

	public AuthorVO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
