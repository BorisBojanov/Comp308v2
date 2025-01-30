//: innerclasses/controller/Controller.java
// The reusable framework for control systems.
// From 'Thinking in Java, 4th ed.' (c) Bruce Eckel 2005
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

/***********************************************************************
 * Adapated for COMP308 Java for Programmer, 
 *		SCIS, Athabasca University
 *
 * Assignment: TME3
 * @author: Steve Leung
 * @date  : Oct 21, 2006
 *
 */

package tme4;
import java.util.*;

import Event;


public class Controller {
  // A class from java.util to hold Event objects:
  protected List<Event> eventList = new ArrayList<Event>();
  public void addEvent(Event c) { eventList.add(c); }

  public void run() {
    while(eventList.size() > 0)
      // Make a copy so you're not modifying the list
      // while you're selecting the elements in it:
      for(Event e : new ArrayList<Event>(eventList))
        if(e.ready()) {
          try {
            System.out.println(e);
            e.action();
            eventList.remove(e); // remove event after "calling" it
          } catch(Exception ex) {
            System.err.println("Error during event execution: " + ex.getMessage());
          }

        }
  }
} ///:~
