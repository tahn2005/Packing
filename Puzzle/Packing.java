package puzzle;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class Packing {

    // Global variables for grid dimensions
    static int n; // X-dim size
    static int m; // Y-dim size
    static Panel visual;
    static int totarea;
     // Output MapType
    static class OutputMapType extends HashMap<Integer, Pair> {
    }

    // Input MapType
    static class InputMapType extends TreeMap<Integer, Shape> {
    }
    static InputMapType input;
    static OutputMapType output;

    // Rectangle structure
    static class Shape {
        int ID;
        int length;
        int height;

        public Shape(int ID, int length, int height) {
            this.ID = ID;
            this.length = length;
            this.height = height;
        }
    }

    // Pair class to hold coordinates (x, y)
    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }


    // Flip the grid entries for the given rectangle
    static void flip(int x1, int y1, Shape r, boolean[][] grid) {
        for (int x = x1; x < x1 + r.length; x++) {
            for (int y = y1; y < y1 + r.height; y++) {
                grid[x][y] = !grid[x][y];
            }
        }
    }

    // Check if a rectangle can be placed at the given coordinates
    static boolean canPlace(Shape r, int x, int y, boolean[][] grid) {
        if ((r.length + x) > n || (r.height + y) > m) {
            return false;
        }
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.height; j++) {
                if (grid[i + x][j + y]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Place the rectangle at the given coordinates and update the grid
    static void place(Shape r, int x, int y, boolean[][] grid, OutputMapType output) {
        flip(x, y, r, grid);
        output.put(r.ID, new Pair(x, y));
        
        //uncomment following lines to adjust delays in between moves
        /*visual.addpanel(x, y, r.length, r.height, r.ID);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    // Rotate the rectangle
    static void rotate(Shape r) {
        int temp = r.height;
        r.height = r.length;
        r.length = temp;
    }

    // Remove the rectangle from the grid (backtracking)
    static void remove(int prev, boolean[][] grid, InputMapType input, OutputMapType output) {
        if (prev == 0) return;

        Pair coords = output.get(prev);
        Shape rect = input.get(prev);
        flip(coords.first, coords.second, rect, grid);
        output.remove(prev);
        
        //uncomment following lines to see the solution finding process in real time
        /*visual.removepanel();
        for (Map.Entry<Integer, Pair> entry : output.entrySet()) {
            int key = entry.getKey();
            Pair c = entry.getValue();
            Shape r = input.get(key);
            visual.addpanel(c.first, c.second, r.length, r.height, r.ID);
        }*/
    }

    // The backtracking search function
    static void search(Map.Entry<Integer, Shape> itr, int prev, boolean[][] grid, InputMapType input, OutputMapType output, boolean[] solved) {
        if(totarea > m * n){
            solved[0] = false;
            return;
        }
        
        if (itr == null) { // Base case: all rectangles placed
            solved[0] = true;
            return;
        }

        int key = itr.getKey();
        Shape rect = itr.getValue();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (solved[0]) return;

                if (canPlace(rect, i, j, grid)) {
                    place(rect, i, j, grid, output);
                    Map.Entry<Integer, Shape> nextItr = input.higherEntry(key);
                    search(nextItr, key, grid, input, output, solved);
                } else {
                    rotate(rect);
                    if (canPlace(rect, i, j, grid)) {
                        place(rect, i, j, grid, output);
                        Map.Entry<Integer, Shape> nextItr = input.higherEntry(key);
                        search(nextItr, key, grid, input, output, solved);
                    }
                }
            }
        }

        if (!output.containsKey(key)) {
            remove(prev, grid, input, output); // Backtrack
        }
    }

    // Print the solution to the output file
    static void printSolution(PrintWriter os) {
        for (Map.Entry<Integer, Pair> entry : output.entrySet()) {
            int key = entry.getKey();
            Pair coords = entry.getValue();
            Shape rect = input.get(key);
            os.printf("%d %d %d %d %d\n", key, coords.first, coords.second, rect.length, rect.height);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Please specify an input and output file");
            return;
        }

        // Read input
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        PrintWriter pw = new PrintWriter(new FileWriter(args[1]));

        String line = br.readLine();
        String[] dimensions = line.split(" ");
        n = Integer.parseInt(dimensions[0]);
        m = Integer.parseInt(dimensions[1]);
        int x = Integer.parseInt(dimensions[2]);

        input = new InputMapType();
        output = new OutputMapType();
        totarea = 0;
        for (int i = 0; i < x; i++) {
            line = br.readLine();
            String[] tokens = line.split(" ");
            int ID = Integer.parseInt(tokens[0]);
            int length = Integer.parseInt(tokens[1]);
            int height = Integer.parseInt(tokens[2]);
            totarea += length * height;
            input.put(ID, new Shape(ID, length, height));
        }
        br.close();
        visual = new Panel(m, n);
        // Initialize the grid (2D boolean array)
        boolean[][] grid = new boolean[n][m];

        // Start backtracking search
        boolean[] solved = {false};
        search(input.firstEntry(), 0, grid, input, output, solved);

        // Output the result
        if (!solved[0]) {
            pw.println("No solution found.");
            visual.unsolved();
        } else {
            printSolution(pw);
            for (Map.Entry<Integer, Pair> entry : output.entrySet()) {
            int key = entry.getKey();
            Pair c = entry.getValue();
            Shape r = input.get(key);
            visual.addpanel(c.first, c.second, r.length, r.height, r.ID);
        }
        }
        pw.close();
    }
}