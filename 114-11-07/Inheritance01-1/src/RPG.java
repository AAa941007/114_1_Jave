public class RPG {

    public static void main(String[] args) {

        // 建立劍士和魔法師角色
        // SwordsMan(name, health, attackPower)
        SwordsMan swordsMan_light = new SwordsMan( "光明劍士",  100, 20);
        SwordsMan swordsMan_dark = new SwordsMan( "黑暗劍士", 100, 25);

        // Magician(name, health, attackPower, healPower)
        Magician magician_light = new Magician( "光明法師",  80,  15,  10);
        Magician magician_dark = new Magician( "黑暗法師",  80,  20,  5);

        // 戰鬥過程
        System.out.println("戰鬥開始！");

        // 1. 光明劍士攻擊黑暗劍士
        swordsMan_light.attack(swordsMan_dark);

        // 2. 黑暗法師攻擊光明法師
        magician_dark.attack(magician_light);

        // 3. 黑暗法師治療黑暗劍士 (新增步驟)
        magician_dark.heal(swordsMan_dark);
    }
}