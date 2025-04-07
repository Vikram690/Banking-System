package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;


public class Login extends JFrame implements ActionListener{
    
    JButton login,clear,signup;
    JTextField cardTextField;
    JPasswordField pinTextField;
    
    Login() {
        setTitle("Banking System");
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel lable = new JLabel(i3);
        lable.setBounds(70, 10, 100, 100);
        add(lable);
        
        JLabel cardno = new JLabel("Welcome to our ATM");
        cardno.setFont(new Font("Osward",Font.BOLD, 40));
        cardno.setBounds(220, 40, 500, 40);
        add (cardno);
        
        JLabel pin = new JLabel("Card No. : ");
        pin.setFont(new Font("Raleway",Font.BOLD, 28));
        pin.setBounds(120, 150, 250, 30);
        add (pin);
        
        cardTextField = new JTextField();
        cardTextField.setBounds(300, 150, 230, 30);
        cardTextField.setFont(new Font("Arial",Font.BOLD , 20));
        add(cardTextField);
        
        JLabel text = new JLabel("PIN No. : ");
        text.setFont(new Font("Raleway",Font.BOLD, 28));
        text.setBounds(120, 220, 250, 30);
        add (text);
        
        pinTextField = new JPasswordField();
        pinTextField.setBounds(300, 220, 230, 30);
        pinTextField.setFont(new Font("Arial",Font.BOLD , 20));
        add(pinTextField);
        
        login = new JButton("SIGN IN");
        login.setBounds(300, 300, 100, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        
        clear = new JButton("CLEAR");
        clear.setBounds(430, 300, 100, 30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        signup = new JButton("SIGN UP");
        signup.setBounds(300, 350, 230, 30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);
        
        getContentPane().setBackground(Color.WHITE);
        
        setSize(800,480);
        setVisible(true);
        setLocation(350,200);

    }
    
    @Override
     public void actionPerformed(ActionEvent ae){
         if(ae.getSource()==clear){
             cardTextField.setText("");
             pinTextField.setText("");
         }else if(ae.getSource()==login){
             Conn conn = new Conn();
             String cardnumber = cardTextField.getText();
             String pinnumber = pinTextField.getText();
             String query = "select * from login where cardnumber = '"+cardnumber+"' and pin = '"+pinnumber+"'";
             
             try{
                 ResultSet rs = (ResultSet) conn.s.executeQuery(query);
                 if (rs.next()){
                     setVisible(false);
                     new Transactions(pinnumber).setVisible(true);
                 }else{
                     JOptionPane.showMessageDialog(null, "Incorrect Card Number or Pin!!");
                     System.out.println(cardnumber + " " + pinnumber);
                 }
             }catch(Exception e){
                 System.out.println(e);
             }
         }else if(ae.getSource()==signup){
             setVisible(false);
             new SignupOne().setVisible(true);
         }
         
         
     }
    public static void main(String args[]){
       
        new Login();
    }
    
}
