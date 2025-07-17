package autoservice.service;

import autoservice.dto.CarServiceMastersQuery;
import autoservice.dto.OrderQuery;
import autoservice.model.CarServiceMaster;
import autoservice.model.Order;
import autoservice.model.RepairOrder;
import autoservice.model.WorkshopPlace;
import autoservice.repository.MasterRepository;
import autoservice.repository.OrderRepository;
import autoservice.repository.WorkshopPlaceRepository;
import autoservice.repository.impl.RepairOrderRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AutoServiceAdmin {
    private final MasterRepository masterRepository;
    private final WorkshopPlaceRepository workshopPlaceRepository;
    private final OrderRepository ordersRepository;
    private final OrderRepository deletedOrdersRepository = new RepairOrderRepository();


    public AutoServiceAdmin(MasterRepository masterRepository, WorkshopPlaceRepository workshopPlaceRepository,
                            OrderRepository ordersRepository) {
        this.masterRepository = masterRepository;
        this.workshopPlaceRepository = workshopPlaceRepository;
        this.ordersRepository = ordersRepository;
    }

    private List<Order> ordersFilter(OrderRepository orders, OrderQuery orderQuery) {
        return orders.getAllOrders().stream()
                .filter(o -> orderQuery.carServiceMaster() == null
                        || o.getAssignPerson().equals(orderQuery.carServiceMaster()))
                .filter(o -> orderQuery.status() == null
                        || o.getStatus().equals(orderQuery.status()))
                .filter(o -> orderQuery.startDateTime() == null
                        || o.getStartDateTime().isAfter(orderQuery.startDateTime()))
                .filter(o -> orderQuery.endDateTime() == null
                        || o.getEndDateTime().isBefore(orderQuery.endDateTime()))
                .toList();
    }

    public void addMaster(CarServiceMaster master) {
        masterRepository.addMaster(master);
    }

    public void removeMaster(CarServiceMaster master) {
        masterRepository.removeMaster(master);
    }

    public void addWorkshopPlace(WorkshopPlace place) {
        workshopPlaceRepository.addPlace(place);
    }

    public void removePlace(WorkshopPlace place) {
        workshopPlaceRepository.removePlace(place);
    }

    public UUID createRepairOrder(LocalDateTime start, LocalDateTime end,
                                  String description, CarServiceMaster master, WorkshopPlace place) {
        master.setBusy(true);
        RepairOrder order = new RepairOrder(start, end, description);
        order.assignPerson(master);
        order.setWorkshopPlace(place);
        place.setOccupied(true);
        ordersRepository.addOrder(order);
        return order.getId();
    }

    public void cancelOrder(UUID orderId) {
        RepairOrder order = ordersRepository.getOrderById(orderId).orElse(null);
        if (order == null) return;
        CarServiceMaster master = (CarServiceMaster) order.getAssignPerson();
        master.setBusy(false);
        ordersRepository.cancelOrder(order);
    }

    public void closedOrder(UUID orderId) {
        RepairOrder order = ordersRepository.getOrderById(orderId).orElse(null);
        if (order == null) return;
        CarServiceMaster master = (CarServiceMaster) order.getAssignPerson();
        master.setBusy(false);
        ordersRepository.closeOrder(order);
    }

    public void removeOrder(UUID orderId) {
        RepairOrder order = ordersRepository.getOrderById(orderId).orElse(null);
        if (order == null) return;
        CarServiceMaster master = (CarServiceMaster) order.getAssignPerson();
        master.setBusy(false);
        deletedOrdersRepository.addOrder(order);
        ordersRepository.removeOrder(order);
    }

    public void delayOrder(UUID orderId, Duration duration) {
        RepairOrder order = ordersRepository.getOrderById(orderId).orElse(null);
        if (order == null) return;
        order.setEndDateTime(order.getEndDateTime().plus(duration));
        ordersRepository.updateOrder(order);
    }

    public String getOrderInfo(UUID orderId) {
        RepairOrder order = ordersRepository.getOrderById(orderId).orElse(null);
        if (order == null) return "Заказ не найден";
        return order.toString();
    }

    public List<Order> getOrders(OrderQuery orderQuery) {
        List<Order> orders = new ArrayList<>();
        if (orderQuery.isRemoved() == null) {
            orders.addAll(ordersFilter(ordersRepository, orderQuery));
            orders.addAll(ordersFilter(deletedOrdersRepository, orderQuery));
        } else if (orderQuery.isRemoved()) {
            orders.addAll(ordersFilter(deletedOrdersRepository, orderQuery));
        } else {
            orders.addAll(ordersFilter(ordersRepository, orderQuery));
        }
        if (orderQuery.sortOrders() != null) {
            orders.sort(orderQuery.sortOrders().getComparator());
        }
        return orders;
    }

    public List<CarServiceMaster> getCarServiceMasters(CarServiceMastersQuery carServiceMastersQuery) {
        List<CarServiceMaster> masters = new ArrayList<>();
        if(carServiceMastersQuery.orderId() != null){
            CarServiceMaster master = (CarServiceMaster)
                    (Objects.requireNonNull(ordersRepository.getOrderById(carServiceMastersQuery.orderId()).orElse(null)))
                            .getAssignPerson();
            masters.add(master);
        }
        else {
            masters = masterRepository.getAllMasters();
            masters.sort(carServiceMastersQuery.sortCarServiceMasters().getComparator());
        }
        return masters;
    }
}
