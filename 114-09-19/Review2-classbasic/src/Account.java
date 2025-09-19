public class Account {
    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶餘額
    private double balance;

    /**
     * 建構子：初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得目前餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存款金額
     */
    public void deposit(double amount) {
        // 檢查存款金額是否大於零
        if (amount > 0) {
            balance += amount; // 增加餘額
        } else {
            System.out.println("Deposit amount must be positive."); // 金額不合法時顯示訊息
        }
    }

    /**
     * 提款方法，從帳戶提取指定金額
     * @param amount 提款金額
     * @return 是否成功提款
     */
    public boolean withdraw(double amount) {
        // 檢查提款金額是否大於零且餘額足夠
        if (amount > 0 && amount <= balance) {
            balance -= amount; // 減少餘額
            return true; // 提款成功
        } else {
            System.out.println("Insufficient funds or invalid amount."); // 金額不合法或餘額不足時顯示訊息
            return false; // 提款失敗
        }
    }
}
