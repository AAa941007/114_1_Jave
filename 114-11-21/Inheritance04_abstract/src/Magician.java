public class Magician extends Role {

    // 封裝（Encapsulation）
    // 私有屬性：治癒力 healPower 只能透過 getter 或類別內部方法操作
    private int healPower;


    // 建構子 Constructor
    // 透過 super(...) 呼叫父類別 Role 的建構子
    // 展現「繼承 Inheritance」，子類別擁有父類別的基本屬性與行為

    public Magician(String name, int health, int attackPower, int healPower) {
        super(name, health, attackPower); // 呼叫父類別 Role 的建構子
        this.healPower = healPower;
    }

    // getter：提供外部取得封裝後的屬性（不能直接修改）
    public int getHealPower() {
        return healPower;
    }


    // 覆寫 attack()：多型（Polymorphism）的核心
    // 父類別 Role 中已定義 attack()，但法師需要「魔法攻擊」的版本
    // 因此使用 @Override 覆寫父類別的行為
    // 當父類型的參考指向 Magician 物件時，執行的是這個版本
    @Override
    public void attack(Role opponent) {
        // 透過父類別提供的 getter / setter 控制血量（封裝）
        opponent.setHealth(opponent.getHealth() - this.getAttackPower());

        System.out.println(
                this.getName() + " 使用魔法攻擊 " +
                        opponent.getName() + "，造成 " +
                        this.getAttackPower() + " 點傷害。" +
                        opponent // 呼叫對象的 toString()
        );
    }


    // 法師專屬行為 heal() — 抽象化 Abstraction 的延伸
    // 父類別 Role 沒有 heal()，代表這是 Magician 的專有能力

    public void heal(Role ally) {
        ally.setHealth(ally.getHealth() + this.healPower);

        System.out.println(
                this.getName() + " 治療 " +
                        ally.getName() + "，回復 " +
                        healPower + " 點生命值。" +
                        ally // 顯示被治療者的新狀態
        );
    }


    // toString() 覆寫：提供法師額外資訊（治癒力）
    @Override
    public String toString() {
        // 呼叫父類別 toString()，再額外加入 Magician 的屬性
        return super.toString() + ", 治癒力: " + healPower;
    }

    @Override
    public void showSpecialSkill() {
        String top = "╔═════════════════════════════╗";
        String mid = "╠═════════════════════════════╣";
        String bot = "╚═════════════════════════════╝";
        System.out.println(top);
        System.out.printf("║ %-12s 的特殊技能   ║%n", getName());
        System.out.println(mid);
        System.out.println("║ 技能名稱：元素爆發          ║");
        System.out.println("║ 技能描述：召喚強大魔法攻擊  ║");
        System.out.println("║ 技能效果：範圍魔法傷害      ║");
        System.out.println("║ 額外效果：恢復自身魔力      ║");
        System.out.println(bot);
    }
}
