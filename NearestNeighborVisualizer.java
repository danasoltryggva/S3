
/******************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointST.java KdTreeST.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input

        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        int repetitions = 1000000;

        // Brute-force performance measurement
        Stopwatch timer1 = new Stopwatch();
        for (int i = 0; i < repetitions; i++) {
            double x = Math.random();
            double y = Math.random();
            Point2D query = new Point2D(x, y);
            brute.nearest(query);
        }
        double elapsedTimeBrute = timer1.elapsedTime();
        double opsBrute = repetitions / elapsedTimeBrute;
        System.out.println(elapsedTimeBrute);
        System.out.println("Brute-force operations per second: " + opsBrute);
    
        // 2d-tree (kdtree) performance measurement
        Stopwatch timer2 = new Stopwatch();
        for (int i = 0; i < repetitions; i++) {
            double x = Math.random();
            double y = Math.random();
            Point2D query = new Point2D(x, y);
            kdtree.nearest(query);
        }
        double elapsedTimeKdTree = timer2.elapsedTime();
        double opsKdTree = repetitions / elapsedTimeKdTree;
        System.out.println(elapsedTimeKdTree);
        System.out.println("2d-tree operations per second: " + opsKdTree);
    
    //     PointSET brute = new PointSET();
    //     KdTree kdtree = new KdTree();
    //     for (int i = 0; !in.isEmpty(); i++) {
    //         double x = in.readDouble();
    //         double y = in.readDouble();
    //         Point2D p = new Point2D(x, y);
    //         kdtree.insert(p);
    //         brute.insert(p);
    //     }

    //     while (true) {

    //         // the location (x, y) of the mouse
    //         double x = StdDraw.mouseX();
    //         double y = StdDraw.mouseY();
    //         Point2D query = new Point2D(x, y);

    //         // draw all of the points
    //         StdDraw.clear();
    //         StdDraw.setPenColor(StdDraw.BLACK);
    //         StdDraw.setPenRadius(.01);
    //         brute.draw();
    //         // for (Point2D p : brute.points())  p.draw();

    //         // draw in red the nearest neighbor according to the brute-force algorithm
    //         StdDraw.setPenRadius(.03);
    //         StdDraw.setPenColor(StdDraw.RED);
    //         brute.nearest(query).draw();
    //         StdDraw.setPenRadius(.02);

    //         // draw in blue the nearest neighbor according to the kd-tree algorithm
    //         StdDraw.setPenColor(StdDraw.BLUE);
    //         kdtree.nearest(query).draw();
    //         StdDraw.show(0);
    //         StdDraw.show(40);
    //     }
    // }

    
}

}