import kareltherobot.*;
import kareltherobot.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    public void swergBots(ArrayList<RemoteBot> bots){
        int i = 1;
        int inc = 300;
        while(i == 1){
            World.setDelay(1);
            for(RemoteBot r : bots){
                turn(Input.AROUND);
            }
        }
    }

    /**
     * Makes a robot move randomly.
     */
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
     * Makes the Robot go insane for a time.
     * @Warning VERY LAGGY!
     */
    public void swerg() {
        World.setDelay(1);
        Random rand = new Random();
        JOptionPane.showMessageDialog(new JDialog(), "Press OK to swerg.",
                "SWERG IN EXECUTION", JOptionPane.PLAIN_MESSAGE);
        ArrayList<RemoteBot> bots = new ArrayList<RemoteBot>();
        bots.add(new RemoteBot(rand.nextInt(World.worldCanvas().getHeight()-1),
                rand.nextInt(World.worldCanvas().getWidth()-1), North, 10, Color.BLACK));
        bots.add(new RemoteBot(rand.nextInt(World.worldCanvas().getHeight() - 1),
                rand.nextInt(World.worldCanvas().getWidth() - 1), North, 10, Color.CYAN));
        bots.add(new RemoteBot(rand.nextInt(World.worldCanvas().getHeight()-1),
                rand.nextInt(World.worldCanvas().getWidth()-1), North, 10, Color.YELLOW));
        bots.add(new RemoteBot(rand.nextInt(World.worldCanvas().getHeight()-1),
                rand.nextInt(World.worldCanvas().getWidth()-1), North, 10, Color.ORANGE));
        bots.add(new RemoteBot(rand.nextInt(World.worldCanvas().getHeight()-1),
                rand.nextInt(World.worldCanvas().getWidth()-1), North, 10, Color.BLUE));
        bots.add(new RemoteBot(rand.nextInt(World.worldCanvas().getHeight()-1),
                rand.nextInt(World.worldCanvas().getWidth()-1), North, 10, Color.WHITE));
        swergBots(bots);
        for (int i = 0; i < 1337; i++) {
            World.setWorldColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            World.setNeutroniumColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            World.setBeeperColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            turn(Input.AROUND);
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
            }
        }
        k.close();
    }
}
