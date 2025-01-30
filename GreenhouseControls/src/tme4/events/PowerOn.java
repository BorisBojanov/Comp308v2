// package tme4.events;
// import tme4.*;

// interface Fixable {
//     void fix();
//     void log();
// }

// public class PowerOn extends Event implements Fixable {
//     public PowerOn(GreenhouseControls greenhouse, long delayTime) {
//         super(greenhouse, delayTime);
//     }

//     @Override
//     public void action() {
//         greenhouse.setVariable("Power", "On");
//         System.out.println("⚡ Power restored.");
//     }

//         /**
//      * @param errorcode
//      * @return Fixable object to fix the error
//      */

//     public Fixable getFixable(int errorcode){
//         switch (errorcode) {
//             case 2:
//             return new PowerOn();
//             default:
//             return null;
//         }
//     }
// }