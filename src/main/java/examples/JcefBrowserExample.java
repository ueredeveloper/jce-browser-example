package examples;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;
import org.cef.handler.CefMessageRouterHandlerAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import service.HttpServer;



/**
 * This is a simple example application using JCEF. It displays a JFrame with a
 * JTextField at its top and a CefBrowser in its center. The JTextField is used
 * to enter and assign an URL to the browser UI. No additional handlers or
 * callbacks are used in this example.
 *
 * The number of used JCEF classes is reduced (nearly) to its minimum and should
 * assist you to get familiar with JCEF.
 *
 * For a more feature complete example have also a look onto the example code
 * within the package "tests.detailed".
 */
public class JcefBrowserExample extends JFrame {
	private static final long serialVersionUID = -5570653778104813836L;
	private final JTextField address_;
	private final CefApp cefApp_;
	private final CefClient client_;
	private final CefBrowser browser_;
	private final Component browerUI_;
	private boolean browserFocus_ = true;

	/**
	 * To display a simple browser window, it suffices completely to create an
	 * instance of the class CefBrowser and to assign its UI component to your
	 * application (e.g. to your content pane). But to be more verbose, this CTOR
	 * keeps an instance of each object on the way to the browser UI.
	 */
	private JcefBrowserExample(String startURL, boolean useOSR, boolean isTransparent, String[] args)
			throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
		// (0) Initialize CEF using the maven loader
		CefAppBuilder builder = new CefAppBuilder();
		// windowless_rendering_enabled must be set to false if not wanted.
		builder.getCefSettings().windowless_rendering_enabled = useOSR;
		// USE builder.setAppHandler INSTEAD OF CefApp.addAppHandler!
		// Fixes compatibility issues with MacOSX
		builder.setAppHandler(new MavenCefAppHandlerAdapter() {
			@Override
			public void stateHasChanged(org.cef.CefApp.CefAppState state) {
				// Shutdown the app if the native CEF part is terminated
				if (state == CefAppState.TERMINATED)
					System.exit(0);
			}
		});

		if (args.length > 0) {
			builder.addJcefArgs(args);
		}

		// (1) The entry point to JCEF is always the class CefApp. There is only one
		// instance per application and therefore you have to call the method
		// "getInstance()" instead of a CTOR.
		//
		// CefApp is responsible for the global CEF context. It loads all
		// required native libraries, initializes CEF accordingly, starts a
		// background task to handle CEF's message loop and takes care of
		// shutting down CEF after disposing it.
		//
		// WHEN WORKING WITH MAVEN: Use the builder.build() method to
		// build the CefApp on first run and fetch the instance on all consecutive
		// runs. This method is thread-safe and will always return a valid app
		// instance.
		cefApp_ = builder.build();

		// (2) JCEF can handle one to many browser instances simultaneous. These
		// browser instances are logically grouped together by an instance of
		// the class CefClient. In your application you can create one to many
		// instances of CefClient with one to many CefBrowser instances per
		// client. To get an instance of CefClient you have to use the method
		// "createClient()" of your CefApp instance. Calling an CTOR of
		// CefClient is not supported.
		//
		// CefClient is a connector to all possible events which come from the
		// CefBrowser instances. Those events could be simple things like the
		// change of the browser title or more complex ones like context menu
		// events. By assigning handlers to CefClient you can control the
		// behavior of the browser. See tests.detailed.MainFrame for an example
		// of how to use these handlers.
		client_ = cefApp_.createClient();

		// (4) One CefBrowser instance is responsible to control what you'll see on
		// the UI component of the instance. It can be displayed off-screen
		// rendered or windowed rendered. To get an instance of CefBrowser you
		// have to call the method "createBrowser()" of your CefClient
		// instances.
		//
		// CefBrowser has methods like "goBack()", "goForward()", "loadURL()",
		// and many more which are used to control the behavior of the displayed
		// content. The UI is held within a UI-Compontent which can be accessed
		// by calling the method "getUIComponent()" on the instance of CefBrowser.
		// The UI component is inherited from a java.awt.Component and therefore
		// it can be embedded into any AWT UI.
		browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
		browerUI_ = browser_.getUIComponent();

		// (5) For this minimal browser, we need only a text field to enter an URL
		// we want to navigate to and a CefBrowser window to display the content
		// of the URL. To respond to the input of the user, we're registering an
		// anonymous ActionListener. This listener is performed each time the
		// user presses the "ENTER" key within the address field.
		// If this happens, the entered value is passed to the CefBrowser
		// instance to be loaded as URL.
		address_ = new JTextField(startURL, 100);
		address_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				URL resource = JcefBrowserExample.class.getResource("/maps/gmaps/index.html");
				System.out.println(resource);

