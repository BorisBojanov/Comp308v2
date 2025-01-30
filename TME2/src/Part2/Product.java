/*
1. Carefully study the class structure in Products.java
*/


package Part2;

abstract class Product {
    protected float price;

    // return the price of a particular product
    abstract float price();
}

//------------------------------------------------------------

class ComputerPart extends Product {
    public ComputerPart(float p) {
	price = p;
    }
    public float price() { return price; }
}

class Motherboard extends ComputerPart {
    protected String manufacturer;
    public Motherboard(String mfg, float p) {
	super(p);
	manufacturer = mfg;
    }
    @Override
    public String toString() {
        return "Motherboard – name=" + manufacturer + ", price=$" + price();
    }
}

class RAM extends ComputerPart {
    protected int size;
    protected String manufacturer;
    public RAM(String mfg, int size, float p) {
        super(p);
        this.manufacturer = mfg;
        this.size = size;
    }
    @Override
    public String toString() {
        return "RAM - name=" + manufacturer + ", size=" + size + "MB, price=$" + price();
    }
}

class Drive extends ComputerPart {
    protected String type;
    protected int speed;
    public Drive(String type, int speed, float p) {
	super(p);
	this.type = type;
	this.speed = speed;
    }
    public String getType() { return type; }
    public int getSpeed() { return speed; }
}


class Peripheral extends Product {
    public Peripheral(float p) {
	price = p;
    }
    public float price() { return price; }
}

class Printer extends Peripheral {
    protected String model;
    public Printer(String model, float p) {
	super(p);
	this.model = model;
    }
    @Override
    public String toString() {
        return "Printer – model=" + model + ", price=$" + price();
    }
}

class Monitor extends Peripheral {
    protected String model;
    public Monitor(String model, float p) {
	super(p);
	this.model = model;
    }
    @Override
    public String toString() {
        return "Monitor – model=" + model + ", price=$" + price();
    }
}

class Service extends Product {
    public Service(float p) {
	price = p;
    }
    public float price() { return price; }
}

//-------------------------------------------------------
class AssemblyService extends Service {
    String provider;
    public AssemblyService(String pv, float p) {
	super(p);
	provider = pv;
    }
    @Override
    public String toString() {
        return "Assembly Service – provider=" + provider + ", price=$" + price();
    }
}

class DeliveryService extends Service {
    String courier;
    public DeliveryService(String c, float p) {
	super(p);
	courier = c;
    }
    @Override
    public String toString() {
        return "Delivery Service – courier=" + courier + ", price=$" + price();
    }
}

//-------------------------------------------------------
class Cheese extends Product {
    public Cheese(float p) {
	price = p;
    }
    public float price() { return price; }
}

class Cheddar extends Cheese {
    public Cheddar(float p) {
	super(p);
    }
    @Override
    public String toString() {
        return "Cheddar – price=$" + price();
    }
}
class Mozzarella extends Cheese {
    public Mozzarella(float p) {
	super(p);
    }
    @Override
    public String toString() {
        return "Mozzarella – price=$" + price();
    }
}

class Fruit extends Product {
    public Fruit(float p) {
	price = p;
    }
    public float price() { return price; }
}
class Apple extends Fruit {
    public Apple(float p) {
	super(p);
    }
    @Override
    public String toString() {
        return "Apple – price=$" + price();
    }
}
class Orange extends Fruit {
    public Orange(float p) {
	super(p);
    }
    @Override
    public String toString() {
        return "Orange – price=$" + price();
    }
}

