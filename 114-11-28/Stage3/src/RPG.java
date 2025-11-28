public class RPG {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第三階段");
        System.out.println("      展示：多層繼承結構設計");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("📋 類別繼承結構：");
        System.out.println("Role (最高層)");
        System.out.println("├─ MeleeRole (近戰角色)");
        System.out.println("│  ├─ SwordsMan (劍士)");
        System.out.println("│  └─ ShieldSwordsMan (持盾劍士)");
        System.out.println("└─ RangedRole (遠程角色)");
        System.out.println("   ├─ Magician (魔法師)");
        System.out.println("   └─ Archer (弓箭手)");
        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // 建立角色 - 注意參數變化
        // 近戰角色：需要 armor（護甲值）
        SwordsMan swordsMan_light = new SwordsMan("光明劍士", 100, 20, 5);
        SwordsMan swordsMan_dark = new SwordsMan("黑暗劍士", 100, 25, 3);
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8, 10);

        // 遠程角色：需要 range（射程）和 maxEnergy（能量值）
        Magician magician_light = new Magician("光明法師", 80, 15, 10, 8, 100);
        Magician magician_dark = new Magician("黑暗法師", 80, 20, 5, 8, 100);
        Archer archer = new Archer("精靈射手", 90, 18, 10, 80, 30);

        Role[] gameRoles = {swordsMan_light, swordsMan_dark, shieldSwordsMan,
                magician_light, magician_dark, archer};

        // ========== 展示所有角色的特殊技能 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          📋 角色特殊技能展示");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        for (Role role : gameRoles) {
            role.showSpecialSkill();
            System.out.println();
        }

        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 展示類別特性 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          🔍 角色類別特性展示");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("【近戰角色特性】");
        for (Role role : gameRoles) {
            if (role instanceof MeleeRole) {
                MeleeRole melee = (MeleeRole) role;
                System.out.println(role.getName() + "：武器=" + melee.getWeaponType() +
                        "，護甲=" + melee.getArmor());
            }
        }
        System.out.println();

        System.out.println("【遠程角色特性】");
        for (Role role : gameRoles) {
            if (role instanceof RangedRole) {
                RangedRole ranged = (RangedRole) role;
                System.out.println(role.getName() + "：攻擊類型=" + ranged.getRangedAttackType() +
                        "，射程=" + ranged.getRange() +
                        "，能量=" + ranged.getEnergy() + "/" + ranged.getMaxEnergy());
            }
        }
        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 戰鬥流程 ==========
        System.out.println("⚔️  戰鬥開始！");
        System.out.println();

        int round = 1;
        int maxRounds = 5; // 限制回合數避免輸出過長

        for (Role currentRole : gameRoles) {
            if (round > maxRounds) break;
            if (!currentRole.isAlive()) {
                continue;
            }

            System.out.println("━━━━━━━━━━ 第 " + round + " 回合 ━━━━━━━━━━");

            // 戰前準備
            currentRole.prepareBattle();
            System.out.println();

            // 執行動作
            if (currentRole instanceof ShieldSwordsMan) {
                // 持盾劍士：可能先防禦
                ShieldSwordsMan shield = (ShieldSwordsMan) currentRole;
                if (Math.random() < 0.3) {
                    shield.defence();
                    System.out.println();
                }
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            } else if (currentRole instanceof SwordsMan) {
                // 一般劍士：直接攻擊
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            } else if (currentRole instanceof Archer) {
                // 弓箭手：遠程攻擊
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            } else if (currentRole instanceof Magician) {
                // 魔法師：攻擊或治療
                Magician magician = (Magician) currentRole;
                if (Math.random() < 0.6) {
                    Role target = getRandomAliveTarget(gameRoles, currentRole);
                    if (target != null) {
                        currentRole.attack(target);
                    }
                } else {
                    Role ally = getRandomAliveRole(gameRoles);
                    if (ally != null) {
                        magician.heal(ally);
                    }
                }
            }

            System.out.println();

            // 戰後行為
            if (currentRole.isAlive()) {
                currentRole.afterBattle();
            }

            System.out.println();
            round++;
        }

        // ========== 戰鬥結束 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          🏆 戰鬥結束");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("【近戰角色狀態】");
        for (Role role : gameRoles) {
            if (role instanceof MeleeRole && role.isAlive()) {
                MeleeRole melee = (MeleeRole) role;
                System.out.println("✅ " + role.getName() + " - 生命值：" + role.getHealth() +
                        "，護甲：" + melee.getArmor());
            }
        }
        System.out.println();

        System.out.println("【遠程角色狀態】");
        for (Role role : gameRoles) {
            if (role instanceof RangedRole && role.isAlive()) {
                RangedRole ranged = (RangedRole) role;
                System.out.println("✅ " + role.getName() + " - 生命值：" + role.getHealth() +
                        "，能量：" + ranged.getEnergy() + "/" + ranged.getMaxEnergy());
            }
        }
        System.out.println();

        System.out.println("【陣亡角色】");
        boolean hasDeadRoles = false;
        for (Role role : gameRoles) {
            if (!role.isAlive()) {
                System.out.println("💀 " + role.getName());
                hasDeadRoles = true;
            }
        }
        if (!hasDeadRoles) {
            System.out.println("所有角色都存活！");
        }
    }

    /**
     * 隨機選擇一個存活的目標（排除自己）
     */
    private static Role getRandomAliveTarget(Role[] roles, Role self) {
        Role[] aliveRoles = new Role[roles.length];
        int count = 0;

        for (Role role : roles) {
            if (role != self && role.isAlive()) {
                aliveRoles[count++] = role;
            }
        }

        if (count == 0) return null;
        return aliveRoles[(int) (Math.random() * count)];
    }

    /**
     * 隨機選擇一個存活的角色（包括自己）
     */
    private static Role getRandomAliveRole(Role[] roles) {
        Role[] aliveRoles = new Role[roles.length];
        int count = 0;

        for (Role role : roles) {
            if (role.isAlive()) {
                aliveRoles[count++] = role;
            }
        }

        if (count == 0) return null;
        return aliveRoles[(int) (Math.random() * count)];
    }
}

