package autoservice.repository.impl;

import autoservice.model.WorkshopPlace;
import autoservice.repository.WorkshopPlaceRepository;

import java.util.*;

public class GarageWorkshopPlaceRepository implements WorkshopPlaceRepository {
    private final Map<String, WorkshopPlace> places = new HashMap<>();

    @Override
    public void addPlace(WorkshopPlace place) {
        places.putIfAbsent(place.getName(), place);
    }

    @Override
    public void removePlace(WorkshopPlace place) {
        if (findByName(place.getName()).isOccupied()) return;
        places.remove(place.getName());
    }

    @Override
    public List<WorkshopPlace> getAllPlaces() {
        return new ArrayList<>(places.values());
    }

    @Override
    public WorkshopPlace findByName(String name) {
        return places.get(name);
    }

    @Override
    public List<WorkshopPlace> getAvailablePlaces() {
        List<WorkshopPlace> available = new ArrayList<>();
        for(WorkshopPlace place : places.values()){
            if (!place.isOccupied()){
                available.add(place);
            }
        }
        available.sort(Comparator.comparing(WorkshopPlace::getName));
        return available;
    }
}
