package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceOverviewController extends AbstractController {
    @FXML
    private TableView<InvoiceDto> table;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<InvoiceDto, BigInteger> invoiceNumberCol;
    @FXML
    private TableColumn<InvoiceDto, LocalDateTime> invoiceDateTimeCol;
    @FXML
    private TableColumn<InvoiceDto, Boolean> isPaidCol;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();

        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        invoiceDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("invoiceDateTime"));
        isPaidCol.setCellValueFactory(new PropertyValueFactory<>("isPaid"));

        table.setRowFactory(invoiceDtoTableView -> {
            TableRow<InvoiceDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    InvoiceDto rowData = row.getItem();
                    session.setTmpInvoice(rowData);
                    controllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/invoice.fxml", "Invoice");
                }
            });
            return row;
        });

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateData(newValue);
        });

        updateData("");
    }


    public void updateData(String filter) {
        table.getItems().clear();
        List<InvoiceDto> invoices = session.getAll(InvoiceDto.class);
        if (filter != null && !filter.isEmpty()) {
            invoices.removeIf(invoice -> invoice.getBooking().getCustomers().stream().noneMatch(customerDto -> {
                return customerDto.getFirstName().toLowerCase().contains(filter.toLowerCase()) || customerDto.getLastName().toLowerCase().contains(filter.toLowerCase());
            }));
        }
        table.getItems().addAll(invoices);
    }
}
