/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.*;

class HeroType {
    public static final HashMap<Integer, HeroType> types = new HashMap<>();
    public String name, passive;
    public int[] baseStats; // STR, AGI, INT, VIT, LUK
    public LinkedList<Skill> skills;
    public Skill ultimate;
    public HeroType ascendedForm;

    static {
        // Define beginner classes
        HeroType warrior = new HeroType("Warrior", "Berserker's Rage (ATK+10% when below 50% HP)",
                new int[]{10, 4, 3, 9, 4},
                new Skill[]{
                    new Skill("Slash", "Quick sword attack", 10, 0),
                    new Skill("Guard", "Block incoming damage", 5, 3),
                    new Skill("Battle Cry", "Increase STR", 0, 0)
                },
                new Skill("Berserker Strike", "Devastating blow", 40, 0)
        );
        HeroType mage = new HeroType("Mage", "Mana Overflow (Regenerate 5 MP/turn)",
                new int[]{3, 6, 13, 5, 3},
                new Skill[]{
                    new Skill("Fireball", "Cast a fireball", 12, 2),
                    new Skill("Ice Shard", "Cast ice shard", 10, 2),
                    new Skill("Magic Shield", "Block damage", 0, 3)
                },
                new Skill("Meteor", "Summon meteor", 45, 2)
        );
        HeroType archer = new HeroType("Archer", "Eagle Eye (ACC+15%)",
                new int[]{7, 13, 4, 6, 5},
                new Skill[]{
                    new Skill("Arrow Shot", "Shoot arrow", 9, 1),
                    new Skill("Double Shot", "Shoot twice", 13, 1),
                    new Skill("Trap", "Set a trap", 7, 1)
                },
                new Skill("Rain of Arrows", "Volley of arrows", 35, 1)
        );
        HeroType healer = new HeroType("Healer", "Holy Light (Heal allies 10% more)",
                new int[]{2, 6, 10, 6, 6},
                new Skill[]{
                    new Skill("Heal", "Restore HP", 12, 2),
                    new Skill("Barrier", "Reduce damage", 0, 3),
                    new Skill("Smite", "Light attack", 8, 2)
                },
                new Skill("Divine Blessing", "Massive heal", 0, 2)
        );
        HeroType rogue = new HeroType("Rogue", "Shadowstep (Evade +10%)",
                new int[]{8, 10, 5, 5, 8},
                new Skill[]{
                    new Skill("Backstab", "High crit attack", 14, 4),
                    new Skill("Poison", "Poison enemy", 6, 4),
                    new Skill("Evade", "Dodge attack", 0, 1)
                },
                new Skill("Assassinate", "Lethal strike", 30, 4)
        );
        // Ascended classes
        HeroType berserker = new HeroType("Berserker", "Rage Unleashed (ATK+25% when below 30% HP)",
                new int[]{20, 7, 6, 15, 7},
                new Skill[]{
                    new Skill("Raging Slash", "Wild sword combo", 20, 0),
                    new Skill("War Stomp", "Stun enemy", 0, 3),
                    new Skill("Whirlwind", "Hit all enemies", 18, 0)
                },
                new Skill("Cataclysm", "Unstoppable rampage", 70, 0)
        );
        HeroType archmage = new HeroType("Archmage", "Infinite Mana (Spell cost -50%)",
                new int[]{6, 12, 26, 9, 6},
                new Skill[]{
                    new Skill("Inferno", "Massive fire attack", 28, 2),
                    new Skill("Blizzard", "Freeze all enemies", 25, 2),
                    new Skill("Arcane Surge", "Boost all stats", 0, 2)
                },
                new Skill("World Ender", "Apocalyptic spell", 80, 2)
        );
        HeroType sniper = new HeroType("Sniper", "Deadeye (Crit +30%)",
                new int[]{14, 25, 8, 11, 8},
                new Skill[]{
                    new Skill("Piercing Shot", "Armor-piercing shot", 27, 1),
                    new Skill("Rapid Fire", "Shoot multiple arrows", 22, 1),
                    new Skill("Snipe", "Long range shot", 30, 1)
                },
                new Skill("Heaven's Arrow", "Ultimate shot", 65, 1)
        );
        HeroType priest = new HeroType("Priest", "Holy Aura (Allies heal 25% more)",
                new int[]{5, 12, 20, 9, 10},
                new Skill[]{
                    new Skill("Mass Heal", "Heals all allies", 22, 2),
                    new Skill("Sanctuary", "Block all damage", 0, 3),
                    new Skill("Exorcism", "Purge evil", 20, 2)
                },
                new Skill("Miracle", "Revive all allies", 0, 2)
        );
        HeroType shadow = new HeroType("Shadow", "Untouchable (Evade +25%)",
                new int[]{15, 25, 12, 11, 25},
                new Skill[]{
                    new Skill("Shadow Dance", "Multi-hit attack", 24, 4),
                    new Skill("Smoke Bomb", "Escape battle", 0, 1),
                    new Skill("Nightmare", "Fear enemy", 12, 4)
                },
                new Skill("Reaper's Kiss", "Instant kill", 90, 4)
        );

        // Ascension mapping
        warrior.ascendedForm = berserker;
        mage.ascendedForm = archmage;
        archer.ascendedForm = sniper;
        healer.ascendedForm = priest;
        rogue.ascendedForm = shadow;
        types.put(1, warrior);
        types.put(2, mage);
        types.put(3, archer);
        types.put(4, healer);
        types.put(5, rogue);
    }

    public HeroType(String name, String passive, int[] baseStats, Skill[] skills, Skill ultimate) {
        this.name = name;
        this.passive = passive;
        this.baseStats = baseStats;
        this.skills = new LinkedList<>(Arrays.asList(skills));
        this.ultimate = ultimate;
        this.ascendedForm = null;
    }

    public LinkedList<Skill> getSkills() {
        return new LinkedList<>(skills);
    }

    public static HeroType chooseHeroType(Scanner scanner) {
        System.out.println("[Choose Your Hero Type]");
        int i = 1;
        for (HeroType t : types.values()) {
            System.out.println(i + ". " + t.name + " | Passive: " + t.passive);
            i++;
        }
        System.out.print("Choose class (1-5): ");
        int choice = 1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
            if (!types.containsKey(choice)) choice = 1;
        } catch (Exception e) {
            choice = 1;
        }
        return types.get(choice);
    }
}