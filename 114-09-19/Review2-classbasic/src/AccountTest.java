public class AccountTest {
    public static void main(String[] args) {
        // 帳戶初始化
        Account account1 = new Account("A123", 1000.0);
        Account account2 = new Account("B456", -2000.0);
        System.out.printf("帳戶號碼: %s%n初始餘額: %.2f%n", account1.getAccountNumber(), account1.getBalance());

        // 正常存款
        account1.deposit(500.0);
        System.out.printf("帳戶號碼: %s%n存款後餘額: %.2f%n", account1.getAccountNumber(), account1.getBalance());
        System.out.printf("帳戶號碼: %s%n初始餘額: %.2f%n", account2.getAccountNumber(), account2.getBalance());
        // 正常提款
        account1.withdraw(1000.0);
        System.out.printf("帳戶號碼: %s%n提款後餘額: %.2f%n", account1.getAccountNumber(), account1.getBalance());

        // 測試非法存款
        try {
            account1.deposit(-100.0);
            System.out.printf("帳戶號碼: %s%n存款後餘額: %.2f%n", account1.getAccountNumber(), account1.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("存款錯誤: " + e.getMessage());
        }

        // 測試非法提款
        try {
            account1.withdraw(2000.0);
            System.out.printf("帳戶號碼: %s%n提款後餘額: %.2f%n", account1.getAccountNumber(), account1.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("提款錯誤: " + e.getMessage());
        }
    }
}
