package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class BookingController extends AbstractController {
    private Boolean editable = true;
    @FXML
    private Button checkInCheckOutButton;
    @FXML
    private Button invoiceButton;
    @FXML
    private Button addRoomButton;
    @FXML
    private Button addCustomerButton;

    @FXML
    public Label bNrPlaceholder;

    @FXML
    public Label nights;

    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private TableView<RoomDto> roomTable;
    @FXML
    private TableColumn<RoomDto, Integer> roomNumberCol;
    @FXML
    private TableColumn<RoomDto, String> roomTypeNameCol;
    @FXML
    private TableColumn<RoomDto, String> roomStateNameCol;

    @FXML
    private TableView<CustomerDto> customerTable;
    @FXML
    private TableColumn<CustomerDto, String> firstNameCol;
    @FXML
    private TableColumn<CustomerDto, String> lastNameCol;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();

        checkInDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });
        checkOutDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now().plusDays(1)) < 0);
            }
        });


        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        roomStateNameCol.setCellValueFactory(new PropertyValueFactory<>("roomStateName"));

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        configureListener();

        updateData();
    }

    private void configureListener() {
        checkInDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDateTime checkInDateTime = LocalDateTime.of(newValue.getYear(), newValue.getMonth(), newValue.getDayOfMonth(), 15, 0);
                tmpBooking.setCheckInDateTime(checkInDateTime);
                updateData();
            }
        });
        checkOutDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDateTime checkOutDateTime = LocalDateTime.of(newValue.getYear(), newValue.getMonth(), newValue.getDayOfMonth(), 11, 0);
                tmpBooking.setCheckOutDateTime(checkOutDateTime);
                updateData();
            }
        });

    }

    @FXML
    public void onCheckInCheckOutButtonClick(Event event) {
        if (checkInCheckOutButton.getText().equals("Check-In")) {
            tmpBooking.setIsCheckedIn(true);
            updateData();
            checkInCheckOutButton.setText("Check-Out");
        } else if (checkInCheckOutButton.getText().equals("Check-Out")){
            tmpBooking.setIsCheckedIn(false);
            updateData();
            checkInCheckOutButton.setText("Checked-Out");
            session.update(tmpBooking.getId(), tmpBooking);
            controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice.fxml", "Invoice");
        }
    }

    @FXML
    public void onCreateBookingButtonClick(Event event) {
        session.update(tmpBooking.getId(), tmpBooking);

        if (tmpBooking.getCustomers()== null || tmpBooking.getCustomers().isEmpty()) {
            NotificationUtil.getInstance().showErrorNotification("Please add a Guest", event);
            customerTable.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

        } else if (tmpBooking.getRooms()== null || tmpBooking.getRooms().isEmpty()) {
            NotificationUtil.getInstance().showErrorNotification("Please add a room", event);
            roomTable.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        else {
            session.resetTmpBooking();
            NotificationUtil.getInstance().showSuccessNotification("The Booking was saved", event);
            controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/dashboard.fxml", "Dashboard");
        }
    }

    public void onDeleteBookingButtonClick(ActionEvent event) {
        boolean isSure = NotificationUtil.showAskNotification("Are you sure to delete this booking?", event);

        if (isSure){
            session.delete(tmpBooking.getId(), BookingDto.class);
            session.resetTmpBooking();
            NotificationUtil.getInstance().showSuccessNotification("The Booking was deleted", event);
            controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/dashboard.fxml", "Dashboard");
        } else {
            NotificationUtil.getInstance().showSuccessNotification("The Booking wasn't deleted", event);
        }
    }

    @FXML
    public void onAddRoomButtonClick(Event event) {
        session.update(tmpBooking.getId(), tmpBooking);

        if (tmpBooking.getCheckInDateTime() == null || tmpBooking.getCheckOutDateTime() == null) {
            NotificationUtil.getInstance().showErrorNotification("Please select a Check-in and Check-out date", event);
            checkInDatePicker.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            checkOutDatePicker.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }

        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-rooms.fxml", "Rooms");
    }

    @FXML
    public void onAddGuestButtonClick(Event event) {
        session.update(tmpBooking.getId(), tmpBooking);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Guests");
    }

    @FXML
    public void onInvoiceButtonClick(Event event) {
        session.update(tmpBooking.getId(), tmpBooking);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice.fxml", "Invoice");
    }

    public void updateData() {
        if (tmpBooking.getCheckInDateTime() != null && tmpBooking.getCheckOutDateTime() != null) {
            if (!tmpBooking.getIsCheckedIn() && tmpBooking.getCheckOutDateTime().toLocalDate().minusDays(1).isBefore(LocalDate.now())) {
                editable = false;
            }
            nights.setText(String.valueOf(Duration.between(tmpBooking.getCheckInDateTime(), tmpBooking.getCheckOutDateTime()).toDays() + 1));
        }
        
        if (!editable) {
            checkInCheckOutButton.setDisable(true);
            invoiceButton.setDisable(true);
            addRoomButton.setDisable(true);
            addCustomerButton.setDisable(true);
            checkInDatePicker.setDisable(true);
            checkOutDatePicker.setDisable(true);
        }
        
        if (tmpBooking.getIsCheckedIn() != null && tmpBooking.getIsCheckedIn()) {
            checkInCheckOutButton.setText("Check-Out");
        }
        if (tmpBooking.getIsCheckedIn() != null && !tmpBooking.getIsCheckedIn()) {
            checkInCheckOutButton.setText("Check-In");
        }

        if (tmpBooking.getCheckInDateTime() != null) {
            checkInDatePicker.setValue(tmpBooking.getCheckInDateTime().toLocalDate());
        }
        if (tmpBooking.getCheckOutDateTime() != null) {
            checkOutDatePicker.setValue(tmpBooking.getCheckOutDateTime().toLocalDate());
        }

        customerTable.getItems().clear();
        List<CustomerDto> customers = tmpBooking.getCustomers();

        if (customers != null) {
            customerTable.getItems().addAll(customers);
        }

        roomTable.getItems().clear();
        List<RoomDto> rooms = tmpBooking.getRooms();

        if (rooms != null) {
            roomTable.getItems().addAll(rooms);
        }

        invoiceButton.setDisable(rooms == null || customers == null || rooms.isEmpty() || customers.isEmpty() || tmpBooking.getCheckOutDateTime() == null);
        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());
    }
}
