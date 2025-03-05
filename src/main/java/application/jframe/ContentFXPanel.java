package application.jframe;

import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ContentFXPanel extends JFXPanel {
	private static final long serialVersionUID = 1L;

	public ContentFXPanel() {
		Platform.runLater(() -> {

			SwingUtilities.invokeLater(() -> {
				AnchorPane anchorPane = new AnchorPane();
				Scene scene = new Scene(anchorPane, 400, 300);

				Label label = new Label("JavaFX Content Always on Top");
				AnchorPane.setTopAnchor(label, 20.0);
				AnchorPane.setLeftAnchor(label, 20.0);
				anchorPane.getChildren().add(label);

				setScene(scene);
			
			});

		});
	}
}
