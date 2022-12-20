package fhv.ws22.se.skyward.domain.service;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.List;

public interface TmpDataService extends Remote {
    List<RoomDto> filterRooms(List<RoomDto> rooms, HashMap<String, Boolean> filterMap);
    void setRoomFilterMap(HashMap<String, Boolean> filterMap);
    HashMap<String, Boolean> getRoomFilterMap();

    BookingDto getTmpBooking();
    void resetTmpBooking();
    void setTmpBooking(BookingDto booking);

    InvoiceDto getTmpInvoice();
    void resetTmpInvoice();
    void setTmpInvoice(InvoiceDto invoice);

    BigDecimal getPrice(String roomTypeName);
}
