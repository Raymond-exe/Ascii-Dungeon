package org.irvinehacks.map;

import org.omg.CosNaming._BindingIteratorImplBase;

import java.util.ArrayList;

public class MazeSolver {

    public static final char WALL_CHAR = '#';
    public static final char SPACE_CHAR = ' ';

    public static char[][] makeBox(int w, int h) {
        char[][] output = new char[h][w];

        for(int y = 0; y < h; y++) {
            if (y==0 || y==h-1) {
                for(int x=0; x<w; x++)
                    output[y][x] = WALL_CHAR;
            } else {
                for(int x=0; x<w; x++)
                    output[y][x] = SPACE_CHAR;
                output[y][0] = WALL_CHAR;
                output[y][w-1] = WALL_CHAR;
            }
        }

        return output;
    }

    public static char[][] randomBox(int w, int h) {
        char[][] output = makeBox(w, h);

        for(int y=0; y < output.length; y++) {
            for(int x=0; x < output[y].length; x++) {
                if(output[y][x]==SPACE_CHAR)
                    output[y][x] = (Math.random()>0.65? WALL_CHAR : SPACE_CHAR);
            }
        }

        return output;
    }

    public static String charArrayToString(char[][] chars) {
        StringBuilder output = new StringBuilder();

        for (char[] row : chars) {
            for(char c : row)
                output.append(c);
            output.append("\n");
        }

        return output.toString();
    }

    public static char[][] intToCharArray(int[][] ints) {
        char[][] output = new char[ints.length][ints[0].length];

        String temp;
        for(int y = 0; y < output.length; y++) {
            for(int x=0; x<output[y].length; x++) {
                switch(ints[y][x]) {
                    case -1:
                        output[y][x] = WALL_CHAR;
                        break;
                    case 0:
                        output[y][x] = SPACE_CHAR;
                        break;
                    default:
                        temp = String.valueOf(ints[y][x]);
                        output[y][x] = temp.charAt(temp.length()-1);
                }

            }
        }

        return output;
    }

    public static char[][] boolToCharArray(boolean[][] bools) {
        char[][] output = new char[bools.length][bools[0].length];

        for(int y = 0; y < bools.length; y++) {
            for(int x=0; x<bools[y].length; x++) {
                output[y][x] = (bools[y][x]? 'T' : 'F');
            }
        }

        return output;
    }

    public static boolean isSolvable(boolean[][] path) {
        for(int y = 0; y < path.length; y++) {
            for(int x = 0; x < path[y].length; x++) {
                if(path[y][x]) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean[][] solve(int startX, int startY, int endX, int endY, char[][] maze) {

        boolean[][] output = new boolean[maze.length][maze[0].length];
        int[][] steps = new int[maze.length][maze[0].length];

        for (int y = 0; y < steps.length; y++) {
            for (int x = 0; x < steps[y].length; x++) {
                steps[y][x] = (maze[y][x] == WALL_CHAR ? -1 : 0);
                output[y][x] = false;
            }
        }

        startX = Math.abs(startX);
        startY = Math.abs(startY);

        if (startY >= maze.length || startX >= maze[0].length || maze[startY][startX] == WALL_CHAR || maze[endY][endX] == WALL_CHAR)
            return output;

        int currentStep = 1;
        steps[startY][startX] = currentStep;

        ArrayList<int[]> coordinateQueue = new ArrayList<>();
        coordinateQueue.add(new int[]{startY, startX});

        int[] yx;
        for(int queueIndex = 0; queueIndex < coordinateQueue.size() && steps[endY][endX]==0; queueIndex++) {
            yx = coordinateQueue.get(queueIndex);

            currentStep = steps[yx[0]][yx[1]]+1;

            if (isTraversable(yx[0] + 1, yx[1], 0, steps)) {
                steps[yx[0] + 1][yx[1]] = currentStep;
                coordinateQueue.add(new int[]{yx[0]+1,yx[1]});
            }
            if (isTraversable(yx[0] - 1, yx[1], 0, steps)) {
                steps[yx[0] - 1][yx[1]] = currentStep;
                coordinateQueue.add(new int[]{yx[0]-1,yx[1]});
            }
            if (isTraversable(yx[0], yx[1] + 1, 0, steps)) {
                steps[yx[0]][yx[1] + 1] = currentStep;
                coordinateQueue.add(new int[]{yx[0],yx[1]+1});
            }
            if (isTraversable(yx[0], yx[1] - 1, 0, steps)) {
                steps[yx[0]][yx[1] - 1] = currentStep;
                coordinateQueue.add(new int[]{yx[0],yx[1]-1});
            }
        }
        //System.out.println(charArrayToString(intToCharArray(steps)));

        // if the end point was never reached,
        // return the current (non-existent) path
        if(steps[endY][endX]==0) {
            return output;
        }

        yx = new int[]{endY, endX};
        while(!output[startY][startX]) {
            output[yx[0]][yx[1]] = true;
            currentStep = steps[yx[0]][yx[1]];

            //System.out.println("Backtracking! Current step: " + currentStep);

            if(isTraversable(yx[0]+1, yx[1], currentStep-1, steps)) {
                yx[0]++;
            } else if(isTraversable(yx[0]-1, yx[1], currentStep-1, steps)) {
                yx[0]--;
            } else if(isTraversable(yx[0], yx[1]+1, currentStep-1, steps)) {
                yx[1]++;
            } else if(isTraversable(yx[0], yx[1]-1, currentStep-1, steps)) {
                yx[1]--;
            } else {
                break;
            }
        }

        timeCompleted = System.currentTimeMillis();
        return output;
    }

    private static char[][] combineMazeLayers(char[][] maze, boolean[][] solution) {
        char[][] output = new char[maze.length][maze[0].length];

        for(int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                if(solution[y][x])
                    output[y][x] = '.';
                else
                    output[y][x] = maze[y][x];
            }
        }

        return output;
    }

    private static boolean isTraversable(int y, int x, int step, int[][] maze) {
        if(y<0 || y>=maze.length || x<0 || x>=maze[y].length)
            return false;

        return maze[y][x]==step;
    }

    private static long timeCompleted = 0;

    public static void main(String[] args) {
        int w;
        int h;
        long timeStarted;
        if(args.length < 2) {
            w = 40;
            h = 20;
        } else {
            w = Integer.parseInt(args[0]);
            h = Integer.parseInt(args[1]);
        }

        char[][] map = new MapGenerator(h, w, 5).getMap();
        boolean[][] solution;

        System.out.println(charArrayToString(map));

        timeStarted = System.currentTimeMillis();
        solution = solve(2, 2, 20, 19, map);

        System.out.println(charArrayToString(combineMazeLayers(map, solution)));

        System.out.println("Time started:  " + timeStarted);
        System.out.println("Time finished: " + timeCompleted);
        System.out.println("Elapsed time: " + (timeCompleted-timeStarted));
    }

}