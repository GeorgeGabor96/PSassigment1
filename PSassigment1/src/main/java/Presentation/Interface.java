package Presentation;

import Logic.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class Interface {
    private JPanel Root;
    private JPanel LoginPanel;
    private JPanel AdminPanel;
    private JPanel CashierPanel;
    private JTextField usernameTextField;
    private JButton LoginButton;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;
    private JLabel ErrorLabel;
    private JButton sellTicketOption;
    private JButton seeTicketsSoldButton;
    private JLabel CashierLabel;
    private JPasswordField passwordTextField;
    private JComboBox ticketSelectCombo;
    private JTable ticketsSoldByTable;
    private JScrollPane ticketsSoldByScroll;
    private JButton TotalsButton;
    private JButton MaxCapButton;
    private JButton TicketPerCashierButton;
    private JButton DeleteCashierButton;
    private JButton UpdateCashierButton;
    private JButton GetCashierPassButton;
    private JButton AddCashierButton;
    private JLabel AdminLabel;
    private JButton CashingPerDayButton;
    private JButton AdminLogOutButoon;
    private JButton CashierLogOutButton;
    private JTextField cashierUsernameTextField;
    private JTextField cashierPassTextField;
    private JLabel cashierUsernameLabel;
    private JLabel cashierPassLabel;
    private JTextField cashierNewPassTextField;
    private JTextField cashierNewUserTextField;
    private JLabel cachierNewUserLabel;
    private JLabel cashierNewPassLabel;
    private JTextField maxCapacityTextField;
    private JLabel maxCapacityLabel;
    private JTable ticketsPerCashierTable;
    private JScrollPane ticketsPerCashierScroll;
    private JScrollPane cashPerDayScroll;
    private JTable cashPerDayTable;
    private JTable cashPerTypeTable;
    private JLabel totalCashLabel;
    private JScrollPane cashPerTypeScroll;
    private DefaultTableModel tableModel;
    private DefaultTableModel ticketsSoldCashierTableModel;
    private DefaultTableModel cashPerDayTableModel;
    private DefaultTableModel cashPerTycketTableModel;

    private static final String LOGIN_CARD = "Login";

    private User currentUser;

    public Interface() {
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) Root.getLayout();
                String username = usernameTextField.getText();
                if (true == username.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please add a username");
                    return;
                }

                String password = String.valueOf(passwordTextField.getPassword());
                if (true == password.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please add a password");
                    return;
                }

                currentUser = new User(username, password);
                currentUser.verifyUser();
                if (currentUser.getType() == null)
                {
                    ErrorLabel.setText("Wrong username or password");
                    currentUser = null;
                }
                else
                {
                    cardLayout.show(Root, currentUser.getType());
                }
                usernameTextField.setText("");
                passwordTextField.setText("");
            }
        });


        AdminLogOutButoon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) Root.getLayout();

                cashierPassTextField.setText("");
                cashierUsernameTextField.setText("");
                cashierNewPassTextField.setText("");
                cashierNewUserTextField.setText("");
                maxCapacityTextField.setText("");

                cardLayout.show(Root, LOGIN_CARD);
                currentUser = null;
            }
        });

        CashierLogOutButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) Root.getLayout();
                cardLayout.show(Root, LOGIN_CARD);
                currentUser = null;
            }
        });

        sellTicketOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = ticketSelectCombo.getSelectedIndex();
                Ticket ticket;
                String type = null;
                String[] data = ticketSelectCombo.getSelectedItem().toString().split(" - ");
                String[] dayData = data[0].split(" ");
                String[] priceData = data[1].split(" ");

                ticket = new Ticket(currentUser.getID(), dayData[0] + "" + dayData[1], Integer.parseInt(priceData[0]));
                Cashier cashier = new Cashier(currentUser.getID());

                if (true == cashier.insertTicket(ticket))
                {
                    JOptionPane.showMessageDialog(null, "Ticket inserted successfully");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No remaining tickets for " + type);
                }
            }
        });

        seeTicketsSoldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cashier cashier = new Cashier(currentUser.getID());
                List<TicketCredentials> tickets = cashier.getTicketsSold();
                clearModelTableRows(tableModel);

                for (TicketCredentials ticket: tickets)
                {
                    tableModel.addRow(new Object[]{ticket.getTicketType(), ticket.getPrice(), ticket.getDate(), ticket.getTime()});
                }
            }
        });

        AddCashierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = cashierUsernameTextField.getText();
                String password = cashierPassTextField.getText();
                cashierPassTextField.setText("");

                if (username.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Enter cashier username");
                    return;
                }
                else if (password.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Enter cashier password");
                    return;
                }

                Admin admin = new Admin(currentUser.getID());
                if (true == admin.addCashier(new User(username, password)))
                {
                    JOptionPane.showMessageDialog(null, "Cashier inserted successfully");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "An error appeared. The cashier " + username + " may already exist ");
                }
            }
        });

        GetCashierPassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = cashierUsernameTextField.getText();
                if (username.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Enter cashier username");
                    return;
                }

                Admin admin = new Admin(currentUser.getID());
                String password = admin.getCashierPassword(username);
                if (password == null)
                {
                    JOptionPane.showMessageDialog(null, "Cashier " + username + " doesn't exist");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Password " + password);
                }
            }
        });

        UpdateCashierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean enterData = false;
                String username = cashierUsernameTextField.getText();
                if (username.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Enter cashier username");
                    return;
                }
                Admin admin = new Admin();

                String newPassword = cashierNewPassTextField.getText();
                if (false == newPassword.equals(""))
                {
                    enterData = true;
                    admin.updateCashierPassword(username, newPassword);
                }

                String newUsername = cashierNewUserTextField.getText();
                if (false == newUsername.equals(""))
                {
                    enterData = true;
                    admin.updateCashierUsername(username, newUsername);
                }

                if (false == enterData)
                {
                    JOptionPane.showMessageDialog(null, "Enter cashier new username or new password");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "The cashier should be updated. (if the username was correct, else no change made)");
                }
            }
        });
        DeleteCashierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = cashierUsernameTextField.getText();
                if (username.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Enter cashier username");
                    return;
                }
                Admin admin = new Admin();
                boolean ok = admin.deleteCashier(username);
                if (false == ok)
                {
                    JOptionPane.showMessageDialog(null, "Could not delete cashier " + username + ", he sold some tickets, he does not diserve it");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "The cashier should be deleted. (if the username was correct, else no change made)");
                }
            }
        });

        MaxCapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newValue = maxCapacityTextField.getText();
                if (newValue.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Enter new value for capacity");
                }
                int newMaxCapacity = 0;
                try
                {
                    newMaxCapacity = Integer.valueOf(maxCapacityTextField.getText());
                }
                catch (NumberFormatException b)
                {
                    b.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Only add integers");
                }
                if (newMaxCapacity < 0)
                {
                    newMaxCapacity = -newMaxCapacity;
                }

                Admin admin = new Admin();
                admin.changeMaxCapacity(newMaxCapacity);
                JOptionPane.showMessageDialog(null, "Update successfully");
            }
        });

        TicketPerCashierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                List<CashierCredentials> ticketsPerCashier = admin.getNumberTicketsPerCashier();
                clearModelTableRows(ticketsSoldCashierTableModel);
                for (CashierCredentials cashier: ticketsPerCashier)
                {
                    ticketsSoldCashierTableModel.addRow(new Object[]{cashier.getUsername(), cashier.getNumberSoldTickers()});
                }
            }
        });
        CashingPerDayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                List<CashDayCredentials> cashPerDay = admin.getCashPerDay();
                clearModelTableRows(cashPerDayTableModel);
                for (CashDayCredentials cashDay: cashPerDay)
                {
                    cashPerDayTableModel.addRow(new Object[]{cashDay.getDate(), cashDay.getCash() + "RON"});
                }
            }
        });
        TotalsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                List<CashDayCredentials> cashPerType = admin.getCashPerTicket();
                clearModelTableRows(cashPerTycketTableModel);
                for (CashDayCredentials cashTicket: cashPerType)
                {
                    cashPerTycketTableModel.addRow(new Object[]{cashTicket.getType(), cashTicket.getCount(), cashTicket.getCash()});
                }
                totalCashLabel.setText("Total " + admin.getTotal(cashPerType));
            }
        });
    }



    public JPanel getLoginPanel()
    {
        setup();
        return Root;
    }

    public void setup()
    {
        String[] columns = {"Type", "Price", "Date", "Time"};
        tableModel = new DefaultTableModel(columns, 0);
        ticketsSoldByTable.setModel(tableModel);
        ticketsSoldByScroll.setBorder(BorderFactory.createEmptyBorder());

        columns = new String[]{"Username", "Tickets sold"};
        ticketsSoldCashierTableModel = new DefaultTableModel(columns, 0);
        ticketsPerCashierTable.setModel(ticketsSoldCashierTableModel);
        ticketsPerCashierScroll.setBorder(BorderFactory.createEmptyBorder());

        columns = new String[]{"Date", "Cash"};
        cashPerDayTableModel = new DefaultTableModel(columns, 0);
        cashPerDayTable.setModel(cashPerDayTableModel);
        cashPerDayScroll.setBorder(BorderFactory.createEmptyBorder());

        columns = new String[]{"Type", "Tickets Sold", "Cash"};
        cashPerTycketTableModel = new DefaultTableModel(columns, 0);
        cashPerTypeTable.setModel(cashPerTycketTableModel);
        cashPerTypeScroll.setBorder(BorderFactory.createEmptyBorder());
    }

    private void clearModelTableRows(DefaultTableModel model)
    {
        int tableRows = model.getRowCount();
        for (int i = 0; i < tableRows; ++i)
        {
            model.removeRow(0);
        }
    }

}
