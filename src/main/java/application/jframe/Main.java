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
    BrowserFXPanel browserFxPanel;
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
        browserFxPanel = new BrowserFXPanel();
        contentFxPanel = new ContentFXPanel();

        // Set bounds for proper layering
        browserFxPanel.setBounds(0, 0, 1920, 1080);
        contentFxPanel.setBounds(200, 100, 1920, 1080); // Position JavaFX panel on top

        Component browserFx = browserFxPanel.getPanel();
        // Add to layeredPane
       // layeredPane.add(browserFx, Integer.valueOf(0)); // Back layer
      layeredPane.add(contentFxPanel, Integer.valueOf(0)); // Front layer
        
        // Force JavaFX panel to be on top after any layout change
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int zOrderMap = layeredPane.getComponentZOrder(browserFx);
                int zOrderContent = layeredPane.getComponentZOrder(contentFxPanel);
                System.out.println("component resized - map " + zOrderMap + " content " + zOrderContent);
                
                if (zOrderMap==-1) {
                	layeredPane.add(browserFx, Integer.valueOf(0));
                	layeredPane.add(contentFxPanel, Integer.valueOf(1));
                	contentFxPanel.repaint();
                } else if (zOrderMap==1) {
                	layeredPane.add(browserFx, Integer.valueOf(0));
                	layeredPane.add(contentFxPanel, Integer.valueOf(1));
                	
                	browserFx.repaint();
                	 
                } else if (zOrderMap == 0) {
                	browserFx.repaint();
                	contentFxPanel.repaint();                	
                }
                 

         
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