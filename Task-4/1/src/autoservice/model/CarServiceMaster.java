package autoservice.model;

import java.time.LocalDate;

public class CarServiceMaster extends Person {
    private Boolean busy;

    public CarServiceMaster(String surname, String name, String patronymic, LocalDate dateOfBirth) {
        super(surname, name, patronymic, dateOfBirth);
        busy = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return getFullName().equals(((CarServiceMaster) o).getFullName());
    }

    @Override
    public int hashCode() {
        return getFullName().hashCode();
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public Boolean getBusy() {
        return busy;
    }
}
