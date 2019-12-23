package ru.vsu.museum;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.persistence.repositories.DatabaseCreator;
import ru.vsu.museum.view.MainMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseCreator dbCreator = new DatabaseCreator();
        //dbCreator.CreateDatabases();

        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }
}
