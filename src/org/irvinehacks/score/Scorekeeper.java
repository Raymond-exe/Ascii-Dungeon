package org.irvinehacks.score;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.StringBuffer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Scorekeeper {
    
    private final static String scoreboardFile = "scores.txt"; 
    private static ArrayList<Score> scores = callScoreboard(); 
    
    public static void clearScores() {
      scores = new ArrayList<Score>();
      saveScores();
    }

    public static void printScoreboard() {
      String output = blankScoreboard()[0] + "\n" + blankScoreboard()[1] + "\n";
      StringBuffer entry;

      Collections.sort(scores);     //sorts the scores
      Collections.reverse(scores);  //reverses scores so the greatest is first

      for (Score sc : scores) {
        entry = new StringBuffer(blankScoreboard()[2]);

        //Sets the player's name starting from index 2
        for (int i = 0; i < sc.getName().length(); i++) {
          entry.setCharAt(i + 2, sc.getName().charAt(i));

          if (i > 7) { break; }
        }

        //Sets the player's point score starting at index 14
        for (int i = 0; i < sc.getPointsStr().length(); i++) {
          entry.setCharAt(i + 14, sc.getPointsStr().charAt(i));

          if (i > 3) { break; }
        }

        //Sets the date the player earned the score starting at index 22
        for (int i = 0; i < sc.getDate().length(); i++) {
          entry.setCharAt(i + 22, sc.getDate().charAt(i));

          if (i > 6) { break; }
        }

        output += entry.toString() + "\n";
      }
      
      output += blankScoreboard()[3];
      
      System.out.println(output);
    }

    public static ArrayList<Score> getScores() {
        return scores;
    }
    
    private static void saveScores() {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(scoreboardFile));
        String saveTxt = "";
      
        for (Score sc : scores) {
          saveTxt += sc.toString() + "\n";
        }
      
        writer.write(saveTxt);
        writer.close();
      } catch (Exception e) {
        //System.out.println("Could not locate " + scoreboardFile);
      }
    }

    public static void addScore(String name, int points) {
        scores.add(new Score(name, points));
        saveScores();
    }
    
    public static void addScore(String name, int points, String date) {
        scores.add(new Score(name, points, date));
        saveScores();
    }

    public static void addScore(Score newScore) {
        scores.add(newScore);
        saveScores();
    }

    //*
    private static ArrayList<Score> callScoreboard() {
        ArrayList<Score> output = new ArrayList<Score>();
        ArrayList<String> entries = new ArrayList<String>();
        String rawScoreboard = getRawScoreboard();

        //Parses through the raw text and fills the ArrayList<String> entries
        while (rawScoreboard.contains("[") && rawScoreboard.contains("]")) {
          entries.add(rawScoreboard.substring(rawScoreboard.indexOf('['), rawScoreboard.indexOf(']') + 1));
          rawScoreboard = rawScoreboard.substring(rawScoreboard.indexOf(']'));
          if (rawScoreboard.contains("["))
                rawScoreboard = rawScoreboard.substring(rawScoreboard.indexOf('[', rawScoreboard.indexOf(']')));
          else
                break;
          //System.out.println("rawScoreboard str: " + rawScoreboard);
        }
        //System.out.println("Entries: " + entries);
        
        //Fills ArrayList<Score> scores from properties in ArrayList<String> entries
        String entryName, entryPoints, entryDate;
        for (String str : entries) {
          entryName = str.substring(str.indexOf('[') + 1, str.indexOf(','));
          entryPoints = str.substring(str.indexOf(',') + 1, str.lastIndexOf(','));
          entryDate = str.substring(str.lastIndexOf(',') + 1, str.indexOf(']'));
          
          output.add(new Score(entryName, Integer.parseInt(entryPoints.trim()), entryDate));
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
            //System.out.println("Unable to locate " + scoreboardFile);
        }

        System.out.println(output);
        return output;
    }

    private static String[] blankScoreboard() {
        String output[] = new String[4];

        output[0] = "+----------HIGH~SCORES----------+";
        output[1] = "| NAME      | SCORE | DATE      |";
        output[2] = "|           |       |           |";
        output[3] = "+-----------+-------+-----------+";

        return output;
    }

    public static void main(String[] args) {
        
    }
}