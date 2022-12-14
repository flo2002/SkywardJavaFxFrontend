package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface SessionService extends Remote {
    <T extends AbstractDto> List getAll(Class<T> clazz) throws RemoteException;
    <T extends AbstractDto> void add(T t) throws RemoteException;
    <T extends AbstractDto> void update(UUID id, T t) throws RemoteException;
    <T extends AbstractDto> void delete(UUID id, Class<T> clazz) throws RemoteException;
    List<RoomDto> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) throws RemoteException;

    List<RoomDto> filterRooms(List<RoomDto> rooms, HashMap<String, Boolean> filterMap) throws RemoteException;
    void setRoomFilterMap(HashMap<String, Boolean> filterMap) throws RemoteException;
    HashMap<String, Boolean> getRoomFilterMap() throws RemoteException;

    BookingDto getTmpBooking() throws RemoteException;
    void resetTmpBooking() throws RemoteException;
    void setTmpBooking(BookingDto booking) throws RemoteException;

    InvoiceDto getTmpInvoice() throws RemoteException;
    void resetTmpInvoice() throws RemoteException;
    void setTmpInvoice(InvoiceDto invoice) throws RemoteException;
}
