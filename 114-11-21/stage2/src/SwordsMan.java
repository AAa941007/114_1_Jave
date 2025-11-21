public class SwordsMan extends Role {

    public SwordsMan(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }

    @Override
    public void attack(Role opponent) {
        opponent.takeDamage(this.getAttackPower());   // 統一流程
        System.out.println(this.getName() + " 揮劍攻擊 " + opponent.getName() +
                " 造成 " + this.getAttackPower() + " 點傷害。" + opponent);
    }

    @Override
    public void onDeath() {
        System.out.println("⚔️ " + getName() + " 的劍掉落在地上，發出清脆的聲響。");
    }

    @Override
    public void prepareBattle() {
        System.out.println("🗡️  " + this.getName() + " 擦拭劍刃，劍身反射出凜冽的寒光...");
    }

    @Override
    public void afterBattle() {
        System.out.println("🗡️  " + this.getName() + " 將劍收入劍鞘。");
    }

    @Override
    public void showSpecialSkill() {
        String top = "┌─────────────────────────────┐";
        String bot = "└─────────────────────────────┘";
        System.out.println(top);
        System.out.printf("│ %-12s 的特殊技能   │%n", getName());
        System.out.println("├─────────────────────────────┤");
        System.out.println("│ 技能名稱：連續斬擊          │");
        System.out.println("│ 技能描述：快速揮劍三次      │");
        System.out.println("│ 技能效果：造成 150% 傷害    │");
        System.out.println(bot);
    }
}