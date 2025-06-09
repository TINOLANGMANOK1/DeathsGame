/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

class Skill {
    String name, desc;
    int power, statIndex; // statIndex: 0-STR, 1-AGI, 2-INT, 3-VIT, 4-LUK

    public Skill(String name, String desc, int power, int statIndex) {
        this.name = name;
        this.desc = desc;
        this.power = power;
        this.statIndex = statIndex;
    }
}