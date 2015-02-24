import kareltherobot.Robot;
import kareltherobot.UrRobot;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by SCurley3465 on 2/17/2015.
 */
public class AdvancedBot extends KtarBot {

    public AdvancedBot(int street, int avenue, Direction direction, int beepers, Color badge) {
        super(street, avenue, direction, beepers, badge);
    }

    public void putBeeper(int num) {
        for (int i = 0; i < num; i++) {
            if (!nextToABeeper()) {
                putBeeper();
            }move();
        }
        turn(Input.AROUND);
        move();
        turn(Input.AROUND);
    }

    public void turn(Input d) {
        if (d == Input.RIGHT) {
            for (int n = 0; n < 3; n++) {
                turnLeft();
            }
        } else if (d == Input.LEFT) {
            turnLeft();
        } else if (d == Input.AROUND) {
            turnLeft();
            turnLeft();
        } else {
            System.out.println("Invalid Command");
        }
    }
}
