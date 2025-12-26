import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient - Concrete class representing a patient.
 * Manages appointments and a linked list of medical records for recursive searching.
 */
public class Patient extends Person implements Serializable {

    private static final long serialVersionUID = 4L;

    // 該病患擁有的預約清單
    private List<Appointment> appointments = new ArrayList<>();

    // 醫療紀錄（採用單向 linked list）
    private MedicalRecordNode historyHead; // Head of the linked list for medical history

    public Patient(String id, String name, String phone, String email) {
        super(id, name, phone, email);
    }

    /**
     * 驗證病患是否超過可預約上限（例如 3 筆）
     * 若超過則丟出 IllegalStateException。
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

    /**
     * 回傳一份預約清單的複本，避免外部直接改動內部 List。
     */
    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * 對外提供遞迴搜尋病歷診斷關鍵字的入口。
     */
    public boolean findDiagnosisRecursively(String keyword) {
        return searchRecursive(historyHead, keyword);
    }

    /**
     * 私有遞迴函式，實際走訪 linked list。
     */
    private boolean searchRecursive(MedicalRecordNode node, String keyword) {
        if (node == null) {
            return false;
        }
        if (node.diagnosis.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        }
        return searchRecursive(node.next, keyword);
    }

    /**
     * 新增一筆病歷紀錄（插入到 linked list 的頭部）。
     */
    public void addRecord(String diagnosis) {
        this.historyHead = new MedicalRecordNode(diagnosis, this.historyHead);
    }

    @Override
    public void displayInfo() {
        System.out.println("👤 病患姓名：" + name
                + " (ID: " + id
                + ", 已預約數：" + appointments.size() + ")");
    }

    /**
     * 匯出病患基本資料到 CSV 的一行字串。
     */
    public String toCsvRow() {
        return String.join(",",
                id,
                name,
                phone,
                email
        );
    }
}
