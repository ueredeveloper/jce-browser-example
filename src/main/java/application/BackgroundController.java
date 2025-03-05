package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import map.MyBrowser;

public class BackgroundController extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFXPanel jfxPanel;

	public BackgroundController() {

		setExtendedState(JFrame.MAXIMIZED_BOTH); // ✅ Fullscreen
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 🔹 AWT Panel for the map (fullscreen)
		JPanel mapPanel = new JPanel();

		mapPanel.setBackground(Color.RED); // ✅ AWT Map background (RED)

		// 🔹 JavaFX Panel for controls (right side)
		jfxPanel = new JFXPanel();

		// 🔹 Layout for stacking
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);

		// Get screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int controlWidth = (int) (width*0.7); 
		
		int mapWidth = width - controlWidth;
		int mapHeight = height-90;

		// Set bounds
		mapPanel.setBounds(0, 0, mapWidth, height);
		mapPanel.add(new MyBrowser(mapWidth, mapHeight ).getBrowserComponent());
		jfxPanel.setBounds(width - controlWidth, 0, controlWidth, height);

		// Add both layers
		layeredPane.add(mapPanel, Integer.valueOf(0)); // Background (lowest layer)
		layeredPane.add(jfxPanel, Integer.valueOf(1)); // UI overlay (right side)

		// Add to JFrame
		add(layeredPane);
		setVisible(true);

		// 🔹 Initialize JavaFX content
		Platform.runLater(() -> initFX(controlWidth, height));
	}

	private void initFX(int width, int height) {
		// Create an AnchorPane for controls
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setStyle("-fx-background-color: blue;"); // ✅ JavaFX Controls background (BLUE)
		anchorPane.setPrefSize(width, height);

		Button btn = new Button("Click Me");
		AnchorPane.setTopAnchor(btn, 10.0);
		AnchorPane.setLeftAnchor(btn, 10.0);
		anchorPane.getChildren().add(btn);

		Scene scene = new Scene(anchorPane, width, height);
		jfxPanel.setScene(scene);
	}

	
}
