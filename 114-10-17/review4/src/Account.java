// 在 Account 類別中示範 deposit 方法的多載範例

import java.util.Scanner;

public class Account {
    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶餘額
    private double balance;

    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.setAccountNumber(accountNumber);
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，將餘額設為0");
        }
    }

    public Account() {
        this("000000", 0.0);
    }

    public Account(String accountNumber) {
        this(accountNumber, 0.0);
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }

    /**
     * 設定帳戶餘額
     * @param balance 欲設定的帳戶餘額，必須為正數
     * @throws IllegalArgumentException 若餘額非正數則拋出例外
     */
    public void setBalance(double balance) {
        Scanner scanner = new Scanner(System.in);
        double validBalance = balance;
        if (!(balance >= 0)) {
            validBalance = getValidAmount(scanner, "請輸入帳戶餘額：", x -> x > 0, "帳戶餘額必須為正數");
        }
        this.balance = validBalance;
    }

    /**
     * 設定帳戶號碼
     * @param accountNumber 欲設定的帳戶號碼
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException 若金額非正數則拋出例外
     */
    public void deposit(double amount) {
        Scanner scanner = new Scanner(System.in);
        double validAmount = amount;
        if (!(amount > 0)) {
            validAmount = getValidAmount(scanner, "請輸入存款金額：", x -> x > 0, "存款金額必須為正數");
        }
        balance += validAmount;
    }

    /**
     * 多幣別存款方法，將指定金額以不同貨幣存入帳戶
     * @param amount 存入金額，必須為正數
     * @param currency 貨幣種類，支援 TWD、USD、EUR、JPY
     */
    public void deposit(double amount, String currency) {
        double exchangeRate = 1.0;
        switch (currency.toUpperCase()) {
            case "USD":
                exchangeRate = 30.0; // 假設1 USD = 30 TWD
                break;
            case "EUR":
                exchangeRate = 35.0; // 假設1 EUR = 35 TWD
                break;
            case "JPY":
                exchangeRate = 0.25; // 假設1 JPY = 0
            default:
                System.out.println("不支援的貨幣，請使用 TWD、USD、EUR 或 JPY");
                return;
        }
        double amountInTWD = amount * exchangeRate;
        this.deposit(amountInTWD);
    }

    /**
     * 多重存款方法，將多個金額一次存入帳戶
     * @param amounts 多個存入金額，皆必須為正數
     */
    public void deposit(double ... amounts) {
        double total = 0;
        for (double amount : amounts) {
            if ( amount >= 0 ) {
                total += amount;
            }
            else
                throw new IllegalArgumentException("存款金額必須為正數");
        }
        this.deposit(total);
    }

    /**
     * 提款方法，從帳戶扣除指定金額
     * @param amount 提款金額，必須為正數且不得超過餘額
     * @throws IllegalArgumentException 若金額不合法則拋出例外
     */
    public void withdraw(double amount) {
        Scanner scanner = new Scanner(System.in);
        double validAmount = amount;
        if (!(amount > 0 && amount <= balance)) {
            validAmount = getValidAmount(scanner, "請輸入提款金額：", x -> x > 0 && x <= balance, "提款金額不合法");
        }
        balance -= validAmount;
    }

    /**
     * 取得有效金額，最多重試3次，失敗則丟出例外
     * @param scanner 輸入來源
     * @param prompt 提示訊息
     * @param validator 驗證金額是否合法
     * @param errorMsg 錯誤訊息
     * @return 合法金額
     */
    private double getValidAmount(Scanner scanner, String prompt, java.util.function.Predicate<Double> validator, String errorMsg) {
        int attempts = 0;
        double amount = 0;
        while (attempts < 3) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (validator.test(amount)) {
                    return amount;
                }
            } else {
                scanner.next(); // 清除非數字輸入
            }
            attempts++;
            if (attempts < 3) {
                System.out.println(errorMsg + " 請重新輸入。");
            }
        }
        throw new IllegalArgumentException(errorMsg);
    }
}
