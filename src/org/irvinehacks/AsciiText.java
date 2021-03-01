package org.irvinehacks;

public class AsciiText {
        
        public static void printMenu() {
          System.out.println("MAIN MENU\n");
          System.out.println("[P] - Play!");
          System.out.println("[H] - Help/tutorial");
          System.out.println("[S] - Scoreboard/High-Scores");
          System.out.println("[C] - Credits");
          System.out.println("[Q] - Quit game\n");
        }

        public static void printLines(int num) {
          for (int i = 0; i < num; i++)
            System.out.print("\n");
        }

        public static void printTutorial() {
            printLines(3);
            System.out.println("\n");
            System.out.println("|-----------CONTROLS------------|");
            System.out.println("|        W - move up            |");
            System.out.println("|        A - move left          |");
            System.out.println("|        S - move down          |");
            System.out.println("|        D - move right         |");
            System.out.println("|        Space - attack         |");
            System.out.println("| Press ENTER to confirm action |\n");
            printLines(3);
            System.out.println("|-------LEGEND-------|");
            System.out.println("| } - bow            |");
            System.out.println("| T - sword          |");
            System.out.println("| h - health potion  |");
            System.out.println("| O - orc            |");
            System.out.println("| G - goblin         |");
            System.out.println("| B - behemoth       |");
            printLines(7);
        }
        
        public static void printTitleScreen() {
            System.out.println("                   _ _   _____                                     _ ");
            System.out.println("    /\\            (_|_) |  __ \\                                   | |");
            System.out.println("   /  \\   ___  ___ _ _  | |  | |_   _ _ __   __ _  ___  ___  _ __ | |");
            System.out.println("  / /\\ \\ / __|/ __| | | | |  | | | | | '_ \\ / _` |/ _ \\/ _ \\| '_ \\| |");
            System.out.println(" / ____ \\\\__ \\ (__| | | | |__| | |_| | | | | (_| |  __/ (_) | | | |_|");
            System.out.println("/_/    \\_\\___/\\___|_|_| |_____/ \\__,_|_| |_|\\__, |\\___|\\___/|_| |_(_)");
            System.out.println("                                             __/ |                   ");
            System.out.println("                                            |___/                    ");          
        }
        
        public static void printGameOver() {
            String end = "";
            end += "  _____                         ____                 \n";
            end += " / ____|                       / __ \\                \n";
            end += "| |  __  __ _ _ __ ___   ___  | |  | |_   _____ _ __ \n";
            end += "| | |_ |/ _` | '_ ` _ \\ / _ \\ | |  | \\ \\ / / _ \\ '__| \n";
            end += "| |__| | (_| | | | | | |  __/ | |__| |\\ V /  __/ |   \n";
            end += " \\_____|\\__,_|_| |_| |_|\\___|  \\____/  \\_/ \\___|_|   \n\n";
            System.out.println(end);
        }
          
        
        public static void printLevel(int level) {
          String[] outputRows = {
            " _                    _  ",
            "| |                  | | ",
            "| |     _____   _____| | ",
            "| |    / _ \\ \\ / / _ \\ | ",
            "| |___|  __/\\ V /  __/ | ",
            "|______\\___| \\_/ \\___|_| " };
          String[] levelNumberAscii = intToAscii(level);
          
          for (int i = 0; i < outputRows.length; i++)
          {
            System.out.println(outputRows[i] + levelNumberAscii[i]);
          }        
        }
        
        private static String[] intToAscii(int num) {
          String str = num + " ";
          String[] outputRows = {" ", " ", " ", " ", " ", " "};
          
          for (int i = 0; i < str.length(); i++) {
            switch(str.charAt(i)) {
              case '1':
                outputRows[0] += " __ ";
                outputRows[1] += "/_ |";
                outputRows[2] += " | |";
                outputRows[3] += " | |";
                outputRows[4] += " | |";
                outputRows[5] += " |_|";
                break;
              case '2':
                outputRows[0] += " ___  ";
                outputRows[1] += "|__ \\ ";
                outputRows[2] += "   ) |";
                outputRows[3] += "  / / ";
                outputRows[4] += " / /_ ";
                outputRows[5] += "|____|";
                break;
              case '3':
                outputRows[0] += " ____  ";
                outputRows[1] += "|___ \\ ";
                outputRows[2] += "  __) |";
                outputRows[3] += " |__ < ";
                outputRows[4] += " ___) |";
                outputRows[5] += "|____/ ";
                break;
              case '4':
                outputRows[0] += " _  _   ";
                outputRows[1] += "| || |  ";
                outputRows[2] += "| || |_ ";
                outputRows[3] += "|__   _|";
                outputRows[4] += "   | |  ";
                outputRows[5] += "   |_|  ";
                break;
              case '5':
                outputRows[0] += " _____ ";
                outputRows[1] += "| ____|";
                outputRows[2] += "| |__  ";
                outputRows[3] += "|___ \\ ";
                outputRows[4] += " ___) |";
                outputRows[5] += "|____/ ";
                break;
              case '6':
                outputRows[0] += "   __  ";
                outputRows[1] += "  / /  ";
                outputRows[2] += " / /_  ";
                outputRows[3] += "/ '_ \\ ";
                outputRows[4] += "| (_) |";
                outputRows[5] += " \\___/ ";
                break;
              case '7':
                outputRows[0] += " ______ ";
                outputRows[1] += "|____  |";
                outputRows[2] += "    / / ";
                outputRows[3] += "   / /  ";
                outputRows[4] += "  / /   ";
                outputRows[5] += " /_/    ";
                break;
              case '8':
                outputRows[0] += "  ___  ";
                outputRows[1] += " / _ \\ ";
                outputRows[2] += "| (_) |";
                outputRows[3] += " > _ < ";
                outputRows[4] += "| (_) |";
                outputRows[5] += " \\___/ ";
                break;
              case '9':
                outputRows[0] += "  ___  ";
                outputRows[1] += " / _ \\ ";
                outputRows[2] += "| (_) |";
                outputRows[3] += " \\__, |";
                outputRows[4] += "   / / ";
                outputRows[5] += "  /_/  ";
                break;
              case '0':
                outputRows[0] += "  ___  ";
                outputRows[1] += " / _ \\ ";
                outputRows[2] += "| | | |";
                outputRows[3] += "| | | |";
                outputRows[4] += "| |_| |";
                outputRows[5] += " \\___/ ";
                break;
            }
          }
          
          return outputRows;
        }
        
}