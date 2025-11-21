public class RPG {
    public static void main(String[] args) {

        // 建立角色物件
        SwordsMan swordsMan_light = new SwordsMan("光明劍士", 100, 20);
        SwordsMan swordsMan_dark  = new SwordsMan("黑暗劍士", 100, 25);
        Magician magician_light = new Magician("光明法師", 80, 15, 10);
        Magician magician_dark  = new Magician("黑暗法師", 80, 20, 5);
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8);

        Role[] gameRoles = {
                swordsMan_light, swordsMan_dark,
                magician_light,  magician_dark,
                shieldSwordsMan
        };

        // ====== 第二階段：標題與回合制戰鬥 ======
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第二階段");
        System.out.println("   展示：具體方法 + 抽象方法的結合");
        System.out.println("════════════════════════════════════════");
        System.out.println("⚔️  戰鬥開始！");

        // 回合計數器
        int round = 1;

        // 主迴圈：每個角色輪流行動一次（第二階段）
        for (Role currentRole : gameRoles) {

            // 若角色已死亡，跳過
            if (!currentRole.isAlive()) {
                continue;
            }

            // 顯示回合分隔線
            System.out.println("━━━━━━━━━━ 第 " + round + " 回合 ━━━━━━━━━━");

            // 戰前準備
            currentRole.prepareBattle();

            // 劍士的行動
            if (currentRole instanceof SwordsMan && !(currentRole instanceof ShieldSwordsMan)) {
                // 隨機選擇攻擊目標
                Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                // 如果目標是持盾劍士，先觸發防禦
                if (target instanceof ShieldSwordsMan) {
                    ((ShieldSwordsMan) target).defence();
                }

                // 執行攻擊
                System.out.println("⚔️  " + currentRole.getName() + " 揮劍攻擊 " + target.getName() + "！");
                currentRole.attack(target);
            }
            // 持盾劍士的行動
            else if (currentRole instanceof ShieldSwordsMan) {
                Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                if (target instanceof ShieldSwordsMan) {
                    ((ShieldSwordsMan) target).defence();
                }

                System.out.println("🛡️  " + currentRole.getName() + " 揮劍攻擊 " + target.getName() + "！");
                currentRole.attack(target);
            }
            // 法師的行動
            else if (currentRole instanceof Magician) {
                Magician magician = (Magician) currentRole;

                // 50% 機率攻擊或治療
                if (Math.random() < 0.5) {
                    Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                    if (target instanceof ShieldSwordsMan) {
                        ((ShieldSwordsMan) target).defence();
                    }

                    System.out.println("✨ " + currentRole.getName() + " 施放魔法攻擊 " + target.getName() + "！");
                    currentRole.attack(target);
                } else {
                    // 治療
                    Role ally = gameRoles[(int) (Math.random() * gameRoles.length)];
                    magician.heal(ally);
                }
            }

            // 戰後整理
            currentRole.afterBattle();

            // 增加回合數
            round++;
        }

        // 第二階段結束
        System.out.println("════════════════════════════════════════");
        System.out.println("          🏆 戰鬥結束");
        System.out.println("════════════════════════════════════════");

        // ====== 第一階段：展示所有角色的特殊技能 ======
        System.out.println("════════════════════════════════════════");
        System.out.println("          角色特殊技能展示");
        System.out.println("════════════════════════════════════════");

        for (Role role : gameRoles) {
            role.showSpecialSkill();
        }

        System.out.println("════════════════════════════════════════");

        // ====== 原始版本戰鬥 ======
        System.out.println("戰鬥開始！");

        // 主迴圈：每個角色輪流行動一次（原始版本）
        for (Role currentRole : gameRoles) {

            if (!currentRole.isAlive()) {
                continue;
            }

            // 劍士的行動
            if (currentRole instanceof SwordsMan) {
                Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                if (target instanceof ShieldSwordsMan)
                    ((ShieldSwordsMan) target).defence();

                currentRole.attack(target);
            }

            // 法師的行動
            else if (currentRole instanceof Magician) {
                Magician magician = (Magician) currentRole;

                if (Math.random() < 0.5) {
                    Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                    if (target instanceof ShieldSwordsMan)
                        ((ShieldSwordsMan) target).defence();

                    currentRole.attack(target);

                } else {
                    magician.heal(
                            gameRoles[(int) (Math.random() * gameRoles.length)]
                    );
                }
            }
        }
    }
}