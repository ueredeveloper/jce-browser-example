package application.jframe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import javafx.embed.swing.JFXPanel;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import service.HttpServer;

public class BrowserFXPanel extends JFXPanel {
    private static final long serialVersionUID = 1L;
    private CefApp cefApp_;
    private CefClient client_;
    private CefBrowser browser_;

    public BrowserFXPanel() {
        setLayout(new BorderLayout());
        setFocusable(false);
        initializeJCEF();
    }

    private void initializeJCEF() {
        CefAppBuilder builder = new CefAppBuilder();
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(CefApp.CefAppState state) {
                System.out.println("🔄 JCEF state changed: " + state);
                if (state == CefApp.CefAppState.TERMINATED) {
                    System.out.println("⚠️ JCEF terminated unexpectedly");
                    SwingUtilities.invokeLater(() -> {
                        // Handle unexpected termination if needed
                    });
                }
            }
        });

        CefSettings settings = builder.getCefSettings();
        settings.windowless_rendering_enabled = false;
        settings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_INFO;

        try {
            cefApp_ = builder.build();
            System.out.println("✅ JCEF initialized successfully");
        } catch (Exception e) {
            System.err.println("❌ JCEF build failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        client_ = cefApp_.createClient();
        System.out.println("✅ CefClient created");

        int PORT = 8080;

        try {
            new HttpServer(PORT);
            System.out.println("✅ HttpServer started on port " + PORT);
        } catch (IOException e) {
            System.err.println("❌ HttpServer failed: " + e.getMessage());
            e.printStackTrace();
        }

        String url = "http://localhost:" + PORT + "/maps/gmaps/index.html";

        try {
            browser_ = client_.createBrowser(url, false, false);
            System.out.println("✅ CefBrowser created");
        } catch (Exception e) {
            System.err.println("❌ Browser creation failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Add Browser Component
        Component browserComponent = browser_.getUIComponent();
   
       add(browserComponent);

        
    }

    public JFXPanel getPanel() {
        return this;
    }
}