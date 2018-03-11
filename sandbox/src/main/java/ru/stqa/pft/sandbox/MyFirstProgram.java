package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	public static void main (String[] args) {
		Point p1 = new Point(10,15);
		Point p2 = new Point(20,25);

		Point p3 = new Point(1,1);
		Point p4 = new Point(2,2);

		Square s = new Square(5);
		print ("Площадь квадрата со стороной " + s.l + "=" + s.area());

		Rectangle r = new Rectangle(4,6);
		print ("Площадь прямоугольника со сторонами " + r.a + "и" + r.b + "=" + r.area());

		/*print("Расстояние = " + distance(p1,p2));*/ /*пункт 3*/
		print("Расстояние = " + p1.distance(p2));
		print("Расстояние = " + p2.distance(p3));
		print("Расстояние = " + p3.distance(p4));

	}
	public static void print (String somebody)  {
		System.out.println(somebody);
	}
	/*пункт 3*/
	/*public static double distance(Point p1, Point p2) {
		double distance = Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y));
		return distance;
	}*/


}
