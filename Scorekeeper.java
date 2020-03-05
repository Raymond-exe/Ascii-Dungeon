import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class Scorekeeper {
    
    private final static String scoreboardFile = "scores.txt"; 
    private static ArrayList<Score> scores;// = callScoreboard();

    public static ArrayList<Score> getScores() {
        return scores;
    }

    public static void addScore(String name, int points) {
        scores.add(new Score(name, points));
    }
    
    public static void addScore(String name, int points, String date) {
        scores.add(new Score(name, points, date));
    }

    public static void addScore(Score newScore) {
        scores.add(newScore);
    }

    //*
    private static ArrayList<Score> callScoreboard() {
        ArrayList<Score> output = new ArrayList<Score>();
        String rawScoreboard = getRawScoreboard();

        System.out.println("Raw scores: " + rawScoreboard);


        String currentEntry; //Keeps track of the entry that this while loop is on

        //Parses through the raw text and fills the ArrayList<Score> output
        while (rawScoreboard.contains("[") && rawScoreboard.contains("]")) {
            currentEntry = rawScoreboard.substring(rawScoreboard.indexOf('['), rawScoreboard.indexOf(']') + 1);

            if (currentEntry.contains(",") && currentEntry.contains("]")) {
                addScore(

                        currentEntry.substring(
                            currentEntry.indexOf('[') + 1, 
                            currentEntry.indexOf(',')), //Gets the player's name

                        Integer.parseInt(
                            currentEntry.substring(
                                currentEntry.indexOf(',') + 1, 
                                currentEntry.lastIndexOf(',')
                                ).trim()), //Gets the player's points

                        currentEntry.substring(
                            currentEntry.lastIndexOf(',') + 1, 
                            currentEntry.indexOf(']')
                            ) //Gets the date the player earned the score

                );
            } else
                System.out.println("Error - unable to parse player data");

            rawScoreboard = rawScoreboard.substring(rawScoreboard.indexOf(']') + 1);
        }

        return output;
    } //*/

    private static String getRawScoreboard() {
        String output = "";

        //This reads the file "scoreboardFile", don't touch it...
        try {
            BufferedReader br = new BufferedReader(new FileReader(scoreboardFile));

            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            br.close();
            return output;
        } catch (Exception e) {
            System.out.println("Uh oh! There's a problem!");
        }

        System.out.println(output);
        return output;
    }

    private static String[] blankScoreboard() {
        String output[] = new String[4];

        output[0] = "+----------HIGH SCORES----------+";
        output[1] = "| NAME      | SCORE | DATE      |";
        output[2] = "|           |       |           |";
        output[3] = "+-----------+-------+-----------+";

        return output;
    }

    public static void main(String[] args) {
        Scorekeeper.getRawScoreboard();
    }
}