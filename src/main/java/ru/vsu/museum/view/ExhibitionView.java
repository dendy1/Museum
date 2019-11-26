package ru.vsu.museum.view;

import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.service.ExhibitionService;
import ru.vsu.museum.service.ExponentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ExhibitionView {
    private Scanner scanner = new Scanner(System.in);
    private ExhibitionService exhibitionService = new ExhibitionService();
    private ExponentService exponentService = new ExponentService();

    public void show() {
        while (true) {
            System.out.println("====Exhibition Menu====");
            System.out.println("1. Показать выставки (Введите 1)");
            System.out.println("2. Полная информация о выставке (Введите 2)");
            System.out.println("3. Добавить выставку (Введите 3)");
            System.out.println("4. Удалить выставку (Введите 4)");
            System.out.println("5. Обновить выставку (Введите 5)");
            System.out.println("10. Выход (Введите 10)");
            System.out.print("Ваш выбор: ");

            int menuItem = scanner.nextInt();
            long itemId;
            switch (menuItem) {
                case 1:
                    printAllExhibitions();
                    break;
                case 2:
                    printAllExhibitions();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    Exhibition exhibition = exhibitionService.getById(itemId);

                    System.out.println("ID: " + exhibition.getId());
                    System.out.println("Дата начала: " + exhibition.getDate());
                    System.out.println("Название: " + exhibition.getName());

                    System.out.println("Экспонаты: ");
                    for (Exponent exponent: exhibitionService.getExponents(exhibition.getId())) {
                        System.out.println(exponent.getId() + ". " + exponent.getName());
                    }
                    break;
                case 3:
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    System.out.print("Введите дату начала в формате DD/MM/YYYY: ");
                    scanner.nextLine();
                    Date date;
                    try {
                        date = dateFormat.parse(scanner.nextLine());
                    } catch (ParseException e) {
                        System.out.print("Неправильно введена дата, попробуйте ещё");
                        break;
                    }

                    System.out.print("Введите название: ");
                    String name = scanner.nextLine();

                    exhibitionService.add(new Exhibition(exhibitionService.getLastId() + 1, date, name));

                    while(scanner.nextInt() != -1) {
                        printAllExponents();
                        System.out.print("Введите id экспоната. Введите -1, если хотите завершить: ");
                        scanner.nextLine();
                        long exponentId = scanner.nextLong();
                        exhibitionService.addExponent(exhibitionService.getLastId() + 1, exponentId);
                    }
                    break;
                case 4:
                    printAllExhibitions();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    exhibitionService.deleteById(itemId);
                    break;
                case 5:
                    dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    printAllExhibitions();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();

                    System.out.print("Введите дату начала в формате DD/MM/YYYY: ");
                    try {
                        date = dateFormat.parse(scanner.nextLine());
                    } catch (ParseException e) {
                        System.out.print("Неправильно введена дата, попробуйте ещё");
                        break;
                    }

                    System.out.print("Введите Имя: ");
                    String newName = scanner.nextLine();

                    exhibitionService.update(new Exhibition(itemId, date, newName));
                    break;
                case 10:
                    return;
            }
        }
    }

    private void printAllExhibitions() {
        for (Exhibition exhibition: exhibitionService.getAll()) {
            System.out.println(exhibition.getId() + ". " + exhibition.getName() + "; " + exhibition.getDate());
        }
    }

    private void printAllExponents() {
        for (Exponent exponent: exponentService.getAll()) {
            System.out.println(exponent.getId() + ". " + exponent.getName());
        }
    }
}
