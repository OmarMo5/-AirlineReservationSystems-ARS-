package javaapplication3;

import java.util.HashMap;
import java.util.List;
import javax.swing.JTextArea;

public interface FlightBuffer {

    public List<Object> seeAvailableSeats(String name) throws InterruptedException;
    public Object[] makeReservation(String name, int seatNo, long customerId) throws InterruptedException;
    public void assignClients (Client c , JTextArea s);
    public int seatCount();
}
