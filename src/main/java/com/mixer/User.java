package com.mixer;

public enum User {
    TESTUSER("testuser", 25),
    JOHN("John", 31),
    MORTIMER("Mortimer", 20),
    PATRICIA("Patricia", 27);
    private String name;
    private int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
