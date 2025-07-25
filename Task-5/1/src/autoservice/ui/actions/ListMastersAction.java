package autoservice.ui.actions;

import autoservice.dto.CarServiceMastersQuery;
import autoservice.enums.SortCarServiceMasters;
import autoservice.model.CarServiceMaster;
import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ListMastersAction implements IAction {
    private final AutoServiceAdmin admin;
    private final Scanner scanner;

    public ListMastersAction(AutoServiceAdmin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\nСписок мастеров - параметры вывода:");
        System.out.println("1. Все мастера");
        System.out.println("2. Только занятые");
        System.out.println("3. Только свободные");
        System.out.println("4. Сортировка");
        System.out.print("Выберите вариант: ");

        int choice = scanner.nextInt();
        CarServiceMastersQuery.Builder builder = CarServiceMastersQuery.builder();

        switch (choice) {
            case 2:
                System.out.print("Введите дату для проверки занятости (гггг-мм-дд): ");
                String dateStr = scanner.next();
                builder.localDate(LocalDate.parse(dateStr)).isOccupied(true);
                break;
            case 3:
                System.out.print("Введите дату для проверки занятости (гггг-мм-дд): ");
                dateStr = scanner.next();
                builder.localDate(LocalDate.parse(dateStr)).isOccupied(false);
                break;
            case 4:
                System.out.println("Доступные варианты сортировки:");
                System.out.println("1. По имени");
                // другие варианты сортировки
                int sortChoice = scanner.nextInt();
                builder.sort(SortCarServiceMasters.NAME);
                break;
        }

        List<CarServiceMaster> masters = admin.getCarServiceMasters(builder.build());
        // Вывод результатов
    }
}
