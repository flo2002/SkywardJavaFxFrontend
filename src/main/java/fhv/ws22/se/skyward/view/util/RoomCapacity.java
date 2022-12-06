package fhv.ws22.se.skyward.view.util;

public class RoomCapacity {
    private Integer roomNumber;
    private Boolean day1;
    private Boolean day2;
    private Boolean day3;
    private Boolean day4;
    private Boolean day5;

    public RoomCapacity() {

    }

    public RoomCapacity(Integer roomNumber, Boolean day1, Boolean day2, Boolean day3, Boolean day4, Boolean day5) {
        this.roomNumber = roomNumber;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Boolean getDay1() {
        return day1;
    }
    public void setDay1(Boolean day1) {
        this.day1 = day1;
    }

    public Boolean getDay2() {
        return day2;
    }
    public void setDay2(Boolean day2) {
        this.day2 = day2;
    }

    public Boolean getDay3() {
        return day3;
    }
    public void setDay3(Boolean day3) {
        this.day3 = day3;
    }

    public Boolean getDay4() {
        return day4;
    }
    public void setDay4(Boolean day4) {
        this.day4 = day4;
    }

    public Boolean getDay5() {
        return day5;
    }
    public void setDay5(Boolean day5) {
        this.day5 = day5;
    }

    @Override
    public String toString() {
        return "RoomCapacity [roomNumber=" + roomNumber + ", day1=" + day1 + ", day2=" + day2 + ", day3=" + day3 + ", day4=" + day4 + ", day5=" + day5 + "]";
    }
}
