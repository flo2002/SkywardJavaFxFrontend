package fhv.ws22.se.skyward.domain.dtos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class RoomDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("RoomDto");
    private Integer roomNumber;
    private String roomTypeName;
    private String roomStateName;

    public RoomDto() {
    }
    public RoomDto(Integer roomNumber, String roomTypeName, String roomStateName) {
        this.roomNumber = roomNumber;
        this.roomTypeName = roomTypeName;
        this.roomStateName = roomStateName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomStateName() {
        return roomStateName;
    }
    public void setRoomStateName(String roomStateName) {
        this.roomStateName = roomStateName;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "roomNumber=" + roomNumber +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", roomStateName='" + roomStateName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(roomNumber, roomDto.roomNumber) && Objects.equals(roomTypeName, roomDto.roomTypeName) && Objects.equals(roomStateName, roomDto.roomStateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomTypeName, roomStateName);
    }
}
