package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import map.MyBrowser;

import javax.swing.*;

public class JavaFXMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 🔹 Criando o AnchorPane (Container Principal)
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: lightgray;"); // ✅ Cor de fundo

        // 🔹 Criando um SwingNode (Vai conter o JPanel)
        SwingNode swingNode = new SwingNode();
        Platform.runLater(() -> createSwingContent(swingNode));

        // 🔹 Adicionando o SwingNode no AnchorPane
        AnchorPane.setTopAnchor(swingNode, 10.0);
        AnchorPane.setLeftAnchor(swingNode, 10.0);
        root.getChildren().add(swingNode);

        // 🔹 Criando a Cena e exibindo
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX com JPanel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            // ✅ Criando um JPanel (Sem JFrame)
            JPanel panel = new JPanel();
           panel.setBackground(java.awt.Color.BLUE);
           panel.add(new JLabel("Este é um JPanel dentro do JavaFX!"));
           swingNode.setContent(panel);
        	
        	MyBrowser browser = new MyBrowser(300,300);

            // ✅ Adicionando o JPanel ao SwingNode (Agora ele fica na tela principal)
           // swingNode.setContent(browser.getBrowserComponent());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
