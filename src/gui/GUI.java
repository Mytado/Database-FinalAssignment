package gui;

import backend.Controller;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private Controller controller;
    private JFrame mainFrame;

    public GUI(Controller controller) {
        this.controller = controller;

        setup();
    }

    private void setup() {
        mainFrame = new JFrame("Mortfors Tours");
        mainFrame.setSize(new Dimension(250, 200));
        mainFrame.add(startUI());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
    }

    private void setLoginUI() {
        JPanel loginPanel = new JPanel(new GridLayout(3,0));
        JLabel loginLabel = new JLabel("Enter your email address");
        JTextField loginTF = new JTextField();
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            try {
                if (!(loginTF.getText().isEmpty()) && controller.login(loginTF.getText())) {
                    setMainUI();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid email address");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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
        JPanel registerPanel = new JPanel(new GridLayout(5,2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTF = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressTF = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTF = new JTextField();
        JLabel phoneLabel = new JLabel("Phone nr:");
        JTextField phoneTF = new JTextField();
        JButton registerBtn = new JButton();
        registerBtn.addActionListener(e->{
            if (!(nameTF.getText().isEmpty()) && !(addressTF.getText().isEmpty()) && !(emailTF.getText().isEmpty()) && !(phoneTF.getText().isEmpty())) {
              //  controller.createAccount(nameTF.getText(), addressTF.getText(), emailTF.getText(), phoneTF.getText());
                setMainUI();
            }
            else {
                JOptionPane.showMessageDialog(null, "Incomplete information");
            }
        });

        registerPanel.add(nameLabel);
        registerPanel.add(nameTF);
        registerPanel.add(addressLabel);
        registerPanel.add(addressTF);
        registerPanel.add(emailLabel);
        registerPanel.add(emailTF);
        registerPanel.add(phoneLabel);
        registerPanel.add(phoneTF);
        mainFrame.getContentPane().removeAll();
        mainFrame.add(registerPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setSize(new Dimension(250, 200));


    }



    private JPanel startUI() {
        JPanel startPanel = new JPanel(new GridLayout(2,0));
        JButton loginBtn = new JButton("Login");
        startPanel.add(loginBtn);
        JButton registerBtn = new JButton("Register");
        startPanel.add(registerBtn);

        loginBtn.addActionListener(e -> setLoginUI());
        registerBtn.addActionListener(e -> setRegisterUI());

        return startPanel;
    }

    private void setMainUI() {

    }

}
