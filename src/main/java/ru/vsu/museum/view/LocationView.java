package ru.vsu.museum.view;

import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationView {
    private Scanner scanner = new Scanner(System.in);
    private LocationService locationService = new LocationService();

    public void show() {
        while (true) {
            System.out.println("====Location Menu====");
            System.out.println("1. Показать местоположения (Введите 1)");
            System.out.println("2. Полная информация о местоположении (Введите 2)");
            System.out.println("3. Добавить местоположение (Введите 3)");
            System.out.println("4. Удалить местоположение (Введите 4)");
            System.out.println("5. Обновить местоположение (Введите 5)");
            System.out.println("10. Выход (Введите 10)");
            System.out.print("Ваш выбор: ");

            int menuItem = scanner.nextInt();
            long itemId;
            switch (menuItem) {
                case 1:
                    printAllLocations();
                    break;
                case 2:
                    printAllLocations();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    Location location = locationService.getById(itemId);

                    System.out.println("ID: " + location.getId());
                    System.out.println("Имя: " + location.getName());

                    break;
                case 3:
                    System.out.print("Введите Имя: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();

                    locationService.add(new Location(locationService.getLastId() + 1, name));
                    break;
                case 4:
                    printAllLocations();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    locationService.deleteById(itemId);
                    break;
                case 5:
                    printAllLocations();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();

                    System.out.print("Введите Имя: ");
                    scanner.nextLine();
                    String newName = scanner.nextLine();

                    locationService.update(new Location(itemId, newName));
                    break;
                case 10:
                    return;
            }
        }
    }

    private void printAllLocations() {
        List<Location> locations = locationService.getAll();
        for (Location location: locations) {
            System.out.println(location.getId() + ". " + location.getName());
        }
    }
}
