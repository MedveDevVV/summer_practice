package autoservice.dto;

import autoservice.enums.SortCarServiceMasters;
import autoservice.model.RepairOrder;

import java.util.Objects;
import java.util.UUID;

public record CarServiceMastersQuery(
        UUID orderId,
        SortCarServiceMasters sortCarServiceMasters
) {

    public CarServiceMastersQuery {
        Objects.requireNonNull(sortCarServiceMasters, "sortCarServiceMasters cannot be null");
    }
}