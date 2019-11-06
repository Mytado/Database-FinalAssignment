package gui;

import backend.AdminController;
import backend.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIAdmin {
    private AdminController controller;
    private JFrame mainFrame;
    private String currentTable;

    public GUIAdmin(AdminController controller) {
        this.controller = controller;
        setup();
    }

    private void setup() {
        mainFrame = new JFrame("Admin");
        mainFrame.setSize(new Dimension(250, 200));
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

    private void setStartUI() {
        JPanel startPanel = new JPanel(new BorderLayout());
        JButton customerBtn = new JButton("Show customers");
        //customerBtn.addActionListener(e ->);
        JButton bookingsBtn = new JButton("Show bookings");
        //bookingsBtn.addActionListener(e ->);
        JButton tripsBtn = new JButton("Show trips");
        //tripsBtn.addActionListener(e ->);
        JButton citiesBtn = new JButton("Show cities");
        //citiesBtn.addActionListener(e ->);

        JPanel buttonPanel = new JPanel(new GridLayout(4,0));
        buttonPanel.add(customerBtn);
        buttonPanel.add(bookingsBtn);
        buttonPanel.add(tripsBtn);
        buttonPanel.add(citiesBtn);
        startPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JTextArea centerTA = new JTextArea();
        centerPanel.add(centerTA, BorderLayout.CENTER);
        startPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel updateDeletePanel = new JPanel(new GridLayout(2, 7));
        JLabel updateLabel = new JLabel("Enter customer-/booking-/travel-id or city name of the one you want to update");
        JTextField primaryKeyTF = new JTextField();
        JLabel attributeLabel = new JLabel("Enter which fields you want to update separated by ','");
        JTextField attributeTF = new JTextField();
        JLabel newInfoLabel = new JLabel("Enter the new values separated by ','");
        JTextField newInfoTF = new JTextField();
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            if (!(primaryKeyTF.getText().isEmpty()) && !(attributeTF.getText().isEmpty()) && !(newInfoTF.getText().isEmpty())) {
                controller.update(currentTable, primaryKeyTF.getText(), attributeTF.getText(), newInfoTF.getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "Incompltete update information\nPlease fill in all update text fields");
            }
        });
        JLabel deleteLabel = new JLabel("Enter customer-/booking-/travel-id or city name of the one you want to delete");
        JTextField deleteTF = new JTextField();
        JButton deleteButton = new JButton("Delete");
        //deleteButton.addActionListener(e ->);

        updateDeletePanel.add(updateLabel);
        updateDeletePanel.add(primaryKeyTF);
        updateDeletePanel.add(attributeLabel);
        updateDeletePanel.add(attributeTF);
        updateDeletePanel.add(newInfoLabel);
        updateDeletePanel.add(newInfoTF);
        updateDeletePanel.add(updateButton);
        updateDeletePanel.add(deleteLabel);
        updateDeletePanel.add(deleteTF);
        updateDeletePanel.add(deleteButton);

        startPanel.add(updateDeletePanel, BorderLayout.SOUTH);

        mainFrame.add(startPanel);
        mainFrame.setSize(new Dimension(250, 200));
    }


}
