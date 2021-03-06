package org.irvinehacks;

import java.util.Scanner;
import java.lang.Math;

import org.irvinehacks.enemy.Enemies;
import org.irvinehacks.enemy.Enemy;
import org.irvinehacks.map.MapGenerator;
import org.irvinehacks.map.MazeSolver;
import org.irvinehacks.player.Player;
import org.irvinehacks.player.Weapon;
import org.irvinehacks.score.Scorekeeper;

public class App {

    private static final char BOWCHAR = '}';
    private static final char SWORDCHAR = 'T';
    private static final char PLAYERCHAR = '@';
    private static final char HEALTHCHAR = 'h';
    public static int announcements = 0;
    //public static char COINCHAR = '©';

    public static final int HEALTH_POTION_VALUE = 3;

    public static void main(String[] args) {


        //MAIN MENU CODE
        Scanner scanner = new Scanner(System.in);
        String input = " ";


        //WINDOW SIZE CALIBRATION
        System.out.println("##################################################################");
        for (int i = 0; i <= 27; i++) {
            System.out.print("#\n");
        }
        System.out.print("Please align your window so you can see the top line of tags AND this line of text UNBROKEN. Press any key to continue");
        scanner.nextLine();

        while (input.toLowerCase().charAt(0) != 'p') {

            //TITLE
            AsciiText.printTitleScreen();
            AsciiText.printLines(13);

            //MENU
            AsciiText.printMenu();

            scanner = new Scanner(System.in);
            input = scanner.nextLine().charAt(0) + "";

            switch (input.toLowerCase()) {
                case "h": //Help
                    AsciiText.printTutorial();

                    System.out.print("\nPress any key to return to main menu");
                    scanner.nextLine();

                    break;
                case "s": //Scoreboard
                    Scorekeeper.printScoreboard();
                    AsciiText.printLines(23 - Scorekeeper.getScores().size());

                    System.out.println("\n[C] - Clear scores");
                    System.out.println("[E] - Exit/Return to main menu");

                    input = scanner.nextLine();
                    if (input.toLowerCase().charAt(0) == 'c') {
                        Scorekeeper.clearScores();
                        System.out.println("\nScores cleared.\nReturning to main menu...\n");
                    } else
                        System.out.println("\nReturning to main menu...\n");


                    break;
                case "c": //Credits
                    AsciiText.printTitleScreen();
                    System.out.println("\nOriginally submitted for the Irvine Hacks 2020 Hackathon\nSee https://irvinehacks.com for more information.\n");
                    System.out.println("Lead programmer:\n  - Ivar Rydstrom\n");
                    System.out.println("Player movement & attacks:\n  - Dante Cofano\n");
                    System.out.println("Map Generation & enemy movement:\n  - Raymond Wong\n");
                    System.out.println("Moral Support: \n  - Kabir Kwatra\n  - Aaron Lee");

                    AsciiText.printLines(5);

                    System.out.print("Press any key to continue");
                    scanner.nextLine();
                    break;
                case "q": //Quit
                    System.out.println("\nQuitting...");
                    System.exit(0);
                    break;
                case "p": //Play

                    //GAME CODE!!!

                    int level = 0;
                    int playerXp = 0;

                    //TUTORIAL
                    AsciiText.printTutorial();

                    System.out.print("Enter your name to start: ");
                    Player p = new Player(5, scanner.nextLine(), 0, 0);
                    System.out.println("Welcome " + p.getName() + "!");

                    //RUNS EVERY LEVEL RESET
                    level: while (true) {
                        int spaceFactor;
                        switch (level) {
                            case 1:
                            case 2:
                            case 3:
                                spaceFactor = 5;
                                break;
                            case 4:
                            case 5:
                            case 6:
                                spaceFactor = 4;
                                break;
                            default:
                                spaceFactor = 3;
                        }
                        MapGenerator mg = new MapGenerator(20, 40, spaceFactor); //randomly generates a new level
                        char[][] screen = mg.map; //assigns to "screen" for local reference

                        //SETS PLAYER POSITION
                        p.setX(mg.getXStart());
                        p.setY(mg.getYStart());
                        AsciiText.printLevel(++level); //Prints "Level #" in ascii art after increasing level by 1
                        System.out.println();

                        int levelFinishXp = (int)(50 * Math.cbrt(level));

                        screen[p.yPos][p.xPos] = PLAYERCHAR;
                        screen[(int)(Math.random() * 18) + 1][(int)(Math.random() * 38) + 1] = HEALTHCHAR;


                        //ADDING WEAPON PICKUPS TO LEVEL
                        int weaponGen = (int)(Math.random() * 4) + 1;
                        switch (weaponGen) {
                            case 1:
                                screen[(int)(Math.random() * 18) + 1][(int)(Math.random() * 38) + 1] = BOWCHAR;
                                break;
                            case 2:
                                screen[(int)(Math.random() * 18) + 1][(int)(Math.random() * 38) + 1] = SWORDCHAR;
                                break;
                            case 3:
                                break;
                        }


                        //ADDING ENEMIES TO LEVEL
                        Enemies enemies = new Enemies();
                        for (int i = level; i > 0; i--) {
                            int xPs = (int)(Math.random()*(mg.map[0].length-1)) + 1; //x-position
                            int yPs = (int)(Math.random()*(mg.map.length-1)) + 1; //y-position
                            int type = (int)(Math.random() * 3) + 1; //randomizes enemy type

                            Enemy enemy;
                            switch (type) {
                                case 1:
                                    enemy = new Enemy('O', 1, 1,1, 1, xPs, yPs); // orc
                                    break;
                                case 2:
                                    enemy = new Enemy('G', 1, 2, 1, 1, xPs, yPs); // goblin
                                    break;
                                case 3:
                                    enemy = new Enemy('B', 3, 1, 1, 2, xPs, yPs); // behemoth
                                    break;
                                default:
                                    enemy = new Enemy(' ', 0, 0, 0, -1, xPs, yPs); // null error
                            }
                            screen[yPs][xPs] = enemy.character;
                            enemies.addEnemy(enemy);
                        }

                        for (int i = 0; i < screen.length; i++) {
                            for (int j = 0; j < screen[0].length; j++) {
                                System.out.print(screen[i][j]);
                            }
                            System.out.print("\n");
                        }

                        System.out.println("Health: " + p.getHealth() + "/" + p.getHealthCap() + "\tWeapon: " + p.getWeapon() + "\tOverall XP: " + playerXp);


                        //RUNS EVERY TURN
                        while (p.getHealth() > 0) {
                            announcements = 0;

                            enemies.clearDeadEnemies();
                            scanner = new Scanner(System.in);
                            input = scanner.nextLine();

                            if(p.getName().equals("DEBUGUSER")) {
                                switch(input) {
                                    case "SKIP":
                                    case "SKIPLVL":
                                    case "SKIPLEVEL":
                                        continue level;
                                    case "HEAL":
                                        p.increaseHealth();
                                        continue;
                                    case "INVINCIBLE":
                                    case "DEATHOFF":
                                        p.setHealthCap(999);
                                        p.increaseHealth(999);
                                        continue;
                                    case "QUIT":
                                    case "DIE":
                                        p.decreaseHealth(999);
                                        continue;
                                    case "KILLALL":
                                        for(int e = 0; e < enemies.enemies.size(); e++)
                                            enemies.enemies.get(e).takeDamage(999);
                                        continue;
                                    case "SWORD":
                                        //TODO give player a sword
                                        continue;
                                    case "BOW":
                                        //TODO give player a bow
                                        continue;
                                }
                            }

                            int deltaX, deltaY;


                            if (p.getWeapon().getDurability() == 0) {
                                p.setWeapon(new Weapon("Fists", 1, 1, 1));
                            }


                            for (Enemy en: enemies.enemies) {
                                deltaX = Math.abs(p.xPos - en.xPos);
                                deltaY = Math.abs(p.yPos - en.yPos);

                                if (deltaX <= en.range && deltaY <= en.range) {
                                    p.decreaseHealth(en.attack);
                                    System.out.println(en + " delt " + en.attack + " damage to " + p.getName() + "!");
                                    continue;
                                }

                                boolean[][] pathToPlayer = MazeSolver.solve(en.xPos, en.yPos, p.xPos, p.yPos, mg.getMap());

                                if(!MazeSolver.isSolvable(pathToPlayer)) {
                                    continue;
                                }


                                screen[en.yPos][en.xPos] = mg.getSpace();
                                if(pathToPlayer[en.yPos-1][en.xPos]) { //up
                                    en.move('w');
                                } else if(pathToPlayer[en.yPos+1][en.xPos]) { //down
                                    en.move('s');
                                } else if(pathToPlayer[en.yPos][en.xPos-1]) { //left
                                    en.move('a');
                                } else if(pathToPlayer[en.yPos][en.xPos+1]) { //right
                                    en.move('d');
                                }

                                /*
                                if (p.xPos > en.xPos && screen[en.yPos][en.xPos + 1] != mg.getWall() && !enemies.isEnemy(screen, en.xPos + 1, en.yPos)) {
                                    en.move('a');
                                } else if (p.xPos < en.xPos && screen[en.yPos][en.xPos - 1] != mg.getWall() && !enemies.isEnemy(screen, en.xPos - 1, en.yPos)) {
                                    en.move('d');
                                } else if (p.yPos > en.yPos && screen[en.yPos + 1][en.xPos] != mg.getWall() && !enemies.isEnemy(screen, en.xPos, en.yPos + 1)) {
                                    en.move('w');
                                } else if (p.yPos < en.yPos && screen[en.yPos - 1][en.xPos] != mg.getWall() && !enemies.isEnemy(screen, en.xPos, en.yPos - 1)) {
                                    en.move('s');
                                } //*/

                                screen[en.yPos][en.xPos] = en.character;
                            }

                            screen[p.yPos][p.xPos] = PLAYERCHAR; //updates player's position on the map

                            if (input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d") || input.equals(" ")) {
                                int lastX = p.xPos;
                                int lastY = p.yPos;

                                int dir = 0;
                                if (input.equals("w")) {
                                    dir = 1;
                                } else if (input.equals("s")) {
                                    dir = 2;
                                } else if (input.equals("d")) {
                                    dir = 3;
                                } else if (input.equals("a")) {
                                    dir = 4;
                                } else {
                                    dir = -1;
                                    p.attack(screen, enemies);
                                }
                                p.move(dir);

                                if (screen[p.yPos][p.xPos] == '-' || (p.yPos == screen.length - 1) && screen[p.yPos][p.xPos] != '#')
                                    break;

                                //ITEM PICKUPS
                                switch (screen[p.yPos][p.xPos]) {
                                    case HEALTHCHAR:
                                        p.increaseHealth(HEALTH_POTION_VALUE);
                                        playerXp += 15;
                                        System.out.println("Picked up a health potion & gained 15 XP!");
                                        announcements+=3;
                                        break;
                                    case BOWCHAR:
                                        p.setWeapon(new Weapon("Bow", 2, 1, 5));
                                        playerXp += 25;
                                        System.out.println("Picked up a bow & gained 25 XP!");
                                        announcements+=3;
                                        break;
                                    case SWORDCHAR:
                                        p.setWeapon(new Weapon("Sword", 1, 2, 10));
                                        playerXp += 25;
                                        System.out.println("Picked up a sword & gained 25 XP!");
                                        announcements+=3;
                                        break;
                                }


                                for (Enemy enemy: enemies.enemies) {
                                    if (enemy.isDead) {
                                        enemy.attack = 0;
                                        enemy.character = ' ';
                                        screen[enemy.yPos][enemy.xPos] = enemy.character;
                                        playerXp += 50;
                                        System.out.println("Defeated a monster & gained 50 XP!");
                                        announcements += 3;
                                    }
                                }

                                if (screen[p.yPos][p.xPos] != mg.getWall() && screen[p.yPos][p.xPos] != 'O' && screen[p.yPos][p.xPos] != 'G' && screen[p.yPos][p.xPos] != 'B') {
                                    screen[p.yPos][p.xPos] = PLAYERCHAR;
                                    if (dir != -1) {
                                        screen[lastY][lastX] = ' ';
                                    }
                                } else {
                                    p.yPos = lastY;
                                    p.xPos = lastX;
                                }



                                ////   ////    //  //   //  //////  //  //   //   ////     ////   ////    //////   ////   ////
                                // //  // //   //  //// //    //    //  //// //  //        // //  // //   //      //     //
                                ////   ////    //  // / //    //    //  // / //  // //     ////   ////    ////     ///    ///
                                //     // //   //  // ////    //    //  // ////  //  //    //     // //   //         //     //
                                //     //  //  //  //   //    //    //  //   //   ////     //     //  //  //////  ////   ////
                                AsciiText.printLines(10 - announcements);
                                for (int i = 0; i < screen.length; i++) {
                                    for (int j = 0; j < screen[0].length; j++) {
                                        System.out.print(screen[i][j]);
                                    }
                                    System.out.print("\n");
                                }
                                System.out.println("Health: " + p.getHealth() + "/" + p.getHealthCap() + "\tWeapon: " + p.getWeapon() + "\tOverall XP: " + playerXp);
                                levelFinishXp--;
                            }
                        }
                        if (levelFinishXp > 0 && p.getHealth() > 0) {
                            if(enemies.isEmpty())
                                levelFinishXp*=Math.max(2, level);

                            System.out.println("Next level! Gained " + levelFinishXp + "XP!" + (enemies.isEmpty()? "(Multiplied for clearing all enemies!)" : ""));
                            playerXp += levelFinishXp;
                        } else if (p.getHealth() > 0) {
                            System.out.println("Next level!");
                        }

                        if (p.getHealth() < 1) {
                            AsciiText.printGameOver();
                            break;
                        }
                    }

                    if(!p.getName().equals("DEBUGUSER"))
                        Scorekeeper.addScore(p.getName(), playerXp);

                    Scorekeeper.printScoreboard();
                    AsciiText.printLines(12 - Scorekeeper.getScores().size());

                    System.out.print("\nPress any key to continue");
                    scanner.nextLine();

                    break;
            }
        }
        //END OF MAIN MENU CODE

        scanner.close();
    }
}