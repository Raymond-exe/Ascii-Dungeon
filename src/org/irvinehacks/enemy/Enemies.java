package org.irvinehacks.enemy;

import java.util.ArrayList;

public class Enemies {

    public ArrayList<Enemy> enemies = new ArrayList<>();

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public boolean isEmpty() {
        clearDeadEnemies();
        return enemies.isEmpty();
    }

    public Enemy getEnemyByPosition(int xPos, int yPos) {
        for (Enemy e : this.enemies) {
            if (e.xPos == xPos && e.yPos == yPos) {
                return e;
            }
        }
        return null;
    }

    public boolean isEnemy(char[][] map, int xPos, int yPos) {

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).character == map[yPos][xPos]) {
                return true;
            }
        }

        return false;
    }

    public void clearDeadEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).character == ' ') {
                enemies.remove(enemies.get(i--));
            }
        }
    }

}