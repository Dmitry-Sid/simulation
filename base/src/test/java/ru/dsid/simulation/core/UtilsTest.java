package ru.dsid.simulation.core;

import org.junit.jupiter.api.Test;
import ru.dsid.simulation.pojo.MovingObject;
import ru.dsid.simulation.pojo.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    void calculateDistancePointTest() {
        assertEquals(0, Utils.calculateDistance(new Point(5.0, 10.0), new Point(5.0, 10.0)));
        assertEquals(5, Utils.calculateDistance(new Point(5.0, 10.0), new Point(8.0, 14.0)));
    }

    @Test
    void calculateDistanceTest() {
        assertEquals(5, Utils.calculateDistance(5.0, 10.0));
        assertEquals(-5, Utils.calculateDistance(10.0, 5.0));
    }

    @Test
    void moveTest() {
        {
            final MovingObject movingObject = new MovingObjectImpl();
            movingObject.setSpeed(5);
            movingObject.setPoint(new Point(0.0, 0.0));
            movingObject.setDestinationPoint(new Point(10.0, 10.0));
            Utils.move(movingObject, 1);
            assertEquals(5.0 * Math.cos(Math.PI / 4), movingObject.getPoint().getX(), 0.01);
            assertEquals(5.0 * Math.cos(Math.PI / 4), movingObject.getPoint().getY(), 0.01);
        }
        {
            final MovingObject movingObject = new MovingObjectImpl();
            movingObject.setSpeed(5);
            movingObject.setPoint(new Point(0.0, 0.0));
            movingObject.setDestinationPoint(new Point(0.0, 10.0));
            Utils.move(movingObject, 1);
            assertEquals(0, movingObject.getPoint().getX(), 0.01);
            assertEquals(5.0, movingObject.getPoint().getY(), 0.01);
        }
        {
            final MovingObject movingObject = new MovingObjectImpl();
            movingObject.setSpeed(15);
            movingObject.setPoint(new Point(0.0, 0.0));
            movingObject.setDestinationPoint(new Point(0.0, 10.0));
            Utils.move(movingObject, 1);
            assertEquals(0, movingObject.getPoint().getX(), 0.01);
            assertEquals(10.0, movingObject.getPoint().getY(), 0.01);
        }
        {
            final MovingObject movingObject = new MovingObjectImpl();
            movingObject.setSpeed(5);
            movingObject.setPoint(new Point(0.0, 0.0));
            movingObject.setDestinationPoint(new Point(0.0, 10.0));
            Utils.move(movingObject, 3);
            assertEquals(0, movingObject.getPoint().getX(), 0.01);
            assertEquals(10.0, movingObject.getPoint().getY(), 0.01);
        }
    }

    @Test
    void calculatePointTest() {
        double angle = 0.0;
        final Point point = new Point(5.0, 5.0);
        assertCalculatePoint(point, 2.0, angle);
        for (int i = 0; i < 3; i++) {
            assertCalculatePoint(point, 2.0, angle + Math.PI / 6);
            assertCalculatePoint(point, 2.0, angle + Math.PI / 3);
            angle += Math.PI / 4;
            assertCalculatePoint(point, 2.0, angle);
        }
        assertCalculatePoint(point, 2.0, Math.PI);
        angle = 0.0;
        for (int i = 0; i < 3; i++) {
            assertCalculatePoint(point, 2.0, angle - Math.PI / 6);
            assertCalculatePoint(point, 2.0, angle - Math.PI / 3);
            angle -= Math.PI / 4;
            assertCalculatePoint(point, 2.0, angle);
        }
    }

    private void assertCalculatePoint(Point point, double distance, double angle) {
        final Point calculatedPoint = Utils.calculatePoint(point, distance, angle);
        assertEquals(point.getX() + distance * Math.cos(angle), calculatedPoint.getX(), 0.01);
        assertEquals(point.getY() + distance * Math.sin(angle), calculatedPoint.getY(), 0.01);
    }

    @Test
    void calculateAngleTest() {
        double angle = 0.0;
        assertAngle(0.0);
        for (int i = 0; i < 3; i++) {
            assertAngle(angle + Math.PI / 6);
            assertAngle(angle + Math.PI / 3);
            angle += Math.PI / 4;
            assertAngle(angle);
        }
        assertAngle(Math.PI);
        angle = 0.0;
        for (int i = 0; i < 3; i++) {
            assertAngle(angle - Math.PI / 6);
            assertAngle(angle - Math.PI / 3);
            angle -= Math.PI / 4;
            assertAngle(angle);
        }
    }

    private void assertAngle(double angle) {
        assertEquals(angle, Utils.calculateAngle(new Point(5.0, 10.0),
                new Point(5.0 + Math.cos(angle), 10.0 + Math.sin(angle))), 0.001);
    }

    private static class MovingObjectImpl extends MovingObject {

    }
}