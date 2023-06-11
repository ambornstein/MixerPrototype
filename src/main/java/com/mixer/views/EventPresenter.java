package com.mixer.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.*;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;

import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.converter.InputStreamInputConverter;
import com.gluonhq.connect.converter.JsonInputConverter;
import com.gluonhq.connect.converter.JsonOutputConverter;
import com.gluonhq.connect.converter.OutputStreamOutputConverter;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.FileClient;

import com.mixer.AutoCompleteAddressField;
import com.mixer.Event;
import com.mixer.Prototype;
import com.mixer.User;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class EventPresenter extends PrimaryPresenter {

    @FXML
    private View event;
    @FXML
    private GridPane input;
    @FXML
    private TextField title;
    @FXML
    private Button start;
    @FXML
    private TextField capacity;
    @FXML
    private Button date;
    @FXML
    private Button submit;
    @FXML
    private CheckBox restrict;
    @FXML
    private ComboBox type;
    private AutoCompleteAddressField location;
    private TimePicker timePicker;
    private DatePicker datePicker;

    public void initialize() {
        super.initialize("Create Event");
        location = new AutoCompleteAddressField();
        timePicker = new TimePicker();
        datePicker = new DatePicker();
        start.setOnAction(e -> timePicker.showAndWait());
        date.setOnAction(e -> datePicker.showAndWait());
        type.getItems().addAll(Arrays.stream(EventType.values()).map(eventType -> eventType.type).toArray());
        input.add(location,1,5);
        submit.setOnAction((e) -> validateInput());
        location.getEntryMenu().setOnAction((ActionEvent e) ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(javafx.event.Event.ANY, (javafx.event.Event event) ->
            {
                if (location.getLastSelectedObject() != null)
                {
                    location.setText(location.getLastSelectedObject().toString());
                }
            });
        });
    }

    public void writeEvent(Event event) {
        try {
            Writer fileClient = new FileWriter("src/main/java/com/mixer/views/events.csv",true);
            CSVWriter writer = new CSVWriter(fileClient);
            //writer.writeNext(new String[]{"Title", "Capacity", "Date", "Start Time","Location", "Type","Private","Creator"});
            writer.writeNext(new String[]{event.getTitle(), String.valueOf(event.getCapacity()), event.getDate().toString(), event.getStartTime().toString(),event.getLocation(), event.getType().toString().toUpperCase(), String.valueOf(event.isRestricted()), User.TESTUSER.toString()});
            fileClient.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        title.setText("");
        location.clear();
        capacity.setText("");
        restrict.setSelected(false);
        type.setValue(null);
    }
    public void validateInput() {
        try {
            Integer.parseInt(capacity.getText());
            if (datePicker.getDate().isBefore(LocalDate.now())) {
                Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.WARNING,"The date must be today or in the future");
                dialog.showAndWait();
            }
            else if (title.getText().isEmpty() || capacity.getText().isEmpty() || type.getSelectionModel().isEmpty() || location.getText().isEmpty()) {
                Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.WARNING,"Some information is missing, please fill out all the fields");
                dialog.showAndWait();
            }
            else {
                Event thisEvent = new Event(User.TESTUSER);
                thisEvent.setDate(datePicker.getDate());
                thisEvent.setStartTime(timePicker.getTime());
                thisEvent.setRestricted(restrict.isSelected());
                thisEvent.setTitle(title.getText());
                thisEvent.setCapacity(Integer.parseInt(capacity.getText()));
                thisEvent.setLocation(location.getText());
                thisEvent.setType(EventType.valueOf(type.getValue().toString().toUpperCase()));
                writeEvent(thisEvent);
                Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,"Event created successfully");
                dialog.setOnCloseRequest(dialogEvent -> {clear();AppManager.getInstance().switchView(Prototype.CATALOG_VIEW);});
                dialog.showAndWait();
            }
        }
        catch (NumberFormatException e) {
            Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.WARNING,"Capacity must be a number");
            dialog.showAndWait();
        }
    }
}
