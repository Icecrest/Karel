import kareltherobot.Directions;
import kareltherobot.World;

import java.awt.*;
import java.util.Random;

public class Runner implements Directions {
        public static void main(String args[]) {
            Random rand = new Random();
            RemoteBot Newton = new RemoteBot(1, 1, East, Integer.MAX_VALUE, Color.BLUE);
            Newton.Run();
            Newton.turnOff();
        }

        static {
            World.reset();
            World.readWorld("Z:\\Karel\\Worlds\\blockSwerg");
            World.setDelay(1);
            World.setVisible(true);
        }
    }
