package autoservice.ui.actions;

import autoservice.model.WorkshopPlace;
import autoservice.service.AutoServiceAdmin;
import autoservice.ui.IAction;

import java.time.LocalDate;
import java.util.List;

public class ListPlacesAction implements IAction {
    private final AutoServiceAdmin admin;

    public ListPlacesAction(AutoServiceAdmin admin) {
        this.admin = admin;
    }

    @Override
    public void execute() {
        System.out.println("\nСписок всех рабочих мест:");
        List<WorkshopPlace> places = admin.getAvailablePlaces(LocalDate.now());

        if (places.isEmpty()) {
            System.out.println("Нет доступных рабочих мест!");
            return;
        }

        for (int i = 0; i < places.size(); i++) {
            System.out.println((i + 1) + ". " + places.get(i).getName());
        }
    }
}