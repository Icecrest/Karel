import kareltherobot.Directions;
import kareltherobot.World;

import java.awt.*;
import java.util.Random;

public class Runner implements Directions {
        public static void main(String args[]) {
            Random rand = new Random();
            ControllerBot Darwin = new ControllerBot(1, 1, East, Integer.MAX_VALUE, Color.BLUE);
            Darwin.ControlListener e = new Darwin.ControlListener();
            World.worldCanvas().addKeyListener(e);
        }

        static {
            World.reset();
            World.readWorld("Z:\\Karel\\Worlds\\blockSwerg");
            World.setDelay(1);
            World.setVisible(true);
        }
    }
