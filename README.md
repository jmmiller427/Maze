# Maze
A DFS generated Maze and Solver

A maze is generated when the generate button is clicked. The maze that is generated is solved when the solve button is clicked.
Show generate and show solve check boxes are available so you can watch the project generate or solve the maze. 

The generation and solving both use DFS to solve and generate the maze. The generate finds random neighbors and breaks the wall
between the two cells. This guarantees that when the maze is generated there will always be a path to the end. The solve checks
if there is a wall between neighbors and goes to the best neighbor. Since the maze starts in the top left corner and ends in 
the bottom right, the solve algorithms tried to go right first, then down, then left, then up. 
