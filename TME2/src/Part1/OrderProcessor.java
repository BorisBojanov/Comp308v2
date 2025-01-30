

package Part1;
import java.util.*;

class OrderProcessor {

    private final List<GenericOrder<?>> orders = new ArrayList<>();

public void accept(GenericOrder<?> order) {
    orders.add(order);
}

public Map<Class<?>, List<String>> process() {
    Map<Class<?>, List<String>> sortedOrders = new LinkedHashMap<>(); // Maintain insertion order

    for (GenericOrder<?> order : orders) {
        //System.out.println("Processing order ID: " + order.getOrderId());
        for (Object item : order.getItems()) {
            //System.out.println("Item: " + item.getClass().getSimpleName());

            sortedOrders.computeIfAbsent(item.getClass(), k -> new ArrayList<>()).add(
                item.toString() + ", order ID: " + order.getOrderId());
        }
    }

    return sortedOrders;
}

    public void dispatchOrders(Class<?> type) {
        Map<Class<?>, List<String>> sortedOrders = process();
        List<String> filteredItems = new ArrayList<>();
    
        for (Map.Entry<Class<?>, List<String>> entry : sortedOrders.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                filteredItems.addAll(entry.getValue());
            }
        }
    
        System.out.println("Dispatching items of type: " + type.getSimpleName());
        if (filteredItems.isEmpty()) {
            System.out.println("No items of type " + type.getSimpleName() + " found.");
        } else {
            for (String item : filteredItems) {
                System.out.println(item);
            }
        }
    }
}
