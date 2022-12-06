package fhv.ws22.se.skyward.domain.dtos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
