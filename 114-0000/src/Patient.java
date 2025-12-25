import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient - Concrete class representing a patient.
 * Manages appointments and a linked list of medical records for recursive searching.
 */
public class Patient extends Person {
    private static final long serialVersionUID = 4L;
    private List<Appointment> appointments = new ArrayList<>();
    private MedicalRecordNode historyHead; // Head of the linked list for medical history

    public Patient(String id, String name, String phone, String email) {
        super(id, name, phone, email);
    }

    /**
     * Validates if the patient has exceeded the maximum appointment limit.
     * @throws IllegalStateException if the limit of 3 is reached. [cite: 576]
     */
    public void validateAppointmentLimit() {
        if (appointments.size() >= 3) {
            throw new IllegalStateException("Patient has reached maximum appointment limit (3)");
        }
    }

    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }

    public boolean removeAppointmentById(String appointmentId) {
        return appointments.removeIf(a -> a.getId().equals(appointmentId));
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments); // Return a copy to prevent external modification
    }

    /**
     * Public method to initiate the recursive search for a diagnosis.
     */
    public boolean findDiagnosisRecursively(String keyword) {
        return searchRecursive(historyHead, keyword);
    }

    /**
     * Private helper method that performs the actual recursion.
     */
    private boolean searchRecursive(MedicalRecordNode node, String keyword) {
        // Base Case: If the list ends, the keyword was not found.
        if (node == null) {
            return false;
        }
        // Check if the current node's diagnosis contains the keyword.
        if (node.diagnosis.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        }
        // Recursive Step: Move to the next node in the list.
        return searchRecursive(node.next, keyword);
    }

    public void addRecord(String diagnosis) {
        // Prepend new records to the head of the list
        this.historyHead = new MedicalRecordNode(diagnosis, this.historyHead);
    }

    @Override
    public void displayInfo() {
        System.out.println("👤 病患姓名：" + name + " (ID: " + id + ", 已預約數：" + appointments.size() + ")");
    }
}