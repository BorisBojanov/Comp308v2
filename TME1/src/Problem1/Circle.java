package Problem1;

public class Circle {
    private double xLocal; // X-coordinate of the circle's center
    private double yLocal; // Y-coordinate of the circle's center
    private double radiusLocal; // Radius of the circle
    private static final double MAX_RADIUS = 100.0; // Example maximum radius

    // Default constructor
    public Circle() {
        this.xLocal = 0.0;
        this.yLocal = 0.0;
        this.radiusLocal = 1.0; // Default radius
    }

    // Parameterized constructor
    public Circle(double x, double y, double radius) {
        this.xLocal = x;
        this.yLocal = y;
        setRadius(radius); // Ensures radius does not exceed the maximum
    }

    public double circumference() {
    	return 2 * Math.PI * radiusLocal;
    }
    
	//Returns the area of the circle.
	public double area() {
		double area = Math.PI * radiusLocal * radiusLocal;
		return area;
	}
	
	//Is called in the constructor and checks the radius against a maximum. 
	// If the radius is greater than the maximum, setRadius resets it to the maximum (using the ternary conditional operator). 
	// You may set your own maximum value.
	void setRadius(double r) {
        radiusLocal = (r > MAX_RADIUS) ? MAX_RADIUS : r; // Ternary conditional operator
	}
	
	//Prints the coordinates, the radius, the circumference, and the area.
	void printAttributes() {
        System.out.println("Center: (" + xLocal + ", " + yLocal + ")");
        System.out.println("Radius: " + radiusLocal);
        System.out.println("Circumference: " + circumference());
        System.out.println("Area: " + area());
        System.out.println();
	}
	
	//Return true if a point represented in the parameters falls inside the circle, false otherwise.
	boolean isInside(double x, double y) {
        double distance = Math.sqrt(Math.pow(x - xLocal, 2) + Math.pow(y - yLocal, 2)); // Distance formula
        return distance <= radiusLocal; // True if the distance is less than or equal to the radius
	}
	
	//Moves the origin by a specified amount.
	void move(int x, int y) {
        xLocal += x;
        yLocal += y;    
	}
}