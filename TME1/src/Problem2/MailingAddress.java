/*
Title: MailingAddress.java
Description: Class that represents a mailing address.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Write a class called MailingAddress that represents a mailing address. 
It must have separate fields for a FullName object, street address, city, province and postal code. 
Other than FullName all other fields are Strings. 
Override the toString() method to return a nicely formatted address. 
Create as many methods as you think necessary.

Compile: 
This class is not meant to be compiled on its own.

Run: 
This class is not meant to be run on its own.

Classes:
MailingAddress

Variables:
- fullName: FullName
- streetAddress: String
- city: String
- province: String
- postalCode: String

Methods:
- MailingAddress(): Default Constructor
- MailingAddress(FullName fullName, String streetAddress, String city, String province, String postalCode): Constructor
- toString(): - String - Override the toString() method to return a nicely formatted address.

TEST PLAN:
Normal case:
This class is tested in Problem2.java.
*/

package Problem2;

public class MailingAddress {
    private FullName fullName;
    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;

    // Default Constructor
    public MailingAddress() {
    }

    public MailingAddress(FullName fullName, String streetAddress, String city, String province, String postalCode) {
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return String.format("%s%n%s%n%s, %s%n%s", fullName, streetAddress, city, province, postalCode);
    }
}