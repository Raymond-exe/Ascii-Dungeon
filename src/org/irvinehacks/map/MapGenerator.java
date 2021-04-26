package org.irvinehacks.map;

import java.lang.Math;

public class MapGenerator {

    public char[][] map;
    private int[][] walls;
    private boolean[][] mapChecker;
    private final char WALL = '#';
    private final char SPACE = ' ';
    private final char DOOR = '-';

    private int xStart;
    private int yStart;

    public char getWall() { return WALL; }
    public char getSpace() { return SPACE; }
    public char[][] getMap() { return map; }

    public MapGenerator(int y, int x, int spaceFactor) {
        map = new char[y][x];
        mapChecker = new boolean[y][x];
        walls = new int[map.length / 3][ map[0].length / 3];


        boolean[][] solution;

        do {
            xStart = (int)(Math.random()*(map[0].length-2))+1;
            yStart = (int)(Math.random()*3)+1;
            fillMap(spaceFactor);
            map[yStart][xStart] = SPACE;
            solution = MazeSolver.solve(xStart, yStart, map[0].length/2, map.length-1, map);
        } while(!MazeSolver.isSolvable(solution));
    }

    //DEBUGGING ONLY
    public String wallsToString() {
        String output = "";
        for (int y = 0; y < walls.length; y++) {
            for (int x = 0; x < walls[y].length; x++) {
                output += walls[y][x] + ", ";
            }
            output += "\n";
        }
        return output;        
    }

    public String toString() {
        String output = "";
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                output += map[y][x];
            }
            output += "\n";
        }
        return output;
    }

    private void fillMap(int spaceFactor) {
        boolean isXEdge = false;
        boolean isYEdge = false;

        for (int y = 0; y < walls.length; y++) {
            for (int x = 0; x < walls[y].length; x++) {
                walls[y][x] = (int)(Math.random() * spaceFactor);
                if (walls[y][x] > 5)
                    walls[y][x] = 5;
            }
        }

        //SAFE SPACE
        //walls[0]
        for (int x = 0; x < walls[0].length; x++) {
            if (x > walls[0].length * 2/5 && x < walls[0].length * 3/5) {
                walls[0][x] = 5;
                walls[walls.length - 1][x] = 5;
            }
        }        

        int xCoordinate, yCoordinate;
        for (int y = 0; y < walls.length; y++) {
            yCoordinate = getRelativeCoordinate(y);
            for (int x = 0; x < walls[y].length; x++) {
                xCoordinate = getRelativeCoordinate(x);
                map[yCoordinate+1][xCoordinate+1] = SPACE;
                map[yCoordinate+1][xCoordinate  ] = SPACE;
                map[yCoordinate+1][xCoordinate-1] = SPACE;
                map[yCoordinate  ][xCoordinate+1] = SPACE;
                map[yCoordinate  ][xCoordinate  ] = SPACE;
                map[yCoordinate  ][xCoordinate-1] = SPACE;
                map[yCoordinate-1][xCoordinate+1] = SPACE;
                map[yCoordinate-1][xCoordinate  ] = SPACE;
                map[yCoordinate-1][xCoordinate-1] = SPACE;

                switch (walls[y][x]) {
                    case 1:
                        map[yCoordinate + 1][xCoordinate - 1] = WALL; //LOW-LEFT
                        map[yCoordinate][xCoordinate - 1] = WALL; //LEFT
                        map[yCoordinate - 1][xCoordinate - 1] = WALL; //UP-LEFT
                        break;
                    case 2:
                        map[yCoordinate - 1][xCoordinate + 1] = WALL; //UP-RIGHT
                        map[yCoordinate - 1][xCoordinate - 1] = WALL; //UP-LEFT
                        map[yCoordinate - 1][xCoordinate] = WALL; //UP
                        break;
                    case 3:
                        map[yCoordinate - 1][xCoordinate + 1] = WALL; //UP-RIGHT
                        map[yCoordinate][xCoordinate + 1] = WALL; //RIGHT
                        map[yCoordinate + 1][xCoordinate + 1] = WALL; //LOW-RIGHT
                        break;
                    case 4:
                        map[yCoordinate + 1][xCoordinate + 1] = WALL; //LOW-RIGHT
                        map[yCoordinate + 1][xCoordinate] = WALL; //LOW
                        map[yCoordinate + 1][xCoordinate - 1] = WALL; //LOW-LEFT
                        break;
                    case 5:
                    default:
                        break;
                }
            }
        }
        
        //DOOR
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                isXEdge = x == 0 || x == map[y].length - 1;
                isYEdge = y == 0 || y == map.length - 1;
                if (isXEdge || isYEdge) {
                    map[y][x] = WALL;
                    if (x > map[y].length * 2/5 && x < map[y].length * 3/5 && y != 0)
                        map[y][x] = DOOR;
                }

            }
        }
    }

    public boolean isValidPosition(int y, int x) {
        return (y>=0 && y<map.length && x>=0 && x<map.length);
    }

    public int getXStart() {
        return xStart;
    }

    public int getYStart() {
        return yStart;
    }

    private int getRelativeCoordinate(int num) {
        return 2 + 3 * num;
    }

    //0 = space
    //-1 = wall



    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator(Integer.parseInt(args[0]), Integer.parseInt((args[1])), Integer.parseInt(args[2]));
        System.out.println(mg);
    }

} 