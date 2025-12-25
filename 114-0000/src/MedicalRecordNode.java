import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;
import java.io.*;
import java.util.Scanner;

class MedicalRecordNode implements Serializable {
    String diagnosis; MedicalRecordNode next;
    public MedicalRecordNode(String d, MedicalRecordNode n) { this.diagnosis = d; this.next = n; }
}

class TimeSlot implements Serializable {
    private LocalTime startTime; private boolean isBooked = false;
    public TimeSlot(LocalTime t) { this.startTime = t; }
    public boolean isBooked() { return isBooked; }
    public boolean book(String doc) { if (isBooked) return false; return isBooked = true; }
    public LocalTime getStartTime() { return startTime; }
}

class Schedule implements Serializable {
    private LocalDate date; private List<TimeSlot> slots = new ArrayList<>();
    public Schedule(LocalDate d) { this.date = d; }
    public LocalDate getDate() { return date; }
    public List<TimeSlot> getSlots() { return slots; }
}

// ==================== 6. 持久化管理與主程式 (Infrastructure & Main) ====================

class ClinicManager implements Serializable {
    private static final long serialVersionUID = 2025L;
    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();

    /** [技巧] 高效能緩衝 I/O：使用 BufferedOutputStream [來源 72, 167, 173] */
    public void performBackup(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            oos.writeObject(this);
            System.out.println("✨ 已利用 8KB 緩衝機制完成備份。");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static ClinicManager load(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            return (ClinicManager) ois.readObject();
        } catch (Exception e) { return new ClinicManager(); }
    }

    public void addPatient(Patient p) {
        if (patients.stream().anyMatch(e -> e.getId().equals(p.getId())))
            throw new IllegalArgumentException("Patient with this ID already exists"); // [來源 167]
        patients.add(p);
    }
    public List<Patient> getPatients() { return patients; }
    public List<Doctor> getDoctors() { return doctors; }
}
