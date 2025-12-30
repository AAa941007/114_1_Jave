import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;
import java.io.*;
import java.util.Scanner;

class MedicalRecordNode implements Serializable {
    String diagnosis;
    MedicalRecordNode next;
    public MedicalRecordNode(String d, MedicalRecordNode n) {
        this.diagnosis = d;
        this.next = n;
    }
}

class TimeSlot implements Serializable {
    private LocalTime startTime;
    private boolean isBooked = false;

    public TimeSlot(LocalTime t) {
        this.startTime = t;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public boolean book(String doc) {
        if (isBooked) return false;
        // 這裡目前沒有用到 doc，可以視需求之後擴充
        isBooked = true;
        return true;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}

class Schedule implements Serializable {
    private LocalDate date;
    private List<TimeSlot> slots = new ArrayList<>();

    public Schedule(LocalDate d) {
        this.date = d;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<TimeSlot> getSlots() {
        return slots;
    }
}

// ==================== 6. 持久化管理與主程式 (Infrastructure & Main) ====================

class ClinicManager implements Serializable {
    private static final long serialVersionUID = 2025L;

    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();

    /** 高效能緩衝 I/O：使用 BufferedOutputStream 進行備份 */
    public void performBackup(String fileName) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new BufferedOutputStream(
                                     new FileOutputStream(fileName)))) {
            oos.writeObject(this);
            System.out.println("✨ 已利用 8KB 緩衝機制完成備份。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClinicManager load(String fileName) {
        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     new FileInputStream(fileName)))) {
            return (ClinicManager) ois.readObject();
        } catch (Exception e) {
            // 若讀不到檔案或反序列化失敗，就回傳新的 Manager
            return new ClinicManager();
        }
    }

    public void addPatient(Patient p) {
        if (patients.stream().anyMatch(e -> e.getId().equals(p.getId()))) {
            throw new IllegalArgumentException("Patient with this ID already exists");
        }
        patients.add(p);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    /** 匯出所有資料到指定資料夾 */
    public void exportAll(String folderPath) throws IOException {
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();      // 如果沒有資料夾，就自動建立
        }

        exportPatients(folderPath + "/patients.csv");
        exportDoctors(folderPath + "/doctors.csv");
        exportAppointments(folderPath + "/appointments.csv");
    }

    /** 從 CSV 匯入所有資料的總入口（先病患、再醫師、最後預約） */
    public void importAll(String folderPath) throws IOException {
        importPatients(folderPath + "/patients.csv");
        importDoctors(folderPath + "/doctors.csv");
        importAppointments(folderPath + "/appointments.csv");
    }

    // ==================== 匯入：病患 ====================
    private void importPatients(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("⚠️ 找不到病患檔案: " + file.getAbsolutePath());
            return;
        }

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // 標題列
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String id    = parts[0].trim();
                String name  = parts[1].trim();
                String phone = parts[2].trim();
                String mail  = parts[3].trim();

                Patient p = new Patient(id, name, phone, mail);
                if (patients.stream().noneMatch(pp -> pp.getId().equals(id))) {
                    patients.add(p);
                    count++;
                }
            }
        }
        System.out.println("📥 已從 patients.csv 匯入病患筆數: " + count);
    }

    // ==================== 匯入：醫師 ====================
    private void importDoctors(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("⚠️ 找不到醫師檔案: " + file.getAbsolutePath());
            return;
        }

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // 標題列
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String id    = parts[0].trim();
                String name  = parts[1].trim();
                String phone = parts[2].trim();
                String mail  = parts[3].trim();
                String spec  = parts[4].trim();
                String skill = parts[5].trim();

                Doctor d = new Doctor(id, name, phone, mail, spec, skill);
                if (doctors.stream().noneMatch(dd -> dd.getId().equals(id))) {
                    doctors.add(d);
                    count++;
                }
            }
        }
        System.out.println("📥 已從 doctors.csv 匯入醫師筆數: " + count);
    }

    // ==================== 匯入：預約 ====================
    private void importAppointments(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("⚠️ 找不到預約檔案: " + file.getAbsolutePath());
            return;
        }

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // 標題列
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String apptId    = parts[0].trim();
                String patientId = parts[1].trim();
                String doctorId  = parts[2].trim();
                LocalDate date   = LocalDate.parse(parts[3].trim());
                LocalTime time   = LocalTime.parse(parts[4].trim());
                String type      = parts[5].trim();

                // 找到對應病患與醫師
                Patient p = patients.stream()
                        .filter(pp -> pp.getId().equals(patientId))
                        .findFirst().orElse(null);
                Doctor d = doctors.stream()
                        .filter(dd -> dd.getId().equals(doctorId))
                        .findFirst().orElse(null);
                if (p == null || d == null) {
                    // 若 CSV 裡的病患或醫師 ID 找不到，就略過
                    continue;
                }

                TimeSlot slot = new TimeSlot(time);

                Appointment a = type.equalsIgnoreCase("EMERGENCY")
                        ? new EmergencyAppointment(apptId, p, d, date, slot)
                        : new RegularAppointment(apptId, p, d, date, slot);

                // 一定要用 addAppointment，不能用 getAppointments().add(...)
                p.addAppointment(a);
                count++;
            }
        }
        System.out.println("📥 已從 appointments.csv 匯入預約筆數: " + count);
    }

    // ==================== 匯出：病患 ====================
    private void exportPatients(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("patientId,name,phone,email");
            bw.newLine();
            for (Patient p : patients) {
                bw.write(p.toCsvRow());
                bw.newLine();
            }
        }
    }

    // ==================== 匯出：醫師 ====================
    private void exportDoctors(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("doctorId,name,phone,email,specialty,skill");
            bw.newLine();
            for (Doctor d : doctors) {
                bw.write(d.toCsvRow());
                bw.newLine();
            }
        }
    }

    // ==================== 匯出：預約 ====================
    private void exportAppointments(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("apptId,patientId,doctorId,date,time,type");
            bw.newLine();
            for (Patient p : patients) {
                for (Appointment a : p.getAppointments()) {
                    bw.write(a.toCsvRow());
                    bw.newLine();
                }
            }
        }
    }
}
