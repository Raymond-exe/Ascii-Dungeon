package org.irvinehacks.score;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Comparable;

public class Score implements Comparable<Score> {
    private String name;
    private int points;
    private String date;


    public Score(String n, int p) {
        this(n, p, getCurrentDate());
    }

    public Score(String n, int p, String d) {
        name = n.trim();
        points = p;
        date = d.trim();
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.format(new Date());
    }
    
    @Override
    public int compareTo(Score sc) {
        if (points == sc.getPoints())
            return 0;

        return (points > sc.getPoints() ? 1 : -1);
    }
    
    public String toString() {
      String output = "[";
      
      output += name + ", ";
      output += points + ", ";
      output += date + "]";
      
      return output;
    }


    public String getName() { return name; }
    public int getPoints() { return points; }
    public String getPointsStr() { return points + ""; }
    public String getDate() { return date; }
}