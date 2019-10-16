package ru.vsu.museum.view;

import ru.vsu.museum.domain.Author;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.service.AuthorService;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthorView {
    private Scanner scanner = new Scanner(System.in);
    private AuthorService authorService = new AuthorService();

    public void show() {
        while (true) {
            System.out.println("====Author Menu====");
            System.out.println("1. Показать авторов (Введите 1)");
            System.out.println("2. Полная информация об авторе (Введите 2)");
            System.out.println("3. Добавить автора (Введите 3)");
            System.out.println("4. Удалить автора (Введите 4)");
            System.out.println("10. Выход (Введите 10)");
            System.out.print("Ваш выбор: ");

            int menuItem = scanner.nextInt();
            long itemId;
            switch (menuItem) {
                case 1:
                    printAllAuthors();
                    break;
                case 2:
                    printAllAuthors();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    Author author = authorService.getById(itemId);

                    System.out.println("ID: " + author.getId());
                    System.out.println("Имя: " + author.getName());
                    System.out.println("Работы: ");
                    for (Exponent exponent: authorService.getExponents(author.getId())) {
                        System.out.println(exponent.getId() + ". " + exponent.getName() + "; " + exponent.getCreateDate());
                    }
                    break;
                case 3:
                    System.out.print("Введите Имя: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    authorService.add(new Author(authorService.getLastId() + 1, name));
                    break;
                case 4:
                    printAllAuthors();
                    System.out.print("Введите ID: ");
                    itemId = scanner.nextLong();
                    authorService.deleteById(itemId);
                    break;
                case 10:
                    return;
            }
        }
    }

    private void printAllAuthors()
    {
        ArrayList<Author> authors = authorService.getAll();
        for (Author author: authors) {
            System.out.println(author.getId() + ". " + author.getName());
        }
    }
}
