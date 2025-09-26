public class Account {
    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶餘額 (使用 double，支援小數，與 AccountTest 相容)
    private double balance;

    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額，必須為正數
     */
    public Account(String accountNumber, double initialBalance) {
        this.setAccountNumber(accountNumber);

        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，將餘額設為 0");
            this.balance = 0; // 若初始餘額不合法，將餘額設為 0
        }
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 設定帳戶號碼
     * @param accountNumber 新的帳戶號碼
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }

    /**
     * 取得合法金額，根據不同模式檢查金額，給三次輸入機會
     * @param amount 初始金額
     * @param mode 檢查模式：setBalance、deposit、withdraw
     * @return 合法金額
     * @throws IllegalArgumentException 三次都不合法則拋出
     */
    private double getValidAmount(double amount, String mode) {
        int attempts = 0;
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (attempts < 3) {
            boolean valid = false;
            switch (mode) {
                case "setBalance":
                    valid = amount >= 0;
                    if (!valid) System.out.println("餘額必須為正數，請重新輸入：");
                    break;
                case "deposit":
                    valid = amount > 0;
                    if (!valid) System.out.println("存款金額必須為正數，請重新輸入：");
                    break;
                case "withdraw":
                    valid = amount > 0 && amount <= balance;
                    if (!valid) System.out.println("提款金額不合法，請重新輸入：");
                    break;
            }
            if (valid) return amount;
            if (attempts < 2) amount = scanner.nextDouble();
            attempts++;
        }
        throw new IllegalArgumentException(mode.equals("setBalance") ? "餘額必須為正數" : (mode.equals("deposit") ? "存款金額必須為正數" : "提款金額不合法"));
    }

    /**
     * 設定帳戶餘額
     * @param balance 新餘額，必須為正數
     */
    public void setBalance(double balance) {
        try {
            this.balance = getValidAmount(balance, "setBalance");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "，餘額設為 0");
            this.balance = 0;
        }
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     */
    public void deposit(double amount) {
        try {
            double validAmount = getValidAmount(amount, "deposit");
            balance += validAmount;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "，餘額設為 0");
            this.balance = 0;
        }
    }

    /**
     * 提款方法，從帳戶餘額扣除指定金額
     * @param amount 提款金額，必須為正數且不得超過餘額
     */
    public void withdraw(double amount) {
        try {
            double validAmount = getValidAmount(amount, "withdraw");
            balance -= validAmount;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "，餘額設為 0");
            this.balance = 0;
        }
    }
}
