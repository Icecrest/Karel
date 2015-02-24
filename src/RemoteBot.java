import kareltherobot.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by SCurley3465 on 2/17/2015.
 */
public class RemoteBot extends AdvancedBot {

    public RemoteBot(int street, int avenue, Direction direction, int beepers, Color badge) {
        super(street, avenue, direction, beepers, badge);

    }

    public void runLine() {
        if (frontIsClear() == true) {
            move();
        } else turnLeft();
        turnLeft();
    }

    public void runAround() {
        if (nextToARobot()) {
            turnLeft();
            if (frontIsClear()) move();
        } else if (!frontIsClear()) {
            Random r = new Random();
            switch (r.nextInt(3)) {
                case 0:
                    break;
                case 1:
                    turn(Input.LEFT);
                    break;
                case 2:
                    turn(Input.RIGHT);
                    break;
                case 3:
                    turn(Input.AROUND);
                    break;
            }
            if (frontIsClear()) move();
        } else move();

        if (nextToABeeper()) {
            pickBeeper();
        } else if (!nextToABeeper() && anyBeepersInBeeperBag()) {
            putBeeper();
        }
    }

    /**
     * @return Runs a remote bot by reading strings being input to the console.
     */

    public void Run() {
        Scanner k = new Scanner(System.in);
        String next;
        cmd: while ((next = k.next()) != null) {
            if (next.equalsIgnoreCase("Right")) {
                turn(Input.RIGHT);
            } else if (next.equalsIgnoreCase("Left")) {
                turn(Input.LEFT);
            } else if (next.equalsIgnoreCase("Around")) {
                turnLeft();
                turnLeft();
            } else if (next.equalsIgnoreCase("Move")) {
                move();
            } else if (next.equalsIgnoreCase("Back")) {
                turnLeft();
                turnLeft();
                move();
                turnLeft();
                turnLeft();
            } else if (next.equalsIgnoreCase("SWERG")) {
                World.setDelay(1);
                Random rand = new Random();
                JOptionPane.showMessageDialog(new JDialog(), "Press OK to swerg.",
                        "SWERG IN EXECUTION", JOptionPane.PLAIN_MESSAGE);
                RemoteBot r = new RemoteBot(1, 1, North, 10, Color.BLACK);
                RemoteBot a = new RemoteBot(2, 2, North, 10, Color.CYAN);
                RemoteBot b = new RemoteBot(3, 3, North, 10, Color.YELLOW);
                RemoteBot c = new RemoteBot(4, 4, North, 10, Color.ORANGE);
                RemoteBot d = new RemoteBot(5, 5, North, 10, Color.BLUE);
                RemoteBot e = new RemoteBot(6, 6, North, 10, Color.WHITE);
                for (int i = 0; i < 1337; i++) {
                    World.setWorldColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                    World.setNeutroniumColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                    World.setBeeperColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                    runAround();
                    r.runAround();
                    a.runAround();
                    b.runAround();
                    c.runAround();
                    d.runAround();
                    e.runAround();
                }
            } else if (next.equalsIgnoreCase("Reset")) {
                World.reset();
            } else if (next.equalsIgnoreCase("Goodbye")) {
                break cmd;
            } else if (next.equalsIgnoreCase("Get")) {
                pickBeeper();
            } else if (next.equalsIgnoreCase("Put")) {
                putBeeper();
            } else if (next.equalsIgnoreCase("build")) {
                System.out.println("What number do you want to build?");
                int num = k.nextInt();
                placeNumbers(num);
            } else if(next.equalsIgnoreCase("Origin")){
                goToOrigin();
            } else if(next.equalsIgnoreCase("sort")){
                sortWorld();
            }
        }
        k.close();
    }
}
