/*
Title: Problem1.java
Description: This file contains the main method that tests the Circle class.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Create a class called Circle that represents a circle. 
It must have fields for the x and y coordinates of the center and the radius.

Compile: javac -d bin src/Assignment1/Problem1/*.java 
Run: java -cp bin Assignment1.Problem1Files.Problem1

Classes:
Problem1 - this class
Circle - class that represents a circle

Variables:
- defaultCircle: Circle - Circle object created using the default constructor
- customCircle: Circle - Circle object created using the parameterized constructor

Methods:
- main(String[] args): void - main method that tests the Circle class

TEST PLAN:
Normal case:
1. Create a Circle object using the default constructor.
2. Print the attributes of the default Circle object.
3. Create a Circle object using the parameterized constructor.
4. Print the attributes of the custom Circle object.
5. Test the circumference and area methods of the custom Circle object.
6. Test the isInside method of the custom Circle object.
7. Move the custom Circle object's center.
8. Print the attributes of the custom Circle object after moving.

Expected Output:
Default Circle:
Center: (0.0, 0.0)
Radius: 1.0
Circumference: 6.283185307179586
Area: 3.141592653589793


Custom Circle:
Center: (5.0, 7.0)
Radius: 1.0
Circumference: 6.283185307179586
Area: 3.141592653589793


Circumference of customCircle: 6.283185307179586
Area of customCircle: 3.141592653589793

Is point (5, 7) inside customCircle? true

After moving customCircle:
Center: (15.0, 22.0)
Radius: 1.0
Circumference: 6.283185307179586
Area: 3.141592653589793

*/

package Problem1;

public class Problem1 {
    public static void main(String[] args) {
        // Create a Circle object using the default constructor
        Circle defaultCircle = new Circle();
        System.out.println("Default Circle:");
        defaultCircle.printAttributes();
        
        // Create a Circle object using the parameterized constructor
        Circle customCircle = new Circle(5.0, 7.0, 1.0);
        System.out.println("\nCustom Circle:");
        customCircle.printAttributes();

        // Test the circumference and area methods
        System.out.println("\nCircumference of customCircle: " + customCircle.circumference());
        System.out.println("Area of customCircle: " + customCircle.area());

        // Test the isInside method
        System.out.println("\nIs point (5, 7) inside customCircle? " + customCircle.isInside(5, 7));

        // Move the customCircle's center
        customCircle.move(10, 15);
        System.out.println("\nAfter moving customCircle:");
        customCircle.printAttributes();
    }
}
