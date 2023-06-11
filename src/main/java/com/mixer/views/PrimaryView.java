package com.mixer.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;

import com.mixer.Prototype;
import javafx.fxml.FXMLLoader;

public class PrimaryView {

    public View getView() {
        try {
            View view = FXMLLoader.load(PrimaryView.class.getResource("primary.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
