# Tutor Marked Exercises 1

### **Skeleton for Any Java Program**

A basic Java program consists of the following structure:

```java
// Package declaration (optional)
package packageName;

// Import statements (optional)
import java.util.Scanner;

// Class declaration
public class ClassName {
    // Main method: Entry point of the program
    public static void main(String[] args) {
        // Program logic
        System.out.println("Hello, World!");
    }
}

```

---

### **Five Characteristics of Object-Oriented Programming (OOP)**

1. **Encapsulation**: Bundling data (fields) and methods (behavior) that operate on the data into a single unit (class) and restricting direct access to some components.
2. **Inheritance**: Mechanism for creating a new class (subclass) based on an existing class (superclass) to promote code reuse.
3. **Polymorphism**: Ability to treat objects of different classes uniformly through a common interface or parent class.
4. **Abstraction**: Hiding implementation details while exposing only essential features via abstract classes or interfaces.
5. **Dynamic (Late) Binding**: Decisions about which method to invoke are made at runtime, allowing polymorphism to work effectively.

---

### **How Late Binding Enables Upcasting and Polymorphism**

- **Late Binding**: Refers to method calls being resolved at runtime rather than compile time. This is critical for polymorphism, as the actual method invoked depends on the runtime type of the object, not the compile-time type.
- **Upcasting**: Assigning a subclass object to a reference of its superclass, allowing polymorphic behavior.

**Example:**

```java
class Animal {
    void makeSound() { System.out.println("Animal sound"); }
}

class Dog extends Animal {
    @Override
    void makeSound() { System.out.println("Woof!"); }
}

public class Main {
    public static void main(String[] args) {
        Animal animal = new Dog(); // Upcasting
        animal.makeSound();        // Late binding resolves to Dog's makeSound
    }
}

```

---

### **Parameterized Types/Generics and Elimination of Downcasting**

- **Parameterized Types/Generics**: Allow classes and methods to operate on specified types while maintaining type safety.
- **Eliminates Downcasting**: With generics, you no longer need to cast objects when retrieving them from collections.

**Example Without Generics (Requires Downcasting):**

```java
List list = new ArrayList();
list.add("Hello");
String str = (String) list.get(0); // Downcasting

```

**With Generics:**

```java
List<String> list = new ArrayList<>();
list.add("Hello");
String str = list.get(0); // No downcasting needed

```

---

### **Function of Exception Handling in Java**

- **Purpose**: To manage runtime errors gracefully, prevent program crashes, and maintain application stability.
- **Reinforcement in Java**:
    1. Enforced checked exceptions: Methods must declare or handle exceptions.
    2. Keywords like `try`, `catch`, `finally`, and `throw` provide structured error management.

**Example:**

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero!");
}

```

---

### **Primary Idea of a Client/Server System**

A client/server system involves:

1. **Client**: Requests services or resources.
2. **Server**: Provides the requested services or resources.
The client and server communicate over a network, often using protocols like HTTP.

---

### **Difference Between an Object and Its Handle**

- **Object**: The actual instance residing in memory.
- **Handle**: The reference (variable) that points to the memory location of the object.

**Example:**

```java
String handle = "Hello"; // "Hello" is the object; handle is the reference.

```

---

### **Default Values for Class Members vs. Local Variables**

- **Class Members (Fields)**:
    - Automatically initialized (e.g., `0` for integers, `null` for objects).
- **Local Variables**:
    - Must be explicitly initialized before use.

---

### **Purpose of the `import` Keyword**

The `import` keyword allows the inclusion of other packages or classes, enabling their use in the current file without fully qualifying their names.

**Example:**

```java

import java.util.Scanner; // Import Scanner class

```

---

### **Explanation of `public static void main(String[] args)`**

1. **`public`**: Accessible from anywhere.
2. **`static`**: Belongs to the class, not an instance.
3. **`void`**: Does not return a value.
4. **`main`**: Entry point for the JVM.
5. **`String[] args`**: Accepts command-line arguments.

---

### **Relational Operators**

Used to compare two values:

- `==`, `!=`, `<`, `>`, `<=`, `>=`.

---

### **Bitwise Operators**

Operate on bits:

- `&` (AND), `|` (OR), `^` (XOR), `~` (Complement).

---

### **Shift Operators**

Shift bits left or right:

- `<<` (Left shift), `>>` (Right shift), `>>>` (Unsigned right shift).

---

### **Ternary `if-else` Operator**

Syntax:

```java
condition ? expression1 : expression2;

```

Example:

```java
int x = (5 > 3) ? 10 : 20; // x = 10

```

---

### **Explicit Casting in Java**

Used to convert a value from one type to another.

**Example:**

```java
double d = 9.7;
int i = (int) d; // Explicit casting

```

---

### **Difference Between `while` and `do-while`**

- **`while`**: The condition is checked before executing the loop body.
- **`do-while`**: Executes the loop body at least once before checking the condition.

---

### **Use of `break` in `switch`**

The `break` statement exits the `switch` block, preventing fall-through.

**Example:**

```java
switch (x) {
    case 1:
        System.out.println("One");
        break;
    case 2:
        System.out.println("Two");
        break;
}

```

---