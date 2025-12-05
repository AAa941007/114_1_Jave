public class CharacterDemo {
    public static void main(String[] args) {
        // 基本型態 char
        char ch1 = 'A';
        char ch2 = '中';
        char ch3 = 65;  // ASCII碼
        char ch4 = '\u4E2D';  // Unicode編碼（中）

        System.out.println("ch1 = " + ch1);
        System.out.println("ch2 = " + ch2);
        System.out.println("ch3 = " + ch3);
        System.out.println("ch4 = " + ch4);

        // Character 包裝類別
        Character chObj = 'B';
        System.out.println("Character物件: " + chObj);
    }
}
