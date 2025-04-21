package bank.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;


public class Payment extends JFrame implements ActionListener {

    JButton pay, back;
    JTextField amountField, recipientPinField;
    String pinnumber;

    Payment(String pinnumber) {
        this.pinnumber = pinnumber;
        setLayout(null);

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        // Title text
        JLabel title = new JLabel("Enter Payment Details");
        title.setBounds(220, 290, 700, 35);
        title.setForeground(Color.GRAY);
        title.setFont(new Font("System", Font.BOLD, 16));
        image.add(title);

        // Amount input field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(165, 350, 180, 25);
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setFont(new Font("System", Font.BOLD, 16));
        image.add(amountLabel);
        
        amountField = new JTextField();
        amountField.setFont(new Font("Releway", Font.BOLD, 22));
        amountField.setBounds(330, 350, 180, 25);
        image.add(amountField);

        // Recipient's PIN input field
        JLabel recipientPinLabel = new JLabel("Recipient's PIN:");
        recipientPinLabel.setBounds(165, 400, 180, 25);
        recipientPinLabel.setForeground(Color.WHITE);
        recipientPinLabel.setFont(new Font("System", Font.BOLD, 16));
        image.add(recipientPinLabel);
        
        recipientPinField = new JTextField();
        recipientPinField.setFont(new Font("Releway", Font.BOLD, 22));
        recipientPinField.setBounds(330, 400, 180, 25);
        image.add(recipientPinField);

        // Pay button
        pay = new JButton("Pay");
        pay.setBounds(355, 450, 150, 30);
        pay.addActionListener(this);
        image.add(pay);

        // Back button
        back = new JButton("Back");
        back.setBounds(355, 485, 150, 30);
        back.addActionListener(this);
        image.add(back);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        } else if (ae.getSource() == pay) {
            String amount = amountField.getText();
            String recipientPin = recipientPinField.getText();

            if (amount.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an amount");
                return;
            }

            if (recipientPin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the recipient's PIN");
                return;
            }

            Conn conn = new Conn();
            try {
                // Check if the recipient's PIN exists in the database
                ResultSet rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + recipientPin + "'");
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Recipient's PIN not found");
                    return;
                }

                // Check if the user has sufficient balance
                rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");
                int balance = 0;
                while (rs.next()) {
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }

                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                
                Date date = new Date(System.currentTimeMillis()); 
                String query = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + pinnumber + "','" + date + "','Payment','" + amount + "')";
                conn.s.executeUpdate(query);

                
                String recipientQuery = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + recipientPin + "','" + date + "','Deposit','" + amount + "')";
                conn.s.executeUpdate(recipientQuery);

                JOptionPane.showMessageDialog(null, "Payment of Rs. " + amount + " Successful to PIN: " + recipientPin);

                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String args[]) {
        new Payment("").setVisible(true);
    }
}
