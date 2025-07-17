package autoservice.dto;

import autoservice.enums.OrderStatus;
import autoservice.enums.SortOrders;
import autoservice.model.CarServiceMaster;

import java.time.LocalDateTime;


public record OrderQuery(
        OrderStatus status,
        Boolean isRemoved,
        CarServiceMaster carServiceMaster,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        SortOrders sortOrders
) {
}
