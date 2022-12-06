package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.ChargeableItemDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceController extends AbstractController {
    @FXML
    private Button payButton;
    @FXML
    private Button editButton;

    @FXML
    private Label payPlaceholder;
    @FXML
    private Label checkInDatePlaceholder;
    @FXML
    private Label checkOutDatePlaceholder;
    @FXML
    private Label namePlaceholder;
    @FXML
    private ComboBox<CustomerDto> namePlaceholderInput;
    @FXML
    private Label invoiceDatePlaceholder;
    @FXML
    private Label invoiceNumberPlaceholder;
    @FXML
    private Label bookingNumberPlaceholder;


    @FXML
    private Label hotelNamePlaceholder;
    @FXML
    private Label hotelStreetPlaceholder;
    @FXML
    private Label hotelCityPlaceholder;
    @FXML
    private Label hotelCountryPlaceholder;

    @FXML
    private Label customerStreetPlaceholder;
    @FXML
    private Label customerCityPlaceholder;
    @FXML
    private Label customerCountryPlaceholder;


    @FXML
    private TableView<ChargeableItemDto> chargeableItemTable;
    @FXML
    private TableColumn<ChargeableItemDto, String> itemNameCol;
    @FXML
    private TableColumn<ChargeableItemDto, BigDecimal> itemPriceCol;
    @FXML
    private TableColumn<ChargeableItemDto, Integer> itemQuantityCol;

    @FXML
    private Label totalPricePlaceholder;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();
        tmpInvoice = session.getTmpInvoice();

        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        namePlaceholderInput.getItems().addAll(tmpBooking.getCustomers());
        namePlaceholderInput.getSelectionModel().selectFirst();
        namePlaceholderInput.setConverter(new StringConverter<>() {
            @Override
            public String toString(CustomerDto customerDto) {
                if (customerDto == null) {
                    return "";
                } else {
                    return customerDto.getFirstName() + " " + customerDto.getLastName();
                }
            }
            @Override
            public CustomerDto fromString(String s) {
                return null;
            }
        });
        namePlaceholderInput.setVisible(false);

        updateData();
    }

    @FXML
    public void onSplitButtonClick(Event event){

    }

    @FXML
    public void onPayButtonClick(Event event){
        if (payButton.getText().equals("Pay")) {
            payButton.setText("Unpay");
            tmpInvoice.setIsPaid(true);
            updateData();
        } else if (payButton.getText().equals("Unpay")){
            payButton.setText("Pay");
            tmpInvoice.setIsPaid(false);
            updateData();
        }
    }

    @FXML
    public void onConfirmButtonClick(Event event){
        session.update(tmpInvoice.getId(), tmpInvoice);
        session.resetTmpInvoice();
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
    @FXML
    public void onEditButtonClick(Event event) {
        if (editButton.getText().equals("Edit")) {
            editButton.setText("Save Edit");
            namePlaceholder.setVisible(false);
            namePlaceholderInput.setVisible(true);
        } else if (editButton.getText().equals("Save Edit")) {
            editButton.setText("Edit");
            tmpInvoice.setBilledCustomer(namePlaceholderInput.getValue());
            namePlaceholder.setVisible(true);
            namePlaceholderInput.setVisible(false);
            updateData();
        }
    }

    @FXML
    public void onPrintButtonClick(Event event){

    }

    @FXML
    public void backButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    public void updateData() {
        hotelNamePlaceholder.setText(tmpInvoice.getCompanyName());
        hotelStreetPlaceholder.setText(tmpInvoice.getHotelAddress().getStreet() + " " + tmpInvoice.getHotelAddress().getHouseNumber());
        hotelCityPlaceholder.setText(tmpInvoice.getHotelAddress().getZipCode() + " " + tmpInvoice.getHotelAddress().getCity());
        hotelCountryPlaceholder.setText(tmpInvoice.getHotelAddress().getCountry());

        customerStreetPlaceholder.setText(tmpInvoice.getCustomerAddress().getStreet() + " " + tmpInvoice.getCustomerAddress().getHouseNumber());
        customerCityPlaceholder.setText(tmpInvoice.getCustomerAddress().getZipCode() + " " + tmpInvoice.getCustomerAddress().getCity());
        customerCountryPlaceholder.setText(tmpInvoice.getCustomerAddress().getCountry());

        checkInDatePlaceholder.setText(tmpBooking.getCheckInDateTime().toLocalDate().toString());
        checkOutDatePlaceholder.setText(tmpBooking.getCheckOutDateTime().toLocalDate().toString());
        namePlaceholder.setText(tmpInvoice.getBilledCustomer().getFirstName() + " " + tmpInvoice.getBilledCustomer().getLastName());

        if (tmpInvoice != null) {
            invoiceDatePlaceholder.setText(tmpInvoice.getInvoiceDateTime().toLocalDate().toString());
            payPlaceholder.setText(tmpInvoice.getIsPaid() ? "Yes" : "No");
            invoiceNumberPlaceholder.setText(tmpInvoice.getInvoiceNumber().toString());
            bookingNumberPlaceholder.setText(tmpBooking.getBookingNumber().toString());
        }

        chargeableItemTable.getItems().clear();
        List<ChargeableItemDto> chargeableItems = session.getAll(ChargeableItemDto.class);
        chargeableItems.removeIf(chargeableItemDto -> !chargeableItemDto.getBooking().getId().equals(tmpInvoice.getBooking().getId()));
        chargeableItemTable.getItems().addAll(chargeableItems);

        BigDecimal totalPrice = new BigDecimal(0);
        for (ChargeableItemDto chargeableItem : chargeableItems) {
            totalPrice = totalPrice.add(chargeableItem.getPrice().multiply(BigDecimal.valueOf(chargeableItem.getQuantity())));
        }

        totalPricePlaceholder.setText(totalPrice.toString());
    }
}
