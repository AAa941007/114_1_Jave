public class Magician extends Role {

    //==== 封裝：私有屬性 =========================
    private int healPower;

    //==== 建構子 ================================
    public Magician(String name, int health, int attackPower, int healPower) {
        super(name, health, attackPower);
        this.healPower = healPower;
    }

    //==== getter ================================
    public int getHealPower() {
        return healPower;
    }

    //==== 魔法攻擊：覆寫父類抽象方法 ============
    @Override
    public void attack(Role opponent) {
        // ① 統一受傷流程（含死亡判斷）
        opponent.takeDamage(this.getAttackPower());

        // ② 戰鬥日誌
        System.out.println(this.getName() + " 使用魔法攻擊 " +
                opponent.getName() + "，造成 " +
                this.getAttackPower() + " 點傷害。" + opponent);
    }

    //==== 法師獨有：補血 ========================
    public void heal(Role ally) {
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println(this.getName() + " 治療 " +
                ally.getName() + "，回復 " +
                healPower + " 點生命值。" + ally);
    }

    //==== 死亡效果 ==============================
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    //==== 戰前 / 戰後 / 特殊技能 ===============
    @Override
    public void prepareBattle() {
        System.out.println("📖 " + this.getName() + " 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }

    @Override
    public void afterBattle() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，恢復消耗的魔力。");
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

    //==== toString 補上治癒力 ==================
    @Override
    public String toString() {
        return super.toString() + ", 治癒力: " + healPower;
    }
}