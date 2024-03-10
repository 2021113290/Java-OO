/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        turtle.color(PenColor.BLACK);
        for (int i=0;i<4;i++){
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {

        return (sides-2)*(180.0/sides);
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int) Math.rint(360/(180-angle));

    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle =180-calculateRegularPolygonAngle(sides);
        turtle.color(PenColor.RED);
        for (int i=0;i<sides;i++){
            turtle.forward(sideLength);
            turtle.turn(angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double m=Math.atan2(targetY-currentY,targetX-currentX)*180/Math.PI;
        double n=90-currentBearing-m;
        if(n<0){
            n+=360;
        }
        return n;
    }
    public static double calculateBearingToPointDouble(double currentBearing, double currentX, double currentY,
                                                 double targetX, double targetY) {
        double m=Math.atan2(targetY-currentY,targetX-currentX)*180/Math.PI;
        double n=90-currentBearing-m;
        if(n<0){
            n+=360;
        }
        return n;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        if(xCoords.size()!=yCoords.size()){
            System.out.println("两个点集长度不相等！！");
                System.exit(0);
        }
        List<Double> mlist=new ArrayList<Double>();
        mlist.add(calculateBearingToPoint(0.0, xCoords.get(0),yCoords.get(0),xCoords.get(1),yCoords.get(1) ));
        //务必使用calculateBearingToPoint来写
        for(int i=2;i<xCoords.size();i++){
            mlist.add(calculateBearingToPoint(mlist.get(i-2) ,xCoords.get(i-1),yCoords.get(i-1),xCoords.get(i),yCoords.get(i) ));
        }
        return mlist;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        if(points.size()<4){
            return points;
        }//此时这几个点一定构成凸包
        HashSet<Point> set=new HashSet<Point>();//凸包
        set.clear();
        Point begin=points.iterator().next();
        for (Point tmp:points) {
            if (tmp.x() < begin.x() || (tmp.x() == begin.x() && tmp.y() < begin.y())) {
                begin = tmp;
            }
        }//选取最左下角的点最为初始点
        set.add(begin);
        double angle;
        double minAngle;
        double currentBearing =0;
        Point target=points.iterator().next(),current=begin;
        do{
            minAngle=360;//最小角度初值360，一定会被迭代
            for (Point p:points) {
                if(p==current)continue;
                angle=calculateBearingToPointDouble(currentBearing,current.x(),current.y(),p.x(),p.y());//相当于重载方法calculateBearingToPoint
                double dis=Math.sqrt(p.y()-current.y())+Math.sqrt(p.x()-current.x())-Math.sqrt(target.y()-current.y())-Math.sqrt(target.y()-current.y());//在target点与p点在与current同一角度时，取距离更大的用到
                if((angle<minAngle)||(angle==minAngle)&&dis>0){//选角度最小的那一点是要找的点，在target点与p点在与current同一角度时，取距离更大的那一点
                    target=p;
                    minAngle=angle;
                }
            }
            set.add(target);
            current=target;
            currentBearing=minAngle;
        }while (target!=begin);
        return set;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        turtle.color(PenColor.YELLOW);
        int sidelength=50;
        for (int i=1;i<=80;i++){
            turtle.forward(sidelength);
            turtle.turn(144);
            if(i%5==0) {
                sidelength += 50;
                turtle.color(PenColor.BLUE);
            }
            if(i==20){
                turtle.color(PenColor.MAGENTA);
                turtle.turn(90);
                sidelength=50;
            }
            if(i==40){
                turtle.color(PenColor.ORANGE);
                turtle.turn(90);
                sidelength=50;
            }
            if(i==60) {
                turtle.color(PenColor.GREEN);
                turtle.turn(90);
                sidelength = 50;
            }
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle,7,40);
          drawPersonalArt(turtle);
        // draw the window
        turtle.draw();
    }

}
