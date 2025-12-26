import java.io.Serializable;
import java.time.LocalDate;

/** Appointment - 預約流程模板類別 */
abstract class Appointment implements Serializable {
    private static final long serialVersionUID = 300L;

    protected String id;
    protected Patient patient;
    protected Doctor doctor;
    protected LocalDate date;
    protected TimeSlot slot;

    public Appointment(String id, Patient patient, Doctor doctor,
                       LocalDate date, TimeSlot slot) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.slot = slot;
    }

    /** 匯出 CSV 時共同的欄位 */
    protected String baseCsvFields() {
        return String.join(",",
                id,
                patient.getId(),
                doctor.getId(),
                date.toString(),
                slot.getStartTime().toString()
        );
    }

    /** 由子類別回報類型：REGULAR / EMERGENCY */
    public abstract String getTypeCode();

    /** 匯出成 CSV 一行字串 */
    public String toCsvRow() {
        return baseCsvFields() + "," + getTypeCode();
    }

    public String getId() { return id; }

    /** final 模板方法：鎖定預約 SOP 順序 */
    public final void processBooking() {
        System.out.println("\n>>> [啟動預約程序] 編號: " + id);

        if (!preCheck()) return;   // 1. 檢查
        onPreparing();             // 2. 子類別準備
        beforeConfirm();           // 3. Hook

        if (doctor.bookSlot(slot)) {
            executeFinalStep();    // 4. 寫入資料
            afterConfirm();        // 5. Hook
        } else {
            System.out.println("❌ 該時段已被預約，無法掛號。");
        }
    }

    /** 事前檢查：病患額度 + 醫師狀態 */
    private boolean preCheck() {
        System.out.println("🔍 SOP-1: 檢查預約額度與醫師狀態...");
        try {
            patient.validateAppointmentLimit();
            return doctor.checkWorkStatus();
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
            return false;
        }
    }

    /** 由子類別實作：準備流程（安排診間等） */
    protected abstract void onPreparing();

    /** 最終寫入資料庫（目前以加入病患預約清單表示） */
    private void executeFinalStep() {
        System.out.println("💾 SOP-4: 寫入診所資料庫...");
        patient.addAppointment(this);
    }

    /** Hook：預約前可加額外檢查／提示 */
    protected void beforeConfirm() { }

    /** Hook：預約成功後可加額外通知 */
    protected void afterConfirm() {
        System.out.println("✅ 預約確認成功。");
    }

    /** 由子類別決定要如何顯示完整資訊 */
    public abstract void displayFullDetails();
}

/** 一般門診預約 */
class RegularAppointment extends Appointment implements Billable {

    public RegularAppointment(String id, Patient p, Doctor d,
                              LocalDate dt, TimeSlot s) {
        super(id, p, d, dt, s);
    }

    @Override
    public String getTypeCode() {
        return "REGULAR";
    }

    @Override
    protected void onPreparing() {
        System.out.println("📋 SOP-2: 配置一般門診診間...");
    }

    @Override
    public double calculateFee() {
        return 500.0;
    }

    @Override
    public double getInsuranceCoverage() {
        return 200.0;
    }

    @Override
    public void displayFullDetails() {
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃ 📅 一般預約: " + id
                + " | 病患: " + patient.getName()
                + " | 醫師: " + doctor.getName()
                + " | 日期: " + date
                + " | 時段: " + slot.getStartTime()
                + " | 預估自付額: " + getPatientPayable()
                + " ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }
}

/** 急診預約 */
class EmergencyAppointment extends Appointment implements Queueable, Billable {

    public EmergencyAppointment(String id, Patient p, Doctor d,
                                LocalDate dt, TimeSlot s) {
        super(id, p, d, dt, s);
    }

    @Override
    public String getTypeCode() {
        return "EMERGENCY";
    }

    @Override
    protected void onPreparing() {
        System.out.println("🚨 SOP-2: 指派急救醫療團隊...");
    }

    @Override
    protected void afterConfirm() {
        System.out.println("🔔 [HOOK] 急診警報：已通知主治醫師！");
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public int getEstimatedWaitTime() {
        return 5;
    }

    @Override
    public double calculateFee() {
        return 1500.0;
    }

    @Override
    public double getInsuranceCoverage() {
        return 500.0;
    }

    @Override
    public void displayFullDetails() {
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃ 🆘 急診預約: " + id
                + " | 病患: " + patient.getName()
                + " | 醫師: " + doctor.getName()
                + " | 日期: " + date
                + " | 時段: " + slot.getStartTime()
                + " | 優先權: " + getPriority()
                + " | 估計等待時間(分): " + getEstimatedWaitTime()
                + " ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }
}
