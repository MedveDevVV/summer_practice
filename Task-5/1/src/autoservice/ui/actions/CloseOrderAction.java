package autoservice.ui.actions;

import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.util.Scanner;
import java.util.UUID;

public class CloseOrderAction implements IAction {
    private final AutoServiceAdmin admin;
    private final Scanner scanner;

    public CloseOrderAction(AutoServiceAdmin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\nЗавершение заказа:");
        System.out.print("Введите ID заказа: ");
        String orderIdStr = scanner.next();

        try {
            UUID orderId = UUID.fromString(orderIdStr);
            admin.closedOrder(orderId);
            System.out.println("Заказ завершен!");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный формат ID заказа!");
        }
    }
}