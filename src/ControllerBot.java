import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.EventHandler;

/**
 * Written by SCurley on 9/6/2016.
 */
public class ControllerBot extends AdvancedBot{
    private boolean run = true;

    public ControllerBot(int av, int st, Direction dir, int beepers, Color badge){
        super(av, st, dir, beepers, badge);
    }

    public void remoteMove(KeyEvent e){
       while(run == true) {
           if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
               if (facingNorth()) {
                   move();
               } else if (facingEast()) {
                   turn(Input.LEFT);
               } else if (facingSouth()) {
                   faceOpposite();
               } else if (facingWest()) {
                   turn(Input.RIGHT);
               }
           } else if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
               if (facingNorth()) {
                   turn(Input.RIGHT);
               } else if (facingEast()) {
                   move();
               } else if (facingSouth()) {
                   turn(Input.LEFT);
               } else if (facingWest()) {
                   faceOpposite();
               }
           } else if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
               if (facingNorth()) {
                   faceOpposite();
               } else if (facingEast()) {
                   turn(Input.RIGHT);
               } else if (facingSouth()) {
                   move();
               } else if (facingWest()) {
                   turn(Input.LEFT);
               }
           } else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
               if (facingNorth()) {
                   turn(Input.LEFT);
               } else if (facingEast()) {
                   faceOpposite();
               } else if (facingSouth()) {
                   turn(Input.RIGHT);
               } else if (facingWest()) {
                   move();
               }
           } else if (e.getKeyChar() == 'z' || e.getKeyChar() == 'k') {
               putBeeper(1);
           } else if (e.getKeyChar() == 'x' || e.getKeyChar() == 'l') {
               pickBeeper();
           }
       }

    }

}
