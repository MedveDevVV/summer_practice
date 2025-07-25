package autoservice.ui.actions;

import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.time.Period;
import java.util.Scanner;
import java.util.UUID;

public class DelayOrderAction implements IAction {
    private final AutoServiceAdmin admin;
    private final Scanner scanner;

    public DelayOrderAction(AutoServiceAdmin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\nПеренос заказа:");
        System.out.print("Введите ID заказа: ");
        String orderIdStr = scanner.next();

        System.out.print("На сколько дней перенести? ");
        int days = scanner.nextInt();

        try {
            UUID orderId = UUID.fromString(orderIdStr);
            admin.delayOrder(orderId, Period.ofDays(days));
            System.out.println("Заказ перенесен на " + days + " дней!");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный формат ID заказа!");
        }
    }
}