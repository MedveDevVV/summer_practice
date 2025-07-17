package autoservice.model;

import autoservice.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Order {
    public String getDescription();
    public OrderStatus getStatus();
    public UUID getId();
    public void setStartDateTime(LocalDateTime startDateTime);
    public void setEndDateTime(LocalDateTime endDateTime);
    public void setTotalPrice(Float totalPrice);
    public void cancel();
    public void closed();
    public void assignPerson(Person person);
    public LocalDateTime getStartDateTime();
    public LocalDateTime getEndDateTime();
    public Float getTotalPrice();
    public Person getAssignPerson();
    public LocalDateTime getCreationDateTime();
}
