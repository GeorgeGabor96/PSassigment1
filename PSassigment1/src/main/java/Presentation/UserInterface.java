package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class UserInterface {
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
    private JPasswordField passwordField;
    private JComboBox comboBox1;
    private JTable TicketsSoldTable;
    private JButton SellTicketButton;
    private JScrollPane TablePanel;
    private JButton TotalsButton;
    private JButton MaxCapButton;
    private JButton TicketPerCashierButton;
    private JButton DeleteCashierButton;
    private JButton UpdateCashierButton;
    private JButton GetCashierButton;
    private JButton AddCashierButton;
    private JLabel AdminLabel;
    private JButton CashingPerDayButton;
    private JButton AdminLogOutButoon;
    private JButton CashierLogOutButton;

    public JPanel getLoginPanel()
    {
        setup();
        return Root;
    }

    public void setup()
    {
        String[] columns = {"Type", "Price", "Date", "Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        tableModel.addRow(new Object[]{"Day 1", 200, "20.3.2018", "20:30:11"});
        TicketsSoldTable.setModel(tableModel);
        TablePanel.setBorder(BorderFactory.createEmptyBorder());

    }

}
