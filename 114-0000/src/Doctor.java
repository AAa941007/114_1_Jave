import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Doctor - Concrete class for physicians.
 * Inherits from MedicalStaff and implements Schedulable capabilities. [cite: 574]
 */
public class Doctor extends MedicalStaff {
    private static final long serialVersionUID = 3L;
    private String specialty;
    private List<Schedule> schedules = new ArrayList<>();

    public Doctor(String id, String name, String phone, String email, String department, String specialty) {
        super(id, name, phone, email, department);
        if (specialty == null || specialty.trim().isEmpty())
            throw new IllegalArgumentException("specialty cannot be null or empty"); // [cite: 574]
        this.specialty = specialty;
    }

    public String getSpecialty() { return specialty; }

    public List<TimeSlot> getAvailableSlots(LocalDate date) {
        return schedules.stream()
                .filter(s -> s.getDate().equals(date))
                .flatMap(s -> s.getSlots().stream())
                .filter(slot -> !slot.isBooked())
                .collect(Collectors.toList());
    }

    public boolean bookSlot(TimeSlot slot) {
        if (!isAvailable) throw new IllegalStateException("Doctor is not available"); // [cite: 574]
        return slot.book(this.name);
    }

    public void checkAvailability() {
        System.out.println("🩺 醫師 " + name + " [" + specialty + "] 正在確認診表...");
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

    @Override
    public void displayInfo() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ 醫師詳細資料 (V2.0)                 │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ 姓名：%-28s│%n", name);
        System.out.printf("│ 專科：%-28s│%n", specialty);
        System.out.printf("│ 狀態：%-28s│%n", isAvailable ? "✅ 服務中" : "❌ 休假中");
        System.out.println("└─────────────────────────────────────┘");
    }
}