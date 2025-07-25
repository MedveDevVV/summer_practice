package autoservice.ui.actions;

import autoservice.dto.CarServiceMastersQuery;
import autoservice.model.CarServiceMaster;
import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FindAvailableMastersAction implements IAction {
    private final AutoServiceAdmin admin;
    private final Scanner scanner;

    public FindAvailableMastersAction(AutoServiceAdmin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\nПоиск свободных мастеров:");
        System.out.print("Введите дату (гггг-мм-дд): ");
        String dateStr = scanner.next();

        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<CarServiceMaster> masters = admin.getCarServiceMasters(
                    new CarServiceMastersQuery(date, false, null));

            System.out.println("Свободные мастера на " + date + ":");
            if (masters.isEmpty()) {
                System.out.println("Нет свободных мастеров на указанную дату!");
            } else {
                for (int i = 0; i < masters.size(); i++) {
                    System.out.println((i + 1) + ". " + masters.get(i).getFullName());
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода даты!");
        }
    }
}