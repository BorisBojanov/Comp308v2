/*
Title: ShippingLabel.java
Description: Class called ShippingLabel that consists of ship-from and ship-to MailingAddress objects.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Write a class called ShippingLabel that consists of ship-from and ship-to MailingAddress objects. 
Write a single method that prints the label to the console. 
Use these statements in the method:

ShippingLabel label = new ShippingLabel(... your parameter list ...);
System.out.println(label);
Write a simple test program in the main method of ShippingLabel to test the above classes.

Classes:
ShippingLabel - this class

Variables:
- shipFrom: MailingAddress
- shipTo: MailingAddress

Methods:
- ShippingLabel(): Default constructor
- ShippingLabel(MailingAddress xshipFrom, MailingAddress xshipTo): Parameterized constructor
- toString(): - String - Override the toString() method to return a nicely formatted address.

*/

package Problem2;

public class ShippingLabel {
    private MailingAddress shipFrom;
    private MailingAddress shipTo;

    // Default Constructor
    public ShippingLabel() {
    }

    public ShippingLabel(MailingAddress shipFrom, MailingAddress shipTo) {
        this.shipFrom = shipFrom;
        this.shipTo = shipTo;
    }

    @Override
    public String toString() {
        return String.format("Shipping From:%n%s%n%nShipping To:%n%s", shipFrom, shipTo);
    }
}