package autoservice.utils.csv;

import autoservice.model.CarServiceMaster;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.function.Function;

public class MasterCsvHelper {
    public static final String[] MASTER_HEADER = {"id", "surname", "name", "patronymic", "dateOfBirth"};

    public static Function<CarServiceMaster, String[]> masterToFields = master -> {
        if (master == null) {
            throw new IllegalArgumentException("Master cannot be null");
        }
        if (master.getFullName() == null) {
            throw new IllegalArgumentException("Master's full name cannot be null");
        }

        String[] fields = new String[5];
        fields[0] = master.getId().toString();

        String[] nameParts = master.getFullName().split(" ");
        if (nameParts.length < 3) {
            throw new IllegalArgumentException("Full name must contain surname, name and patronymic");
        }

        fields[1] = nameParts[0]; // surname
        fields[2] = nameParts[1]; // name
        fields[3] = nameParts[2]; // patronymic
        fields[4] = master.getDateOfBirth().toString();
        return fields;
    };

    public static Function<String[], CarServiceMaster> fieldsToMaster = fields -> {
        if (fields == null) {
            throw new IllegalArgumentException("Fields array cannot be null");
        }
        if (fields.length < 5) {
            throw new IllegalArgumentException("Fields array must have at least 5 elements");
        }

        try {
            UUID id = UUID.fromString(fields[0]);
            String surname = fields[1];
            String name = fields[2];
            String patronymic = fields[3];
            LocalDate dob = LocalDate.parse(fields[4]);

            return new CarServiceMaster(id,
                    String.join(" ", surname, name, patronymic),
                    dob);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid field format in master data", e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format in master data", e);
        }
    };
}