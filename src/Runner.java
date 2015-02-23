import kareltherobot.Directions;
import kareltherobot.World;

import java.awt.*;

    public class Runner implements Directions {
        public static void main(String args[]) {
            RemoteBot Newton = new RemoteBot(1, 1, East, Integer.MAX_VALUE, Color.RED);
            Newton.Run();
        }

        static {
            World.reset();
            World.readWorld("Z:\\Karel\\Worlds\\blockSwerg");
            World.setDelay(20);
            World.setVisible(true);
        }
    }
