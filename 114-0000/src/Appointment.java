import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;


/** Appointment - 預約流程模板類別 [來源 165, 168] */
abstract class Appointment implements Serializable {
    private static final long serialVersionUID = 300L;
    protected String id;
    protected Patient patient;
    protected Doctor doctor;
    protected LocalDate date;
    protected TimeSlot slot;

    public Appointment(String id, Patient patient, Doctor doctor, LocalDate date, TimeSlot slot) {
        this.id = id; this.patient = patient; this.doctor = doctor; this.date = date; this.slot = slot;
    }

    public String getId() { return id; }

    /** [技巧] final 模板方法：鎖定 SOP 順序 [來源 48, 169] */
    public final void processBooking() {
        System.out.println("\n>>> [啟動預約程序] 編號: " + id);
        if (!preCheck()) return; // 1. 具體檢查
        onPreparing();           // 2. 抽象準備
        beforeConfirm();         // 3. 鉤子方法 (Hook)
        if (doctor.bookSlot(slot)) {
            executeFinalStep();  // 4. 核心執行
            afterConfirm();      // 5. 鉤子方法 (Hook) [來源 166]
        }
    }

    private boolean preCheck() {
        System.out.println("🔍 SOP-1: 檢查預約額度與醫師狀態...");
        try {
            patient.validateAppointmentLimit();
            return doctor.checkWorkStatus();
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage()); return false;
        }
    }

    protected abstract void onPreparing();
    private void executeFinalStep() { System.out.println("💾 SOP-4: 寫入診所資料庫..."); patient.addAppointment(this); }
    protected void beforeConfirm() {}
    protected void afterConfirm() { System.out.println("✅ 預約確認成功。"); }
    public abstract void displayFullDetails();
}

class RegularAppointment extends Appointment implements Billable {
    public RegularAppointment(String id, Patient p, Doctor d, LocalDate dt, TimeSlot s) { super(id, p, d, dt, s); }
    @Override protected void onPreparing() { System.out.println("📋 SOP-2: 配置一般門診診間..."); }
    @Override public double calculateFee() { return 500.0; }
    @Override public double getInsuranceCoverage() { return 200.0; }
    @Override public void displayFullDetails() {
        System.out.println("📅 一般預約: " + id + " | 費用: " + getPatientPayable());
    }
}

class EmergencyAppointment extends Appointment implements Queueable, Billable {
    public EmergencyAppointment(String id, Patient p, Doctor d, LocalDate dt, TimeSlot s) { super(id, p, d, dt, s); }
    @Override protected void onPreparing() { System.out.println("🚨 SOP-2: 指派急救醫療團隊..."); }
    @Override protected void afterConfirm() { System.out.println("🔔 [HOOK] 急診警報：已通知主治醫師！"); }
    @Override public int getPriority() { return 1; }
    @Override public int getEstimatedWaitTime() { return 5; }
    @Override public double calculateFee() { return 1500.0; }
    @Override public double getInsuranceCoverage() { return 500.0; }
    @Override public void displayFullDetails() { System.out.println("🆘 急診預約: " + id + " | 優先權: " + getPriority()); }
}