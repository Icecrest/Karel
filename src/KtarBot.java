    /**
 * Created by SCurley3465 on 2/20/2015.
 */

import kareltherobot.Robot;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KtarBot extends Robot{

    private final String filepath = "Z://Karel/record.txt";
    private final ArrayList<Integer> numbers = new ArrayList<Integer>();
    private final ArrayList<String> rec = new ArrayList<String>();
    private boolean record;
    private boolean running = false;

    public KtarBot (int st, int av, Direction dir, int beeps, Color badge){
        //constructor
        super(st, av, dir, beeps, badge);
    }

    void pickUpLine(){
        faceDirection("north");
        int n = 0;
        while(nextToABeeper()){
            pickBeeper();
            n++;
            move();
        }
        numbers.add(n);
        faceDirection("south");
        walk();
    }

    void pickUpAllLines(){
        while(nextToABeeper()){
            pickUpLine();
            faceDirection("east");
            move();
        }
    }

    void placeSortedBeepers(){
        goToOrigin();
        for (Integer number : numbers) {
            faceDirection("north");
            for (int x = 0; x < number; x++) {
                putBeeper();
                move();
            }
            faceDirection("south");
            walk();
            faceDirection("east");
            move();
        }
    }

    public void sortWorld(){
        pickUpAllLines();
        Collections.sort(numbers);
        placeSortedBeepers();
    }

    void walk(){
        while(frontIsClear()){
            move();
        }
    }

    void moveX(int num){
        for(int n = 0 ; n < num ; n++){
            move();
        }
    }

    boolean wallOnRight(){
        turnRight();

        if(frontIsClear()){
            turnLeft();
            return false;
        }else if(!frontIsClear()){
            turnLeft();
            return true;
        }
        return false;
    }

    void walkAlongWall(){
        if(wallOnRight() && !frontIsClear()){
            while(wallOnRight() && !frontIsClear()){
                turnLeft();
            }
        }else if(wallOnRight()){
            move();
        }else if(!wallOnRight()){
            turnRight();
            move();
        }
    }

    public void navigateToBeeper2(KtarBot bot1, KtarBot bot2){
        while(!nextToABeeper()){
            bot1.walkAlongWall();
            bot2.walkAlongWall();
            walkAlongWall();
        }
    }

    public void navigateToBeeper(){
        while(!nextToABeeper()){
            walkAlongWall();
        }
    }

    void faceOpposite(){
        if(facingNorth()){
            faceDirection("south");
        }else if(facingSouth()){
            faceDirection("north");
        }
    }

    public void evenOrOdd(){
        faceDirection("south");
        //north = odd south = even
        while(nextToABeeper()){
            pickBeeper();
            faceOpposite();
        }
        while(anyBeepersInBeeperBag()){
            putBeeper();
        }
        if(facingNorth()){
            faceDirection("east");
            move();
        }else if(facingSouth()){
            faceDirection("west");
            move();
        }

    }

    void goToOrigin(){
        faceDirection("west");
        while(frontIsClear()){
            move();
        }
        faceDirection("south");
        while(frontIsClear()){
            move();
        }
        faceDirection("east");
    }

    void arrayToFile(){
        String combine = "";
        for (String aRec : rec) {
            combine = combine + aRec;
        }
        //combine = combine + "\n";
        writeToFile(combine);
    }

    void writeToFile(String chars){
        try {
            File file = new File(filepath);
            // if file doesnt exist, then create it
            if (!file.exists()) {
                boolean blnCreated = false;
                try{
                    blnCreated = file.createNewFile();
                }catch(IOException ioe){
                    System.out.println("Error while creating a new empty file: " + ioe);
                }
                if(blnCreated){
                    System.out.println("File: " + file.getName() + " was created at " + file.getPath() + " succesfully");
                }
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(chars);
            bw.close();

            System.out.println(chars + " has been recorded");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void fileToArray(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            if((line = reader != null ? reader.readLine() : null) != null){
                System.out.println("The following commands will be executed: " + line);
                System.out.println(String.valueOf(line.length()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rec.clear();
        for(int n = 0 ; n < (line != null ? line.length() : 0); n++ ){
            if(line.charAt(n) != 'q'){
                rec.add(String.valueOf(line.charAt(n)));
            }else if(line.charAt(n) == 'q'){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void record(){
        this.record = true;

        File file = new File(filepath);
        if(file.exists()){
            try{
                if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
                }else{
                    System.out.println("Delete operation is failed.");
                }
            }catch(Exception e){
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }else{
            System.out.println("No text file found, no need to delete");
        }

        moveing();

        //if(r == true){
        //	rec.clear();
        //}

    }

    public void play(){

        System.out.println();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            int n = 0;
            @Override
            public void run() {

                if(n == 0){
                    fileToArray();
                    running = true;
                }

                if(!record){
                    if(n < rec.size()){
                        //System.out.println(rec.toString());
                        movin(rec.get(n).charAt(0));
                        n++;
                    }else if (!(n < rec.size())){
                        turnOff();
                        cancel();
                        running = false;
                    }

                }
            }
        }, 500, 10);
    }

    void moveing(){
        Scanner key = new Scanner(System.in);
        char move = key.next().charAt(0);
        if(move == 'q'){
            movin(move);
            key.close();
            if(record){
                rec.add(String.valueOf(move));
                record = false;
                arrayToFile();
            }
        }
        else if(record){
            rec.add(String.valueOf(move));
            movin(move);
        }else {
            movin(move);
        }

    }

    void putBeepersInLine(int x){
        for(int n = 0 ; n < x ; n++){
            putBeeper();
            move();
        }
    }

    /**
     * @param number up to the maximum value an integer value can carry
     * @return Builds a number withing the world up.
     */
    public void placeNumbers(int number){

        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (number > 0) {
            stack.push( number % 10 );
            number = number / 10;
        }

        while (!stack.isEmpty()) {
            placeNumber(stack.pop());
        }
    }

    void placeNumber(int num){
        switch(num){
            case 0:
                faceDirection("north");
                move();
                putBeepersInLine(4);
                faceDirection("east");
                move();
                putBeepersInLine(2);
                faceDirection("south");
                move();
                putBeepersInLine(4);
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("east");
                moveX(5);
                break;
            case 1:
                faceDirection("north");
                putBeepersInLine(6);
                faceDirection("south");
                walk();
                faceDirection("east");
                moveX(2);
                break;
            case 2:
                faceDirection("east");
                putBeepersInLine(4);
                faceDirection("west");
                moveX(3);
                faceDirection("north");
                move();
                putBeeper();
                move();
                faceDirection("east");
                move();
                putBeeper();
                move();
                faceDirection("north");
                move();
                putBeepersInLine(2);
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("south");
                move();
                putBeeper();
                walk();
                faceDirection("east");
                moveX(5);
                break;
            case 3:
                faceDirection("east");
                putBeepersInLine(3);
                faceDirection("north");
                move();
                putBeepersInLine(2);
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("north");
                moveX(2);
                faceDirection("east");
                putBeepersInLine(3);
                faceDirection("south");
                move();
                putBeeper();
                walk();
                faceDirection("east");
                moveX(2);
                break;
            case 4:
                faceDirection("north");
                moveX(3);
                putBeepersInLine(3);
                faceDirection("south");
                moveX(3);
                faceDirection("east");
                move();
                putBeepersInLine(2);
                faceDirection("north");
                moveX(2);
                faceDirection("south");
                putBeepersInLine(5);
                putBeeper();
                faceDirection("east");
                moveX(2);
                break;
            case 5:
                putBeepersInLine(3);
                faceDirection("north");
                move();
                putBeepersInLine(2);
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("north");
                putBeepersInLine(2);
                faceDirection("east");
                putBeepersInLine(4);
                faceDirection("south");
                walk();
                faceDirection("east");
                move();
                break;
            case 6:
                faceDirection("east");
                move();
                putBeepersInLine(2);
                faceDirection("north");
                move();
                putBeeper();
                move();
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("south");
                move();
                faceDirection("north");
                putBeepersInLine(4);
                faceDirection("east");
                move();
                putBeepersInLine(2);
                faceDirection("south");
                walk();
                faceDirection("east");
                moveX(2);
                break;
            case 7:
                faceDirection("north");
                moveX(5);
                faceDirection("east");
                putBeepersInLine(3);
                faceDirection("south");
                putBeepersInLine(2);
                faceDirection("west");
                move();
                faceDirection("south");
                putBeepersInLine(3);
                putBeeper();
                faceDirection("east");
                moveX(3);
                break;
            case 8:
                faceDirection("north");
                move();
                putBeepersInLine(2);
                move();
                putBeepersInLine(1);
                faceDirection("east");
                move();
                putBeepersInLine(2);
                faceDirection("south");
                move();
                putBeepersInLine(1);
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("east");
                moveX(3);
                faceDirection("south");
                move();
                putBeepersInLine(2);
                faceDirection("west");
                move();
                putBeepersInLine(2);
                faceDirection("east");
                moveX(5);
                break;
            case 9:
                faceDirection("east");
                moveX(2);
                faceDirection("north");
                moveX(3);
                faceDirection("west");
                putBeepersInLine(2);
                faceDirection("north");
                putBeepersInLine(2);
                faceDirection("east");
                putBeepersInLine(3);
                faceDirection("south");
                putBeepersInLine(5);
                putBeeper();
                faceDirection("east");
                moveX(2);
                break;
        }
    }

    private void movin(char move){
        switch(move){
            case 'w':
                faceDirection("north");
                move();
                if(!running){
                    moveing();
                }
                break;
            case 'a':
                faceDirection("west");
                move();
                if(!running){
                    moveing();
                }
                break;
            case 's':
                faceDirection("south");
                move();
                if(!running){
                    moveing();
                }
                break;
            case 'd':
                faceDirection("east");
                move();
                if(!running){
                    moveing();
                }
                break;
            case 'q':
                turnOff();
                break;
            case 'e':
                if(anyBeepersInBeeperBag()){
                    putBeeper();
                }
                if(!running){
                    moveing();
                }
                break;
            case 'r':
                pickBeeper();
                if(!running){
                    moveing();
                }
                break;
        }

    }

    public void backStep(int x){
        turnLeft();
        turnLeft();
        for(int n = 0 ; n < x ; n++){
            move();
        }
        turnLeft();
        turnLeft();
    }

    void faceDirection(String n){
        if(n.equalsIgnoreCase("north")){
            while(!facingNorth()){
                turnLeft();
            }
        }else if(n.equalsIgnoreCase("east")){
            while(!facingEast()){
                turnLeft();
            }
        }else if(n.equalsIgnoreCase("south")){
            while(!facingSouth()){
                turnLeft();
            }
        }else if(n.equalsIgnoreCase("west")){
            while(!facingWest()){
                turnLeft();
            }
        }
    }

    void turnRight(){
        turnLeft();turnLeft();turnLeft();
    }

    public void placeTriangle(String endDirection){
        putBeeper();
        faceDirection("south");
        move();
        faceDirection("east");
        move();
        putBeeper();
        move();
        faceDirection("north");
        move();
        putBeeper();
        faceDirection(endDirection);
    }

    void climbStairs(){
        if(!frontIsClear()){
            turnLeft();
            move();
            if(nextToABeeper()){pickBeeper();}
            turnRight();
            move();
            if(nextToABeeper()){pickBeeper();}

            if(frontIsClear()){
                move();
                turnRight();
                if(frontIsClear()){
                    while(frontIsClear()){
                        move();
                    }
                }
            }else{
                climbStairs();
            }
        }
    }

    // more commands here ...

} // end of the blue print