package autoservice.enums;

import autoservice.model.Order;

import java.util.Comparator;

public enum SortOrders {
    CREATION_DATE(Comparator.comparing(Order::getCreationDateTime)),
    START_DATE(Comparator.comparing(Order::getStartDateTime)),
    END_DATE(Comparator.comparing(Order::getEndDateTime)),
    TOTAL_PRICE(Comparator.comparing(Order::getTotalPrice));

    private final Comparator<Order> comparator;

    SortOrders(Comparator<Order> comparator){
        this.comparator = comparator;
    }

    public Comparator<Order> getComparator(){
        return comparator;
    }
}
