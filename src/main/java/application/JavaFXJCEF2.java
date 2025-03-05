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
import java.awt.*;

public class JavaFXJCEF2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        SwingNode swingNode = new SwingNode();

        Platform.runLater(() -> {
            SwingUtilities.invokeLater(() -> {
            	JFXPanel browserPanel = createBrowserPanel();
            	swingNode.resize(300.0, 300.0);
                swingNode.setContent(browserPanel); // ✅ Adicionando o JCEF no SwingNode
            });
        });

        root.getChildren().add(swingNode); // ✅ Agora é um Node válido

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX + JCEF");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private JFXPanel createBrowserPanel() {
        JFXPanel panel = new JFXPanel();
        panel.setSize(300, 300);

        // Criar um painel de teste (trocar pelo JCEF real)
        JLabel label = new JLabel("✅ JCEF Browser aqui!", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        MyBrowser browser = new MyBrowser(300,300);
        panel.add(browser.getBrowserComponent());
        return panel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
