
public class Role {
    private String name;
    private int health; // 修正 health 為 int 型別
    private int attackPower;

    // 修正建構子參數類型錯誤，將 string 改為 String，並修正 health 的型別
    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getHealth() { // 修正回傳型別為 int
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setHealth(int health) { // 修正參數型別為 int
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0; // 直接比較 int 型別的 health
    }
    @Override
    public  String toString() {
        return "角色名稱: " + name + ", 生命值: " + health + ", 攻擊力: " + attackPower;
    }
}
