package ru.vsu.museum.view;

import ru.vsu.museum.domain.Author;
import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.ExponentService;
import ru.vsu.museum.service.LocationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

                    System.out.println("ID: " + exponent.getId());
                    System.out.println("Имя: " + exponent.getName());
                    System.out.println("Описание: " + exponent.getDescription());
                    System.out.println("Дата создания: " + exponent.getCreateDate());

                    System.out.println("Авторы: ");
                    for (Author author: exponentService.getAuthors(exponent.getId())) {
                        System.out.println(author.getId() + ". " + author.getName());
                    }

                    Category category = categoryService.getById(exponent.getCategoryId());
                    System.out.println("Категория: " + category.getName());

                    Location location = locationService.getById(exponent.getLocationId());
                    System.out.println("Местоположение: " + location.getName() + "; " + location.getAddress() + "; " + location.getType());
                    break;
                case 3:
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    System.out.print("Введите название: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();

                    System.out.print("Введите описание: ");
                    String description = scanner.nextLine();

                    System.out.print("Введите дату создания экспоната в формате DD/MM/YYYY: ");
                    Date date;
                    try {
                        date = dateFormat.parse(scanner.nextLine());
                    } catch (ParseException e) {
                        System.out.print("Неправильно введена дата, попробуйте ещё");
                        break;
                    }

                    System.out.println("Введите id категории: ");
                    printAllCategories();
                    long categoryId = scanner.nextLong();

                    System.out.println("Введите id местоположения: ");
                    printAllLocations();
                    long locationId = scanner.nextLong();

                    exponentService.add(new Exponent(exponentService.getLastId() + 1, name, description, date, categoryId, locationId));
                    break;
                case 4:
                    printAllExponents();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    exponentService.deleteById(itemId);
                    break;
                case 10:
                    return;
            }
        }
    }

    private void printAllExponents()
    {
        ArrayList<Exponent> exponents = exponentService.getAll();
        for (Exponent exponent: exponents) {
            System.out.println(exponent.getId() + ". " + exponent.getName());
        }
    }

    private void printAllCategories() {
        ArrayList<Category> categories = categoryService.getAll();
        for (Category category: categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
    }

    private void printAllLocations() {
        ArrayList<Location> locations = locationService.getAll();
        for (Location location: locations) {
            System.out.println(location.getId() + ". " + location.getName() + "; " + location.getAddress() + "; " + location.getType());
        }
    }
}
