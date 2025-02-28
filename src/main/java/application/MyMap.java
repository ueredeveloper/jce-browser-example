package application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import com.jogamp.opengl.awt.GLJPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import service.HttpServer;


// PRINCIPAL, TRABALHAR SEGUNDA 28/02/2025

public class MyMap extends JFrame {

	private static final long serialVersionUID = 1L;

	public MyMap() {

		setTitle("JCEF SwingNode Example");
		setSize(1200, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		GLJPanel SwingPanel = new GLJPanel();

		JFXPanel JFXPane = new JFXPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, SwingPanel, JFXPane);
		splitPane.setDividerLocation(800); 

		getContentPane().add(splitPane, BorderLayout.CENTER);

		pack();
		setSize(800, 600);
		setVisible(true);

		Platform.runLater(() -> {
			Component browserComponent = getBrowserComponent();
			SwingPanel.add(browserComponent, BorderLayout.CENTER);

		});
	}

	public Component getBrowserComponent() {

		setTitle("JCEF JFrame Example");
		setSize(900, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout());
		getContentPane().add(panel);

		int PORT = 8080;

		try {
			new HttpServer(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = "http://localhost:" + PORT + "/maps/gmaps/index.html";

		// Get the browser component
		// Se adicionar true o mapa fica lento e com os controles invertidos
		Component browserComponent = getBrowserUi(url, false, false, new String[] {});

		if (browserComponent != null) {
			return browserComponent;
			// panel.add(browserComponent, BorderLayout.CENTER);
		} else {

			System.err.println("❌ Failed to create JCEF browser component.");
			return null;
		}

	}

	public Component getBrowserUi(String startURL, boolean useOSR, boolean isTransparent, String[] args) {
		CefApp cefApp_;
		final CefClient client_;
		final CefBrowser browser_;
		final Component browserUI_;

		CefAppBuilder builder = new CefAppBuilder();
		builder.getCefSettings().windowless_rendering_enabled = useOSR;
		builder.setAppHandler(new MavenCefAppHandlerAdapter() {
			@Override
			public void stateHasChanged(CefAppState state) {
				if (state == CefAppState.TERMINATED)
					System.exit(0);
			}
		});

		try {
			cefApp_ = builder.build();
			System.out.println("✅ JCEF initialized successfully.");
		} catch (IOException | UnsupportedPlatformException | InterruptedException | CefInitializationException e) {
			e.printStackTrace();
			return null;
		}

		client_ = cefApp_.createClient();

		browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
		browserUI_ = browser_.getUIComponent();

		// 🔹 Ensure it's visible
		browserUI_.setPreferredSize(new Dimension(800, 600));
		browserUI_.setSize(800, 600);

		System.out.println("✅ Browser component created: " + startURL);

		return browserUI_;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MyMap::new);
	}
}
