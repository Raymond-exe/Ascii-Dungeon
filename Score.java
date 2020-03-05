import java.text.SimpleDateFormat;  
import java.util.Date;

public class Score {
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


    public String getName() { return name; }
    public int getPoints() { return points; }
    public String getDate() { return date; }
}