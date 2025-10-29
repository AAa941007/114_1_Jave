import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class AccountTest {

    public static void main(String[] args) {
        // 程式碼中使用了 List<Account> customers，因此需要初始化一個 ArrayList
        List<Account> customers = new ArrayList<>();

        // 這裡可以選擇性地初始化一些帳戶，
        // 圖片中未顯示初始化邏輯，我們保持其空白。

        operation(customers);

        // 顯示所有客戶帳戶資訊
        System.out.println("\n所有客戶帳戶資訊:");
        printCustomerAccounts(customers);
    }

    public static void operation(List<Account> customers) {
        Scanner scanner = new Scanner(System.in);
        Account selectedAccount = null;

        while (true) {
            menu();
            System.out.print("請選擇功能 (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除執行行緩衝區

            switch (choice) {
                case 1:
                    System.out.print("輸入帳戶號碼: ");
                    String accNum = scanner.nextLine();
                    System.out.print("輸入持有人姓名: ");
                    String ownerName = scanner.nextLine();
                    System.out.print("輸入初始餘額: ");
                    double initialBalance = scanner.nextDouble();

                    Account newAccount = new Account(accNum, ownerName, initialBalance);
                    addCustomer(customers, newAccount);
                    break;
                case 2:
                    System.out.print("輸入要查詢的帳戶號碼: ");
                    String searchAccNum = scanner.nextLine();
                    selectedAccount = customerInAction(customers, searchAccNum);
                    printCustomerInfo(selectedAccount);
                    break;
                case 3:
                    System.out.println("\n所有客戶帳戶資訊:");
                    printCustomerAccounts(customers);
                    break;
                case 4:
                    System.out.print("輸入要刪除的帳戶號碼: ");
                    String deleteAccNum = scanner.nextLine();
                    deleteCustomer(customers, deleteAccNum);
                    break;
                case 5:
                    System.out.println("\n離開系統，謝謝使用！");
                    return;
                default:
                    System.out.println("無效的選擇，請重新輸入!");
            }
        }
    }

    public static Account customerInAction(List<Account> customers, String accountNumber) {
        for (Account account : customers) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("找不到指定的帳戶號碼: " + accountNumber);
        return null;
    }

    public static void addCustomer(List<Account> customers, Account newAccount) {
        customers.add(newAccount);
        System.out.println("新增客戶成功: " + newAccount.getAccountNumber());
    }

    public static void deleteCustomer(List<Account> customers, String accountNumber) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getAccountNumber().equals(accountNumber)) {
                Account removedAccount = customers.remove(i);
                System.out.println("刪除客戶成功: " + removedAccount.getAccountNumber() + " (" + removedAccount.getOwnerName() + ")");
                return;
            }
        }
        System.out.println("找不到指定的帳戶號碼: " + accountNumber);
    }

    public static void printCustomerAccounts(List<Account> customers) {
        for (Account customer : customers) {
            printCustomerInfo(customer);
        }
    }

    public static void printCustomerInfo(Account account) {
        if (account == null) {
            System.out.println("無法列印帳戶資訊，帳戶不存在");
            return;
        }
        System.out.println("帳戶號碼: " + account.getAccountNumber() +
                ", 持有人: " + account.getOwnerName() +
                ", 餘額: " + account.getBalance());
    }

    public static void menu() {
        System.out.println("\n功能選單:");
        System.out.println("1. 新增客戶");
        System.out.println("2. 列印指定客戶帳戶資訊");
        System.out.println("3. 顯示所有客戶帳戶資訊");
        System.out.println("4. 刪除客戶帳戶");
        System.out.println("5. 離開");
    }
}