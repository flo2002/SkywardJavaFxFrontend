package fhv.ws22.se.skyward.view.util;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationUtil {
    private static final Logger logger = LogManager.getLogger("NotificationController");
    private static final int POPUP_OFFSET = 15;

    private static NotificationUtil singleton;

    public static synchronized NotificationUtil getInstance() {
        if (singleton == null) {
            singleton = new NotificationUtil();
        }
        return singleton;
    }

    public static Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setConsumeAutoHidingEvents(false);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                popup.hide();
            }
        });

        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/css/styles.css").toURI().toURL();
            label.getStylesheets().add(url.toString());
        } catch (MalformedURLException e) {
            logger.error("objects: NotificationUtil, msg: " + e.getMessage());
            e.printStackTrace();
        }

        popup.getContent().add(label);
        return popup;
    }

    public void showSuccessNotification(String message, Event event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        final Popup popup = createPopup(message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(stage.getX() + POPUP_OFFSET);
                popup.setY(stage.getY() + stage.getHeight() - popup.getHeight() - POPUP_OFFSET);
            }
        });
        popup.getContent().get(0).getStyleClass().add("success_popup");
        popup.show(stage);
        logger.info("object: SuccessPopUp, msg: " + message);
    }

    public void showErrorNotification(String message, Event event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        final Popup popup = createPopup(message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(stage.getX() + POPUP_OFFSET);
                popup.setY(stage.getY() + stage.getHeight() - popup.getHeight() - POPUP_OFFSET);
            }
        });
        popup.getContent().get(0).getStyleClass().add("error_popup");
        popup.show(stage);
        logger.info("object: ErrorPopUp, msg: " + message);
    }

    public static boolean showAskNotification(String message, Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();

        return alert.getResult().getText().equals("OK");
    }
}
