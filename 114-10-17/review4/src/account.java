import java.util.Scanner;

class Account {
    // 屬性欄位
    private String accountNumber;
    private String ownerName;
    private double balance;

    // 靜態變數
    private static int accountCount = 0;

    /**
     * 建構子:建立新帳戶 (三參數)
     * @param accountNumber 帳號
     * @param ownerName 帳戶持有人姓名
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, String ownerName, double initialBalance) {
        this.setAccountNumber(accountNumber);
        this.ownerName = ownerName;
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage());
        }
        accountCount++;
    }

    /**
     * 建構子:建立新帳戶 (無參數)
     * 預設帳號為 "000000", 姓名為 "Unknown", 餘額為 0
     */
    public Account() {
        this("000000", "Unknown", 0.0);
    }

    // ===========================
    // Setter 方法
    // ===========================

    /**
     * 設定帳號
     * @param accountNumber 帳號
     */
    private void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 設定餘額 (私有方法,僅供內部使用)
     * @param balance 餘額
     * @throws IllegalArgumentException 如果餘額為負數
     */
    private void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("初始餘額不可為負數");
        }
        this.balance = balance;
    }

    // ===========================
    // Getter 方法
    // ===========================

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public static int getAccountCount() {
        return accountCount;
    }

    // ===========================
    // 存款方法 (多載)
    // ===========================

    /**
     * 存款方法 (基本版本)
     * @param amount 存款金額
     * @throws IllegalArgumentException 如果金額小於等於 0
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("存款金額必須大於 0");
        }
        this.balance += amount;
    }

    /**
     * 存款方法 (支援多幣別)
     * @param amount 存款金額
     * @param currency 貨幣類型
     * @throws IllegalArgumentException 如果不支援該貨幣
     */
    public void deposit(double amount, String currency) {
        double rate;
        rate = switch (currency.toUpperCase()) {
            case "USD" -> 30.0;
            case "EUR" -> 35.0;
            case "JPY" -> 0.25;
            case "TWD" -> 1.0;
            default -> throw new IllegalArgumentException("不支援的貨幣: " + currency);
        };
        this.deposit(amount * rate);
    }

    /**
     * 存款方法 (支援多筆金額)
     * @param amounts 可變參數,多筆存款金額
     * @throws IllegalArgumentException 如果任一金額小於等於 0
     */
    public void deposit(double... amounts) {
        double total = 0;
        for (double amount : amounts) {
            if (amount <= 0) {
                throw new IllegalArgumentException("存款金額必須為正數");
            }
            total += amount;
        }
        this.deposit(total);
    }

    // ===========================
    // 提款方法
    // ===========================

    /**
     * 提款方法
     * @param amount 提款金額
     * @throws IllegalArgumentException 如果金額無效或餘額不足
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("提款金額必須大於 0");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("餘額不足,無法提款");
        }
        this.balance -= amount;
    }

    /**
     * 互動式提款方法 (從 Scanner 讀取金額)
     * @param scanner Scanner 物件
     * @throws IllegalArgumentException 如果輸入無效
     */
    public void withdraw(Scanner scanner) {
        Scanner scan = new Scanner(System.in);
        double validAmount = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("請輸入提款金額 (1~" + this.balance + "): ");
                validAmount = Double.parseDouble(scan.nextLine());

                if (validAmount > 0 && validAmount <= this.balance) {
                    valid = true;
                } else {
                    throw new IllegalArgumentException("提款金額超出範圍");
                }
            } catch (NumberFormatException e) {
                System.out.println("輸入格式錯誤: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("錯誤: " + e.getMessage());
            }
        }

        this.withdraw(validAmount);
    }

    /**
     * 顯示帳戶資訊
     */
    public String getAccountInfo() {
        return String.format("帳號: %s, 持有人: %s, 餘額: %.2f",
                accountNumber, ownerName, balance);
    }
}

// ===========================
// 測試類別
// ===========================
class AccountTest {
    public static void main(String[] args) {
        // 建立帳戶
        Account account1 = new Account("A123", "Alice", 1000.0);
        Account account2 = new Account("B456", "Bob", 2000.0);

        // 顯示初始資訊
        System.out.printf("帳戶1 - 帳號: %s, 餘額: %.2f\n",
                account1.getAccountNumber(), account1.getBalance());
        System.out.printf("帳戶2 - 帳號: %s, 餘額: %.2f\n",
                account2.getAccountNumber(), account2.getBalance());

        // 測試單筆存款
        account1.deposit(500.0);
        System.out.printf("帳戶1存款後 - 帳號: %s, 餘額: %.2f\n",
                account1.getAccountNumber(), account1.getBalance());

        // 測試提款
        account1.withdraw(100.0);
        System.out.printf("帳戶1提款後 - 帳號: %s, 餘額: %.2f\n",
                account1.getAccountNumber(), account1.getBalance());

        // 測試多幣別存款
        try {
            account1.deposit(100, "USD");
            System.out.printf("帳戶1外幣存款後 - 帳號: %s, 餘額: %.2f\n",
                    account1.getAccountNumber(), account1.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("外幣存款錯誤: " + e.getMessage());
        }

        // 測試多筆存款
        try {
            account1.withdraw(100.0);
            System.out.printf("帳戶1再次提款後 - 帳號: %s, 餘額: %.2f\n",
                    account1.getAccountNumber(), account1.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("提款失敗: " + e.getMessage());
        }

        // 測試多筆存款
        try {
            account2.deposit(1000, 2000, 5000);
            System.out.printf("帳戶2多筆存款後 - 帳號: %s, 餘額: %.2f\n",
                    account2.getAccountNumber(), account2.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("多筆存款錯誤: " + e.getMessage());
        }

        // 顯示帳戶總數
        System.out.println("\n目前帳戶總數: " + Account.getAccountCount());
    }
}