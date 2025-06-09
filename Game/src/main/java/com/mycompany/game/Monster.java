/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.*;

class Monster extends Entity {
    int atk, def, level;
    String skill;
    public Monster(String name, int hp, int atk, int def, int level, String skill) {
        this.name = name;
        this.hp = this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.level = level;
        this.skill = skill;
        this.stats = new int[]{atk, 0, 0, def, 0}; // Example
    }

    public static Monster generate(String baseName, int playerLevel) {
        int level = playerLevel + (int)(Math.random()*3);
        int hp = 50 + playerLevel * 5;
        int atk = 7 + playerLevel * 2;
        int def = 3 + playerLevel;
        String[] skills = {"Poison Bite", "Swift Strike", "Web Shot", "Fury", "Venom Cloud"};
        String s = skills[new Random().nextInt(skills.length)];
        return new Monster(baseName, hp, atk, def, level, s);
    }
}