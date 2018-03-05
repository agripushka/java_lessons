package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	public static void main (String[] args) {
		Point p1 = new Point(10,15);
		Point p2 = new Point(20,25);

		print("Расстояние = " + distance(p1,p2));
	}
	public static void print (String somebody)  {
		System.out.println(somebody);
	}
	public static double distance(Point p1, Point p2) {
		double distance = Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y));
		return distance;
	}

}
