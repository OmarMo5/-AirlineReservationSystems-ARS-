package javaapplication3;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.*;


public class Client extends JFrame implements Runnable
{	
    private JButton reserveBtn,avaliableBtn;
    private JLabel reserveLabel;
    private JTextField resrveInput;
    private  JTextArea log;

    private JScrollPane logScroll;
    private  SeatPlanPanel seatPlan;

    private static FlightBuffer BF;

    private ExecutorService pool; 

    public Client(FlightBuffer flight)
    {		
        log=new JTextArea();
        logScroll=new JScrollPane(log);
        BF = flight;
        BF.assignClients(this,log);
        JButtonInit();
        JTextAreaInit();
        seatPlanInit();
        JFrameInit();
    }

    private void JButtonInit()
    {
        /************** BtnRegisterSet **************/
        reserveLabel=new JLabel("Enter Seat Number");
        resrveInput=new JTextField();
        reserveBtn=new JButton("Reserve");
        avaliableBtn=new JButton("Avaliable Seats");
        reserveLabel.setBounds(390, 220, 200, 100);		
        resrveInput.setBounds(560, 245, 200, 50);
        reserveBtn.setBounds(790, 255, 100, 30);
        avaliableBtn.setBounds(910, 255, 170, 30);
        reserveLabel.setFont(new Font("Serif", Font.BOLD, 20)); 
        add(reserveLabel);
        add(resrveInput);
        add(reserveBtn);
        add(avaliableBtn);
    }

    private void seatPlanInit() {
        seatPlan = new SeatPlanPanel(BF.seatCount());
        seatPlan.setBounds(10, 10, 1180, 200);
        add(seatPlan);
    }

    private void JTextAreaInit()
    {
        /************ log ************/
        logScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logScroll.setBounds(10, 340, 1190, 360);
        log.setFont(new Font("Consolas", Font.PLAIN, 14));
        log.setBackground(Color.white);
        log.setForeground(Color.black);
        log.setEditable(false);
        add(logScroll);
    }

    private void JFrameInit()
    {
        setTitle("Airlane");
        setSize(1280, 765);
        setResizable(false);
        setLayout(null);
        setVisible(true);
    }

    public int getSetNumber() {
        String SetNumber = resrveInput.getText();
        boolean check=false;
        if (SetNumber.equals("")) {
            JOptionPane.showMessageDialog(null, "Pleaser Choose Number", "Message", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 1; i <25 ; i++) {
                if(Integer.parseInt(SetNumber)==i){
                    check=true;
                     return Integer.parseInt(SetNumber) ;
                }
            }

        }
        if (check == false && !(SetNumber.equals(""))) {
            JOptionPane.showMessageDialog(null, "Invalid Set Number", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;       
    }
   
    public  void  updateSeatPlan(int seatNo) {
        seatPlan.bookSeat(seatNo);
    }

    public void  updateAvaliableSets(HashMap<Integer,Long> seats) {
       seatPlan.AvaliableSeats(seats);
    }
    
    @Override
    public void run() { 
        String name="client: "+Thread.currentThread().getName();
            reserveBtn.addActionListener(new ActionListener(){
            @Override
            
            public  void actionPerformed(ActionEvent e) {
                System.out.println("sssss");
                 try {
            if (e.getSource() == reserveBtn) {
                if (getSetNumber() != 0) {
                        
                    try {
                        Object[]hisOwnGui=BF.makeReservation(name, getSetNumber(),Thread.currentThread().getId());
                        log.setText((String) hisOwnGui[1]);
                        if (hisOwnGui[0].equals("true")){
                                updateSeatPlan(getSetNumber());
                        }
                        
                       
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }

            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Invalid Set Number", "Error", JOptionPane.ERROR_MESSAGE);
        }
        resrveInput.setText("");
            }
        });
        avaliableBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sssss");
                try {
                    if (e.getSource() == avaliableBtn) {
                        try {
                            List<Object> sa=BF.seeAvailableSeats(name);
                            updateAvaliableSets((HashMap<Integer, Long>) sa.get(1));
                            log.setText((String) sa.get(0));
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Invalid Set Number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}