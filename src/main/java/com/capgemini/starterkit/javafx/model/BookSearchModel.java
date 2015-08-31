package com.capgemini.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Data displayed on the person search screen.
 *
 * @author Leszek
 */
public class BookSearchModel { // do wyciagania danych i ustawiania w FXML(czyli
								// w oknie)

	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty newName = new SimpleStringProperty();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getNewName() {
		return newName.get();
	}

	public final void setNewName(String value) {
		newName.set(value);
	}

	public final String getName() {
		return name.get();
	}

	public final void setName(String value) {
		name.set(value);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty newNameProperty() {
		return newName;
	}

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}

}
