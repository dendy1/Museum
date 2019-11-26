package ru.vsu.museum;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.view.MainMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        //PoolManager.getInstance().shutdown();
    }
}
