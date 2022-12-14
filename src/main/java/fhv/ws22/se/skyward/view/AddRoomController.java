package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.List;

public class AddRoomController extends AbstractController {
    @FXML
    private TableView<RoomDto> roomTable;
    @FXML
    private TableColumn<RoomDto, Integer> roomNumberCol;
    @FXML
    private TableColumn<RoomDto, CheckBox> checkboxCol;
    @FXML
    private TableColumn<RoomDto, String> roomTypeNameCol;
    @FXML
    private TableColumn<RoomDto, String> roomStateNameCol;

    @FXML
    private CheckBox filterSingleRoom;
    @FXML
    private CheckBox filterDoubleRoom;
    @FXML
    private CheckBox filterTripleRoom;
    @FXML
    private CheckBox filterTwinRoom;
    @FXML
    private CheckBox filterQueenRoom;

    @FXML
    public Label bNrPlaceholder;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();
        List<RoomDto> selectedRooms = tmpBooking.getRooms();

        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomNumberCol.setSortType(TableColumn.SortType.ASCENDING);
        roomTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        roomStateNameCol.setCellValueFactory(new PropertyValueFactory<>("roomStateName"));
        checkboxCol.setCellValueFactory(entry -> {
            CheckBox checkBox = new CheckBox();

            for (RoomDto room : tmpBooking.getRooms()) {
                if (room.getId() == entry.getValue().getId()) {
                    checkBox.setSelected(true);
                }
            }

            checkBox.setOnAction(event -> {
                selectedRooms.removeIf(room -> room.getId() == entry.getValue().getId());
                if (checkBox.isSelected()) {
                    selectedRooms.add(entry.getValue());
                }
                tmpBooking.setRooms(selectedRooms);
            });
            return new SimpleObjectProperty<>(checkBox);
        });

        configureListener();
        updateData();
        roomTable.setRowFactory(roomDtoTableView -> {
            TableRow<RoomDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    onConfirmButtonClick(mouseEvent);
                }
            });
            return row;
        });
    }

    private void configureListener() {
        if (session.getRoomFilterMap().size() == 0) {
            HashMap<String, Boolean> filterMap = new HashMap<>();
            filterMap.put("Single", true);
            filterMap.put("Double", true);
            filterMap.put("Triple", false);
            filterMap.put("Twin", false);
            filterMap.put("Queen", false);
            session.setRoomFilterMap(filterMap);
        }
        HashMap<String, Boolean> filterMap = session.getRoomFilterMap();

        filterSingleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Single", filterSingleRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterDoubleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Double", filterDoubleRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterTripleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Triple", filterTripleRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterTwinRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Twin", filterTwinRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterQueenRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Queen", filterQueenRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
    }

    @FXML
    public void onConfirmButtonClick(Event event) {
        session.update(tmpBooking.getId(), tmpBooking);
        NotificationUtil.getInstance().showSuccessNotification("The Rooms were added to the booking", event);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void backButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    public void updateData() {
        HashMap<String, Boolean> filterMap = session.getRoomFilterMap();
        if (filterMap.get("Single")) {
            filterSingleRoom.setSelected(true);
        }
        if (filterMap.get("Double")) {
            filterDoubleRoom.setSelected(true);
        }
        if (filterMap.get("Triple")) {
            filterTripleRoom.setSelected(true);
        }
        if (filterMap.get("Twin")) {
            filterTwinRoom.setSelected(true);
        }
        if (filterMap.get("Queen")) {
            filterQueenRoom.setSelected(true);
        }

        List<RoomDto> rooms = session.getAvailableRooms(tmpBooking.getCheckInDateTime(), tmpBooking.getCheckOutDateTime());
        rooms = session.filterRooms(rooms, filterMap);
        rooms.addAll(tmpBooking.getRooms());

        roomTable.getItems().clear();
        roomTable.getItems().addAll(rooms);
        roomTable.getSortOrder().add(roomNumberCol);

        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());
    }
}
