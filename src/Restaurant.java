import java.util.LinkedList;
import java.util.Queue;

public class Restaurant {
    public String Name;
    public Queue<Customer> CustomersQueue;
    public Queue<Table> TablesQueue;
    //GUI-Simulation Board
    public SimulationBoard SimulationBoard;

    public Restaurant(int size, String name, SimulationBoard simulationBoard) {
        Name = name;
        SimulationBoard = simulationBoard;
        TablesQueue = new LinkedList<>();
        CustomersQueue = new LinkedList<>();
        for (int id = 0; id < size; id++) {
            Table newTable = new Table(id);
            TablesQueue.add(newTable);
        }
    }

    public synchronized boolean enterRestaurant(Customer customer){
        CustomersQueue.add(customer);
        return true;
    }

    public synchronized boolean occupyTable(Customer customer){
        if(!tableAvailable())
            return false;

        //TODO (Remove Assert after testing)
        assert customer == CustomersQueue.peek();

        //Pull customer from Queue
        CustomersQueue.poll();

        //Pull Table from Queue and Link with customer
        Table table = TablesQueue.poll();
        customer.Table = table;
        return true;
    }

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
    }

    public boolean tableAvailable(){
        return TablesQueue.size() > 0 && TablesQueue.peek() != null;    //in a test Queue had size but null elements.
    }

    public void AddCustomersToSim(String [] Names){
        for (int i = 0; i < Names.length; i++) {
            (new Customer(Names[i], this)).start();     //Create customers and start their threads.
        }
    }

}
