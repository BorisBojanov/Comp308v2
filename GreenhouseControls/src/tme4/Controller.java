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

import tme4.events.Bell;



public class Controller {
  // A class from java.util to hold Event objects:
  protected List<Event> eventList = new ArrayList<Event>();
  public void addEvent(Event c) { 
    // System.out.println("✅ Adding event: " + event);
    // eventThreads.put(event, new Thread(event));
    eventList.add(c); 
  }



  public void run() {
    System.out.println("✅ Controller started running events...");
    while(eventList.size() > 0) {
        for(Event e : new ArrayList<>(eventList)) {
            if(e.ready()) {
                System.out.println("🟢 Executing event: " + e);
                e.action();
                eventList.remove(e);
                System.out.println("✅ Event executed: " + e);
            }
        }
    }
}
} ///:~
