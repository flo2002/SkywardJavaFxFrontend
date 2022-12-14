package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.ServiceProviderService;
import fhv.ws22.se.skyward.domain.SessionService;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

public abstract class AbstractController {
    protected static final Logger logger = LogManager.getLogger("JavaFxController");
    protected SessionService session;
    @Inject
    private ServiceProviderService service;
    @Inject
    protected ControllerNavigationUtil controllerNavigationUtil;

    protected BookingDto tmpBooking;
    protected InvoiceDto tmpInvoice;

    @FXML
    protected void initialize() {
        try {
            session = (SessionService) service.getService("session");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHomeButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            session.update(tmpInvoice.getId(), tmpInvoice);
            session.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/dashboard.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            session.update(tmpInvoice.getId(), tmpInvoice);
            session.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onInvoicePageButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            session.update(tmpInvoice.getId(), tmpInvoice);
            session.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }

    @FXML
    public void onRoomCapacityButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            session.update(tmpInvoice.getId(), tmpInvoice);
            session.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/room-capacity.fxml", "Room Capacity");
    }
}
