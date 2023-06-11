package com.mixer.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class EventView {

    public View getView() {
        try {
            View view = FXMLLoader.load(EventView.class.getResource("event.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}