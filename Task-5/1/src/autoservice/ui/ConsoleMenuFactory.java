package autoservice.ui;

import autoservice.service.AutoServiceAdmin;
import autoservice.ui.actions.*;

public class ConsoleMenuFactory extends BaseMenuFactory {
    private final AutoServiceAdmin admin;
    private final Navigator navigator;

    public ConsoleMenuFactory(AutoServiceAdmin admin) {
        this.admin = admin;
        this.navigator = Navigator.getInstance();
    }

    @Override
    public Menu createMainMenu() {
        Menu mainMenu = new Menu("Главное меню");

        mainMenu.addMenuItem(new MenuItem("Мастера",
                () -> navigator.setCurrentMenu(createMastersMenu()),
                null, MenuAction.EXECUTE));

        mainMenu.addMenuItem(new MenuItem("Заказы",
                () -> navigator.setCurrentMenu(createOrdersMenu()),
                null, MenuAction.EXECUTE));

        mainMenu.addMenuItem(new MenuItem("Рабочие места",
                () -> navigator.setCurrentMenu(createPlacesMenu()),
                null, MenuAction.EXECUTE));

        return mainMenu;
    }

    @Override
    public Menu createMastersMenu() {
        Menu menu = new Menu("Управление мастерами");

        menu.addMenuItem(new MenuItem("Добавить мастера",
                new AddMasterAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Удалить мастера",
                new RemoveMasterAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Список мастеров",
                new ListMastersAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Найти свободных мастеров",
                new FindAvailableMastersAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        return menu;
    }

    @Override
    public Menu createOrdersMenu() {
        Menu menu = new Menu("Управление заказами");

        menu.addMenuItem(new MenuItem("Создать заказ",
                new CreateOrderAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Просмотреть заказ",
                new GetOrderByIdAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Отменить заказ",
                new CancelOrderAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Завершить заказ",
                new CloseOrderAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Перенести заказ",
                new DelayOrderAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Найти свободную дату",
                new FindAvailableDateAction(admin),
                null, MenuAction.EXECUTE));

        return menu;
    }

    @Override
    public Menu createPlacesMenu() {
        Menu menu = new Menu("Управление рабочими местами");

        menu.addMenuItem(new MenuItem("Добавить рабочее место",
                new AddPlaceAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Удалить рабочее место",
                new RemovePlaceAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Список рабочих мест",
                new ListPlacesAction(admin),
                null, MenuAction.EXECUTE));

        menu.addMenuItem(new MenuItem("Найти свободные места",
                new FindAvailablePlacesAction(admin, navigator.getScanner()),
                null, MenuAction.EXECUTE));

        return menu;
    }
}
