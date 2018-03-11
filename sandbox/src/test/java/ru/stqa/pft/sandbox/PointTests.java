package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testArea (){
        Point p1 = new Point(10,15);
        Point p2 = new Point(20,25);
        Point p3 = new Point(1,1);
        Point p4 = new Point(2,2);
        Assert.assertEquals(p1.distance(p2), 14.142135623730951);
        Assert.assertEquals(p2.distance(p3), 30.610455730027933);
        Assert.assertEquals(p3.distance(p4), 1.4142135623730951);

    }
}
