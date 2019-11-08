package gui;

import backend.AdminController;
import backend.Controller;
//import jdk.nashorn.internal.scripts.JO;

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
        //mainFrame.pack();
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
        setStartUI();
        mainFrame.setSize(new Dimension(1400, 800));
    }

    private void setStartUI() {
        JPanel startPanel = new JPanel(new BorderLayout());
        JTextArea centerTA = new JTextArea();
        centerTA.setEditable(false);
        startPanel.add(centerTA, BorderLayout.CENTER);
        JButton customerBtn = new JButton("Show customers");
        customerBtn.addActionListener(e -> {
            currentTable = "customer";
            centerTA.setText("");
            centerTA.append(controller.showInfo(currentTable));
        });
        JButton bookingsBtn = new JButton("Show bookings");
        bookingsBtn.addActionListener(e -> {
            currentTable = "customertravel";
            centerTA.setText("");
            centerTA.append(controller.showInfo(currentTable));
        });
        JButton tripsBtn = new JButton("Show trips");
        tripsBtn.addActionListener(e -> {
            currentTable = "travel";
            centerTA.setText("");
            centerTA.append(controller.showInfo(currentTable));
        });
        JButton citiesBtn = new JButton("Show cities");
        citiesBtn.addActionListener(e -> {
            currentTable = "city";
            centerTA.setText("");
            centerTA.append(controller.showInfo(currentTable));
        });
        JButton driverBtn = new JButton("Show drivers");
        driverBtn.addActionListener(e -> {
            currentTable = "driver";
            centerTA.setText("");
            centerTA.append(controller.showInfo(currentTable));
        });

        JPanel buttonPanel = new JPanel(new GridLayout(6, 0));
        buttonPanel.add(customerBtn);
        buttonPanel.add(bookingsBtn);
        buttonPanel.add(tripsBtn);
        buttonPanel.add(citiesBtn);
        buttonPanel.add(driverBtn);
        startPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel updateDeletePanel = new JPanel(new GridLayout(8, 2));
        JLabel updateLabel = new JLabel("Enter customer-/booking-/travel-id or city name of the one you want to update");
        JTextField primaryKeyTF = new JTextField();
        JLabel attributeLabel = new JLabel("Enter which fields you want to update separated by ',' (the first column can not be updated)");
        JTextField attributeTF = new JTextField();
        JLabel newInfoLabel = new JLabel("Enter the new values separated by ','");
        JTextField newInfoTF = new JTextField();
        JButton updateButton = new JButton("Update");
        JLabel blanklbl1 = new JLabel();
        updateButton.addActionListener(e -> {
            if (!(primaryKeyTF.getText().isEmpty()) && !(attributeTF.getText().isEmpty()) && !(newInfoTF.getText().isEmpty())) {
                if (controller.update(currentTable, primaryKeyTF.getText(), attributeTF.getText(), newInfoTF.getText())) {
                    JOptionPane.showMessageDialog(null, "The data has been updated");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid entries in update fields");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incomplete update information\nPlease fill in all update text fields");
            }
        });
        JLabel deleteLabel = new JLabel("Enter customer-/booking-/travel-id or city name of the one you want to delete");
        JTextField deleteTF = new JTextField();
        JButton deleteButton = new JButton("Delete");
        JLabel blankLabel2 = new JLabel();
        deleteButton.addActionListener(e -> {
            if (!(deleteTF.getText().isEmpty())) {
                if (controller.delete(currentTable, deleteTF.getText())) {
                    JOptionPane.showMessageDialog(null, "The data has been deleted");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid entry in delete field");
                }
            }
        });
        JLabel insertLabel = new JLabel("Enter all the values needed to insert into a row separated by ','");
        JTextField insertTF = new JTextField();
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            if (!(insertTF.getText().isEmpty())) {
                if (controller.insert(currentTable, insertTF.getText())) {
                    JOptionPane.showMessageDialog(null, "The data has been inserted");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid data in insert text field");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Text field is empty\nPlease insert data");
            }
        });

        updateDeletePanel.add(updateLabel);
        updateDeletePanel.add(primaryKeyTF);
        updateDeletePanel.add(attributeLabel);
        updateDeletePanel.add(attributeTF);
        updateDeletePanel.add(newInfoLabel);
        updateDeletePanel.add(newInfoTF);
        updateDeletePanel.add(updateButton);
        updateDeletePanel.add(blanklbl1);
        updateDeletePanel.add(deleteLabel);
        updateDeletePanel.add(deleteTF);
        updateDeletePanel.add(deleteButton);
        updateDeletePanel.add(blankLabel2);
        updateDeletePanel.add(insertLabel);
        updateDeletePanel.add(insertTF);
        updateDeletePanel.add(insertButton);

        startPanel.add(updateDeletePanel, BorderLayout.SOUTH);

        mainFrame.add(startPanel);
    }

    public static void main(String[] args) {
        AdminController c = new AdminController();
        GUIAdmin GUIAdmin = new GUIAdmin(c);

    }
}




