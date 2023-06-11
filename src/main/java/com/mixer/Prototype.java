package com.mixer;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.mixer.views.CatalogView;
import com.mixer.views.EventView;
import com.mixer.views.PrimaryView;
import com.mixer.views.SecondaryView;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class Prototype extends Application {

    public static final String PRIMARY_VIEW = "Primary View";
    public static final String SECONDARY_VIEW = "Secondary View";
    public static final String EVENT_VIEW = "Create Event";
    public static final String CATALOG_VIEW = HOME_VIEW;

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        appManager.addViewFactory(PRIMARY_VIEW, () -> {
            View item = new PrimaryView().getView();
            item.setBottom(createBottomNavigation());
            return item;
        });
        appManager.addViewFactory(SECONDARY_VIEW, () -> {
            View item = new SecondaryView().getView();
            item.setBottom(createBottomNavigation());
            return item;
        });
        appManager.addViewFactory(EVENT_VIEW, () -> {
            View item = new EventView().getView();
            item.setBottom(createBottomNavigation());
            return item;
        });
        appManager.addViewFactory(CATALOG_VIEW, () -> {
            View item = new CatalogView().getView();
            item.setBottom(createBottomNavigation());
            return item;
        });

        DrawerManager.buildDrawer(appManager);
    }

    private BottomNavigation createBottomNavigation () {
        BottomNavigation bottomNavigation = new BottomNavigation();
        BottomNavigationButton btn1 = new BottomNavigationButton("Create Event", MaterialDesignIcon.CREATE.graphic(), e -> showView(EVENT_VIEW));
        BottomNavigationButton btn2 = new BottomNavigationButton("Event Catalog", MaterialDesignIcon.DASHBOARD.graphic(), e -> showView(CATALOG_VIEW));

        bottomNavigation.getActionItems().addAll(btn2, btn1);

        return bottomNavigation;
    }

    private void showView(String viewName) {
       appManager.switchView(viewName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMaximized(true);
        appManager.start(primaryStage);
    }

    private void postInit(Scene scene) {
        Swatch.DEEP_ORANGE.assignTo(scene);

        scene.getStylesheets().add(Prototype.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Prototype.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String args[]) {
        launch(args);
    }
}
