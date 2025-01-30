/**
 * Titile: Assignment2Part1.java
 * Description: This is a solution to TME 2 Part 1
 * Author: Boris B
 * Date: Jan 20, 2025
 * Code version: 1.0
 * CopyRight: 2025 Boris B
 *
 * 
 * Program Purpose:
 * 1. Carefully study the class structure in Products.java.
 * 2. Design a generic container called GenericOrder that acts as a collection of an arbitrary number of objects in Products.java.
 * 3. Create and implement subclasses ComputerOrder and PartyTrayOrder.
 * 4. Design and implement an OrderProcessor class.
 * 5. Create a client class to test OrderProcessor with a data generator.
 * 6. Provide evidence of compilation and execution.
 * 
 * Compile: javac -d bin src/Assignment2/Problem1/*.java
 * Run: java -cp bin Assignment2.Problem1.Assignment2Part1

 * 
 * */
package Part1;

import java.util.Random;
/**
 * This class is a solution to TME 2 Part 1
 */

public class Assignment2Part1 {
        /**
         * Entry point of the application. Creates an OrderProcessor, generates test orders, accepts them,
         * and then dispatches relevant parts and cheese.
         *
         * @param args command line arguments
         */
        public static void main(String[] args) {
            // Create an instance of the OrderProcessor
            OrderProcessor processor = new OrderProcessor();
    
            // Generate test data
            ComputerOrder computerOrder = generateComputerOrder();
            PartyTrayOrder partyOrder = generatePartyTrayOrder();
    
            // Accept orders
            processor.accept(computerOrder);
            processor.accept(partyOrder);
    
            System.out.println("Orders accepted!");
    
            // Dispatch orders
            System.out.println("\nDispatching Computer Parts:");
            processor.dispatchOrders(ComputerPart.class);
    
            System.out.println("\nDispatching Cheese:");
            processor.dispatchOrders(Cheese.class);
        }
        /**
         * Generates a random ComputerOrder by adding motherboards, RAM modules, and a service.
         *
         * @return A ComputerOrder populated with random items
         */    
        private static ComputerOrder generateComputerOrder() {
        ComputerOrder computerOrder = new ComputerOrder();
        Random random = new Random();

        // Add random Motherboards
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            computerOrder.addComputerPart(new Motherboard("Asus-" + (i + 1), randomPrice(30, 50)));
        }

        // Add random RAMs
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            computerOrder.addComputerPart(new RAM("Kingston-" + (i + 1), randomSize(256, 1024), randomPrice(20, 40)));
        }

        // Add random services
        computerOrder.addService(new AssemblyService("TechService", randomPrice(10, 20)));
        return computerOrder;
    }

    /**
     * Generates a random PartyTrayOrder by adding cheeses, fruits, and a delivery service.
     *
     * @return A PartyTrayOrder populated with random items
     */
    private static PartyTrayOrder generatePartyTrayOrder() {
        PartyTrayOrder partyOrder = new PartyTrayOrder();
        Random random = new Random();

        // Add random Cheeses
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            partyOrder.addCheese(new Cheddar(randomPrice(3, 10)));
        }

        // Add random Fruits
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            partyOrder.addFruit(new Orange(randomPrice(2, 5)));
        }

        // Add random services
        partyOrder.addService(new DeliveryService("FastCourier", randomPrice(5, 15)));
        return partyOrder;
    }

    /**
     * Generates a random price within a given range.
     *
     * @param min The minimum price
     * @param max The maximum price
     * @return A randomly generated float value within the specified range
     */
    private static float randomPrice(int min, int max) {
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }

    /**
     * Generates a random RAM size rounded to multiples of 256 within a given range.
     *
     * @param min The minimum size
     * @param max The maximum size
     * @return A randomly generated RAM size
     */
    private static int randomSize(int min, int max) {
        Random random = new Random();
        return min + random.nextInt((max - min) / 256 + 1) * 256;
    }
}

