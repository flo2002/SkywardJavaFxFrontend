package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.RoomCapacity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoomCapacityController extends AbstractController {
    @FXML
    private TableView<RoomCapacity> table;

    private int roomCapacityShift = 0;

    private Boolean isOccupied(RoomDto askedRoom, LocalDate date) {
        List<RoomDto> availableRooms = session.getAvailableRooms(LocalDateTime.of(date, LocalTime.now()), LocalDateTime.of(date.plusDays(1), LocalTime.now()));
        if (availableRooms != null) {
            for (RoomDto room : availableRooms) {
                if (room.getId().equals(askedRoom.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    protected void initialize() {
        List<RoomDto> rooms = session.getAll(RoomDto.class);
        List<RoomCapacity> roomCaps = new ArrayList<>();

        for (RoomDto room : rooms) {
            RoomCapacity roomCap = new RoomCapacity();

            roomCap.setRoomNumber(room.getRoomNumber());
            roomCap.setDay1(isOccupied(room, LocalDate.now().plusDays(roomCapacityShift)));
            roomCap.setDay2(isOccupied(room, LocalDate.now().plusDays(1+roomCapacityShift)));
            roomCap.setDay3(isOccupied(room, LocalDate.now().plusDays(2+roomCapacityShift)));
            roomCap.setDay4(isOccupied(room, LocalDate.now().plusDays(3+roomCapacityShift)));
            roomCap.setDay5(isOccupied(room, LocalDate.now().plusDays(4+roomCapacityShift)));

            roomCaps.add(roomCap);
        }

        TableColumn<RoomCapacity, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        List<TableColumn<RoomCapacity, String>> columns = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TableColumn<RoomCapacity, String> column = new TableColumn<>(LocalDate.now().plusDays(i + roomCapacityShift).format(DateTimeFormatter.ofPattern("dd.MM")) + " to " + LocalDate.now().plusDays(i + 1 + roomCapacityShift).format(DateTimeFormatter.ofPattern("dd.MM")));
            if (i == 0) {
                column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay1() ? "free" : "occupied"));
            } else if (i == 1) {
                column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay2() ? "free" : "occupied"));
            } else if (i == 2) {
                column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay3() ? "free" : "occupied"));
            } else if (i == 3) {
                column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay4() ? "free" : "occupied"));
            } else if (i == 4) {
                column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay5() ? "free" : "occupied"));
            } else {
                column.setCellValueFactory(entry -> new SimpleObjectProperty<>(""));
            }

            column.setCellFactory(col -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);
                        if (item.equals("occupied")) {
                            setStyle("-fx-text-fill: #f44336");
                        } else {
                            setStyle("-fx-text-fill: #50c878ff");
                        }
                    }
                }
            });

            columns.add(column);
        }

        table.getColumns().addAll(roomNumberCol, columns.get(0), columns.get(1), columns.get(2), columns.get(3), columns.get(4));
        table.getItems().addAll(roomCaps);
    }

    public void onMinusButtonClick(Event event) {
        roomCapacityShift -= 7;
        table.getColumns().clear();
        table.getItems().clear();
        initialize();
    }

    public void onPlusButtonClick(Event event) {
        roomCapacityShift += 7;
        table.getColumns().clear();
        table.getItems().clear();
        initialize();
    }
}
