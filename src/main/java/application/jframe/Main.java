package application.jframe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class Main extends JFrame {
    private static final long serialVersionUID = 7052387842413089905L;

    JLayeredPane layeredPane;
    BrowserFXPanel mapFxPanelPanel;
    ContentFXPanel contentFxPanel;

    public Main() {
        super("Embedded Browser and JavaFX Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create LayeredPane to stack components
        layeredPane = new JLayeredPane();

        layeredPane.setPreferredSize(new Dimension(1920, 1080));

        // Create Browser and JavaFX Panels
        mapFxPanelPanel = new BrowserFXPanel();
        contentFxPanel = new ContentFXPanel();

        // Set bounds for proper layering
        mapFxPanelPanel.setBounds(0, 0, 1920, 1080);
        contentFxPanel.setBounds(200, 100, 1920, 1080); // Position JavaFX panel on top

        Component mapFxPanel = mapFxPanelPanel.getPanel();
        // Add to layeredPane
       layeredPane.add(mapFxPanel, Integer.valueOf(0)); // Back layer
       layeredPane.add(contentFxPanel, Integer.valueOf(1)); // Front layer
        
        // Force JavaFX panel to be on top after any layout change
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int zOrderMap = layeredPane.getComponentZOrder(mapFxPanel);
                int zOrderContent = layeredPane.getComponentZOrder(contentFxPanel);
                System.out.println("component resized - map " + zOrderMap + " content " + zOrderContent);
                
               

         
            }

          
        });

        add(layeredPane, BorderLayout.CENTER);

        setVisible(true);
        setAlwaysOnTop(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}