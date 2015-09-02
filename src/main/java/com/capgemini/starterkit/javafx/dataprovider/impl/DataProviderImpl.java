package com.capgemini.starterkit.javafx.dataprovider.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.capgemini.starterkit.javafx.dataprovider.DataProvider;
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

	private ConnectionWithServer connection = new ConnectionWithServer();

	public DataProviderImpl() {
		List<BookVO> bookVo = null;
		String jsons = connection.getJSON("http://localhost:9721/workshop/rest/books/books-by-title", 10000);
		System.out.println(jsons);
		ObjectMapper mapper = new ObjectMapper();

		try {
			bookVo = mapper.readValue(jsons, new TypeReference<List<BookVO>>() {
			});
			persons = bookVo;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(bookVo.get(0).getTitle());
	}

	@Override
	public Collection<BookVO> findPersons(String name) {
		LOG.debug("Entering findPersons()");

		Collection<BookVO> result = persons.stream().filter(p -> //
		((name == null || name.isEmpty()) || (name != null && !name.isEmpty() && p.getTitle().contains(name))))
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
			if (result.get(i).getId() == Long.parseLong(id)) {
				bookToAdd = result.get(i);
				persons.remove(bookToAdd);
				bookToAdd.setTitle(newTitle);
				persons.add(bookToAdd);
			}
		}

	}

}