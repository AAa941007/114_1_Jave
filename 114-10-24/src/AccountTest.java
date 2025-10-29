public class AccountTest {
    private static int customerCount; // 客戶數量
    public static void main(String[] args) {
        Account[] customers = new Account[10]; // 儲存客戶帳戶的陣列
        Account acc1 = new Account("A001", "Alice", 5000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("A002", "Bob", 3000);
        addCustomer(customers, acc2);
        Account acc3 = new Account("A003", "Charlie", -100);
        addCustomer(customers,  acc3);

        // 顯示所有客戶帳戶資訊
        System.out.println("\n所有位客戶帳戶資訊:");
        printCustomerAccounts(customers);
    }

    public static void addCustomer(Account[] customers, Account newAccount) {
        if ( customerCount < customers.length) {
            customers[customerCount] = newAccount;
            customerCount++;
            System.out.println("新增客戶成功: " + newAccount.getAccountNumber());
            return;
        }
        System.out.println("無法新增客戶，客戶數量已達上限");
    }

    public static void printCustomerAccounts(Account[] customers) {
        for (int i = 0; i < customerCount; i++) {
            printCustomerInfo(customers[i]);
        }
    }

    public static void printCustomerInfo(Account account) {
        System.out.println("帳戶號碼: " + account.getAccountNumber() +
                ", 持有人: " + account.getOwnerName() +
                ", 餘額: " + account.getBalance());
    }
}
