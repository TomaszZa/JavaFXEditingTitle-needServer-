package com.capgemini.starterkit.javafx.dataprovider.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.capgemini.starterkit.javafx.dataprovider.DataProvider;
import com.capgemini.starterkit.javafx.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;

/**
 * Provides data. Data is stored locally in class. Additionally call delay is
 * simulated.
 *
 * @author Leszek
 */
public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	private Collection<BookVO> persons = new ArrayList<>();

	public DataProviderImpl() {
		persons.add(new BookVO(1L, "Book1", new AuthorVO("Jan", "Jakis")));
		persons.add(new BookVO(2L, "Book2", new AuthorVO("sds", "Jakis")));
		persons.add(new BookVO(3L, "Book3", new AuthorVO("Jan", "sdsd")));
		persons.add(new BookVO(4L, "Book4", new AuthorVO("ff", "Jakis")));
		persons.add(new BookVO(5L, "Book5", new AuthorVO("Jan", "Jakis")));
		persons.add(new BookVO(6L, "Book6", new AuthorVO("Jan", "bb")));
		persons.add(new BookVO(7L, "Book7", new AuthorVO("tt", "Jakis")));
		persons.add(new BookVO(8L, "Book8", new AuthorVO("Jan", "xcv")));
		persons.add(new BookVO(9L, "Book9", new AuthorVO("zz", "Jakis")));
	}

	@Override
	public Collection<BookVO> findPersons(String name) {
		LOG.debug("Entering findPersons()");

		Collection<BookVO> result = persons.stream().filter(p -> //
		((name == null || name.isEmpty()) || (name != null && !name.isEmpty() && p.getName().contains(name))))
				.collect(Collectors.toList()); // stream() zamienia dane na
												// strumien, filter() filtruje,
												// collect() akumuluje strumien
												// Collectors to obiekt z
												// metodami do akumulacji
												// .toList() wiec do listy

		LOG.debug("Leaving findPersons()");
		return result;
	}

	@Override
	public void changeTitle(List<BookVO> result, String id, String newTitle) {
		LOG.debug("changeTitle() " + id);
		BookVO bookToAdd = null;
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).getID() == Long.parseLong(id)) {
				bookToAdd = result.get(i);
				persons.remove(bookToAdd);
				bookToAdd.setName(newTitle);
				persons.add(bookToAdd);
			}
		}

	}

}
