public class RPG {
    public static void main(String[] args) {


        // 建立角色物件（實例化）
        // SwordsMan、Magician 都「繼承」自 Role，
        // 因此具備 Role 定義的基本屬性與方法（如姓名、血量、攻擊力）
        // 子類別可加入自己的特色（如 Magician 的 heal）
        SwordsMan swordsMan_light = new SwordsMan("光明劍士", 100, 20);
        SwordsMan swordsMan_dark  = new SwordsMan("黑暗劍士", 100, 25);

        // Magician 是 Role 的子類別，除了 Role 的能力以外，還有 healPower
        Magician magician_light = new Magician("光明法師", 80, 15, 10);
        Magician magician_dark  = new Magician("黑暗法師", 80, 20, 5);

        // ShieldSwordsMan 繼承 SwordsMan，也繼承 Role
        // 顯示多層繼承：Role → SwordsMan → ShieldSwordsMan
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8);
        Role[] gameRoles = {
                swordsMan_light, swordsMan_dark,
                magician_light,  magician_dark,
                shieldSwordsMan
        };


        // 建立 Role 陣列（多型 Polymorphism）
        // 陣列類型是 Role，可以容納所有繼承自 Role 的物件
        // 讓不同的角色（劍士、法師、持盾劍士）在同一結構中運作
        // ====== 第一阶段新增：展示所有角色的特殊技能 ======
        System.out.println("==========================================");
        System.out.println("              角色特殊技能展示");
        System.out.println("==========================================");
        System.out.println();

        for (Role role : gameRoles) {
            role.showSpecialSkill();
            System.out.println();
        }

        System.out.println("==========================================");
        System.out.println();

        System.out.println("戰鬥開始！");




        // 主迴圈：每個角色輪流行動一次
        for (Role currentRole : gameRoles) {

            // isAlive() 是 Role 中封裝好的方法，用來判斷角色是否還存活
            // 若角色血量 <= 0 則跳過該角色的行動
            if (!currentRole.isAlive()) {
                continue;
            }




            // 劍士的行動（SwordsMan）
            // instanceof 用於判斷物件是否為某類型
            if (currentRole instanceof SwordsMan) {

                // 從角色陣列中隨機挑選攻擊目標
                Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                // 如果攻擊對象是 ShieldSwordsMan，則先觸發其防禦能力
                if (target instanceof ShieldSwordsMan)
                    ((ShieldSwordsMan) target).defence();

                // attack() 原本定義於 Role 中，但各子類別可能覆寫 Override
                // 實際執行哪個版本，取決於 currentRole 實際的類別（多型）
                currentRole.attack(target);
            }


            // 法師的行動（Magician）
            else if (currentRole instanceof Magician) {

                // 因需使用 heal()，此處向下轉型為 Magician 類型
                Magician magician = (Magician) currentRole;

                // Math.random() < 0.5 表示約 50% 機率攻擊，50% 機率治療
                if (Math.random() < 0.5) {

                    // 隨機選擇攻擊對象
                    Role target = gameRoles[(int) (Math.random() * gameRoles.length)];

                    // 若目標是持盾劍士，先觸發防禦
                    if (target instanceof ShieldSwordsMan)
                        ((ShieldSwordsMan) target).defence();

                    // 執行攻擊（魔法攻擊版本）
                    currentRole.attack(target);

                } else {
                    // 否則施放治療技能
                    // 隨機選擇隊友進行治療
                    magician.heal(
                            gameRoles[(int) (Math.random() * gameRoles.length)]
                    );
                }
            }


        }
    }
}
