    import java.util.Scanner;
    import java.lang.Math;


    public class App {

        public static void main (String[] args) {
            Scanner scanner = new Scanner(System.in);
            int level = 0;
            int playerXp = 0;
            
            //TITLE
            AsciiText.printTitleScreen();
            
            //TUTORIAL
            AsciiText.printIntro();
            
            System.out.print("Enter your name to start: ");
            Player p = new Player(5, scanner.nextLine(), 0, 0);            
            System.out.println("Welcome " + p.getName() + "!");

            //RUNS EVERY LEVEL RESET
            while(true) {
                MapGenerator mg = new MapGenerator(20, 40); //randomly generates a new level
                char[][] screen = mg.map; //assigns to "screen" for local reference

                //SETS PLAYER POSITION
                p.setX(screen.length/2);
                p.setY(3);
                AsciiText.printLevel(++level); //Prints "Level #" in ascii art

                int levelFinishXp = (int)(50 * Math.cbrt(level));

                screen[p.yPos][p.xPos] = '@';
                screen[(int)(Math.random() * 18) + 1][(int)(Math.random() * 38) + 1] = 'h';


                //ADDING WEAPON PICKUPS TO LEVEL
                int weaponGen = (int)(Math.random() *4) +1;
                switch (weaponGen) {
                case 1:
                    screen[(int)(Math.random() * 18) + 1][(int)(Math.random() * 38) + 1] = 'D';
                    break;
                case 2:
                    screen[(int)(Math.random() * 18) + 1][(int)(Math.random() * 38) + 1] = 'T';
                    break;
                case 3:
                    break;
                }


                //ADDING ENEMIES TO LEVEL
                Enemies enemies = new Enemies();
                for (int i = level; i > 0; i--) {
                    int xPs =  (int)(Math.random()*12*3)+2; //x-position
                    int yPs =  (int)(Math.random()*6*3)+2;  //y-position
                    int type = (int)(Math.random()*3) + 1;   //randomizes enemy type

                    Enemy enemy;
                    switch (type) {
                        case 1:
                            enemy = new Enemy('O', 1, 1, 2, xPs, yPs); // orc
                            break;
                        case 2:
                            enemy = new Enemy('G', 1, 2, 1, xPs, yPs); // goblin
                            break;
                        case 3:
                            enemy = new Enemy('B', 3, 1, 1, xPs, yPs); // behemoth
                            break;
                        default:
                            enemy = new Enemy(' ', 0, 0, 0, xPs, yPs); // null error
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
                    enemies.clearDeadEnemies();
                    scanner = new Scanner(System.in);
                    String input = scanner.nextLine();
                    
                    boolean enemyIsNearPlayer;
                    int deltaX, deltaY;


                    if (p.getWeapon().getDurability() == 0) {
                        p.setWeapon(new Weapon ("Fists", 1, 1, 1));
                    }


                    for (Enemy en : enemies.enemies) {
                        deltaX = Math.abs(p.xPos - en.xPos);
                        deltaY = Math.abs(p.yPos - en.yPos);
                        enemyIsNearPlayer = deltaX <= en.range && deltaY <= en.range;

                        if (enemyIsNearPlayer) {
                            p.decreaseHealth(en.attack);
                            System.out.println(en + " delt " + en.attack + " damage to " + p.getName() + "!");
                        }
                        else if (p.xPos > en.xPos && screen[en.yPos][en.xPos + 1] != mg.getWall() && !enemies.isEnemy(screen, en.xPos + 1, en.yPos)) {
                            screen[en.yPos][en.xPos] = mg.getSpace();
                            en.move('a');
                            screen[en.yPos][en.xPos] = en.character;
                        } 
                        else if (p.xPos < en.xPos && screen[en.yPos][en.xPos - 1] != mg.getWall() && !enemies.isEnemy(screen, en.xPos - 1, en.yPos)) {
                            screen[en.yPos][en.xPos] = mg.getSpace();
                            en.move('d');
                            screen[en.yPos][en.xPos] = en.character;
                        }
                        else if (p.yPos > en.yPos && screen[en.yPos + 1][en.xPos] != mg.getWall() && !enemies.isEnemy(screen, en.xPos, en.yPos + 1)) {
                            screen[en.yPos][en.xPos] = mg.getSpace();
                            en.move('w');
                            screen[en.yPos][en.xPos] = en.character;
                        }
                        else if (p.yPos < en.yPos && screen[en.yPos - 1][en.xPos] != mg.getWall() && !enemies.isEnemy(screen, en.xPos, en.yPos - 1)) {
                            screen[en.yPos][en.xPos] = mg.getSpace();
                            en.move('s'); 
                            screen[en.yPos][en.xPos] = en.character;
                        }              
                    }
                    
                    screen[p.yPos][p.xPos] = '@'; //updates player's position on the map

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
                        } else if (input.equals(" ")) {
                            dir = -1; 
                            p.attack(screen, enemies);
                        }
                        p.move(dir);
                        
                        if(screen[p.yPos][p.xPos] == '-' || p.yPos == screen.length - 1)
                            break;
                        
                        if(screen[p.yPos][p.xPos] == 'h') {
                            if(p.getHealth() < p.getHealthCap()) {
                                p.increaseHealth();
                            }
                            playerXp += 15;
                            System.out.println("Picked up a health potion & gained 15 XP!");
                        }
                        
                        if(screen[p.yPos][p.xPos] == 'D') {
                            p.setWeapon(new Weapon("Bow", 2, 1, 5));
                            playerXp += 25;
                            System.out.println("Picked up a bow & gained 25 XP!");
                        }
                        if(screen[p.yPos][p.xPos] == 'T') {
                            p.setWeapon(new Weapon("Sword", 1, 2, 10));
                            playerXp += 25;
                            System.out.println("Picked up a sword & gained 25 XP!");
                        }
                        

                        for (Enemy enemy : enemies.enemies) {
                            if (enemy.isDead) {
                                enemy.attack = 0;
                                enemy.character = ' ';
                                screen[enemy.yPos][enemy.xPos] = enemy.character;
                                playerXp += 50;
                                System.out.println("Defeated a monster & gained 50 XP!");
                            }
                        }

                        if (screen[p.yPos][p.xPos] != mg.getWall() && screen[p.yPos][p.xPos] != 'O' && screen[p.yPos][p.xPos] != 'G' && screen[p.yPos][p.xPos] != 'B') {
                            screen[p.yPos][p.xPos] = '@';
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
            if (levelFinishXp > 0) {
                System.out.println("Next level! Gained " + levelFinishXp + "XP!");
                playerXp += levelFinishXp;
            } else {
                System.out.println("Next level!");
            }
            if(p.getHealth() < 1) {
              AsciiText.printGameOver();
              break;
            }
            }

        }
    }