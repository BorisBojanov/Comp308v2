/*
Title: Assignment 2 Part 2
Description: This class demonstrates the OrderProcessor class by generating a random ComputerPartyOrder and dispatching the orders.
Author: Boris B
Date: January 21, 2025
Version: 1.0
Copyright: Boris B 2025

DOCUMENTATION
Program Purpose:
    This program demonstrates the OrderProcessor class by generating a random ComputerPartyOrder and dispatching the orders.
    Implement ComputerPartyOrder Class, the current implementation of OrderProcessor class can already handle the ComputerPartyOrder class.
    It accepts any GenericOrder<?>  processes items based on type. 

    No changes are required to OrderProcessor.

Compile: javac -d bin src/Assignment2/Problem1/*.java  
Run: java -cp bin Assignment2.Problem1.Assignment2Part2

Classes: 
    Assignment2Part2 - Main class that demonstrates the OrderProcessor class by generating a random ComputerPartyOrder and dispatching the orders.
    ComputerPartyOrder - Represents a ComputerPartyOrder that contains ComputerParts, Cheeses, Fruits, and Services.
    ComputerPart - Represents a ComputerPart that has a name and price.
    Cheese - Represents a Cheese that has a price.
    Fruit - Represents a Fruit that has a price.
    Service - Represents a Service that has a name and price.
    Motherboard - Represents a Motherboard that extends ComputerPart.
    RAM - Represents a RAM that extends ComputerPart and has a size.

Variables:
    processor (OrderProcessor) - An instance of the OrderProcessor class.
    computerPartyOrder (ComputerPartyOrder) - A random ComputerPartyOrder.
    random (Random) - A random number generator.

Methods:
    main(String[]) - The main method that demonstrates the OrderProcessor class by generating a random ComputerPartyOrder and dispatching the orders.
    generateComputerPartyOrder() - Generates a random ComputerPartyOrder.
    randomPrice(int, int) - Generates a random price within a range.
    randomSize(int, int) - Generates a random RAM size.

TEST PLAN
Normal Case:
    The program generates a random ComputerPartyOrder and dispatches the orders.
    Expected output: 
Orders accepted!

Dispatching Computer Parts:
Dispatching items of type: ComputerPart
Motherboard – name=Asus-1, price=$35.00465, order ID: 1
Motherboard – name=Asus-2, price=$41.95266, order ID: 1
Motherboard – name=Asus-3, price=$44.657238, order ID: 1
RAM - name=Kingston-1, size=1024MB, price=$37.004173, order ID: 1
RAM - name=Kingston-2, size=512MB, price=$21.56364, order ID: 1

Dispatching Cheese:
Dispatching items of type: Cheese
Cheddar – price=$6.9109716, order ID: 1
Cheddar – price=$6.2855353, order ID: 1

Dispatching Fruits:
Dispatching items of type: Fruit
Orange – price=$4.056382, order ID: 1
Orange – price=$2.3142471, order ID: 1
Orange – price=$3.8452601, order ID: 1

Dispatching Services:
Dispatching items of type: Service
Assembly Service – provider=TechService, price=$19.75346, order ID: 1
Delivery Service – courier=FastCourier, price=$10.353099, order ID: 1

*/
package Part2;

import java.util.Random;

public class Assignment2Part2 {
    public static void main(String[] args) {
        // Create an instance of the OrderProcessor
        OrderProcessor processor = new OrderProcessor();

        // Generate a ComputerPartyOrder
        ComputerPartyOrder computerPartyOrder = generateComputerPartyOrder();

        // Accept the order
        processor.accept(computerPartyOrder);

        System.out.println("Orders accepted!");

        // Dispatch orders
        System.out.println("\nDispatching Computer Parts:");
        processor.dispatchOrders(ComputerPart.class);

        System.out.println("\nDispatching Cheese:");
        processor.dispatchOrders(Cheese.class);

        System.out.println("\nDispatching Fruits:");
        processor.dispatchOrders(Fruit.class);

        System.out.println("\nDispatching Services:");
        processor.dispatchOrders(Service.class);
    }

    // Generate a random ComputerPartyOrder
    private static ComputerPartyOrder generateComputerPartyOrder() {
        ComputerPartyOrder computerPartyOrder = new ComputerPartyOrder();
        Random random = new Random();

        // Add random Motherboards
        for (int i = 0; i < random.nextInt(2) + 1; i++) {
            computerPartyOrder.addComputerPart(new Motherboard("Asus-" + (i + 1), randomPrice(30, 50)));
        }

        // Add random RAMs
        for (int i = 0; i < random.nextInt(5) + 1; i++) {
            computerPartyOrder.addComputerPart(new RAM("Kingston-" + (i + 1), randomSize(256, 1024), randomPrice(20, 40)));
        }

        // Add random Cheeses
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            computerPartyOrder.addCheese(new Cheddar(randomPrice(3, 10)));
        }

        // Add random Fruits
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            computerPartyOrder.addFruit(new Orange(randomPrice(2, 5)));
        }

        // Add random Services
        computerPartyOrder.addService(new AssemblyService("TechService", randomPrice(10, 20)));
        computerPartyOrder.addService(new DeliveryService("FastCourier", randomPrice(5, 15)));

        return computerPartyOrder;
    }

    // Utility method to generate a random price within a range
    private static float randomPrice(int min, int max) {
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }

    // Utility method to generate a random RAM size
    private static int randomSize(int min, int max) {
        Random random = new Random();
        return min + random.nextInt((max - min) / 256 + 1) * 256;
    }
}
