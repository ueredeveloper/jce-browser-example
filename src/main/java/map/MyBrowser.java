package map;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import service.HttpServer;

public class MyBrowser extends JFrame {
	
	int width_;
	int height_;
	CefBrowser browser_;
	
	public MyBrowser(int width_, int height_, CefBrowser browser_) throws HeadlessException {
		super();
		this.width_ = width_;
		this.height_ = height_;
		this.browser_ = browser_;
	}

	public MyBrowser(int width_, int height_) throws HeadlessException {
		super();
		this.width_ = width_;
		this.height_ = height_;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyBrowser() {
		super();
		
	}

	public JComponent getBrowserComponent() {

		setSize(this.width_, this.height_);
	
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
		JComponent browserComponent = getBrowserUi(url, false, false, new String[] {});

		if (browserComponent != null) {
			return browserComponent;
			// panel.add(browserComponent, BorderLayout.CENTER);
		} else {

			System.err.println("❌ Failed to create JCEF browser component.");
			return null;
		}
		

	}

	private JComponent getBrowserUi(String startURL, boolean useOSR, boolean isTransparent, String[] args) {
		CefApp cefApp_;
		final CefClient client_;
		
		final JComponent browserUI_;

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
		
		CefMessageRouter msgRouter = CefMessageRouter.create();
		msgRouter.addHandler(new MessageRouterHandler(), true);
		browser_.getClient().addMessageRouter(msgRouter);
		
		browserUI_ = (JComponent) browser_.getUIComponent();

		// 🔹 Ensure it's visible
		browserUI_.setPreferredSize(new Dimension(this.width_, this.height_));
		browserUI_.setSize(this.width_, this.height_);


		return browserUI_;
	}
	
	class MessageRouterHandler extends CefMessageRouterHandlerAdapter {
		
		@Override
		public boolean onQuery(
		    CefBrowser browser, 
		    CefFrame frame,  // <-- Add this parameter
		    long query_id, 
		    String request, 
		    boolean persistent, 
		    CefQueryCallback callback) {
			
			Gson gson = new Gson();
	        JsonObject jsonObject = gson.fromJson(request, JsonObject.class);
	        
	        System.out.println(jsonObject);
		    
		    System.out.println("Received request: " + request);
		    callback.success("Response from Java");
		    return true;
		}

	}

	public CefBrowser getBrowser_() {
		// TODO Auto-generated method stub
		return this.browser_;
	}

}
