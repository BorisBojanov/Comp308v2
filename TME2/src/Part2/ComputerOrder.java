/*
2. GenericOrder Class
ComputerOrder.java: Contains GenericOrder, ComputerOrder, and PartyTrayOrder.

A collection of an arbitrary number of objects in Products.java. 
Design a generic container called GenericOrder that acts as a collection of an arbitrary number of objects in Products.java.
 Design a mechanism that gives each instance of the container a unique identifier. 
 Implement as many methods as necessary. You must use Java generics features.
*/
package Part2;

import java.util.*;

class GenericOrder<T> {
    private static long idCounter = 1;
    private final long orderId;
    private final List<T> items;

    public GenericOrder() {
        items = new ArrayList<>();
        orderId = idCounter++;
    }

    public long getOrderId() {
        return orderId;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public List<T> getItems() {
        return items;
    }
}

class ComputerOrder extends GenericOrder<Product> {
    public void addComputerPart(ComputerPart part) {
        addItem(part);
    }

    public void addPeripheral(Peripheral peripheral) {
        addItem(peripheral);
    }

    public void addService(Service service) {
        addItem(service);
    }
}

class PartyTrayOrder extends GenericOrder<Product> {
    public void addCheese(Cheese Cheese) {
        addItem(Cheese);
    }

    public void addFruit(Fruit Fruit) {
        addItem(Fruit);
    }

    public void addService(Service Service) {
        addItem(Service);
    }
}

// Part 2 of the assignment
class ComputerPartyOrder extends GenericOrder<Product> {
    public void addComputerPart(ComputerPart part) {
        addItem(part);
    }

    public void addPeripheral(Peripheral peripheral) {
        addItem(peripheral);
    }

    public void addCheese(Cheese cheese) {
        addItem(cheese);
    }

    public void addFruit(Fruit fruit) {
        addItem(fruit);
    }

    public void addService(Service service) {
        addItem(service);
    }
}