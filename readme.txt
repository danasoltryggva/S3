/**********************************************************************
 *  readme.txt template                                                   
 *  Kd-tree
**********************************************************************/

Name 1: Dana Sól Tryggvadóttir
kt 1:  311298-2089

Name 2: Karítas Etna Elmarsdóttir
kt 2:   200798-2799

/**********************************************************************
 *  Briefly describe the Node data type you used to implement the
 *  2d-tree data structure.
 **********************************************************************/

/**********************************************************************
The Node data type used to implement the 2d-tree is an inner static class of the KdTree class. Each Node has a Point2D referenced as `p` that represents the 2D point, a RectHV referenced as `rect` that represents the axis-aligned rectangle corresponding to the node and references to the left and right child nodes, named `left` and `right`.

The constructor of the Node class accepts a Point2D and a RectHV as arguments to initialize its values. There is no explicit key-value pair as seen in a typical binary search tree; instead, the point itself is used as the key.
**********************************************************************/



/**********************************************************************
 *  Describe your method for range search in a kd-tree.
 **********************************************************************/
/**********************************************************************
The method we used for range search in the kd-tree is implemented using a recursive approach. The method `range` accepts a rectangle and returns an Iterable<Point2D> containing all points that are inside the given rectangle.

1. A new queue is instantiated to hold points inside the given rectangle.
2. The recursive helper method `AddToRange` is called, which examines the current node to see if its rectangle intersects with the query rectangle.
3. If the node's rectangle doesn't intersect the query rectangle, the search is cut off, and the subtree rooted at that node is not explored.
4. If the point at the node lies within the query rectangle, it is added to the queue.
5. The method then recursively checks both the left and right children.
6. Finally, the queue containing the points inside the query rectangle is returned.

By this method, only nodes whose rectangles intersect the query rectangle are explored, resulting in an efficient cutting off of the search space.
**********************************************************************/



/**********************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 **********************************************************************/
/**********************************************************************
The method for nearest neighbor search in the kd-tree, `nearest`, finds the closest point in the tree to a specific query point. The method is recursive and follows these steps:

1. If the current node is null, return the current closest point.
2. If there's no current closest point or the current closest point's distance to the query point is greater than or equal to the distance from the query point to the rectangle of the node, further exploration is allowed.
3. If the point at the current node is closer to the query point than the current closest point, the current closest point is updated.
4. Decide which of the left or right subtree to explore first. If the query point is contained within the right child's rectangle, the right subtree is explored first, otherwise, the left is explored first.
5. Explore the other subtree. However, only explore this subtree if there's a possibility of a closer point, which is determined by the distance of the closest point to the query point and the distance of the rectangle of the node to the query point.

This approach ensures that only the necessary nodes are explored and provides an efficient way to find the nearest neighbor.
**********************************************************************/




/**********************************************************************
 *  Give the total memory usage in bytes (using tilde notation and 
 *  the standard 64-bit memory cost model) of your 2d-tree data
 *  structure as a function of the number of points N. Justify your
 *  answer below.
 *
 *  Include the memory for all referenced objects (deep memory),
 *  including memory for the nodes, points, and rectangles.
 **********************************************************************/
Node: Each node has a Point2D, RectHV, left and right references to other nodes.
Reference to Point2D object = 8 bytes
Reference to RectHV object = 8 bytes
Reference to left Node and right Node = 8 bytes each
Int size = 4 bytes
Point2D: Each Point2D object has x and y (double values) = 8 bytes each = 16 bytes total.
RectHV: Each RectHV object has xmin, xmax, ymin, ymax (double values) = 8 bytes each =  32 bytes total.
Overhead = 16 bytes
Each Node will then have the memory usage 100 bytes.
So for N points the 2d-tree will have N nodes. 
Total memory usage as a function of the number of points N will be =  N * 100 bytes ≈ ~ 100N bytes


/**********************************************************************
 *  Give the expected running time in seconds (using tilde notation)
 *  to build a 2d-tree on N random points in the unit square.
 *  Use empirical evidence by creating a table of different values of N
 *  and the timing results. (Do not count the time to generate the N 
 *  points or to read them in from standard input.)
 **********************************************************************/

N       Time (seconds)
----------------------
100     0.0010
500     0.0000
1000    0.0000
5000    0.0030
10000   0.0060
50000   0.0200


/**********************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Explain how you determined the
 *  operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 **********************************************************************/
We determined the number of operations per second by running 1000000 repetitions of nearest neighbor calculations of each txt input, recording the time with Stopwatch(), and dividing the time elapsed in seconds with the number of repetitions.

Brute-force calculations
Repetitions = 1000000
Time elapsed = 0.039 seconds
Operations per second = repetitions / time elapsed = 2.5641 operations per second

2d-tree calculations
Repetitions = 1000000
Time elapsed = 0.034 seconds
Operations per second = repetitions / time elapsed = 2.9411 operations per second

                     calls to nearest() per second
                     brute force           2d-tree

input100K.txt       

input1M.txt



/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/


/**********************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and d�mat�mar, but do
 *  include any help from people (including course staff, 
 *  classmates, and friends) and attribute them by name.
 **********************************************************************/


/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/



