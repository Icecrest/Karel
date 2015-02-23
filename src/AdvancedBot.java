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

    public void toOrigin(){

        //while(UrRobot.areYouHere == (0,0))
        if(facingEast()) turn(Input.AROUND);
        else if(facingNorth()) turn(Input.LEFT);
        else if(facingSouth()) turn(Input.RIGHT);
        if(facingWest()) move();
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


    public void buildNum(String num) {
        for (char c : num.toCharArray()) {
            switch (c) {
                case '0':
                    putBeeper(3);
                    turnLeft();
                    putBeeper(5);
                    turnLeft();
                    putBeeper(3);
                    turnLeft();
                    putBeeper(5);
                    break;
                case '1':
                    putBeeper(3);turn(Input.AROUND);move();turn(Input.RIGHT);putBeeper(5);turn(Input.LEFT);move();
                    turn(Input.LEFT);move();putBeeper();
                    break;
            }
        }
    }
}
