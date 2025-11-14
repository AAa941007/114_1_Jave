public class ShieldSwordsMan extends SwordsMan {

    private int defenseCapacity; // 4 usages

    // 建構子：初始化持盾劍士的名稱、生命值和攻擊力
    public ShieldSwordsMan(String name, int health, int attackPower, int defenseCapacity) { // no usages
        super(name, health, attackPower);
        this.defenseCapacity = defenseCapacity;
    }

    // 攻擊對手(劍士/魔法師)，父類別的參考指到了子類別物件
    @Override // 2 usages
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5; // 持盾劍士攻擊力減少5點
        opponent.setHealth(opponent.getHealth() - reducedDamage);
        System.out.println(this.getName() + " 揮劍攻擊 " + opponent.getName() + " 造成 " +
                reducedDamage + " 點傷害 " + opponent);
    }

    public int getDefenseCapacity() { // no usages
        return defenseCapacity;
    }

    public void defence() { // no usages
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println(this.getName() + " 使用盾牌防禦，恢復 " + defenseCapacity + " 點生命值 " + this);
    }
}