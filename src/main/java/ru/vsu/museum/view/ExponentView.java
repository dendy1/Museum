package ru.vsu.museum.view;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.ExponentService;
import ru.vsu.museum.service.LocationService;

import java.util.List;
import java.util.Scanner;

public class ExponentView {
    private Scanner scanner = new Scanner(System.in);
    private ExponentService exponentService = new ExponentService();
    private CategoryService categoryService = new CategoryService();
    private LocationService locationService = new LocationService();

    public void show() {
        while (true) {
            System.out.println("====Exponent Menu====");
            System.out.println("1. Показать экспонаты (Введите 1)");
            System.out.println("2. Полная информация об экспонате (Введите 2)");
            System.out.println("3. Добавить экспонат (Введите 3)");
            System.out.println("4. Удалить экспонат (Введите 4)");
            System.out.println("5. Обновить экспонат (Введите 5)");
            System.out.println("10. Выход (Введите 10)");
            System.out.print("Ваш выбор: ");

            int menuItem = scanner.nextInt();
            long itemId;
            switch (menuItem) {
                case 1:
                    printAllExponents();
                    break;
                case 2:
                    printAllExponents();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    Exponent exponent = exponentService.getById(itemId);

                    System.out.println("ID: " + exponent.getExponentId());
                    System.out.println("Имя: " + exponent.getName());

                    Category category = categoryService.getById(exponent.getCategoryId());
                    System.out.println("Категория: " + category.getName());

                    Location location = locationService.getById(exponent.getLocationId());
                    System.out.println("Местоположение: " + location.getName());
                    break;
                case 3:
                    System.out.print("Введите название: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();

                    System.out.println("Введите id категории: ");
                    printAllCategories();
                    long categoryId = scanner.nextLong();

                    System.out.println("Введите id местоположения: ");
                    printAllLocations();
                    long locationId = scanner.nextLong();

                    exponentService.add(new Exponent(exponentService.getLastId() + 1, name, categoryId, locationId));
                    break;
                case 4:
                    printAllExponents();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    exponentService.deleteById(itemId);
                    break;
                case 5:
                    printAllExponents();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();

                    System.out.print("Введите Имя: ");
                    scanner.nextLine();
                    String newName = scanner.nextLine();

                    System.out.println("Введите id категории: ");
                    printAllCategories();
                    categoryId = scanner.nextLong();

                    System.out.println("Введите id местоположения: ");
                    printAllLocations();
                    locationId = scanner.nextLong();

                    exponentService.update(new Exponent(itemId, newName, categoryId, locationId));
                    break;
                case 10:
                    return;
            }
        }
    }

    private void printAllExponents() {
        List<Exponent> exponents = exponentService.getAll();
        for (Exponent exponent: exponents) {
            System.out.println(exponent.getExponentId() + ". " + exponent.getName());
        }
    }

    private void printAllCategories() {
        List<Category> categories = categoryService.getAll();
        for (Category category: categories) {
            System.out.println(category.getCategoryId() + ". " + category.getName());
        }
    }

    private void printAllLocations() {
        List<Location> locations = locationService.getAll();
        for (Location location: locations) {
            System.out.println(location.getLocationId() + ". " + location.getName());
        }
    }
}
