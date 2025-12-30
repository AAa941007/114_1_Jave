import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.io.IOException;


public class HospitalSystemApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final ClinicManager manager = new ClinicManager();

    public static void main(String[] args) {
        initSampleData();   // 預設初始化多位醫師與病患
        System.out.println("目前工作目錄: " + System.getProperty("user.dir"));

        boolean run = true;
        while (run) {
            System.out.println("\n=== 醫院門診管理系統  ===");
            System.out.println("目前醫師人數: " + manager.getDoctors().size());
            System.out.println("目前病患人數: " + manager.getPatients().size());
            System.out.println("------------------------------------");
            System.out.println("1. 新增病患資料");
            System.out.println("2. 預約掛號");
            System.out.println("3. 查詢指定病患資訊");
            System.out.println("4. 顯示所有預約");
            System.out.println("5. 取消預約");
            System.out.println("6. 管理醫師時段");
            System.out.println("7. 查詢/新增病歷");
            System.out.println("8. 每日統計");
            System.out.println("9. 顯示診所資訊");
            System.out.println("10. 儲存離開");
            System.out.println("11. 從檔案載入資料");
            System.out.print("請選擇 (1-11): ");

            String op = sc.nextLine().trim();
            try {
                switch (op) {
                    case "1" -> addPatient();
                    case "2" -> makeAppt();
                    case "3" -> queryPatient();
                    case "4" -> showAll();
                    case "5" -> cancelAppt();
                    case "6" -> manageDoc();
                    case "7" -> manageHistory();
                    case "8" -> showDailyStats();
                    case "9" -> showClinicInfo();
                    case "10" -> {
                        manager.performBackup("hospital.dat");
                        System.out.println("💾 資料已備份，系統即將關閉。");
                        run = false;
                    }
                    case "A" -> {
                        try {
                            manager.exportAll("data");
                            java.io.File f1 = new java.io.File("data/patients.csv");
                            System.out.println("patients.csv 存在? " + f1.exists() + ", 大小: " + f1.length());
                        } catch (IOException e) {
                            System.out.println("🛑 匯出失敗: " + e.getMessage());
                        }
                        run = false;
                    }
                    case "11" -> {
                        try {
                            manager.importAll("data");
                            System.out.println("✅ 已從 data 資料夾載入 CSV 資料。");
                        } catch (IOException e) {
                            System.out.println("🛑 匯入失敗: " + e.getMessage());
                        }
                    }


                    default -> System.out.println("❌ 無效選項，請重新輸入。");
                }
            } catch (Exception e) {
                System.out.println("🛑 系統錯誤: " + e.getMessage());
            }
        }
    }

    // 預設建立幾位醫師與病患
    private static void initSampleData() {
        // 醫師 1

        Doctor dr1 = new Doctor("D01", "王大夫", "0911-000001",
                "wang@hospital.com", "內科", "心臟手術");
        Schedule s1 = new Schedule(LocalDate.now());
        s1.getSlots().add(new TimeSlot(LocalTime.of(9, 0)));
        s1.getSlots().add(new TimeSlot(LocalTime.of(10, 0)));
        dr1.addSchedule(s1);

        // 醫師 2
        Doctor dr2 = new Doctor("D02", "李醫師", "0911-000002",
                "li@hospital.com", "骨科", "關節置換");
        Schedule s2 = new Schedule(LocalDate.now());
        s2.getSlots().add(new TimeSlot(LocalTime.of(14, 0)));
        s2.getSlots().add(new TimeSlot(LocalTime.of(15, 0)));
        dr2.addSchedule(s2);

        manager.getDoctors().add(dr1);
        manager.getDoctors().add(dr2);

        // 病患預設 3 位
        Patient p1 = new Patient("P01", "張小明", "09XX-111111", "p01@test.com");
        Patient p2 = new Patient("P02", "陳小美", "09XX-222222", "p02@test.com");
        Patient p3 = new Patient("P03", "林大華", "09XX-333333", "p03@test.com");

        manager.addPatient(p1);
        manager.addPatient(p2);
        manager.addPatient(p3);
    }

    private static void addPatient() {
        System.out.print("請輸入病患 ID: ");
        String id = sc.nextLine().trim();
        System.out.print("請輸入病患姓名: ");
        String name = sc.nextLine().trim();
        System.out.print("請輸入電話 (可略過，預設 09XX): ");
        String phone = sc.nextLine().trim();
        if (phone.isEmpty()) phone = "09XX";
        System.out.print("請輸入 Email (可略過，預設 e@t.com): ");
        String mail = sc.nextLine().trim();
        if (mail.isEmpty()) mail = "e@t.com";

        manager.addPatient(new Patient(id, name, phone, mail));
        System.out.println("✅ 病患註冊成功。");
    }

    private static void makeAppt() {
        if (manager.getPatients().isEmpty() || manager.getDoctors().isEmpty()) {
            System.out.println("⚠️ 目前沒有病患或醫師，無法預約。");
            return;
        }

        System.out.println("\n可選病患：");
        for (int i = 0; i < manager.getPatients().size(); i++) {
            Patient p = manager.getPatients().get(i);
            System.out.println((i + 1) + ". " + p.getId() + " - " + p.getName());
        }
        System.out.print("請選擇病患編號: ");
        int pIdx = parseIndex(sc.nextLine(), manager.getPatients().size());
        if (pIdx < 0) return;
        Patient p = manager.getPatients().get(pIdx);

        System.out.println("\n可選醫師：");
        for (int i = 0; i < manager.getDoctors().size(); i++) {
            Doctor d = manager.getDoctors().get(i);
            System.out.println((i + 1) + ". " + d.getId() + " - " + d.getName()
                    + " (" + d.getSpecialty() + ")");
        }
        System.out.print("請選擇醫師編號: ");
        int dIdx = parseIndex(sc.nextLine(), manager.getDoctors().size());
        if (dIdx < 0) return;
        Doctor d = manager.getDoctors().get(dIdx);

        LocalDate date = LocalDate.now();
        List<TimeSlot> slots = d.getAvailableSlots(date);
        if (slots.isEmpty()) {
            System.out.println("⚠️ 目前此醫師在今天沒有可用時段。");
            return;
        }

        System.out.println("\n可選看診時段：");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println((i + 1) + ". " + slots.get(i).getStartTime());
        }
        System.out.print("請選擇時段編號: ");
        int sIdx = parseIndex(sc.nextLine(), slots.size());
        if (sIdx < 0) return;

        TimeSlot slot = slots.get(sIdx);

        System.out.print("請選擇類型 (1: 一般, 2: 急診): ");
        String type = sc.nextLine().trim();

        String idPrefix = type.equals("2") ? "E" : "R";
        String apptId = idPrefix + System.currentTimeMillis() % 100000; // 簡單產生一個 ID

        Appointment a = type.equals("2")
                ? new EmergencyAppointment(apptId, p, d, date, slot)
                : new RegularAppointment(apptId, p, d, date, slot);

        a.processBooking();
        System.out.println("✅ 已建立預約。");
    }

    private static void queryPatient() {
        if (manager.getPatients().isEmpty()) {
            System.out.println("目前沒有任何病患資料。");
            return;
        }
        System.out.print("請輸入病患 ID: ");
        String id = sc.nextLine().trim();

        manager.getPatients().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(p -> {
                    p.displayInfo();
                    p.getAppointments()
                            .forEach(Appointment::displayFullDetails);
                }, () -> System.out.println("❌ 查無此人。"));
    }

    private static void showAll() {
        if (manager.getPatients().isEmpty()) {
            System.out.println("目前沒有病患及預約紀錄。");
            return;
        }

        System.out.println("================================");
        System.out.println("📋 系統中所有預約紀錄");
        System.out.println("================================");

        int count = 0;
        for (Patient p : manager.getPatients()) {
            for (Appointment a : p.getAppointments()) {
                a.displayFullDetails();   // 會印出你在 Regular / Emergency 裡設計的方框與內容
                count++;
            }
        }

        if (count == 0) {
            System.out.println("目前沒有任何預約紀錄。");
        } else {
            System.out.println("🔢 總預約數: " + count);
        }
    }

    private static void cancelAppt() {
        // 這裡留給你依作業規格實作
        System.out.println("⚙️ 取消預約功能執行中 (尚未實作細節)...");
    }

    private static void manageDoc() {
        if (manager.getDoctors().isEmpty()) {
            System.out.println("目前沒有醫師資料。");
            return;
        }
        manager.getDoctors().forEach(Doctor::checkAvailability);
    }

    private static void manageHistory() {
        if (manager.getPatients().isEmpty()) {
            System.out.println("目前沒有病患資料。");
            return;
        }
        System.out.print("請輸入病患 ID: ");
        String id = sc.nextLine().trim();

        Patient p = manager.getPatients().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (p == null) {
            System.out.println("❌ 查無此人。");
            return;
        }

        System.out.print("1: 新增病歷, 2: 遞迴搜尋: ");
        String sub = sc.nextLine().trim();
        if (sub.equals("1")) {
            System.out.print("請輸入診斷內容: ");
            p.addRecord(sc.nextLine());
            System.out.println("✅ 已新增病歷。");
        } else {
            System.out.print("請輸入關鍵字: ");
            String keyword = sc.nextLine();
            System.out.println("結果: " + p.findDiagnosisRecursively(keyword));
        }
    }

    private static void showDailyStats() {
        long count = manager.getPatients().stream()
                .mapToLong(p -> p.getAppointments().size())
                .sum();
        System.out.println("📊 今日預約數: " + count);
    }

    private static void showClinicInfo() {
        System.out.println("\n=== 診所資訊 ===");
        System.out.println("醫師列表:");
        manager.getDoctors().forEach(Doctor::displayInfo);
        System.out.println("\n病患列表:");
        manager.getPatients().forEach(Patient::displayInfo);
    }

    // 將輸入字串轉成 index (0-based)，非法就回傳 -1
    private static int parseIndex(String input, int size) {
        try {
            int n = Integer.parseInt(input.trim());
            if (n < 1 || n > size) {
                System.out.println("❌ 編號超出範圍。");
                return -1;
            }
            return n - 1;
        } catch (NumberFormatException e) {
            System.out.println("❌ 請輸入數字編號。");
            return -1;
        }
    }
}
