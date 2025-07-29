package autoservice.utils.csv;

import autoservice.model.WorkshopPlace;

import java.util.UUID;
import java.util.function.Function;

public class PlaceCsvHelper {
    public static final String[] PLACE_HEADER = {"id", "name"};

    public static Function<WorkshopPlace, String[]> placeToFields = place -> {
        if (place == null) {
            throw new IllegalArgumentException("Workshop place cannot be null");
        }
        if (place.getName() == null) {
            throw new IllegalArgumentException("Workshop place name cannot be null");
        }

        String[] fields = new String[2];
        fields[0] = place.getId().toString();
        fields[1] = place.getName();
        return fields;
    };

    public static Function<String[], WorkshopPlace> fieldsToPlace = fields -> {
        if (fields == null) {
            throw new IllegalArgumentException("Fields array cannot be null");
        }
        if (fields.length < 2) {
            throw new IllegalArgumentException("Fields array must have at least 2 elements");
        }
        if (fields[1] == null || fields[1].isEmpty()) {
            throw new IllegalArgumentException("Workshop place name cannot be empty");
        }

        try {
            UUID id = UUID.fromString(fields[0]);
            String name = fields[1];
            return new WorkshopPlace(id, name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format in workshop place data", e);
        }
    };
}