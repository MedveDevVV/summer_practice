package autoservice.ui;

public class MenuItem {
    private final String title;
    private final IAction action;
    private final Menu nextMenu;
    private final MenuAction menuAction;

    public MenuItem(String title, IAction action, Menu nextMenu, MenuAction menuAction) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
        this.menuAction = menuAction;
    }

    public String getTitle() {
        return title;
    }

    public IAction getAction() {
        return action;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public MenuAction getMenuAction() {
        return menuAction;
    }
}