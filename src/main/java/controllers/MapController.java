package controllers;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.webkit.WebConsoleListener;

import javafx.application.Platform;
//import controllers.views.AddInterferenceController;
//import controllers.views.InterferenceTextFieldsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import map.MyBrowser;
//import models.Interferencia;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Controlador para a interface de mapa.
 */
public class MapController implements Initializable {

	@FXML AnchorPane apMap;

	private static MapController instance;

	public static MapController getInstance() {
		return instance;
	}

	public MapController(AnchorPane apContent) {
	
		instance = this; // Define a instância no construtor
	}

	public AnchorPane getAnchorPaneMap() {
		return null;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	
		 SwingNode swingNode = new SwingNode(); // ✅ Swing wrapper for JavaFX

	        SwingUtilities.invokeLater(() -> {
	        	
	        	MyBrowser browser = new MyBrowser(300, 300);
	           
	            swingNode.setContent(browser.getBrowserComponent()); 
	        });

	      apMap.getChildren().add(swingNode);

	}


}