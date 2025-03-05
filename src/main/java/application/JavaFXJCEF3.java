package application;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import map.MyBrowser;

import javax.swing.*;

public class JavaFXJCEF3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        SwingNode swingNode = new SwingNode();

        Platform.runLater(() -> {
            createSwingContent(swingNode);
        });

        root.getChildren().add(swingNode); // ✅ Adicionando o SwingNode ao JavaFX
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX + JCEF");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JFXPanel jfxPanel = new JFXPanel();
            jfxPanel.setSize(800, 600);
            
            // Criando e adicionando o navegador JCEF
            MyBrowser browser = new MyBrowser(800, 600);
            jfxPanel.add(browser.getBrowserComponent());

            swingNode.setContent(jfxPanel); // ✅ Adicionando o JFXPanel ao SwingNode
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
