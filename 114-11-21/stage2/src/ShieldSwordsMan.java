public class ShieldSwordsMan extends SwordsMan {

    private int defenseCapacity;

    public ShieldSwordsMan(String name, int health, int attackPower, int defenseCapacity) {
        super(name, health, attackPower);
        this.defenseCapacity = defenseCapacity;
    }

    @Override
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5;

        // 先執行統一受傷流程（會顯示💥訊息）
        opponent.takeDamage(reducedDamage);

        // 再顯示原始攻擊訊息
        System.out.println(this.getName() + " 揮劍攻擊 " + opponent.getName() + " 造成 " +
                reducedDamage + " 點傷害。" + opponent);
    }

    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    public void defence() {
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println(this.getName() + " 使用盾牌防禦，恢復 " + defenseCapacity + " 點生命值。" + this);
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 力竭倒下...");
        System.out.println("🛡️  厚重的盾牌砸在地上，揚起一陣塵土。");
        System.out.println("⚔️  " + this.getName() + " 的劍也隨之掉落。");
        System.out.println("---");
    }

    @Override
    public void prepareBattle() {
        System.out.println("🛡️  " + this.getName() + " 檢查盾牌的牢固程度...");
        System.out.println("⚔️  同時確認劍刃的鋒利度，準備應戰。");
    }

    @Override
    public void afterBattle() {
        System.out.println("🛡️  " + this.getName() + " 檢視盾牌上的新傷痕，並進行簡單修補。");
    }

    @Override
    public void showSpecialSkill() {
        String top = "╔═════════════════════════════╗";
        String mid = "╠═════════════════════════════╣";
        String bot = "╚═════════════════════════════╝";
        System.out.println(top);
        System.out.printf("║ %-12s 的特殊技能   ║%n", getName());
        System.out.println(mid);
        System.out.println("║ 技能名稱：盾牌猛擊          ║");
        System.out.println("║ 技能描述：使用盾牌撞擊敵人  ║");
        System.out.println("║ 技能效果：造成傷害並暈眩    ║");
        System.out.printf("║ 防禦加成：+%-2d 防禦力          ║%n", getDefenseCapacity());
        System.out.println(bot);
    }
}