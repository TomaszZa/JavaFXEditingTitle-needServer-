package com.capgemini.starterkit.javafx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.capgemini.starterkit.javafx.dataprovider.DataProvider;
import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;
import com.capgemini.starterkit.javafx.model.BookSearchModel;
import com.capgemini.starterkit.javafx.texttospeech.Speaker;
import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * Controller for the person search screen.
 * <p>
 * The JavaFX runtime will inject corresponding objects in the @FXML annotated
 * fields. The @FXML annotated methods will be called by JavaFX runtime at
 * specific points in time.
 * </p>
 *
 * @author Leszek
 */
public class PersonSearchController {

	private static final Logger LOG = Logger.getLogger(PersonSearchController.class);

	/**
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	ResourceBundle resources; // TWORZY SIE JAKOS AUTOMATYCZNIE CZY RECZNIE ?

	/**
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	URL location; // Tak samo jak wyzej ?

	@FXML
	TextField nameField;

	@FXML
	Button searchButton;

	@FXML
	TableView<BookVO> resultTable;

	@FXML
	TableColumn<BookVO, String> bookColumn;

	@FXML
	TableColumn<BookVO, String> authorColumn;

	@FXML
	TextField newNameField;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final Speaker speaker = Speaker.INSTANCE;

	private final BookSearchModel model = new BookSearchModel();

	@FXML
	TableColumn editColumn;

	@FXML
	TableColumn<BookVO, String> idColumn;

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public PersonSearchController() {
		LOG.debug("Constructor: nameField = " + nameField);
	}

	/**
	 * The JavaFX runtime calls this method after the FXML file loaded.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() { // metoda wbudowana controllera
		LOG.debug("initialize(): nameField = " + nameField);

		initializeResultTable();

		/*
		 * Bind controls properties to model properties.
		 */
		newNameField.textProperty().bindBidirectional(model.newNameProperty());
		nameField.textProperty().bindBidirectional(model.nameProperty()); // laczy
																			// nameField(z
																			// FXML)
																			// z
																			// name
																			// z
																			// PersonSerachModel
		resultTable.itemsProperty().bind(model.resultProperty()); // laczy
																	// resultTable(z
																	// FXML) z
																	// result z
																	// PersonSerchModel
																	// jednokierunkowo
																	// (czyli
																	// zmiany w
																	// modelu
																	// powoduja
																	// zmiany w
																	// oknie)

		searchButton.disableProperty().bind(nameField.textProperty().isEmpty());
	}

	private void initializeResultTable() {
		LOG.debug("INICJALIZACJAA KLAWISZY");
		/*
		 * Define what properties of PersonVO will be displayed in different
		 * columns.
		 */
		bookColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		// tylko
		// zakladaja
		// jak
		// beda
		// dzialac
		// komowrki
		// w
		// kolumnach a rezultat jets pobierany z akcji serch buttona oraz
		// wyswietlania dzieki bindowi reltatow serch buttona z modelu z
		// rezultTable czyli tabela w okienku

		authorColumn.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthors().get(0).getFirstName() + " "
						+ cellData.getValue().getAuthors().get(0).getLastName()));
		// dla kolumny wywolywana jest metoda .setCellValueFactory()
		// ktora tworzy wiersze kolumny,za pomoca wyrazenia lambda
		// zaimplementowanay jest w neij obikt
		// read-only(ReadOnlyStringWrapper) do ktorego przekazujemy dane
		// do kolumny
		idColumn.setCellValueFactory(callData -> new ReadOnlyStringWrapper(callData.getValue().getId().toString()));

		////////////////// EDIT
		// Define the button cell
		class ButtonCell extends TableCell<Record, Boolean> {
			final Button cellButton = new Button("Edit");

			ButtonCell() {

				// Action when the button is pressed
				cellButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						LOG.debug("'Edit' button clicked");
						dataProvider.changeTitle(model.getResult(), idColumn.getCellData(getIndex()),
								model.getNewName());// AKCJA
					}
				});
			}

			// Display button if the row is not empty
			@Override
			protected void updateItem(Boolean t, boolean empty) {
				super.updateItem(t, empty);
				if (!empty) {
					setGraphic(cellButton);
				}
			}
		}

		editColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adding the Button to the cell
		editColumn.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

			@Override
			public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
				return new ButtonCell();
			}

		});

		//////////////////////////////////////////////////////////////////////////

		/*
		 * Show specific text for empty table.
		 */
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		/*
		 * When table row gets select say given person name.
		 */
		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookVO>() {

			@Override
			public void changed(ObservableValue<? extends BookVO> observable, BookVO oldValue, BookVO newValue) {
				LOG.debug(newValue + " selected");

				Task<Void> backgroundTask = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						speaker.say(newValue.getTitle());
						return null;
					}
				};
				new Thread(backgroundTask).start();
			}
		});
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	public void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchButtonActionVersion();
	}

	/**
	 * This implementation is correct.
	 * <p>
	 * The {@link DataProvider#findPersons(String, SexVO)} call is executed in a
	 * background thread.
	 * </p>
	 */
	private void searchButtonActionVersion() {
		/*
		 * Use task to execute the potentially long running call in background
		 * (separate thread), so that the JavaFX Application Thread is not
		 * blocked.
		 */
		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Collection<BookVO> call() throws Exception {
				LOG.debug("call() called");

				/*
				 * Get the data.
				 */
				return dataProvider.findPersons( // wyszukuje w danych osoby o
													// danym imieniu
						model.getName());
			}
		};

		/*
		 * Monitor the "state" property to get informed when the background task
		 * finishes.
		 */
		backgroundTask.stateProperty().addListener(new ChangeListener<Worker.State>() {

			/**
			 * This method will be executed in the JavaFX Application Thread.
			 */
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					LOG.debug("changed() called");

					/*
					 * Copy the result to model.
					 */
					model.setResult(new ArrayList<BookVO>(backgroundTask.getValue())); // przypisuje
																						// wyniki
																						// wyszukania
																						// do
																						// modelu

					/*
					 * Reset sorting in table.
					 */
					resultTable.getSortOrder().clear();
				}
			}
		});

		/*
		 * Start the background task. In real life projects some framework
		 * manages background tasks.
		 */
		new Thread(backgroundTask).start();
	}

}
