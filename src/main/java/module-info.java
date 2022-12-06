module fhv.ws22.se.skyward {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires modelmapper;
    requires com.google.guice;
    requires java.rmi;


    exports fhv.ws22.se.skyward;
    opens fhv.ws22.se.skyward to javafx.fxml;

    exports fhv.ws22.se.skyward.view;
    opens fhv.ws22.se.skyward.view to javafx.fxml, com.google.guice;
    exports fhv.ws22.se.skyward.view.util;

    opens fhv.ws22.se.skyward.view.util to com.google.guice, javafx.fxml;
    exports fhv.ws22.se.skyward.domain;
    opens fhv.ws22.se.skyward.domain;
}