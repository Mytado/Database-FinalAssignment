package gui;

import backend.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUICustomer {
    private Controller controller;
    private JFrame mainFrame;
    private String email = "";
    private Boolean emailBoo = false;

    public GUICustomer(Controller controller) {
        this.controller = controller;
        setup();
    }

    private void setup() {
        mainFrame = new JFrame("Mörtfors Tours");
        mainFrame.setSize(new Dimension(250, 200));
        mainFrame.add(startUI());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                controller.disconnect();

            }
        });
    }

    private void setLoginUI() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 0));
        JLabel loginLabel = new JLabel("Enter your email address");
        JTextField loginTF = new JTextField();
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
                if (!(loginTF.getText().isEmpty()) && controller.login(loginTF.getText())) {
                    email = loginTF.getText();
                    setMainUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email address");
                }
        });

        loginPanel.add(loginLabel);
        loginPanel.add(loginTF);
        loginPanel.add(loginBtn);
        mainFrame.getContentPane().removeAll();
        mainFrame.add(loginPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setSize(new Dimension(250, 200));
    }

    private void setRegisterUI() {
        JPanel registerPanel = new JPanel(new GridLayout(9, 2));
        JLabel firstNameLabel = new JLabel("First name:");
        JTextField firstNameTF = new JTextField();
        JLabel lastNameLabel = new JLabel("Last name:");
        JTextField lastNameTF = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressTF = new JTextField();
        JLabel zipLabel = new JLabel("Zip code (5 digits)");
        JTextField zipTF = new JTextField();
        JLabel cityLabel = new JLabel("City");
        JTextField cityTF = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTF = new JTextField();
        JLabel phoneLabel = new JLabel("Phone nr:");
        JTextField phoneTF = new JTextField();
        JButton registerBtn = new JButton("Register");
        JLabel blankLabel = new JLabel();
        registerBtn.addActionListener(e -> {
            if (!(firstNameTF.getText().isEmpty()) && !(lastNameTF.getText().isEmpty()) && !(addressTF.getText().isEmpty()) && !(zipTF.getText().isEmpty()) && !(cityTF.getText().isEmpty()) && !(emailTF.getText().isEmpty()) && zipTF.getText().length() == 5) {
                try {
                   if (controller.createAccount(firstNameTF.getText(), lastNameTF.getText(), addressTF.getText(), Integer.parseInt(zipTF.getText()), cityTF.getText(), emailTF.getText(), phoneTF.getText())) {
                       email = emailTF.getText();
                       emailBoo = true;
                   }
                   else {
                       JOptionPane.showMessageDialog(null, "Email already in use");
                       emailBoo = false;
                   }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entry\nMake sure zip code is composed of numbers");
                }
                if (emailBoo) {
                    setMainUI();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incomplete information");
            }
        });
        registerPanel.add(firstNameLabel);
        registerPanel.add(firstNameTF);
        registerPanel.add(lastNameLabel);
        registerPanel.add(lastNameTF);
        registerPanel.add(addressLabel);
        registerPanel.add(addressTF);
        registerPanel.add(zipLabel);
        registerPanel.add(zipTF);
        registerPanel.add(cityLabel);
        registerPanel.add(cityTF);
        registerPanel.add(emailLabel);
        registerPanel.add(emailTF);
        registerPanel.add(phoneLabel);
        registerPanel.add(phoneTF);
        registerPanel.add(blankLabel);
        registerPanel.add(registerBtn);
        mainFrame.getContentPane().removeAll();
        mainFrame.add(registerPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setSize(new Dimension(250, 200));
    }

    private JPanel startUI() {
        JPanel startPanel = new JPanel(new GridLayout(2, 0));
        JButton loginBtn = new JButton("Login");
        startPanel.add(loginBtn);
        JButton registerBtn = new JButton("Register");
        startPanel.add(registerBtn);

        loginBtn.addActionListener(e -> setLoginUI());
        registerBtn.addActionListener(e -> setRegisterUI());

        return startPanel;
    }

    private void setMainUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JTextArea tripsTA = new JTextArea();
        tripsTA.setFont(new Font("monospaced", Font.PLAIN, 12));
        tripsTA.setEditable(false);
        mainPanel.add(tripsTA, BorderLayout.CENTER);
        JPanel searchPanel = new JPanel(new GridLayout(4, 6));
        JLabel fromLabel = new JLabel("From:");
        JTextField fromTF = new JTextField();
        JLabel toLabel = new JLabel("To:");
        JTextField toTF = new JTextField();
        JButton searchBtn = new JButton("Search");
        JLabel departureLabel = new JLabel("Departure:");
        JTextField departureTF = new JTextField();
        JLabel arrivalLabel = new JLabel("Arrival");
        JTextField arrivalTF = new JTextField();
        JLabel fromPriceLabel = new JLabel("Price lowest:");
        JTextField fromPriceTF = new JTextField();
        JLabel toPriceLabel = new JLabel("Price highest:");
        JTextField toPriceTF = new JTextField();
        JLabel formatLable = new JLabel("Arrival/Departure format: YYYY-MM-DD,HH:MM");
        searchBtn.addActionListener(e -> {
                tripsTA.setText("");
                try {
                    if (fromPriceTF.getText().isEmpty() && !(toPriceTF.getText().isEmpty())) {
                        tripsTA.append(controller.search(fromTF.getText(), toTF.getText(), -1, Integer.parseInt(toPriceTF.getText()), departureTF.getText(), arrivalTF.getText()));
                    }
                    else if (!(fromPriceTF.getText().isEmpty()) && toPriceTF.getText().isEmpty()) {
                            tripsTA.append(controller.search(fromTF.getText(), toTF.getText(), Integer.parseInt(fromPriceTF.getText()), -1, departureTF.getText(), arrivalTF.getText()));
                        }
                    else if (fromPriceTF.getText().isEmpty() && toPriceTF.getText().isEmpty()){
                        tripsTA.append(controller.search(fromTF.getText(), toTF.getText(), -1, -1, departureTF.getText(), arrivalTF.getText()));
                    }
                    else if (!(fromPriceTF.getText().isEmpty()) && !(toPriceTF.getText().isEmpty())) {
                        tripsTA.append(controller.search(fromTF.getText(), toTF.getText(), Integer.parseInt(fromPriceTF.getText()), Integer.parseInt(toPriceTF.getText()), departureTF.getText(), arrivalTF.getText()));

                    }
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Only digits are allowed in the price fields");
                }
        });

        searchPanel.add(fromLabel);
        searchPanel.add(fromTF);
        searchPanel.add(toLabel);
        searchPanel.add(toTF);
        searchPanel.add(departureLabel);
        searchPanel.add(departureTF);
        searchPanel.add(arrivalLabel);
        searchPanel.add(arrivalTF);
        searchPanel.add(fromPriceLabel);
        searchPanel.add(fromPriceTF);
        searchPanel.add(toPriceLabel);
        searchPanel.add(toPriceTF);
        searchPanel.add(formatLable);
        searchPanel.add(searchBtn);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel bookingPanel = new JPanel(new GridLayout(0, 5));
        JLabel travelIDLabel = new JLabel("TravelID:");
        JTextField travelIDTF = new JTextField();
        JLabel seatsLabel = new JLabel("Amount of seats:");
        JTextField seatsTF = new JTextField();
        JButton bookingBtn = new JButton("Book");
           bookingBtn.addActionListener(e -> {
               try {
               if (!(travelIDTF.getText().isEmpty()) && !(seatsTF.getText().isEmpty())) {

                   if (controller.book(Integer.parseInt(travelIDTF.getText()), Integer.parseInt(seatsTF.getText()), email)) {
                       JOptionPane.showMessageDialog(null, "We have registered your booking!");
                   } else {
                       JOptionPane.showMessageDialog(null, "Unfortunately the amount of seats selected is not available on this trip\nPlease select a lower amount");
                   }
               } else {
                   JOptionPane.showMessageDialog(null, "Incomplete travel information, \nPlease enter numbers only");
               }
               } catch (Exception ex) {
                   JOptionPane.showMessageDialog(null,"Invalid entry\nPlease enter numbers when booking");
               }
           });

        bookingPanel.add(travelIDLabel);
        bookingPanel.add(travelIDTF);
        bookingPanel.add(seatsLabel);
        bookingPanel.add(seatsTF);
        bookingPanel.add(bookingBtn);
        mainPanel.add(bookingPanel, BorderLayout.SOUTH);

        mainFrame.getContentPane().removeAll();
        mainFrame.add(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setSize(new Dimension(1300, 800));

    }

    public static void main(String[] args) {
        Controller c = new Controller();
        GUICustomer guiCustomer = new GUICustomer(c);

    }
}
