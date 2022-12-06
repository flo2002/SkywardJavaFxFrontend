package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchCustomerController extends AbstractController {
    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<CustomerDto> customerTable;
    @FXML
    private TableColumn<CustomerDto, String> firstNameCol;
    @FXML
    private TableColumn<CustomerDto, String> lastNameCol;
    @FXML
    private TableColumn<CustomerDto, CheckBox> checkboxCol;

    @FXML
    private TableColumn<CustomerDto, String> addressCol;
    @FXML
    public Label bNrPlaceholder;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();
        List<CustomerDto> selectedCustomer = tmpBooking.getCustomers();

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getAddress().getStreet() + " " + entry.getValue().getAddress().getHouseNumber()));
        checkboxCol.setCellValueFactory(entry -> {
            CheckBox checkBox = new CheckBox();

            for (CustomerDto customer : tmpBooking.getCustomers()) {
                if (customer.getId() == entry.getValue().getId()) {
                    checkBox.setSelected(true);
                }
            }

            checkBox.setOnAction(event -> {
                selectedCustomer.removeIf(customer -> customer.getId() == entry.getValue().getId());
                if (checkBox.isSelected()) {
                    selectedCustomer.add(entry.getValue());
                }
                tmpBooking.setCustomers(selectedCustomer);
            });
            return new SimpleObjectProperty<>(checkBox);
        });

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateData(newValue);
        });

        updateData("");

        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());

        customerTable.setRowFactory(customerDtoTableView -> {
            TableRow<CustomerDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    onConfirmCustomerSearchButtonClick(mouseEvent);
                }
            });
            return row;
        });
    }

    @FXML
    public void onConfirmCustomerSearchButtonClick(Event event) {
        session.update(tmpBooking.getId(), tmpBooking);
        NotificationUtil.getInstance().showSuccessNotification("The guests were added to the booking", event);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onCreateButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-customers.fxml", "Home");
    }

    @FXML
    public void backButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }


    public void updateData(String filter) {
        customerTable.getItems().clear();
        List<CustomerDto> customers = session.getAll(CustomerDto.class);
        List<CustomerDto> filteredCustomers = new ArrayList<CustomerDto>();
        for (CustomerDto customer : customers) {
            if (customer.getFirstName().toLowerCase().contains(filter.toLowerCase())
                    || customer.getLastName().toLowerCase().contains(filter.toLowerCase())
                        || customer.getAddress().getStreet().toLowerCase().contains(filter.toLowerCase())
            ) {
                filteredCustomers.add(customer);
            }
        }
        customerTable.getItems().addAll(filteredCustomers);
    }
}
