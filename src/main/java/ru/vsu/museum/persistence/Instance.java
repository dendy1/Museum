package ru.vsu.museum.persistence;

public class Instance {
    private static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            instance = new Instance();
        }

        return  instance;
    }
}
