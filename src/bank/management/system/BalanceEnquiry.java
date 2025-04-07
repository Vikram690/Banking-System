package bank.management.system;

import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.sql.ResultSet;


public class BalanceEnquiry extends JFrame implements ActionListener{
    
    JButton back;
    String pinnumber;
    
    BalanceEnquiry(String pinnchange){
        this.pinnumber = pinnchange;
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        back = new JButton("Back");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);
        
        Conn conn = new Conn();
        int balance = 0;
        try{
            ResultSet rs = (ResultSet) conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");
            while(rs.next()){
                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt((String) rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt((String) rs.getString("amount"));
}

            }   
        }catch(Exception e){
                System.out.println(e);
        }
        
        JLabel text = new JLabel("Your Current Balance is Rs.  " + balance);
        text.setForeground(Color.GRAY);
        text.setFont(new Font("Raleway",Font.BOLD,17));
        text.setBounds(170, 300, 400, 35);
        image.add(text);

        
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae){
        
        setVisible(false);
        new Transactions(pinnumber).setVisible(true);
        
    }
    public static void main(String args[]){
        new BalanceEnquiry("");
    }
}
