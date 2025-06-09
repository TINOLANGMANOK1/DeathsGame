/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.*;

class Hero extends Entity {
    HeroType type;
    int level;
    int exp;
    LinkedList<Skill> skills = new LinkedList<>();
    boolean ascended = false;

    public Hero(String name, HeroType type) {
        this.name = name;
        this.type = type;
        this.level = 1;
        this.exp = 0;
        this.maxHp = 100 + type.baseStats[3] * 10;
        this.hp = maxHp;
        this.stats = Arrays.copyOf(type.baseStats, 5);
        this.skills = new LinkedList<>(type.getSkills());
        this.ascended = false;
    }

    public void printStatus() {
        System.out.println("\n[Status]");
        System.out.println("Name: " + name);
        System.out.println("Type: " + type.name + (ascended ? " [Ascended]" : ""));
        System.out.println("Level: " + level + "  Exp: " + exp + "/" + (level*10 + 50));
        System.out.println("HP: " + hp + "/" + maxHp);
        String[] statNames = {"STR", "AGI", "INT", "VIT", "LUK"};
        for (int i = 0; i < statNames.length; i++) {
            System.out.print(statNames[i] + ": " + stats[i] + "  ");
        }
        System.out.println("\nSkills:");
        for (Skill s : skills) System.out.println("- " + s.name + ": " + s.desc);
        System.out.println("Ultimate: " + type.ultimate.name + " - " + type.ultimate.desc);
        System.out.println("Passive: " + type.passive);
    }

    public String getHeroTypeName() {
        return type.name;
    }

    public void gainExp(int amount) {
        exp += amount;
        while (exp >= level*10 + 50) {
            exp -= (level*10 + 50);
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        for (int i = 0; i < stats.length; i++) stats[i] += 1 + (int)(Math.random()*2);
        maxHp += 10 + stats[3];
        hp = maxHp;
        System.out.println("[System] Leveled Up! Now level " + level + ".");
        if (!ascended && level >= 50 && type.ascendedForm != null) {
            ascend();
        }
    }

    private void ascend() {
        System.out.println("[System] You are eligible to ascend to " + type.ascendedForm.name + "! Do you want to ascend? (y/n)");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("y")) {
            this.type = type.ascendedForm;
            this.skills = new LinkedList<>(type.getSkills());
            this.ascended = true;
            System.out.println("[System] Ascended to " + type.name + "!");
        }
    }

    // NOW: Accepts any Entity (Hero or Monster)
    public boolean attack(Entity opponent) {
        int dmg = stats[0] + (int)(Math.random()*5) + 1;
        opponent.hp -= dmg;
        System.out.println(this.name + " hit " + opponent.name + " for " + dmg + " damage! (" + opponent.hp + "/" + opponent.maxHp + " HP)");
        return opponent.hp <= 0;
    }

    public boolean useSkill(Entity opponent) {
        if (skills.isEmpty()) return false;
        Skill s = skills.get(new Random().nextInt(skills.size()));
        int dmg = s.power + stats[s.statIndex];
        opponent.hp -= dmg;
        System.out.println(this.name + " used " + s.name + "! (" + dmg + " damage)");
        return opponent.hp <= 0;
    }

    public boolean useUltimate(Entity opponent) {
        Skill ult = type.ultimate;
        int dmg = ult.power + stats[ult.statIndex] * 2;
        opponent.hp -= dmg;
        System.out.println(this.name + " used Ultimate " + ult.name + "! (" + dmg + " damage)");
        return opponent.hp <= 0;
    }

    public boolean fightDeath() {
        int myPower = level * Arrays.stream(stats).sum();
        int deathPower = 15000;
        if (myPower > deathPower) {
            System.out.println("You defeated Death!");
            return true;
        }
        System.out.println("You lost to Death.");
        return false;
    }
}