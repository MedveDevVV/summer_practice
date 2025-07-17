package autoservice.model;

public class WorkshopPlace {
    private final String name;
    private Boolean occupied;

    public WorkshopPlace(String name) {
        this.name = name;
        this.occupied = false;
    }

    public String getName() {
        return name;
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }
}