package autoservice.ui.filters;

import autoservice.dto.OrderQuery;
import autoservice.enums.OrderStatus;
import autoservice.enums.SortOrders;
import autoservice.model.Order;
import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class OrderFilterAction implements IAction {
    private final AutoServiceAdmin admin;
    private final Scanner scanner;

    public OrderFilterAction(AutoServiceAdmin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        OrderQuery.Builder builder = OrderQuery.builder();

        System.out.println("\nФильтрация заказов:");
        System.out.println("1. По статусу");
        System.out.println("2. По мастеру");
        System.out.println("3. По дате создания");
        System.out.println("4. Сортировка");
        System.out.print("Выберите вариант фильтрации: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        switch (choice) {
            case 1:
                System.out.println("Доступные статусы:");
                for (OrderStatus status : OrderStatus.values()) {
                    System.out.println(status.ordinal() + ". " + status);
                }
                System.out.print("Выберите статус: ");
                int statusChoice = scanner.nextInt();
                builder.status(OrderStatus.values()[statusChoice]);
                break;

            case 2:
                // Реализация выбора мастера
                break;

            case 3:
                System.out.print("Введите начальную дату (гггг-мм-дд): ");
                String startDate = scanner.nextLine();
                System.out.print("Введите конечную дату (гггг-мм-дд): ");
                String endDate = scanner.nextLine();
                builder.startDate(LocalDate.parse(startDate));
                builder.endDate(LocalDate.parse(endDate));
                break;

            case 4:
                System.out.println("Доступные варианты сортировки:");
                for (SortOrders sort : SortOrders.values()) {
                    System.out.println(sort.ordinal() + ". " + sort.name());
                }
                System.out.print("Выберите сортировку: ");
                int sortChoice = scanner.nextInt();
                builder.sortOrders(SortOrders.values()[sortChoice]);
                break;
        }

        List<Order> orders = admin.getOrders(builder.build());
        // Вывод отфильтрованных заказов
    }
}