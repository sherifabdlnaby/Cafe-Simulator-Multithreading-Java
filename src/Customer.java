import java.awt.*;
import java.util.Random;

public class Customer extends Thread {
    public String Name;
    public Table Table;
    public Restaurant Restaurant;
    private Random rand = new Random();
    public Customer(String name, Restaurant restaurant) {
        Name = name;
        Restaurant = restaurant;
    }

    private void waitRandomTime(){
        int x = rand.nextInt(2001);
        try {
            sleep(500 + x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        //Enter The Restaurant
        Restaurant.enterRestaurant(this);
        System.out.println(Name + " Entered the restaurant.");

        //Try to Occupy Table
        try {
            synchronized (this){

                while(!Restaurant.occupyTable(this)) {
                    System.out.println(Name + " is waiting");
                    wait();     //Will be notified when someone Leaves a Table.
                }
                // While Loop for Sanity Checking and Synchronization
                // (however it shouldn't be notified unless there is a free table)
            }
            System.out.println(Name + " sat-down on Table #" + Table.ID);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //UI Elements Manipulation Variables
        SimulationBoard.ChessButton Button;
        int TableID = Table.ID;
        Button = (SimulationBoard.ChessButton) Restaurant.SimulationBoard.getComponent(TableID);

        //GUI-Update
        Button.setBackground(Color.BLUE);
        Button.setText(TableID + " : " + Name + " sat-down");

        //Order Food
        waitRandomTime();
        //GUI-Update
        Button.setText(TableID + " : " + Name + " Ordered Food.");
        Button.setBackground(Color.yellow);

        //Eat Food
        waitRandomTime();
        //GUI-Update
        Button.setText(TableID + " : " + Name + " Eating.");
        Button.setBackground(Color.green);

        //Leaving
        waitRandomTime();
        //GUI-Update
        Button.setText(TableID + " : " + Name + " Leaving.");
        Button.setBackground(Color.red);

        //Leave
        waitRandomTime();
        Restaurant.leaveTable(this);
        //GUI-Update
        System.out.println(Name + " Left the Restaurant");
        Button.setText(String.valueOf(TableID));
        Button.setBackground(Color.GRAY);
    }
}
