package autoservice.utils.csv;

import autoservice.enums.OrderStatus;
import autoservice.model.CarServiceMaster;
import autoservice.model.RepairOrder;
import autoservice.model.WorkshopPlace;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.function.Function;

public class OrderCsvHelper {
    public static final String[] ORDER_HEADER = {
            "id", "creationDate", "startDate", "endDate",
            "description", "status", "totalPrice",
            "masterId", "placeId"
    };

    public static Function<RepairOrder, String[]> orderToFields = order -> {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        String[] fields = new String[9];
        fields[0] = order.getId().toString();
        fields[1] = order.getCreationDate().toString();
        fields[2] = order.getStartDate() != null ? order.getStartDate().toString() : "";
        fields[3] = order.getEndDate() != null ? order.getEndDate().toString() : "";
        fields[4] = order.getDescription() != null ? order.getDescription() : "";
        fields[5] = order.getStatus().name();
        fields[6] = order.getTotalPrice() != null ? order.getTotalPrice().toString() : "";
        fields[7] = order.getAssignPerson() != null ? order.getAssignPerson().getId().toString() : "";
        fields[8] = order.getWorkshopPlace() != null ? order.getWorkshopPlace().getId().toString() : "";
        return fields;
    };

    public static Function<String[], RepairOrder> fieldsToOrder = fields -> {
        if (fields == null) {
            throw new IllegalArgumentException("Fields array cannot be null");
        }
        if (fields.length < 9) {
            throw new IllegalArgumentException("Fields array must have at least 9 elements");
        }

        try {
            UUID id = UUID.fromString(fields[0]);
            LocalDate creationDate = LocalDate.parse(fields[1]);
            LocalDate startDate = fields[2].isEmpty() ? null : LocalDate.parse(fields[2]);
            LocalDate endDate = fields[3].isEmpty() ? null : LocalDate.parse(fields[3]);
            String description = fields[4];

            OrderStatus status;
            try {
                status = OrderStatus.valueOf(fields[5]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid order status: " + fields[5], e);
            }

            Float totalPrice = fields[6].isEmpty() ? null : Float.parseFloat(fields[6]);
            UUID masterId = fields[7].isEmpty() ? null : UUID.fromString(fields[7]);
            UUID placeId = fields[8].isEmpty() ? null : UUID.fromString(fields[8]);

            CarServiceMaster master = masterId != null ?
                    new CarServiceMaster(masterId, "", null) : null;
            WorkshopPlace place = placeId != null ?
                    new WorkshopPlace(placeId, "") : null;

            return new RepairOrder(
                    creationDate, startDate, endDate, description,
                    status, totalPrice, id, master, place
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid field format in order data", e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format in order data", e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in total price", e);
        }
    };
}