import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class WebViewSample extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Web View");
        try {
			scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Browser extends Region {
    final private WebView browser = new WebView();
    final private WebEngine webEngine = browser.getEngine();

    public Browser() throws URISyntaxException {
    	
    	URL url = this.getClass().getResource("");
    	
    	File f = new File(url.toURI());
    	
    	f = f.getParentFile();
    	f = new File(f, "form/form.html");

        webEngine.load("file:\\\\\\" + f);
        
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                     continueSetup();
                }
            }
        });
        
        getChildren().add(browser);
    }
    
    private void continueSetup() {
        
        
        EventListener listener = new EventListener() {
        	@Override
            public void handleEvent(Event ev) {
        		System.out.println("Event");
            }
        };

        Document doc = webEngine.getDocument();        
        Element el = doc.getElementById("submit");
        
        System.out.println(el);
        
        ((EventTarget) el).addEventListener("click", listener, false);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0,HPos.CENTER,VPos.CENTER);
    }
}