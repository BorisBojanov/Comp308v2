/*
Title: Program2.java
Description: Program that demonstrates the functionality of the FullName, MailingAddress, and ShippingLabel classes.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Write a simple test program in the main method of ShippingLabel to test the above classes.

Compile: javac -d bin src/Assignment1/Problem2/*.java
Run: java -cp bin Assignment1.Problem2.Problem2

Classes:
Problem2 - this class
FullName - class that represents a person's full name
MailingAddress - class that represents a mailing address
ShippingLabel - class that consists of ship-from and ship-to MailingAddress objects

Variables:
- sender: FullName
- shipFrom: MailingAddress
- receiver: FullName
- shipTo: MailingAddress
- label: ShippingLabel

Methods:
- main(String[] args): main method that tests the classes

TEST PLAN:
Normal case:
1. Create a FullName object for the sender.
2. Create a MailingAddress object for the sender.
3. Create a FullName object for the receiver.
4. Create a MailingAddress object for the receiver.
5. Create a ShippingLabel object with the sender and receiver MailingAddress objects.
6. Print the ShippingLabel object.

Expected Output:
Shipping From:
Mr. Boris  B
1234 Street
Toronto, ON
M1M1M1

Shipping To:
Mr. John  Doe
5678 Street
Toronto, ON
M2M2M2

*/

package Problem2;


public class Problem2 {
    public static void main(String[] args) {
        FullName sender = new FullName("Mr.", "Boris", "", "B");
        MailingAddress shipFrom = new MailingAddress(sender, "1234 Street", "Toronto", "ON", "M1M1M1");

        FullName receiver = new FullName("Mr.", "John", "", "Doe");
        MailingAddress shipTo = new MailingAddress(receiver, "5678 Street", "Toronto", "ON", "M2M2M2");

        ShippingLabel label = new ShippingLabel(shipFrom, shipTo);
        System.out.println(label);
    }
}