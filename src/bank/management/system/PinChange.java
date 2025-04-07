package bank.management.system;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;

public class PinChange extends JFrame implements ActionListener{
    
    JPasswordField oldPin, pin, repin;
    JButton back, change;
    String pinnumber;
    
    PinChange(String pinnumber){
        this.pinnumber = pinnumber;
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        JLabel text = new JLabel("Are you sure !! You want to change your PIN");
        text.setForeground(Color.GRAY);
        text.setFont(new Font("System",Font.BOLD,16));
        text.setBounds(170,280,700,35);
        image.add(text);
        
        JLabel oldPinText = new JLabel("Old PIN:");
        oldPinText.setForeground(Color.WHITE);
        oldPinText.setFont(new Font("System",Font.BOLD,16));
        oldPinText.setBounds(165,340,180,25);
        image.add(oldPinText);
        
        oldPin = new JPasswordField();
        oldPin.setFont(new Font("Releway",Font.BOLD,22));
        oldPin.setBounds(330, 340, 180, 25);
        image.add(oldPin);
        
        JLabel pintext = new JLabel("New PIN:");
        pintext.setForeground(Color.WHITE);
        pintext.setFont(new Font("System",Font.BOLD,16));
        pintext.setBounds(165,380,180,25);
        image.add(pintext);
        
        pin = new JPasswordField();
        pin.setFont(new Font("Releway",Font.BOLD,22));
        pin.setBounds(330, 380, 180, 25);
        image.add(pin);
        
        JLabel repintext = new JLabel("Re-Enter New PIN:");
        repintext.setForeground(Color.WHITE);
        repintext.setFont(new Font("System",Font.BOLD,16));
        repintext.setBounds(165,410,180,25);
        image.add(repintext);
        
        repin = new JPasswordField();
        repin.setFont(new Font("Releway",Font.BOLD,22));
        repin.setBounds(330, 410, 180, 25);
        image.add(repin);
        
        back = new JButton("Back");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);
        
        change = new JButton("Change");
        change.setBounds(355,485,150,30);
        change.addActionListener(this);
        image.add(change);
        
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==change){
            try{
                String enteredOldPin = new String(oldPin.getPassword());
                String npin = new String(pin.getPassword());
                String rpin = new String(repin.getPassword());
            
                if(!enteredOldPin.equals(pinnumber)){
                    JOptionPane.showMessageDialog(null,"Old PIN is incorrect!!");
                    return;
                }
                
                if(!npin.equals(rpin)){
                    JOptionPane.showMessageDialog(null,"Entered PIN Doesn't Match!!");
                    return;
                }
                
                if(npin.equals("")){
                    JOptionPane.showMessageDialog(null,"Please Enter new PIN!!");
                    return;
                }
                
                if(rpin.equals("")){
                    JOptionPane.showMessageDialog(null,"Please Re-Enter new PIN!!");
                    return;
                }
                
                Conn conn = new Conn();
                String query1 = "update bank set pin ='"+rpin+"' where pin='"+pinnumber+"' ";
                String query2 = "update login set pin ='"+rpin+"' where pin='"+pinnumber+"' ";
                String query3 = "update signupthree set pin ='"+rpin+"' where pin='"+pinnumber+"' ";
                
                conn.s.executeUpdate(query1);
                conn.s.executeUpdate(query2);
                conn.s.executeUpdate(query3);
                
                JOptionPane.showMessageDialog(null,"PIN Changed Successfully ");
                
                setVisible(false);
                new Transactions(rpin).setVisible(true);
                
            }catch(Exception e){
            System.out.println(e);
            }
        }else{
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }
    
    public static void main(String args[]){
        new PinChange("").setVisible(true);
    }   
}
