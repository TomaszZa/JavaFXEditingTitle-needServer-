package com.capgemini.starterkit.javafx.dataprovider.data;

/**
 * Person data.
 *
 * @author Leszek
 */
public class BookVO {

	private String name;
	private AuthorVO author;
	private Long Id;

	public BookVO(Long Id, String name, AuthorVO author) {
		this.name = name;
		this.author = author;
		this.Id = Id;
	}

	public Long getID() {
		return Id;
	}

	public void setID(Long Id) {
		this.Id = Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthorVO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorVO author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", birthDate=" + author + "]";
	}

}
