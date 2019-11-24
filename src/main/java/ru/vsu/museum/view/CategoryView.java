package ru.vsu.museum.view;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.service.CategoryService;
import ru.vsu.museum.service.ExponentService;

import java.util.ArrayList;
import java.util.Scanner;

public class CategoryView {
    private Scanner scanner = new Scanner(System.in);
    private CategoryService categoryService = new CategoryService();

    public void show() {
        while (true) {
            System.out.println("====Category Menu====");
            System.out.println("1. Показать категории (Введите 1)");
            System.out.println("2. Полная информация о категории категории (Введите 2)");
            System.out.println("3. Добавить категорию (Введите 3)");
            System.out.println("4. Удалить категорию (Введите 4)");
            System.out.println("10. Выход (Введите 10)");
            System.out.print("Ваш выбор: ");

            int menuItem = scanner.nextInt();
            switch (menuItem) {
                case 1:
                    printAllCategories();
                    break;
                case 2:
                    printAllCategories();
                    System.out.print("Введите ID: ");
                    long catId = scanner.nextLong();
                    Category cat = categoryService.getById(catId);
                    System.out.println("Категория: " + cat.getName());
                    System.out.println("Описание: " + cat.getDescription());
                    System.out.println("Экспонаты: ");
                    for (Exponent exponent: categoryService.getExponents(catId)) {
                        System.out.println(exponent.getName());
                    }
                    break;
                case 3:
                    System.out.print("Введите Имя: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();

                    System.out.print("Введите Описание: ");
                    String description = scanner.nextLine();

                    categoryService.add(new Category(categoryService.getLastId() + 1, name, description));
                    break;
                case 4:
                    printAllCategories();
                    System.out.print("Введите ID: ");
                    long itemId = scanner.nextLong();
                    categoryService.deleteById(itemId);
                    break;
                case 10:
                    return;
            }
        }
    }

    private void printAllCategories()
    {
        for (Category category: categoryService.getAll()) {
            System.out.println(category.getId() + ". " + category.getName());
        }
    }
}
