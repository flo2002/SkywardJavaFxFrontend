package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.domain.service.DomainService;
import fhv.ws22.se.skyward.domain.service.ServiceProviderService;
import fhv.ws22.se.skyward.domain.service.SessionService;
import fhv.ws22.se.skyward.domain.service.TmpDataService;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractController {
    protected static final Logger logger = LogManager.getLogger("JavaFxController");
    @Inject
    private SessionService session;
    protected DomainService domainService;
    protected TmpDataService tmpDataService;
    @Inject
    protected ControllerNavigationUtil controllerNavigationUtil;

    protected BookingDto tmpBooking;
    protected InvoiceDto tmpInvoice;

    @FXML
    protected void initialize() {
        ServiceProviderService serviceProvider = session.getServiceProvider();
        domainService = (DomainService) serviceProvider.getService(DomainService.class);
        tmpDataService = (TmpDataService) serviceProvider.getService(TmpDataService.class);
        System.out.println(serviceProvider);
        System.out.println(domainService);
        System.out.println(tmpDataService);
    }

    @FXML
    public void onHomeButtonClick(Event event) {
        if (tmpBooking != null) {
            domainService.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            domainService.update(tmpInvoice.getId(), tmpInvoice);
            tmpDataService.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/dashboard.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(Event event) {
        if (tmpBooking != null) {
            domainService.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            domainService.update(tmpInvoice.getId(), tmpInvoice);
            tmpDataService.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onInvoicePageButtonClick(Event event) {
        if (tmpBooking != null) {
            domainService.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            domainService.update(tmpInvoice.getId(), tmpInvoice);
            tmpDataService.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }

    @FXML
    public void onRoomCapacityButtonClick(Event event) {
        if (tmpBooking != null) {
            domainService.update(tmpBooking.getId(), tmpBooking);
        }
        if (tmpInvoice != null) {
            domainService.update(tmpInvoice.getId(), tmpInvoice);
            tmpDataService.resetTmpInvoice();
        }
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/room-capacity.fxml", "Room Capacity");
    }
}
