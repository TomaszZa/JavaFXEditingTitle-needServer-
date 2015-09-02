package com.capgemini.starterkit.javafx.dataprovider.data;

import java.io.Serializable;
import java.util.List;

/**
 * Person data.
 *
 * @author Leszek
 */
public class BookVO implements Serializable {

	private Long id;
	private String title;
	private List<AuthorVO> authors;

	public BookVO() {

	}

	public BookVO(Long Id, String name, List<AuthorVO> author) {
		this.title = name;
		this.authors = author;
		this.id = Id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long Id) {
		this.id = Id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public List<AuthorVO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorVO> author) {
		this.authors = author;
	}

	@Override
	public String toString() {
		return "Person [name=" + title + ", birthDate=" + authors + "]";
	}

}
