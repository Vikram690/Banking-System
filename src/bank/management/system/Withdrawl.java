package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*; // Import SQL package for database operations
import java.util.Date;
import java.sql.ResultSet;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField amount;
    JButton withdraw, back;
    String pinnumber;

    // Constructor to initialize the withdrawal window
    Withdrawl(String pinnumber) {
        this.pinnumber = pinnumber;
        setLayout(null);

        // Load and resize ATM background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        // Text label to instruct user
        JLabel text = new JLabel("Enter the Amount you want to Withdraw");
        text.setForeground(Color.GRAY);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(185, 290, 700, 35);
        image.add(text);

        // Text field for user to enter the withdrawal amount
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

    // Main method to test the class independently
    public static void main(String args[]) {
        new Withdrawl("");
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == withdraw) { // If Withdraw button is clicked
            String number = amount.getText(); // Get user input amount
            Date date = new Date(); // Get current date and time

            if (number.equals("")) { // Check if amount is empty
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to Withdraw!!");
            } else {
                try {
                    Conn conn = new Conn(); // Create a connection to the database

                    // SQL query to fetch the current balance from the bank table
                    String query1 = "SELECT SUM(CASE WHEN type='Deposit' THEN amount ELSE -amount END) AS balance FROM bank WHERE pin = '" + pinnumber + "'";
                    ResultSet rs = conn.s.executeQuery(query1);

                    int balance = 0; // Variable to store account balance
                    if (rs.next()) {
                        balance = rs.getInt("balance"); // Get balance value from database result
                    }

                    int withdrawAmount = Integer.parseInt(number); // Convert entered amount to integer

                    // Check if the withdrawal amount is more than available balance
                    if (withdrawAmount > balance) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance! You cannot withdraw more than your current balance.");
                    } else {
                        // Insert the withdrawal transaction into the database
                        String query2 = "INSERT INTO bank VALUES('" + pinnumber + "', '" + date + "', 'Withdrawl', '" + withdrawAmount + "')";
                        conn.s.executeUpdate(query2);

                        // Show success message
                        JOptionPane.showMessageDialog(null, "Rs. " + number + " Withdrawn Successfully!");

                        // Close withdrawal window and open Transactions page
                        setVisible(false);
                        new Transactions(pinnumber).setVisible(true);
                    }
                } catch (Exception e) {
                    System.out.println(e); // Print exception if any error occurs
                }
            }
        } else if (ae.getSource() == back) { // If Back button is clicked
            setVisible(false);
            new Transactions(pinnumber).setVisible(true); // Redirect to Transactions page
        }
    }
}
