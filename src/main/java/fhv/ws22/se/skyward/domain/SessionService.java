package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;

import java.rmi.Remote;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SessionService extends Remote {
    <T extends AbstractDto> List getAll(Class<T> clazz);
    <T extends AbstractDto> void add(T t);
    <T extends AbstractDto> void update(UUID id, T t);
    <T extends AbstractDto> void delete(UUID id, Class<T> clazz);
    List<RoomDto> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut);
}
