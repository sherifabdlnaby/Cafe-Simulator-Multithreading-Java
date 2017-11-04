import java.awt.*;
import java.util.Random;

public class Customer extends Thread {
    public String Name;
    public Table Table;
    public Restaurant Restaurant;
    public Customer(String name, Restaurant restaurant) {
        Name = name;
        Restaurant = restaurant;
    }
    private static int randomTime(){
        Random rand = new Random();
        int x = rand.nextInt(2001);
        return 500 + x;
    }
    public void run(){
        //Enter The Restaurant
        Restaurant.enterRestaurant(this);

        //Try to Occupy Table
        if (Restaurant.occupyTable(this)) {
            System.out.println(Name + " Entered the restaurant and sat-down on Table #" + Table.ID);
        }
        else {
            //Wait Turn
            System.out.println(Name + " Entered the restaurant and waiting");
            try {
                synchronized (this){
                    //Will be notified when someone Leaves a Table.
                    do
                        wait();
                    while(!Restaurant.occupyTable(this));
                    // While Loop for Sanity Check(however it shouldn't be notified unless there is a free table)
                }
                System.out.println(Name + " sat-down on Table #" + Table.ID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Dashboard.ChessButton Button;
        int TableID = Table.ID;
        Button = (Dashboard.ChessButton) Restaurant.Dashboard.getComponent(TableID);
        Button.setBackground(Color.BLUE);
        Button.setText(TableID + " : " + Name + " sat-down");

        //Order Food
        try {
            sleep(randomTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button.setText(TableID + " : " + Name + " Ordered Food.");
        Button.setBackground(Color.yellow);

        //Eat Food
        try {
            sleep(randomTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button.setText(TableID + " : " + Name + " Eating.");
        Button.setBackground(Color.green);

        //Leaving
        try {
            sleep(randomTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button.setText(TableID + " : " + Name + " Leaving.");
        Button.setBackground(Color.red);

        //Leave
        try {
            sleep(randomTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Restaurant.leaveTable(this);
        Button.setText(String.valueOf(TableID));
        Button.setBackground(Color.GRAY);

        System.out.println(Name + " Left the Restaurant");

    }
}
