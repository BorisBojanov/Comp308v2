/*
Title: Program2.java
Description: Program that demonstrates the functionality of the FullName, MailingAddress, and ShippingLabel classes.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Write a class called FullName that represents a person's full name. 
It must have separate fields for title 
(e.g., Mr., Mrs., Ms.), first name, middle name, and last name. 
Override the toString() method to return a nicely formatted name. 
Create as many methods as you think necessary.

Compile: 
This class is not meant to be compiled on its own.

Run: 
This class is not meant to be run on its own.

Classes: 
FullName

Variables:
- title: String
- firstName: String
- middleName: String
- lastName: String

Methods:
- toString(): String

TEST PLAN:
Normal case:
This class is tested in Problem2.java.

*/
package Problem2;

public class FullName {
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;

    // Default Constructor
    public FullName() {
    }
	
    public FullName(String title, String firstName, String middleName, String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s %s", title, firstName, middleName, lastName).trim();
    }
}
