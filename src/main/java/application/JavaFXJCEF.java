package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import map.MyBrowser;

import javax.swing.*;
import java.awt.*;

public class JavaFXJCEF extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        SwingNode swingNode = new SwingNode();

        Platform.runLater(() -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    MyBrowser browser = new MyBrowser(800, 600);
                    JComponent browserComponent = browser.getBrowserComponent();

                    if (browserComponent != null) {
                        SwingUtilities.invokeLater(() -> swingNode.setContent(browserComponent));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        root.setCenter(swingNode);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX + JCEF");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}