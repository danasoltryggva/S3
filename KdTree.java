
/*************************************************************************
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;



public class KdTree {

    private static final double XMIN = 0.0, XMAX = 1.0, YMIN = 0.0, YMAX = 1.0;
    private int size;
    private Node root;

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node left, right;

        private Node(Point2D value, RectHV rect) {
            this.p = value;
            this.rect = rect;
            left = null;
            right = null;
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, XMIN, YMIN, XMAX, YMAX, 0);
    };

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, 0) != null;
    }

    private int comparePoints(Point2D a, Point2D b, boolean compareX) {
        int result = Double.compare(compareX ? a.x() : a.y(), compareX ? b.x() : b.y());
        if (result == 0) {
            result = Double.compare(compareX ? a.y() : a.x(), compareX ? b.y() : b.x());
        }
        return result;
    }

    private Node insert(Node x, Point2D value, double xmin, double ymin, double xmax, double ymax, int level) {
        if (x == null) {
            size++;
            return new Node(value, new RectHV(xmin, ymin, xmax, ymax));
        }

        boolean compareX = level % 2 == 0;
        int cmp = comparePoints(value, x.p, compareX);

        if (cmp < 0) {
            if (compareX) {
                x.left = insert(x.left, value, xmin, ymin, x.p.x(), ymax, level + 1);
            } else {
                x.left = insert(x.left, value, xmin, ymin, xmax, x.p.y(), level + 1);
            }
        } else if (cmp > 0) {
            if (compareX) {
                x.right = insert(x.right, value, x.p.x(), ymin, xmax, ymax, level + 1);
            } else {
                x.right = insert(x.right, value, xmin, x.p.y(), xmax, ymax, level + 1);
            }
        }   

        return x;
    }

    private Point2D contains(Node x, Point2D p, int level) {
        while (x != null) {
            int compare = comparePoints(p, x.p, level % 2 == 0);
            if (compare < 0) {
                x = x.left;
            } else if (compare > 0) {
                x = x.right;
            } else {
                return x.p;
            }
            level++;
        }
        return null;
    }

    // draw all of the points to standard draw
    public void draw() {
        StdDraw.clear();
        drawLine(root, 0);
    }

    private void drawLine(Node x, int level) {
        if (x == null) return;

        drawLine(x.left, level + 1);

        StdDraw.setPenRadius();
        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.p.draw();

        drawLine(x.right, level + 1);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();
        AddToRange(root, rect, queue);
        return queue;
    }

    private void AddToRange(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null || !rect.intersects(x.rect)) return;
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
        }
        AddToRange(x.left, rect, queue);
        AddToRange(x.right, rect, queue);
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return isEmpty() ? null : nearest(root, p, null);
    }

    private Point2D nearest(Node x, Point2D p, Point2D closest) {
        if (x == null) return closest;
        if (closest == null || closest.distanceSquaredTo(p) >= x.rect.distanceSquaredTo(p)) {
            if (x.p.distanceSquaredTo(p) < (closest == null ? Double.POSITIVE_INFINITY : closest.distanceSquaredTo(p))) {
                closest = x.p;
            }

            if (x.right != null && x.right.rect.contains(p)) {
                closest = nearest(x.right, p, closest);
                closest = nearest(x.left, p, closest);
            } else {
                closest = nearest(x.left, p, closest);
                closest = nearest(x.right, p, closest);
            }
        }

        return closest;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        
        In in = new In();
        Out out = new Out();

        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];

        

        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }
}
