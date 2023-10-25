
/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution: 
 *  Dependencies:
 *  Author: Dana Sól Tryggvadóttir & Karítas Etna Elmarsdóttir
 *  Date: 22.10.2023
 *
 *  Data structure for maintaining a set of 2-D points, 
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/


import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private final TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException(); // check for null argument
        pointSet.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException(); // check for null argument
        return pointSet.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
    
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> foundPoints = new Queue<>();


        // Iterate through the set and enqueue points that are inside the given rectangle
        for (Point2D currentPoint : pointSet) {
            if (rect.contains(currentPoint)) {
                foundPoints.enqueue(currentPoint);
            }
        }
    
        return foundPoints;
    }
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D queryPoint) {
        if (isEmpty()) return null;
    
        Point2D closest = null;
    
        // Iterate through the set to find the closest point to the queryPoint
        for (Point2D currentPoint : pointSet) {
            if (closest == null || currentPoint.distanceSquaredTo(queryPoint) < closest.distanceSquaredTo(queryPoint)) {
                closest = currentPoint;
            }
        }
    
        return closest;
    }

    public static void main(String[] args) {

    }
}