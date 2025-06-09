/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

abstract class Entity {
    String name;
    int hp, maxHp;
    int[] stats = new int[5]; // [STR, AGI, INT, VIT, LUK]

    public boolean isDead() {
        return hp <= 0;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;
        System.out.println(name + " took " + dmg + " damage! (" + hp + "/" + maxHp + " HP)");
    }
}