/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.*;

class Arena {
    String[] levels = {"Bronze", "Silver", "Epic", "Legend", "Mythic", "Champion"};
    String[] botNames = {"Raven", "Wolf", "Crow", "Lion", "Ghost", "Blade", "Storm", "Zero"};
    Random rand = new Random();

    public String getArenaName() {
        return "Colosseum of Destiny";
    }

    public void startArena(Hero player, Scanner scanner, Stack<String> deathStack) {
        int levelIdx = Math.min(player.level / 20, levels.length-1);
        String arenaLevel = levels[levelIdx];
        System.out.println("\n[Colosseum: " + arenaLevel + "]");
        System.out.println("You face a mysterious challenger...");

        // Create Bot
        String botName = botNames[rand.nextInt(botNames.length)];
        HeroType randomType = HeroType.types.get(rand.nextInt(5)+1);
        Hero bot = new Hero(botName, randomType);
        bot.level = player.level + rand.nextInt(3) - 1;
        for (int i = 0; i < bot.stats.length; i++) bot.stats[i] += rand.nextInt(6);

        System.out.println("Challenger: " + bot.name + " (" + bot.type.name + ")");

        while (!player.isDead() && !bot.isDead()) {
            System.out.println("\n1. Attack  2. Skill  3. Ultimate  4. Yield");
            String c = scanner.nextLine();
            boolean actionDone = false;
            switch (c) {
                case "1":
                    actionDone = player.attack(bot); // Now allowed: both are Entity
                    break;
                case "2":
                    actionDone = player.useSkill(bot);
                    break;
                case "3":
                    actionDone = player.useUltimate(bot);
                    break;
                case "4":
                    System.out.println("You yield. The crowd is disappointed!");
                    player.hp = 0;
                    return;
            }
            if (bot.isDead()) {
                System.out.println("You defeated " + bot.name + "! Gained lots of EXP!");
                player.gainExp(50 + levelIdx * 10);
                return;
            }
            // Bot attacks back
            int botMove = rand.nextInt(3);
            boolean botAction = false;
            if (botMove == 0) botAction = bot.attack(player);
            else if (botMove == 1) botAction = bot.useSkill(player);
            else botAction = bot.useUltimate(player);

            if (player.isDead()) {
                System.out.println("You lost the arena match!");
                deathStack.push(player.getHeroTypeName());
                break;
            }
        }
    }
}