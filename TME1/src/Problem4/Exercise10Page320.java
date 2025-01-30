/*
Title: Problem4
Description: Program that demonstrates polymorphism with the 
Instrument, Playable, Wind, Percussion, Stringed, Brass, and Woodwind classes.
Author: Boris B
Date: Nov 17 2024
Copyright: Boris B 2024

DOCUMENTATION:
Program Purpose:
Modify Music5.java by adding a Playable interface.
Move the play() declaration from Instrument to Playable. Add Playable
to the derived classes by including it in the implements list.
Change tune()so that it takes a Playable instead of an Instrument.

Compile: javac -d bin src/Assignment1/Problem4/*.java
Run: java -cp bin Assignment1.Problem4.Music5

Classes:
    Music5 - Main class that demonstrates polymorphism with the Instrument, Playable, Wind, Percussion, Stringed, Brass, and Woodwind classes.
    Note - Enum that represents musical notes.
    Playable - Interface that represents a playable object.
    Instrument - Interface that represents an instrument.
    Wind - Class that represents a wind instrument.
    Percussion - Class that represents a percussion instrument.
    Stringed - Class that represents a stringed instrument.
    Brass - Class that represents a brass instrument.
    Woodwind - Class that represents a woodwind instrument.

Variables:
    orchestra - Array of Playable objects that contains a Wind, Percussion, Stringed, Brass, and Woodwind.

Functions:
    tune - Takes a Playable object and plays a note.
    tuneAll - Takes an array of Playable objects and plays a note for each object.
    main - Entry point for the program that demonstrates polymorphism with the Instrument, Playable, Wind, Percussion, Stringed, Brass, and Woodwind classes.

TEST PLAN:
Normal case:
    The program demonstrates polymorphism with the Instrument, Playable, Wind, Percussion, Stringed, Brass, and Woodwind classes.
    The program creates an array of Playable objects that contains a Wind, Percussion, Stringed, Brass, and Woodwind.
    The program calls tuneAll with the array of Playable objects.
    The program prints the output of each Playable object playing a note.

Expected output:
Wind.play()MIDDLE_C
Percussion.play()MIDDLE_C
Stringed.play()MIDDLE_C
Brass.play()MIDDLE_C
Woodwind.play()MIDDLE_C

*/

package Problem4;

enum Note {
    MIDDLE_C, C_SHARP, B_FLAT;
}

interface Playable {
    void play(Note n);
}

interface Instrument {
    void adjust();
}

class Wind implements Instrument, Playable {
    public void play(Note n) {
        System.out.println(this + ".play()" + n);
    }

    public String toString() {
        return "Wind";
    }

    public void adjust() {
        System.out.println(this + " .adjust()");
    }
}

class Percussion implements Instrument, Playable {
    public void play(Note n) {
        System.out.println(this + ".play()" + n);
    }

    public String toString() {
        return "Percussion";
    }

    public void adjust() {
        System.out.println(this + " .adjust()");
    }
}

class Stringed implements Instrument, Playable {
    public void play(Note n) {
        System.out.println(this + ".play()" + n);
    }

    public String toString() {
        return "Stringed";
    }

    public void adjust() {
        System.out.println(this + ".adjust()");
    }
}

class Brass extends Wind {
    public String toString() {
        return "Brass";
    }
}

class Woodwind extends Wind {
    public String toString() {
        return "Woodwind";
    }
}

public class Exercise10Page320 {
    static void tune(Playable p) {

        p.play(Note.MIDDLE_C);
    }

    static void tuneAll(Playable[] e) {
        for (Playable p : e)
            tune(p);
    }

    public static void main(String[] args) {
        Playable[] orchestra = {new Wind(), new Percussion(), new Stringed(),
                new Brass(), new Woodwind()};
        tuneAll(orchestra);
    }
}