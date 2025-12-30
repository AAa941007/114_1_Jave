import java.time.LocalDate;
import java.util.List;

public interface Schedulable {
    List<TimeSlot> getAvailableSlots(LocalDate date);
    boolean bookSlot(TimeSlot slot);
    void checkAvailability();
}
