package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.AddressDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class AddCustomerController extends AbstractController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    public Label bNrPlaceholder;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private void initialize() {
        tmpBooking = session.getTmpBooking();
        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());

        zipTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!zipTextField.getText().matches("^[0-9]{4,5}(?:-[0-9]{1,4})?$")) {
                    zipTextField.setText("");
                }
            }
        });

        numberTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!numberTextField.getText().matches("^\\d+(\\s|-)?\\w*$")) {
                    numberTextField.setText("");
                }
            }
        });
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        tmpBooking = session.getTmpBooking();

        if (firstNameTextField.getText().isEmpty()) {
            firstNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at firstname field", event);
            return;
        }
        if (lastNameTextField.getText().isEmpty()) {
            lastNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at lastname field", event);
            return;
        }
        if (streetTextField.getText().isEmpty()) {
            streetTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at street field", event);
            return;
        }
        if (zipTextField.getText().isEmpty()) {
            zipTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at zip field", event);
            return;
        }
        if (countryTextField.getText().isEmpty()) {
            countryTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at country field", event);
            return;
        }
        if (numberTextField.getText().isEmpty()) {
            numberTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at numbers field", event);
            return;
        }
        if (cityTextField.getText().isEmpty()) {
            cityTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            NotificationUtil.getInstance().showErrorNotification("Wrong input at city field", event);
            return;
        }

        CustomerDto customerDto = new CustomerDto(firstNameTextField.getText(), lastNameTextField.getText(),
                new AddressDto(streetTextField.getText(), Integer.parseInt(numberTextField.getText()), Integer.parseInt(zipTextField.getText()),
                        cityTextField.getText(), countryTextField.getText()));

        try {
            session.add(customerDto);

            List<CustomerDto> customers = tmpBooking.getCustomers();
            customers.add(customerDto);
            tmpBooking.setCustomers(customers);

            NotificationUtil.getInstance().showSuccessNotification("The guest was added to the booking", event);
            controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
        } catch (Exception e) {
            try {
                NotificationUtil.getInstance().showErrorNotification(e.getCause().getCause().getCause().getMessage(), event);
            } catch (Exception ex) {
                NotificationUtil.getInstance().showErrorNotification(e.getMessage(), event);
                e.printStackTrace();
            }
        }

        
    }

    @FXML
    public void onSearchCustomerButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Search");
    }

    public void backButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Search");
    }
}
