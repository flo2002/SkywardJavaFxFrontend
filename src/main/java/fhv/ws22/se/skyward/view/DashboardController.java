package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardController extends AbstractController {
    @FXML
    private TableView<BookingDto> arrivalTable;
    @FXML
    private TableView<BookingDto> departureTable;
    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<BookingDto, BigInteger> arrivalBookingNumberCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> arrivalCheckInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> arrivalCheckOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, String> arrivalIsCheckedInCol;

    @FXML
    private TableColumn<BookingDto, BigInteger> departureBookingNumberCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> departureCheckInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> departureCheckOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, String> departureIsCheckedInCol;
    @FXML
    protected void initialize() {
        arrivalBookingNumberCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        arrivalCheckInDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckInDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckInDateTime().toLocalDate()));
        arrivalCheckOutDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckOutDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckOutDateTime().toLocalDate()));
        arrivalIsCheckedInCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));
        arrivalCheckInDateTimeCol.setSortType(TableColumn.SortType.DESCENDING);

        departureBookingNumberCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        departureCheckInDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckInDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckInDateTime().toLocalDate()));
        departureCheckOutDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckOutDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckOutDateTime().toLocalDate()));
        departureIsCheckedInCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));
        departureCheckOutDateTimeCol.setSortType(TableColumn.SortType.DESCENDING);

        arrivalTable.setRowFactory(bookingDtoTableView -> {
            TableRow<BookingDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BookingDto rowData = row.getItem();
                    session.setTmpBooking(rowData);
                    controllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
                }
            });
            return row;
        });
        departureTable.setRowFactory(bookingDtoTableView -> {
            TableRow<BookingDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BookingDto rowData = row.getItem();
                    session.setTmpBooking(rowData);
                    controllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
                }
            });
            return row;
        });

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateData(newValue);
        });
        updateData("");
    }

    public void onCreateBookingButtonClick(Event event) {
        session.resetTmpBooking();
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    public void updateData(String filter) {
        arrivalTable.getItems().clear();
        List<BookingDto> arrivalTempBookings = session.getAll(BookingDto.class);
        List<BookingDto> arrivalBookings = new ArrayList<>();
        for (BookingDto arrivalTempBooking : arrivalTempBookings) {
            if (arrivalTempBooking.getCheckInDateTime() != null) {
                if (arrivalTempBooking.getCheckInDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() &&
                        arrivalTempBooking.getCheckInDateTime().getYear() == LocalDateTime.now().getYear()) {
                    arrivalBookings.add(arrivalTempBooking);
                }
            }
        }
        if (filter != null && !filter.isEmpty()) {
            arrivalBookings.removeIf(booking -> booking.getCustomers().stream().noneMatch(customerDto -> {
                return customerDto.getFirstName().toLowerCase().contains(filter.toLowerCase()) || customerDto.getLastName().toLowerCase().contains(filter.toLowerCase());
            }));
        }
        arrivalTable.getItems().addAll(arrivalBookings);
        arrivalTable.getSortOrder().setAll(arrivalCheckInDateTimeCol);

        departureTable.getItems().clear();
        List<BookingDto> departureTempBookings = session.getAll(BookingDto.class);
        List<BookingDto> departureBookings = new ArrayList<>();
        for (BookingDto departureTempBooking : departureTempBookings) {
            if (departureTempBooking.getCheckOutDateTime() != null) {
                if (departureTempBooking.getCheckOutDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() &&
                        departureTempBooking.getCheckOutDateTime().getYear() == LocalDateTime.now().getYear()) {
                    departureBookings.add(departureTempBooking);
                }
            }
        }
        if (filter != null && !filter.isEmpty()) {
            departureBookings.removeIf(booking -> booking.getCustomers().stream().noneMatch(customerDto -> {
                return customerDto.getFirstName().toLowerCase().contains(filter.toLowerCase()) || customerDto.getLastName().toLowerCase().contains(filter.toLowerCase());
            }));
        }
        departureTable.getItems().addAll(departureBookings);
        departureTable.getSortOrder().setAll(departureCheckOutDateTimeCol);
    }
}
