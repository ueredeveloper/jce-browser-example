package controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.BackgroundController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.MyBrowser;

/**
 * Controlador para a interface de login.
 */
public class LoginController implements Initializable {
	
	
	
	// Injetando elementos do arquivo FXML
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Label createAccountLabel;

	@FXML
	private Pane greenPane;

	@FXML
	private Label loginLabel;

	@FXML
	private Label emailLabel;

	@FXML
	private JFXTextField tfSignInEmail;

	@FXML
	private JFXButton btnSignUp2;

	@FXML
	private Label passwordLabel;

	@FXML
	private JFXTextField tfSignInPassword;

	@FXML
	private Label emailLabel2;

	@FXML
	private JFXTextField tfSignUpEmail;

	@FXML
	private JFXButton btnSignUp;

	@FXML
	private Label passwordLabel2;

	@FXML
	private JFXTextField tfSiginUpPassWord;

	@FXML
	private JFXButton btnSignUpGoogle;
	
	private JFXPanel jfxPanel;

	// Ícone do Google para login
	// FontAwesomeIconView iconGoogle = new
	// FontAwesomeIconView(FontAwesomeIcon.GOOGLE_PLUS);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Configurando o ícone do Google
		/*
		 * iconGoogle.setSize("2em"); iconGoogle.setId("icon-google");
		 * 
		 * Label lblGoogle = new Label("Google"); lblGoogle.setId("lbl-google"); HBox
		 * hbox = new HBox(10); hbox.getChildren().addAll(iconGoogle, lblGoogle);
		 * hbox.setAlignment(Pos.CENTER);
		 */

		// btnSignUpGoogle.setGraphic(hbox);

		// Configurando a ação do botão de SignUp
		btnSignUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				/*SwingUtilities.invokeLater(() -> new BackgroundController());
				
				
				// Fecha a tela de login
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();*/
				

				try {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();

					// Carregando a cena principal
					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));

					// Redimensionando o stage de acordo com as dimensões do monitor
					Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

					// Configurando o stage para tela cheia
					stage.setX(primaryScreenBounds.getMinX());
					stage.setY(primaryScreenBounds.getMinY());
					stage.setWidth(primaryScreenBounds.getWidth());
					stage.setHeight(primaryScreenBounds.getHeight());

					// Configurando o tamanho mínimo do stage
					stage.setHeight(1080);
					stage.setWidth(1920);

					// Configurando o título da janela
					stage.setTitle("SAD/DF - Geo - Cadastro");

					// Definindo a cena no stage
					stage.setScene(scene);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}