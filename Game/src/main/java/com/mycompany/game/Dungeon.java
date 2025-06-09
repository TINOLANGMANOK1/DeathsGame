/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.*;

class Dungeon {
    String name, rank, desc, monsterType;
    int monsterCount, minLevel;

    public Dungeon(String name, String rank, String desc, String monsterType, int monsterCount, int minLevel) {
        this.name = name; this.rank = rank; this.desc = desc;
        this.monsterType = monsterType; this.monsterCount = monsterCount; this.minLevel = minLevel;
    }

    public String getWelcomeMessage() {
        return desc + " [" + name + "]";
    }

    public void runDungeon(Hero player, Scanner scanner, Stack<String> deathStack, LinkedList<String> dungeonHistory) {
        System.out.println("\n[Dungeon: " + name + " | Rank " + rank + "]");
        if (player.level < minLevel) {
            System.out.println("Warning: Your level is below dungeon minimum (" + minLevel + ").");
        }
        dungeonHistory.add(name);
        Queue<Monster> encounters = new LinkedList<>();
        int moves = 0;
        int bossThreshold = 20;
        int expPerMonster = 7 + minLevel;
        int expPerBoss = 22 + minLevel*2;

        // Fill monster queue randomly
        for (int i = 0; i < monsterCount; i++) {
            if (Math.random() < 0.8)
                encounters.add(Monster.generate(monsterType, player.level));
        }
        while (!player.isDead()) {
            moves++;
            System.out.println("\nMove #" + moves + ". What will you do?");
            System.out.println("1. Move Forward");
            System.out.println("2. Rest (heal 10%)");
            System.out.println("3. Check Status");
            System.out.print("Choice: ");
            String c = scanner.nextLine();
            if (c.equals("1")) {
                if (Math.random() < 0.5 && !encounters.isEmpty()) {
                    Monster m = encounters.poll();
                    System.out.println("A wild " + m.name + " (Lv." + m.level + ") appears! [" + m.skill + "]");
                    while (m.hp > 0 && !player.isDead()) {
                        System.out.println("1. Attack  2. Skill  3. Ultimate  4. Run");
                        String action = scanner.nextLine();
                        switch (action) {
                            case "1":
                                if (player.attack(m)) {
                                    System.out.println("Defeated " + m.name + "! Gained " + expPerMonster + " EXP.");
                                    player.gainExp(expPerMonster);
                                }
                                break;
                            case "2":
                                if (player.useSkill(m)) {
                                    System.out.println("Defeated " + m.name + "! Gained " + expPerMonster + " EXP.");
                                    player.gainExp(expPerMonster);
                                }
                                break;
                            case "3":
                                if (player.useUltimate(m)) {
                                    System.out.println("Defeated " + m.name + "! Gained " + expPerMonster + " EXP.");
                                    player.gainExp(expPerMonster);
                                }
                                break;
                            case "4":
                                System.out.println("You escaped safely!");
                                m.hp = 0;
                                break;
                        }
                        // Monster attacks back if still alive
                        if (m.hp > 0 && !player.isDead()) {
                            int dmg = m.atk - player.stats[3]/2;
                            if (dmg < 1) dmg = 1;
                            player.takeDamage(dmg);
                        }
                    }
                    if (player.isDead()) break;
                } else {
                    System.out.println("Nothing here...");
                }
            } else if (c.equals("2")) {
                int heal = player.maxHp/10;
                player.hp += heal;
                if (player.hp > player.maxHp) player.hp = player.maxHp;
                System.out.println("Restored " + heal + " HP.");
            } else if (c.equals("3")) {
                player.printStatus();
            }

            if (moves == bossThreshold) {
                System.out.println("\n[Boss Room]");
                Monster boss = new Monster(monsterType + " King", 150 + minLevel*10, 20 + minLevel*2, 10 + minLevel, minLevel+5, "Boss Skill");
                System.out.println("The boss " + boss.name + " appears!");
                while (boss.hp > 0 && !player.isDead()) {
                    System.out.println("1. Attack  2. Skill  3. Ultimate");
                    String action = scanner.nextLine();
                    switch (action) {
                        case "1":
                            if (player.attack(boss)) {
                                System.out.println("Boss defeated! Gained " + expPerBoss + " EXP.");
                                player.gainExp(expPerBoss);
                            }
                            break;
                        case "2":
                            if (player.useSkill(boss)) {
                                System.out.println("Boss defeated! Gained " + expPerBoss + " EXP.");
                                player.gainExp(expPerBoss);
                            }
                            break;
                        case "3":
                            if (player.useUltimate(boss)) {
                                System.out.println("Boss defeated! Gained " + expPerBoss + " EXP.");
                                player.gainExp(expPerBoss);
                            }
                            break;
                    }
                    if (boss.hp > 0 && !player.isDead()) {
                        int dmg = boss.atk - player.stats[3]/2;
                        if (dmg < 1) dmg = 1;
                        player.takeDamage(dmg);
                    }
                }
                if (player.isDead()) break;
                System.out.println("You found a rare accessory! (Stat boost)");
                for (int i = 0; i < player.stats.length; i++) player.stats[i] += 2;
                System.out.print("Do you want to exit the dungeon (y/n)? ");
                if (scanner.nextLine().equalsIgnoreCase("y")) break;
            }
        }
    }
}