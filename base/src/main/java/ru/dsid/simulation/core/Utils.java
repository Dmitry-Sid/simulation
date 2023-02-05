package ru.dsid.simulation.core;

import ru.dsid.simulation.pojo.MovingObject;
import ru.dsid.simulation.pojo.Point;

public class Utils {

    public static double calculateDistance(Point point1, Point point2) {
        return Math.sqrt(Math.pow(calculateDistance(point1.getX(), point2.getX()), 2) +
                Math.pow(calculateDistance(point1.getY(), point2.getY()), 2));
    }

    public static double calculateDistance(double x1, double x2) {
        return x2 - x1;
    }

    public static void move(MovingObject movingObject, long time) {
        final double distance = movingObject.getSpeed() * time;
        if (calculateDistance(movingObject.getPoint(), movingObject.getDestinationPoint()) <= distance) {
            movingObject.setPoint(movingObject.getDestinationPoint());
        } else {
            movingObject.setPoint(calculatePoint(movingObject.getPoint(), distance,
                    calculateAngle(movingObject.getPoint(), movingObject.getDestinationPoint())));
        }
    }

    public static Point calculatePoint(Point startPoint, double distance, double angle) {
        return new Point(startPoint.getX() + distance * Math.cos(angle),
                startPoint.getY() + distance * Math.sin(angle));
    }

    public static double calculateAngle(Point point1, Point point2) {
        return Math.atan2(calculateDistance(point1.getY(), point2.getY()),
                calculateDistance(point1.getX(), point2.getX()));
    }
}
