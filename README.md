# Rectangle Packing Puzzle Solver

A Java application that solves rectangle packing puzzles using backtracking algorithms with visual GUI.

## What it does

Fits rectangles into a grid without overlapping. Each rectangle can be rotated 90 degrees. The program shows the solution visually in real-time.

## Project Structure

```
puzzle/
├── src/
│   ├── Packing.java          # Main solver with backtracking algorithm
│   └── Panel.java            # GUI visualization using Swing
├── input.txt                 # Sample input file
├── output.txt                # Sample output file
└── README.md                 # This file
```

## How to run

1. **Compile:**
   ```bash
   javac src/*.java
   ```

2. **Run:**
   ```bash
   java -cp src Packing
   ```

3. **Input format:**
   ```
   <grid_width> <grid_height> <number_of_rectangles>
   <rectangle_id> <length> <height>
   ...
   ```

## Example

**Input (input.txt):**
```
12 12 12
1 3 5
2 1 3
3 7 2
4 5 4
5 3 3
6 4 5
7 1 6
8 3 6
9 1 7
10 2 3
11 4 3
12 7 2
```

**Output:** Visual display showing rectangles placed in the grid with IDs, plus solution coordinates in output.txt.

## Requirements

- Java 8 or higher

## How it works

1. Uses backtracking to try placing rectangles at all possible positions
2. Rotates rectangles 90 degrees when needed
3. Shows the solving process in real-time with a GUI
4. Displays "No solution exists" if no valid arrangement is found

## Features

- Real-time visual solving
- Rectangle rotation support
- Color-coded rectangles with IDs
- Automatic window sizing
- Step-by-step visualization
- File input/output support 