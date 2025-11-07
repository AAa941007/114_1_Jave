public class Magician extends Role {
    private int healPower;

    // 建構子: 初始化魔法師的名稱, 生命值, 攻擊力和治癒力
    public Magician(String name, int health, int attackPower, int healPower) {
        super(name, health, attackPower);
        this.healPower = healPower;
    }

    // 取得治癒力
    public int getHealPower() {
        return healPower;
    }

    // 攻擊對手
    public void attack(Magician opponent) {
        opponent.setHealth(opponent.getHealth() - this.getHealPower());
        System.out.println(this.getName() + " 攻擊 " + opponent.getName() + " 造成 " +
                this.getHealPower() + " 點傷害。"+ opponent);
    }

    // 治療盟友
    public void heal(SwordsMan ally) {
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println(this.getName() + " 治療 " + ally.getName() + " 回復 " +
                this.healPower + " 點生命值。"+ ally);
    }
    public  String toString() {
        return super.toString() + ", 治癒力: " + healPower;
    }
}