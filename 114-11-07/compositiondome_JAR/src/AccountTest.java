import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountTest {
    public static void main(String[] args) {
        List<Account> customers = new ArrayList<>(); // 儲存客戶帳戶的List
        Account acc1 = new Account("A001", "ID001", "Alice", 5000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("A002", "ID002", "Alie", 5000);
        addCustomer(customers, acc2);
        Account acc3 = new Account("A003", "ID003", "Alce", 5000);
        addCustomer(customers, acc3);

        operation(customers);
    }

    public static void operation(List<Account> customers) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu();
            System.out.print("請選擇功能(1-7): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("輸入錯誤，請輸入數字");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("輸入帳戶號碼: ");
                    String accNum = scanner.nextLine();
                    System.out.print("輸入持有人身分證字號: ");
                    String ownerID = scanner.nextLine();
                    System.out.print("輸入持有人名稱: ");
                    String ownerName = scanner.nextLine();
                    System.out.print("輸入初始餘額: ");
                    double initialBalance;
                    try {
                        initialBalance = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("初始餘額輸入錯誤，設為0");
                        initialBalance = 0;
                    }
                    Account newAccount = new Account(accNum, ownerID, ownerName, initialBalance);
                    addCustomer(customers, newAccount);
                    break;
                case 2:
                    System.out.print("輸入要查詢的帳戶號碼: ");
                    String searchAccNum = scanner.nextLine();
                    Account selectedAccount = customerInAction(customers, searchAccNum);
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
                    System.out.print("輸入存款帳戶號碼: ");
                    String depositAccNum = scanner.nextLine();
                    System.out.print("輸入存款金額: ");
                    double amount;
                    try {
                        amount = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("金額輸入錯誤");
                        break;
                    }
                    depositToAccount(customers, depositAccNum, amount);
                    break;
                case 6:
                    System.out.print("輸入提款帳戶號碼: ");
                    String withdrawAccNum = scanner.nextLine();
                    System.out.print("輸入提款金額: ");
                    double withdrawAmount;
                    try {
                        withdrawAmount = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("金額輸入錯誤");
                        break;
                    }
                    withdrawFromAccount(customers, withdrawAccNum, withdrawAmount);
                    break;
                case 7:
                    System.out.println("離開系統，謝謝使用!");
                    scanner.close();
                    return;
                default:
                    System.out.println("無效的選擇，請重新輸入");
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
                System.out.println("刪除客戶成功: " + removedAccount.getAccountNumber()
                        + " (持有人姓名: " + removedAccount.getOwnerName()
                        + ", 身分證: " + removedAccount.getOwner().getId() + ")");
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
        System.out.println(account);
    }

    public static void depositToAccount(List<Account> customers, String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("存款金額必須大於0");
            return;
        }
        Account account = customerInAction(customers, accountNumber);
        if (account != null) {
            try {
                account.deposit(amount);
                System.out.println("存款成功，帳戶 " + accountNumber + " 的新餘額為: " + account.getBalance());
            } catch (IllegalArgumentException e) {
                System.out.println("存款失敗: " + e.getMessage());
            }
        }
    }

    public static void withdrawFromAccount(List<Account> customers, String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("提款金額必須大於0");
            return;
        }
        Account account = customerInAction(customers, accountNumber);
        if (account != null) {
            try {
                account.withdraw(amount);
                System.out.println("提款成功，帳戶 " + accountNumber + " 的新餘額為: " + account.getBalance());
            } catch (IllegalArgumentException e) {
                System.out.println("提款失敗: " + e.getMessage());
            }
        }
    }

    // 功能選單 (1) 新增客戶 (2) 列印指定客戶帳戶資訊 (3) 顯示所有客戶帳戶資訊 (4) 刪除客戶帳戶 (5) 存款 (6) 提款 (7) 離開
    public static void menu() {
        System.out.println("功能選單:");
        System.out.println("1. 新增客戶");
        System.out.println("2. 列印指定客戶帳戶資訊");
        System.out.println("3. 顯示所有客戶帳戶資訊");
        System.out.println("4. 刪除客戶帳戶");
        System.out.println("5. 存款");
        System.out.println("6. 提款");
        System.out.println("7. 離開");
    }
}