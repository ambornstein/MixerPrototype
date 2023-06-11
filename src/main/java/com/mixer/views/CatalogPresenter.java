package com.mixer.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CardPane;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.mixer.Event;
import com.mixer.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.scene.Cursor;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CatalogPresenter extends PrimaryPresenter {

    private CardPane cards;
    public void initialize() {
        super.initialize("Event Catalog");
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                cards = new CardPane();
                cards.setMaxWidth(400);
                primary.setCenter(cards);
                try {
                    FileReader fileReader = new FileReader("src/main/java/com/mixer/views/events.csv");
                    CSVReader csvReader = new CSVReaderBuilder(fileReader)
                            .withSkipLines(1)
                            .build();
                    List<String[]> allData = csvReader.readAll();

                    for (String[] row : allData) {
                        Event thisEvent = new Event(User.valueOf(row[7]));
                        thisEvent.setTitle(row[0]);
                        thisEvent.setCapacity(Integer.parseInt(row[1]));
                        thisEvent.setDate(LocalDate.parse(row[2]));
                        thisEvent.setStartTime(LocalTime.parse(row[3]));
                        thisEvent.setLocation(row[4]);
                        thisEvent.setType(EventType.valueOf(row[5]));
                        thisEvent.setRestricted(Boolean.valueOf(row[6]));
                        makeCard(thisEvent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void makeCard(Event e) {
        HBox h = new HBox();
        Label label = new Label(e.getTitle());
        Label date = new Label(e.getDate().toString());
        Icon icon = new Icon(MaterialDesignIcon.valueOf(e.getType().icon));
        h.getChildren().addAll(icon, label, date);
        h.setOnMouseClicked(mouseEvent -> inspectEvent(e));
        h.setCursor(Cursor.HAND);
        cards.getItems().add(h);
    }

    public void inspectEvent(Event e) {
        Dialog inspect = new Dialog(e.getTitle()+ " - " + e.getType().type);
        Button register = new Button("Register");
        Button close = new Button("Close");
        register.setOnAction(actionEvent -> {
            e.addAttendant(User.TESTUSER);
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.WARNING,"Not implemented yet");
            alert.showAndWait();
        });
        close.setOnAction(actionEvent -> inspect.hide());
        inspect.getButtons().addAll(register,close);
        inspect.setGraphic(new Icon(MaterialDesignIcon.valueOf(e.getType().icon)));
        inspect.setContentText(String.format("Location: %s\nCreated by: %s\nCapacity: %d\nDate: %s\nStart Time: %s\nPrivate: %b\nRegistered Guests: %s",e.getLocation(),e.getCreator().toString(),e.getCapacity(), e.getDate(), e.getStartTime(), e.isRestricted(), e.getAttendants().toString()));
        inspect.showAndWait();
    }
}
