/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.game;


import java.util.*;

public class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static Stack<String> deathStack = new Stack<>(); // Track deaths
    private static final int MAX_DEATHS = 10;
    private static int reincarnations = 0;
    private static Hero player;
    private static String playerName;
    private static HashMap<String, Dungeon> dungeons = new HashMap<>();
    private static Arena arena;
    private static LinkedList<String> dungeonHistory = new LinkedList<>();
    private static boolean isGameOver = false;

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine();

        introCutscene();

        // Pick HeroType
        HeroType chosenType = HeroType.chooseHeroType(scanner);
        player = new Hero(playerName, chosenType);

        // Welcome Message
        System.out.println("\n[System] Welcome to ????? ????");
        System.out.println("[System] Your adventure begins now as the 7th prince, Level 1 " + chosenType.name + "!\n");

        initializeDungeons();
        arena = new Arena();

        firstDungeon();

        mainMenu();

        if (isGameOver) endGame();
    }

    private static void introCutscene() {
        System.out.println("\n[Cutscene]");
        System.out.println("You have died.");
        System.out.println("Once, you were a peasant, bullied and oppressed by the strong and wealthy.");
        System.out.println("Despair consumed you, and you ended your own life.");
        System.out.println("But as darkness took you, a mysterious figure appeared...");
        System.out.println("A strange voice: \"Do you want another chance? Entertain me. Survive.\"\n");
        pause();
    }

    private static void firstDungeon() {
        System.out.println("[System] You awaken in a new body, a young prince.");
        System.out.println("[System] A floating window appears before you.");
        Dungeon testDungeon = new Dungeon("Test Dungeon", "F", "Training Grounds", "Slime", 30, 1);
        System.out.println("[System] Welcome to '" + testDungeon.getWelcomeMessage() + "'!");
        testDungeon.runDungeon(player, scanner, deathStack, dungeonHistory);
    }

    private static void mainMenu() {
        while (!isGameOver) {
            System.out.println("\n[Main Menu]");
            System.out.println("1. Enter Dungeon");
            System.out.println("2. Enter Arena (" + arena.getArenaName() + ")");
            System.out.println("3. Check Status");
            System.out.println("4. Quit");

            System.out.print("Choose an action: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    chooseDungeon();
                    break;
                case "2":
                    arena.startArena(player, scanner, deathStack);
                    checkDeath();
                    break;
                case "3":
                    player.printStatus();
                    break;
                case "4":
                    isGameOver = true;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void chooseDungeon() {
        System.out.println("\nAvailable Dungeons:");
        int idx = 1;
        for (String key : dungeons.keySet()) {
            System.out.println(idx + ". " + dungeons.get(key).name + " (" + dungeons.get(key).rank + ")");
            idx++;
        }
        System.out.print("Choose a dungeon (number): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= dungeons.size()) {
                String selected = (String)dungeons.keySet().toArray()[choice-1];
                dungeons.get(selected).runDungeon(player, scanner, deathStack, dungeonHistory);
                checkDeath();
            } else {
                System.out.println("Invalid dungeon!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a number.");
        }
    }

    private static void checkDeath() {
        if (player.isDead()) {
            reincarnations++;
            deathStack.push(player.getHeroTypeName());
            System.out.println("\n[System] You died. Returning to Death's realm...");
            pause();
            if (deathStack.size() >= MAX_DEATHS) {
                deathCutscene();
                isGameOver = true;
            } else if (reincarnations < MAX_DEATHS) {
                System.out.println("[Death] That was amusing... Try again.");
                HeroType newType = HeroType.chooseHeroType(scanner);
                player = new Hero(playerName, newType);
            }
        }
    }

    private static void deathCutscene() {
        System.out.println("\n[Final Cutscene]");
        System.out.println("The mysterious figure appears, now revealed as Death.");
        System.out.println("\"You have used up all your chances. Your soul will be cast into the void!\"");
        System.out.println("[Your Progress]");
        player.printStatus();
        System.out.println("Dungeon History: " + dungeonHistory);
        System.out.println("Lives used: " + deathStack.size());
        System.out.println("\n[System] Game Over. You cease to exist.");
    }

    private static void endGame() {
        System.out.println("\n[Ending]");
        if (player.level >= 100) {
            System.out.println("You reach the final stage of Death's Game.");
            System.out.println("[Death] Choose: 1. Challenge me, or 2. Accept Reincarnation.");
            String finalChoice = scanner.nextLine();
            if (finalChoice.equals("1")) {
                boolean win = player.fightDeath();
                if (win) {
                    System.out.println("You have defeated Death! You transcend the cycle and awake in a hospital bed...");
                    System.out.println("It was all a dream. You survived your coma. A new life awaits.");
                } else {
                    System.out.println("You have lost to Death. [System] Game Over.");
                }
            } else {
                System.out.println("You accept reincarnation. You awake in a hospital bed, realizing it was all a test. You survived.");
            }
            System.out.println("[System] The true name is revealed: 'Death's Game'");
        } else {
            System.out.println("[System] Thanks for playing. Try reaching Level 100 for the true ending!");
        }
    }

    private static void initializeDungeons() {
        dungeons.put("F", new Dungeon("Slime Fields", "F", "Beginner's Trial", "Slime", 30, 1));
        dungeons.put("E", new Dungeon("Goblin Den", "E", "Forest of Mischief", "Goblin", 35, 5));
        dungeons.put("D", new Dungeon("Spider Nest", "D", "Webbed Abyss", "Spider", 38, 10));
        dungeons.put("C", new Dungeon("Wolf Cavern", "C", "Moonlit Grotto", "Wolf", 42, 15));
        dungeons.put("B", new Dungeon("Orc Fortress", "B", "Crimson Hold", "Orc", 46, 20));
        dungeons.put("A", new Dungeon("Undead Crypt", "A", "Halls of the Damned", "Undead", 50, 25));
        dungeons.put("S", new Dungeon("Dragon Lair", "S", "Eternal Flame", "Dragon", 60, 30));
        dungeons.put("SS", new Dungeon("Demon Abyss", "SS", "Abyssal Gate", "Demon", 70, 40));
        dungeons.put("SSS", new Dungeon("Celestial Arena", "SSS", "Heaven's Door", "Angel", 80, 50));
    }

    private static void pause() {
        System.out.print("[Press Enter to continue]");
        scanner.nextLine();
    }
}