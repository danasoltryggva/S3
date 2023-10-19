public class Point2D implements Comparable<Point2D> {
    // construct the point (x, y)
    public Point2D(double x, double y);
    public double x(); // x-coordinate
    public double y(); // y-coordinate
    // Euclidean distance between two points
    public double distanceTo(Point2D that);
    // square of Euclidean distance between two points
    public double distanceSquaredTo(Point2D that);
    // for use in an ordered symbol table
    public int compareTo(Point2D that);
    // does this point equal that?
    public boolean equals(Object that);
    // draw to standard draw
    public void draw();
    // string representation
    public String toString();
}
