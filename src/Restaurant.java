import java.awt.*;
import java.util.*;
import java.util.List;

public class Restaurant {
    public int Size = 0;
    public String Name;
    public HashMap<Integer, Table> Tables;
    public Queue<Customer> CustomersQueue;
    public Queue<Table> TablesQueue;
    public Dashboard Dashboard;

    public Restaurant(int size, String name, Dashboard dashboard) {
        Size = size;
        Name = name;
        Dashboard = dashboard;
        Tables = new HashMap<>();
        TablesQueue = new LinkedList<>();
        CustomersQueue = new LinkedList<>();
        for (int id = 0; id < size; id++) {
            Table newTable = new Table(id);
            Tables.put(id,newTable);
            TablesQueue.add(newTable);
        }
    }

    public synchronized boolean enterRestaurant(Customer customer){
        CustomersQueue.add(customer);
        return true;
    };

    public synchronized boolean occupyTable(Customer customer){
        if(!tableAvailable())
            return false;

        //TODO (Remove Assert after testing)
        assert customer == CustomersQueue.peek();

        //Pull customer from Queue
        CustomersQueue.poll();

        //Pull Table from Queue and Link
        Table table = TablesQueue.poll();
        customer.Table = table;


        //Color this Table
        Dashboard.ChessButton Button = (Dashboard.ChessButton) Dashboard.getComponent(table.ID);
        Button.setBackground(Color.blue);

        return true;
    };

    public synchronized boolean leaveTable(Customer customer){
        //Put Table back to queue.
        TablesQueue.add(customer.Table);



        customer.Table = null;

        //Call-in First Customer in the Queue
        Customer newCustomer = CustomersQueue.peek();
        if(newCustomer != null){
            synchronized (newCustomer)  //TODO check Intellij warning.
            {
                newCustomer.notify();
            }
        }
        return true;
    };

    public boolean tableAvailable(){
        return TablesQueue.size() > 0;
    }

    public void AddCustomersToSim(String [] Names){
        for (int i = 0; i < Names.length; i++) {
            (new Customer(Names[i], this)).start();
        }
    }

}
