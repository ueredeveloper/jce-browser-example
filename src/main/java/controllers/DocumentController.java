package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;


public class DocumentController implements Initializable {
	
	@FXML
	private ComboBox<?> cbDocument;

	private String urlService;

	public DocumentController() {
		this.urlService = "";
	}

	@FXML
	private AnchorPane apContent;

	@FXML
	private JFXComboBox<?> cbDocType;


	@FXML
	private JFXTextField tfNumber;

	@FXML
	private JFXTextField tfNumberSei;

	@FXML
	private JFXComboBox<?> cbProcess;
	ObservableList<?> obsProcess = FXCollections.observableArrayList();
	@FXML
	private JFXComboBox<?> cbAddress;
	ObservableList<?> obsAddress = FXCollections.observableArrayList();

	@FXML
	private JFXComboBox<?> cbAttachment;
	ObservableList<?> obsAttachment = FXCollections.observableArrayList();

	@FXML
	private JFXComboBox<?> cbUser;

	@FXML
	private JFXTextField tfLatitude;

	@FXML
	private JFXTextField tfLongitude;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnEdit;

	@FXML
	private JFXButton btnDelete;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private TableView<?> tvDocs;
	

	@FXML
	private JFXButton btnViews;

	@FXML
	private FontAwesomeIconView btnAddress, btnInterference, btnProcess, btnAttachment, btnUser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}