/*
Title: Problem 3.java
Description: Program that demonstrates polymorphism with the Cycle, Unicycle, Bicycle, and Tricycle classes.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Demonstrate polymorphism with the Cycle, Unicycle, Bicycle, and Tricycle classes.

Compile: javac -d bin src/Assignment1/Problem3/*.java
Run: java -cp bin Assignment1.Problem3.Exercise5Page286

Classes:
    Exercise5Page286 - Main class that demonstrates polymorphism with the Cycle, Unicycle, Bicycle, and Tricycle classes.
    Cycle - Represents a generic cycle with a ride method and wheels method.
    Unicycle - Represents a unicycle with a wheels method that returns 1.
    Bicycle - Represents a bicycle with a wheels method that returns 2.
    Tricycle - Represents a tricycle with a wheels method that returns 3.

Variables:
    cycles - Array of cycles that contains a Unicycle, Bicycle, and Tricycle.

Functions:
    main - Entry point for the program that demonstrates polymorphism with the Cycle, Unicycle, Bicycle, and Tricycle classes.


*/

package Problem3;

class Cycle {
    public void ride() {
        System.out.println("Riding a cycle with " + wheels() + " wheels.");
    }

    public int wheels() {
        return 0; // Default for a generic cycle
    }
}

class Unicycle extends Cycle {
    @Override
    public int wheels() {
        return 1;
    }
}

class Bicycle extends Cycle {
    @Override
    public int wheels() {
        return 2;
    }
}

class Tricycle extends Cycle {
    @Override
    public int wheels() {
        return 3;
    }
}

public class Exercise5Page286 {
    public static void main(String[] args) {
        Cycle[] cycles = { new Unicycle(), new Bicycle(), new Tricycle() };
        for (Cycle cycle : cycles) {
            cycle.ride(); // Demonstrates polymorphism with the wheels method
        }
    }
}
