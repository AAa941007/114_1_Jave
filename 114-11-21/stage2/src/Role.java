public abstract class Role {
    private String name;
    private int health;
    private int attackPower;

    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    /**
     * 受到傷害（具體方法）
     * 統一的受傷流程
     */
    public void takeDamage(int damage) {
        // 步驟 1: 扣血
        this.health -= damage;

        // 步驟 2: 顯示訊息
        System.out.println("💥 " + this.name + " 受到 " + damage + " 點傷害！目前生命值：" +
                (this.health > 0 ? this.health : 0));

        // 步驟 3: 檢查死亡
        if (!isAlive()) {
            // 步驟 4: 死亡處理
            onDeath();
        }
    }

    /**
     * 死亡時的處理（抽象方法）
     */
    public abstract void onDeath();

    // Getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    // Setter
    public void setHealth(int health) {
        this.health = health;
    }

    // 檢查角色是否存活
    public boolean isAlive() {
        return health > 0;
    }

    // 抽象方法
    public abstract void prepareBattle();
    public abstract void afterBattle();
    public abstract void attack(Role opponent);
    public abstract void showSpecialSkill();

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }
}