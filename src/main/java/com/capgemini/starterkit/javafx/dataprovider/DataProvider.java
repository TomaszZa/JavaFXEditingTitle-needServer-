package com.capgemini.starterkit.javafx.dataprovider;

import java.util.Collection;
import java.util.List;

import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;
import com.capgemini.starterkit.javafx.dataprovider.impl.DataProviderImpl;

/**
 * Provides data.
 *
 * @author Leszek
 */
public interface DataProvider {

	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	/**
	 * Finds persons with their name containing specified string and/or given
	 * sex.
	 *
	 * @param name
	 *            string contained in name
	 * @param sex
	 *            sex
	 * @return collection of persons matching the given criteria
	 */
	Collection<BookVO> findPersons(String name);

	void changeTitle(List<BookVO> result, String name, String newTitle);
}
