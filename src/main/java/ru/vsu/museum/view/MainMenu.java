package ru.vsu.museum.view;

import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);

    private CategoryView categoryView = new CategoryView();
    private ExponentView exponentView = new ExponentView();
    private ExhibitionView exhibitionView = new ExhibitionView();

    public void show() {
        while (true) {
            System.out.println("====Menu====");
            System.out.println("1. Меню категорий (Введите 1)");
            System.out.println("2. Меню экспонатов (Введите 2)");
            System.out.println("3. Меню выставок (Введите 3)");
            System.out.println("10. Выход (Введите 10)");
            System.out.print("Ваш выбор: ");

            int menuItem = scanner.nextInt();

            switch (menuItem) {
                case 1:
                    categoryView.show();
                    break;
                case 2:
                    exponentView.show();
                    break;
                case 3:
                    exhibitionView.show();
                    break;
                case 10:
                    System.exit(0);
                    break;
            }
        }
    }
}
