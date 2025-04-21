package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*;
import java.util.Date;
import java.sql.ResultSet;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField amount;
    JButton withdraw, back;
    String pinnumber;

    
    Withdrawl(String pinnumber) {
        this.pinnumber = pinnumber;
        setLayout(null);

        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        
        JLabel text = new JLabel("Enter the Amount you want to Withdraw");
        text.setForeground(Color.GRAY);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(185, 290, 700, 35);
        image.add(text);

        
        amount = new JTextField();
        amount.setFont(new Font("Releway", Font.BOLD, 22));
        amount.setBounds(170, 350, 320, 25);
        image.add(amount);

        // Withdraw button
        withdraw = new JButton("Withdraw");
        withdraw.setBounds(355, 485, 150, 30);
        withdraw.addActionListener(this);
        image.add(withdraw);

        // Back button
        back = new JButton("Back");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);

        // Window settings
        setSize(900, 900);
        setLocation(300, 0);
        setVisible(true);
    }

    // Main method
    public static void main(String args[]) {
        new Withdrawl("");
    }

    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == withdraw) {
            String number = amount.getText(); 
            Date date = new Date(); 

            if (number.equals("")) { 
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to Withdraw!!");
            } else {
                try {
                    Conn conn = new Conn(); 

                    
                    String query1 = "SELECT SUM(CASE WHEN type='Deposit' THEN amount ELSE -amount END) AS balance FROM bank WHERE pin = '" + pinnumber + "'";
                    ResultSet rs = conn.s.executeQuery(query1);

                    int balance = 0; 
                    if (rs.next()) {
                        balance = rs.getInt("balance"); 
                    }

                    int withdrawAmount = Integer.parseInt(number); 

                    
                    if (withdrawAmount > balance) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance! You cannot withdraw more than your current balance.");
                    } else {
                        
                        String query2 = "INSERT INTO bank VALUES('" + pinnumber + "', '" + date + "', 'Withdrawl', '" + withdrawAmount + "')";
                        conn.s.executeUpdate(query2);

                        
                        JOptionPane.showMessageDialog(null, "Rs. " + number + " Withdrawn Successfully!");

                        
                        setVisible(false);
                        new Transactions(pinnumber).setVisible(true);
                    }
                } catch (Exception e) {
                    System.out.println(e); 
                }
            }
        } else if (ae.getSource() == back) { 
            setVisible(false);
            new Transactions(pinnumber).setVisible(true); 
        }
    }
}
