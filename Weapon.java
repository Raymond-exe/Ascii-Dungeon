public class Weapon {
    private String name;
    private int range;
    private int damage;
    private int durability;
    private int durabilityCap;

    public Weapon(String n, int r, int da, int d) {
        name = n;
        range = r;
        damage = da;
        durabilityCap = d;
        durability = durabilityCap;
    }


    /////GETTER METHODS\\\\\
    public int getRange() { return range; }
    public int getDamage() { return damage; }
    public String getName() { return name; }
    public int getDurability() { return durability; }
    public int getDurabilityCap() { return durabilityCap; }

    public void decreaseDurability() { 
        if (name.equals("Fists"))
            return;
        if (--durability == 0)
            damage = 0;
     }

    public String toString() {
        return name + " (" + durability + "/" + durabilityCap + ")";
    }

}