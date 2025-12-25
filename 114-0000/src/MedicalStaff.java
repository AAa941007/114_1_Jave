import java.io.Serializable;

/**
 * MedicalStaff - Intermediate abstract layer inheriting from Person.
 * Manages properties common to all medical personnel. [cite: 574]
 */
public abstract class MedicalStaff extends Person {
    private static final long serialVersionUID = 2L;
    protected String department;
    protected boolean isAvailable = true;

    public MedicalStaff(String id, String name, String phone, String email, String department) {
        super(id, name, phone, email);
        this.department = department;
    }

    /**
     * A final concrete method to ensure consistent status checks across all staff types. [cite: 574]
     */
    public final boolean checkWorkStatus() {
        if (!isAvailable) {
            System.out.println("⚠️ 注意：" + name + " 目前不在職或無法提供服務。");
        }
        return isAvailable;
    }

    public String getDepartment() { return department; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public boolean isAvailable() { return isAvailable; }
}