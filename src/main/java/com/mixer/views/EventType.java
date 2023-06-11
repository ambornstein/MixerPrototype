package com.mixer.views;

public enum EventType {
    OUTDOORS("Outdoors", "EXPLORE"),SPORTS("Sports", "GOLF_COURSE"), GAMING("Gaming", "GAMES"), PARTY("Party","TAG_FACES"), NETWORKING("Networking", "GROUP"), OTHER("Other","EVENT");
    String type;
    String icon;
    EventType(String t, String i) {
        type = t;
        icon = i;
    }
}
