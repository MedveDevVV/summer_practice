package autoservice.ui.actions;

import autoservice.model.CarServiceMaster;
import autoservice.model.WorkshopPlace;
import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CreateOrderAction implements IAction {
    private final AutoServiceAdmin admin;
    private final Scanner scanner;

    public CreateOrderAction(AutoServiceAdmin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\nСоздание нового заказа:");

        // Выбор мастера
        List<CarServiceMaster> masters = admin.getCarServiceMasters(null);
        System.out.println("Доступные мастера:");
        for (int i = 0; i < masters.size(); i++) {
            System.out.println((i + 1) + ". " + masters.get(i).getFullName());
        }
        System.out.print("Выберите мастера: ");
        int masterChoice = scanner.nextInt();

        // Выбор рабочего места
        List<WorkshopPlace> places = admin.getAvailablePlaces(LocalDate.now());
        System.out.println("Доступные рабочие места:");
        for (int i = 0; i < places.size(); i++) {
            System.out.println((i + 1) + ". " + places.get(i).getName());
        }
        System.out.print("Выберите рабочее место: ");
        int placeChoice = scanner.nextInt();

        // Ввод данных заказа
        scanner.nextLine(); // очистка буфера
        System.out.print("Описание работ: ");
        String description = scanner.nextLine();

        System.out.print("Дата начала (гггг-мм-дд): ");
        String startDateStr = scanner.next();

        System.out.print("Дата окончания (гггг-мм-дд): ");
        String endDateStr = scanner.next();

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            UUID orderId = admin.createRepairOrder(
                    LocalDate.now(), startDate, endDate, description,
                    masters.get(masterChoice - 1), places.get(placeChoice - 1));

            System.out.println("Заказ создан! ID: " + orderId);
        } catch (Exception e) {
            System.out.println("Ошибка при создании заказа: " + e.getMessage());
        }
    }
}