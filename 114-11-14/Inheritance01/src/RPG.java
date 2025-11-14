public class RPG {
    public static void main(String[] args) {
        // --- 角色初始化區塊 ---
        // 建立兩個基礎劍士角色 (名稱, 生命值, 攻擊力)
        SwordsMan swordsman_light = new SwordsMan("光明劍士", 100, 20);
        SwordsMan swordsman_dark = new SwordsMan("黑暗劍士", 100, 25);

        // 建立兩個魔法師角色 (名稱, 生命值, 攻擊力, 治療量)
        Magician magician_light = new Magician("光明法師", 80, 15, 10);
        Magician magician_dark = new Magician("黑暗法師", 80, 20, 5);

        // 建立持盾劍士角色 (名稱, 生命值, 攻擊力, 防禦能力/恢復量)
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8);

        // 將所有角色存入 Role 陣列，方便迭代處理 (多型)
        Role[] gameRoles = {swordsman_light, swordsman_dark, magician_light, magician_dark, shieldSwordsMan};

        // --- 戰鬥過程開始 ---
        System.out.println("戰鬥開始！");

        // 迭代陣列中的所有角色，讓他們進行一次行動
        for (Role currentRole : gameRoles) {
            // 檢查當前角色是否存活
            if (!currentRole.isAlive()) {
                continue; // 如果已死亡，則跳過此角色的行動
            }

            // --- 劍士類角色 (SwordsMan 或 ShieldSwordsMan) 的行動邏輯 ---
            if (currentRole instanceof SwordsMan) {
                // 隨機選擇一個目標 (從整個角色陣列中選擇)
                Role target = gameRoles[(int)(Math.random() * gameRoles.length)];

                // 特殊邏輯：如果目標是 ShieldSwordsMan (持盾劍士)，則目標主動防禦
                if (target instanceof ShieldSwordsMan) {
                    ((ShieldSwordsMan) target).defence(); // 執行 defence() 方法 (生命值恢復)
                }

                // 執行攻擊
                currentRole.attack(target);
            }

            // --- 魔法師類角色 (Magician) 的行動邏輯 ---
            else if (currentRole instanceof Magician) {
                // 將 Role 參考轉型為 Magician，以便能使用其特有的 heal 方法
                Magician magician = (Magician) currentRole;

                // 50% 機率攻擊 (Math.random() < 0.5)
                if (Math.random() < 0.5) {
                    // 攻擊邏輯與劍士類似
                    Role target = gameRoles[(int)(Math.random() * gameRoles.length)];

                    // 特殊邏輯：如果目標是 ShieldSwordsMan (持盾劍士)，則目標主動防禦
                    if (target instanceof ShieldSwordsMan) {
                        ((ShieldSwordsMan) target).defence(); // 執行 defence() 方法
                    }

                    // 執行攻擊
                    currentRole.attack(target);
                }
                // 50% 機率治療
                else {
                    // 隨機選擇一個目標進行治療
                    magician.heal(gameRoles[(int)(Math.random() * gameRoles.length)]);
                }
            }
        }
    }
}