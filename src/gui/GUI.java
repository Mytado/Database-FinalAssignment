package gui;

import backend.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    private Controller controller;
    private JFrame mainFrame;
    private String email = "";
    private Boolean emailBoo = false;
    private Boolean zipBoo = false;

    public GUI(Controller controller) {
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
            if (!(firstNameTF.getText().isEmpty()) && !(lastNameTF.getText().isEmpty()) && !(addressTF.getText().isEmpty()) && !(zipTF.getText().isEmpty()) && !(cityTF.getText().isEmpty()) && !(emailTF.getText().isEmpty())) {
                try {
                   if (controller.createAccount(firstNameTF.getText(), lastNameTF.getText(), addressTF.getText(), Integer.parseInt(zipTF.getText()), cityTF.getText(), emailTF.getText(), phoneTF.getText())) {
                       email = emailTF.getText();
                       emailBoo = true;
                   }
                   else {
                       JOptionPane.showMessageDialog(null, "Email already in use");
                       emailBoo = false;
                   }
                    if (zipTF.getText().length() == 5) {
                        zipBoo = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Your zip code is not 5 digits");
                        zipBoo = false;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entry\nMake sure zip code is composed of numbers");
                    zipBoo = false;
                }
                if (emailBoo && zipBoo) {
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
        JPanel searchPanel = new JPanel(new GridLayout(0, 5));
        JLabel fromLabel = new JLabel("From:");
        JTextField fromTF = new JTextField();
        JLabel toLabel = new JLabel("To:");
        JTextField toTF = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> {
                tripsTA.setText("");
                tripsTA.append(controller.search(fromTF.getText(), toTF.getText()));

        });

        searchPanel.add(fromLabel);
        searchPanel.add(fromTF);
        searchPanel.add(toLabel);
        searchPanel.add(toTF);
        searchPanel.add(searchBtn);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel bookingPanel = new JPanel(new GridLayout(0, 5));
        JLabel travelIDLabel = new JLabel("TravelID:");
        JTextField travelIDTF = new JTextField();
        JLabel seatsLabel = new JLabel("Amount of seats:");
        JTextField seatsTF = new JTextField();
        JButton bookingBtn = new JButton("Book");
        bookingBtn.addActionListener(e -> {
            if (!(travelIDTF.getText().isEmpty()) && !(seatsTF.getText().isEmpty())) {
                    if (controller.book(Integer.parseInt(travelIDTF.getText()), Integer.parseInt(seatsTF.getText()), email)) {
                        JOptionPane.showMessageDialog(null, "We have registered your booking!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Unfortunately the amount of seats selected is not available on this trip\nPlease select a lower amount");
                    }
            } else {
                JOptionPane.showMessageDialog(null, "Incomplete travel information, \nPlease enter numbers only");
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
        mainFrame.setSize(new Dimension(950, 400));

    }

    public static void main(String[] args) {
        Controller c = new Controller();
        GUI gui = new GUI(c);

    }

}
