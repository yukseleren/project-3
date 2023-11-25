import java.util.ArrayList;
import java.util.List;

public class Flight implements Comparable<Flight> {
    public ATC head;
    public ATC tail;
    public String code;
    public ArrayList<Integer> operations;
    public Integer time;
    public boolean priority;

    public Flight(Integer time, String code, ATC head, ATC tail, ArrayList<Integer> operations) {
        this.head = head;
        this.tail = tail;
        this.code = code;
        this.operations = operations;
        this.time = time;
    }


    @Override
    public int compareTo(Flight flight) {
        if (this.time.equals(flight.time)) {
            if (this.priority && !(flight.priority)) {
                return -1;
            } else if (!(this.priority) && flight.priority) {
                return 1;
            } else return this.code.compareTo(flight.code);
        } else {
            return this.time.compareTo(flight.time);
        }

    }
}
