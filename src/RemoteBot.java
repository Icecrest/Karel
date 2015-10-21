import kareltherobot.*;
import kareltherobot.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by SCurley3465 on 2/17/2015.
 */
public class RemoteBot extends AdvancedBot {

    public RemoteBot(int street, int avenue, Direction direction, int beepers, Color badge) {
        super(street, avenue, direction, beepers, badge);

    }

    /**
     * Makes a robot move in a line back and forth.
     */
    public void runLine() {
        if (frontIsClear() == true) {
            move();
        } else turn(Input.AROUND);
    }

    /**
     * Makes robots turn around.  Used for loops.
     * @param c collection of Robots
     */
    public void turnAround(RemoteBot[] c){
        for(RemoteBot r : c){
            r.turn(Input.AROUND);
        }
    }

    /**
     * @param robots
     * Makes all robots in a list move around randomly
     */

    public void runAround(RemoteBot[] robots){
        for(RemoteBot r : robots){
            runAround(r);
        }
    }

    /**
     * @param robot
     * Makes a robot move randomly.
     */
    public void runAround(RemoteBot robot) {
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
     * Makes the Robot go insane for a time.
     * @Warning VERY LAGGY!  Spawns in robots at random locations only within 10 spaces.
     */
    public void swerg() {
        World.setDelay(1);
        Random rand = new Random();
        JOptionPane.showMessageDialog(new JDialog(), "Press OK to swerg.",
                "SWERG IN EXECUTION", JOptionPane.PLAIN_MESSAGE);
        RemoteBot a = new RemoteBot(rand.nextInt(10), rand.nextInt(10), North, Integer.MAX_VALUE, Color.ORANGE);
        RemoteBot b = new RemoteBot(rand.nextInt(10), rand.nextInt(10), North, Integer.MAX_VALUE, Color.RED);
        RemoteBot c = new RemoteBot(rand.nextInt(10), rand.nextInt(10), North, Integer.MAX_VALUE, Color.BLACK);
        RemoteBot d = new RemoteBot(rand.nextInt(10), rand.nextInt(10), North, Integer.MAX_VALUE, Color.CYAN);
        RemoteBot e = new RemoteBot(rand.nextInt(10), rand.nextInt(10), North, Integer.MAX_VALUE, Color.YELLOW);
        RemoteBot f = new RemoteBot(rand.nextInt(10), rand.nextInt(10), North, Integer.MAX_VALUE, Color.PINK);
        RemoteBot[] bots = {a, b, c, d, e, f};
        for (int i = 0; i < 1337; i++) {
            World.setWorldColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            World.setNeutroniumColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            World.setBeeperColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            turn(Input.AROUND);
            runAround(bots);

        }
    }

    /**
     * Makes a Robot move around and interact with a world based on console input.
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
                swerg();
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
            } else if(next.equalsIgnoreCase("Record")){
                record();
            } else if(next.equalsIgnoreCase("play")){
                play();
            } else if(next.equalsIgnoreCase("run")){
                runAround(this);
            }
        }
        k.close();
    }
}
