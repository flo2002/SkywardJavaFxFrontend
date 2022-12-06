package fhv.ws22.se.skyward.view.util;

import javafx.event.Event;

public interface NavigationService {
    void navigate(Event event, String path, String stageTitle);
}
