import kareltherobot.World;
import kareltherobot.WorldBuilder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventHandler;
import java.util.EventListener;

import static java.awt.event.KeyEvent.*;

/**
 * Written by SCurley on 9/6/2016.
 */
public class ControllerBot extends AdvancedBot{
    public class ControlListener implements KeyListener {

        public ControlListener(){}

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            switch(e.getKeyCode()){
                case VK_W | VK_UP:
                    if (facingNorth()) {
                        move();
                    } else if (facingEast()) {
                        turn(Input.LEFT);
                    } else if (facingSouth()) {
                        faceOpposite();
                    } else if (facingWest()) {
                        turn(Input.RIGHT);
                    }
                    break;
                case VK_A | VK_LEFT:
                    if (facingNorth()) {
                        turn(Input.LEFT);
                    } else if (facingEast()) {
                        faceOpposite();
                    } else if (facingSouth()) {
                        turn(Input.RIGHT);
                    } else if (facingWest()) {
                        move();
                    }
                    break;
                case VK_S | VK_DOWN:
                    if (facingNorth()) {
                        faceOpposite();
                    } else if (facingEast()) {
                        turn(Input.RIGHT);
                    } else if (facingSouth()) {
                        move();
                    } else if (facingWest()) {
                        turn(Input.LEFT);
                    }
                    break;
                case VK_D | VK_RIGHT:
                    if (facingNorth()) {
                        turn(Input.RIGHT);
                    } else if (facingEast()) {
                        move();
                    } else if (facingSouth()) {
                        turn(Input.LEFT);
                    } else if (facingWest()) {
                        faceOpposite();
                    }
                    break;
                case VK_Z | VK_K:
                    pickBeeper();
                    break;
                case VK_X | VK_L:
                    putBeeper();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private boolean run = true;


    public ControllerBot(int av, int st, Direction dir, int beepers, Color badge){
        super(av, st, dir, beepers, badge);
    }

    public void remoteMove(KeyEvent e){
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
