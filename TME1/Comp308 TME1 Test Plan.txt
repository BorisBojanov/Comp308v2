### TME 1 Testing Plan

**Course:** Computer Science 308 – Java for Programmers
**Assignment:** TME 1
**Author:** [Your Name]
**Student ID:** [Your Student ID]
**Date:** [Submission Date]

---

## 1. Introduction
This document describes the test plan for all four programs required in TME 1. Each program is evaluated under normal, abnormal, and limiting conditions to ensure correctness, robustness, and compliance with requirements.

**Programs Under Test:**
1. **Program 1** – Class `Circle`
2. **Program 2** – Classes `FullName`, `MailingAddress`, and `ShippingLabel` with a test program in `ShippingLabel`
3. **Program 3** – Exercise 5 from TIJ (page 286)
4. **Program 4** – Exercise 10 from TIJ (page 320)

For each program, the following sections outline a general overview, expected functionalities, test cases, and results.

---

## 2. Program 1: `Circle`
### 2.1 Overview
The `Circle` class:
- Has two constructors (one default, one with parameters for X, Y, and radius)
- Contains methods for `circumference()`, `area()`, `setRadius(double r)`, `printAttributes()`, `isInside(double x, double y)`, and `move(int x, int y)`
- Enforces a maximum radius check in `setRadius` using a ternary operator.

### 2.2 Testing Approach
1. **Compile & Run Instructions**  
   - Compile: `javac Circle.java`  
   - (If a separate test driver is provided, also compile it, e.g., `javac CircleTest.java`)  
   - Run: `java CircleTest`  

2. **Normal Data Tests**
   - **Purpose:** Confirm circle calculations (area, circumference) match expected math formulas with standard inputs.  
   - **Sample Cases:**
     - **Case 1**: X=0.0, Y=0.0, radius=5.0  
       - Expected circumference = `2 * π * 5.0`  
       - Expected area = `π * 5.0^2`  
       - `isInside(3,4)` should be true if distance from center <= 5  
     - **Case 2**: X=2.5, Y=4.0, radius=10.0  
       - Verify correct updates after `move(int x, int y)`

3. **Abnormal Data Tests**
   - **Purpose:** Ensure invalid or unexpected inputs are handled gracefully.  
   - **Sample Cases:**
     - **Negative radius** (e.g., 3.0). The program should correct the radius to the maximum or handle invalid data as per design.
     - **Exceed maximum radius** (e.g., 9999). Check that `setRadius` enforces the maximum.

4. **Limiting Conditions**
   - **Zero radius**: Confirm area=0, circumference=0.
   - **Points exactly on the boundary**: `isInside` should handle boundary conditions correctly.

5. **Expected Results**
   - Each method returns correct numeric outcomes for valid inputs.
   - The class enforces radius limits.
   - `isInside(x, y)` returns correct boolean values.
   - Program does not crash or produce incorrect output for abnormal inputs.

---

## 3. Program 2: `FullName`, `MailingAddress`, and `ShippingLabel`
### 3.1 Overview
- `FullName` has fields for title, first name, middle name, last name. Overrides `toString()` to provide a formatted name.
- `MailingAddress` has fields for a `FullName` object, street address, city, province, postal code; overrides `toString()`.
- `ShippingLabel` includes a ship-from and ship-to `MailingAddress` and prints the label to the console in `System.out.println(label);`
- A main method (in `ShippingLabel`) tests these classes.

### 3.2 Testing Approach
1. **Compile & Run Instructions**  
   - Compile: `javac FullName.java MailingAddress.java ShippingLabel.java`  
   - Run: `java ShippingLabel`

2. **Normal Data Tests**
   - **Purpose:** Ensure the classes format and print valid data correctly.  
   - **Sample Cases:**
     - **Case 1**:  
       - `FullName` -> Title: "Mr.", First: "John", Middle: "T.", Last: "Smith"  
       - `MailingAddress` -> Street: "123 Maple Street", City: "Edmonton", Province: "AB", Postal Code: "T1T 2B2"  
       - Confirm `toString()` output is correct.
     - **Case 2**:  
       - `ShippingLabel` -> Provide distinct ship-from and ship-to addresses.  
       - Print label and verify output is in the expected format.

3. **Abnormal Data Tests**
   - **Purpose:** Verify behavior with missing or partial data.
   - **Sample Cases:**
     - **Empty middle name** (e.g., pass an empty string).  
       - Confirm `toString()` still formats name acceptably.
     - **Invalid postal code** (e.g., "ABCDE").  
       - Depending on program design, test how you handle an invalid code.

4. **Limiting Conditions**
   - Check minimum fields. For example, a user with only a single name or no title.  
   - Very long string inputs (e.g., extremely long street name).

5. **Expected Results**
   - Data is formatted neatly using `toString()` for each class.
   - `ShippingLabel` prints the combined addresses as expected.

---

## 4. Program 3: TIJ Exercise 5 (page 286)
### 4.1 Overview
**Task**: Complete the requested exercise #5 from *Thinking in Java (TIJ)*, page 286. (You will need the book reference to confirm details. This typically involves arrays, loops, or object creation/initialization logic, etc.)

### 4.2 Testing Approach
1. **Compile & Run Instructions**  
   - Compile: `javac Program3.java`  
   - Run: `java Program3`

2. **Normal Data Tests**
   - Provide typical inputs or ensure the logic matches the exercise prompt.  
   - Confirm each step or output matches the book’s specification or your interpretation.

3. **Abnormal Data Tests** (If the exercise deals with user input)
   - Attempt invalid data.  
   - Evaluate error messages or default responses.

4. **Limiting Conditions**
   - Very small or large values (if numeric input is involved).

5. **Expected Results**
   - Output matches the example from *Thinking in Java* or the logically expected result.

---

## 5. Program 4: TIJ Exercise 10 (page 320)
### 5.1 Overview
**Task**: Complete the requested exercise #10 from *Thinking in Java (TIJ)*, page 320. (Likely involves classes, inheritance, or interfaces—depending on the assignment details.)

### 5.2 Testing Approach
1. **Compile & Run Instructions**  
   - Compile: `javac Program4.java`  
   - Run: `java Program4`

2. **Normal Data Tests**
   - Follow the instructions from the exercise to verify logic and output.
   - Provide typical or expected usage scenarios.

3. **Abnormal Data Tests**
   - If user input is accepted, test invalid or unexpected entries.

4. **Limiting Conditions**
   - Edge cases, such as zero, negative values, or boundary conditions, if relevant.

5. **Expected Results**
   - Must match or correspond to the instructions in the TIJ exercise.

---

## 6. Overall Test Summary
- **Number of Test Cases per Program**: Aim for at least 3–5 test cases (normal, abnormal, limit conditions) for each program.
- **Tools**:  
  - JDK: [Specify version, e.g., JDK 8 or 11]  
  - Operating System: [Windows, Linux, etc.]
- **Reporting**: For each test case, record:
  1. **Input** (or steps taken)
  2. **Expected Outcome**
  3. **Actual Outcome**
  4. **Pass/Fail**

- **If any test fails**: Describe steps to debug, correct, and re-test.

---

## 7. Final Notes
1. **Submission**: Include all required .java source files, along with this test plan, as per TME guidelines. Do not include .class files.
2. **HTML for Applets**: If any program is an applet, include the .html file, but these four appear to be standard Java classes.
3. **Enhancements**: As you refine or extend your classes, ensure retesting.

**End of Document**

