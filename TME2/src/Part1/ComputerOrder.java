/*
2. GenericOrder Class
ComputerOrder.java: Contains GenericOrder, ComputerOrder, and PartyTrayOrder.

A collection of an arbitrary number of objects in Products.java. 
Design a generic container called GenericOrder that acts as a collection of an arbitrary number of objects in Products.java.
 Design a mechanism that gives each instance of the container a unique identifier. 
 Implement as many methods as necessary. You must use Java generics features.
*/
package Part1;

import java.util.*;

/**
 * A generic order container that holds a list of items of a specified type and assigns each order a unique ID.
 *
 * @param <T> Type of item stored in this generic order
 */
class GenericOrder<T> {
    private static long idCounter = 1;
    private final long orderId;
    private final List<T> items;

    /**
     * Constructs a new GenericOrder, assigning a unique order ID and initializing an empty item list.
     */
    public GenericOrder() {
        items = new ArrayList<>();
        orderId = idCounter++;
    }

    /**
     * Returns the unique identifier of this order.
     *
     * @return The order's unique ID
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Adds an item to this order.
     *
     * @param item The item to be added
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * Retrieves the list of items in this order.
     *
     * @return A List of items
     */
    public List<T> getItems() {
        return items;
    }
}

/**
 * Represents an order specifically for computer-related products, items such as computer parts,
 * peripherals, and related services.
 */
class ComputerOrder extends GenericOrder<Product> {

    /**
     * Adds a computer part to the order.
     *
     * @param part The ComputerPart to add
     */
    public void addComputerPart(ComputerPart part) {
        addItem(part);
    }

    /**
     * Adds a peripheral to the order.
     *
     * @param peripheral The Peripheral to add
     */
    public void addPeripheral(Peripheral peripheral) {
        addItem(peripheral);
    }


    /**
     * Adds a service to the order.
     *
     * @param service The Service to add
     */
    public void addService(Service service) {
        addItem(service);
    }
}

/**
 * Represents an order specifically for party tray items, such as cheese, fruits, and delivery services.
 */
class PartyTrayOrder extends GenericOrder<Product> {

    /**
     * Adds cheese to the party tray order.
     *
     * @param Cheese The Cheese to add
     */    
    public void addCheese(Cheese Cheese) {
        addItem(Cheese);
    }

    /**
     * Adds fruit to the party tray order.
     *
     * @param Fruit The Fruit to add
     */
    public void addFruit(Fruit Fruit) {
        addItem(Fruit);
    }

    /**
     * Adds a service to the party tray order.
     *
     * @param Service The Service to add
     */
    public void addService(Service Service) {
        addItem(Service);
    }
}