				browser_.loadURL(resource.toExternalForm());
				browser_.loadURL(address_.getText());
			}
		});

		// Update the address field when the browser URL changes.
		client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
			@Override
			public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
				address_.setText(url);
			}
		});

		// Clear focus from the browser when the address field gains focus.
		address_.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!browserFocus_)
					return;
				browserFocus_ = false;
				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				address_.requestFocus();
			}
		});

		JButton zoomInButton = new JButton("Zoom In");
		JButton zoomOutButton = new JButton("Zoom Out");

		// Panel to hold buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(zoomInButton);
		buttonPanel.add(zoomOutButton);

		// Add the panel to the UI
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// Action Listeners for Zoom In
		zoomInButton.addActionListener(e -> {
			browser_.executeJavaScript("setZoomIn();", browser_.getURL(), 0);
		});

		// Action Listeners for Zoom Out
		zoomOutButton.addActionListener(e -> {
			browser_.executeJavaScript("setZoomOut();", browser_.getURL(), 0);
		});

		// Clear focus from the address field when the browser gains focus.
		client_.addFocusHandler(new CefFocusHandlerAdapter() {
			@Override
			public void onGotFocus(CefBrowser browser) {
				if (browserFocus_)
					return;
				browserFocus_ = true;
				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				browser.setFocus(true);
			}

			@Override
			public void onTakeFocus(CefBrowser browser, boolean next) {
				browserFocus_ = false;
			}
		});

		// (6) All UI components are assigned to the default content pane of this
		// JFrame and afterwards the frame is made visible to the user.
		getContentPane().add(address_, BorderLayout.NORTH);
		getContentPane().add(browerUI_, BorderLayout.CENTER);
		pack();
		setSize(800, 600);
		setVisible(true);

		// (7) To take care of shutting down CEF accordingly, it's important to call
		// the method "dispose()" of the CefApp instance if the Java
		// application will be closed. Otherwise you'll get asserts from CEF.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CefApp.getInstance().dispose();
				dispose();
			}
		});

		// (3) Create a simple message router to receive messages from CEF.
		CefMessageRouter msgRouter = CefMessageRouter.create();
		msgRouter.addHandler(new MessageRouterHandler(), true);
		browser_.getClient().addMessageRouter(msgRouter);

		/*
		 * msgRouter.addHandler(new CefMessageRouterHandlerAdapter() {
		 * 
		 * @SuppressWarnings("unused") public boolean onQuery(CefBrowser browser, long
		 * queryId, String request, boolean persistent, CefQueryCallback callback) {
		 * System.out.println("Received request: " + request);
		 * 
		 * try { JsonObject jsonRequest = new Gson().fromJson(request,
		 * JsonObject.class); String action = jsonRequest.has("action") ?
		 * jsonRequest.get("action").getAsString() : "";
		 * 
		 * System.out.println("Parsed action: " + action);
		 * 
		 * switch (action) { case "getData": JsonObject response = new JsonObject();
		 * response.addProperty("message", "Data received successfully!");
		 * callback.success(response.toString()); return true;
		 * 
		 * case "setData": String lat = jsonRequest.has("latitude") ?
		 * jsonRequest.get("latitude").getAsString() : ""; String lng =
		 * jsonRequest.has("longitude") ? jsonRequest.get("longitude").getAsString() :
		 * "";
		 * 
		 * System.out.println("Received coordinates: Lat=" + lat + ", Lng=" + lng);
		 * callback.success("{\"status\":\"success\"}"); return true;
		 * 
		 * default: callback.failure(404, "Unknown action"); return false; } } catch
		 * (Exception e) { e.printStackTrace(); callback.failure(500,
		 * "Internal Server Error"); return false; } }
		 * 
		 * }, true);
		 * 
		 * client_.addMessageRouter(msgRouter);
		 */

		// client_.addMessageRouter(msgRouter);

	}

	public static void main(String[] args)
			throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {

		boolean useOsr = false;
		// URL resource = Main.class.getResource("/maps/gmaps/index.html");
		// System.out.println(resource);

		int PORT = 8080;

		// Start the HTTP server
		new HttpServer(PORT);

		// Load JCEF with the local server URL
		String url = "http://localhost:" + PORT + "/maps/gmaps/index.html";
		System.out.println("Loading: " + url);

		new JcefBrowserExample(url, useOsr, false, args);
		
		for (java.lang.reflect.Method method : CefMessageRouterHandlerAdapter.class.getMethods()) {
		    System.out.println(method);
		}

	}

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