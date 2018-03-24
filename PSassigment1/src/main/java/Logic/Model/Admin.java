package Logic.Model;

import DataAccess.TableAccess.TicketAccess;
import DataAccess.TableAccess.UserAccess;
import Logic.Interfaces.CashDayCredentials;
import Logic.Interfaces.CashierCredentials;
import Logic.Interfaces.UserCredentials;

import java.util.List;

/**
 * Created by GEORGE on 18.03.2018.
 */

public class Admin extends User {

    public Admin()
    {

    }

    public Admin(int id) {
        super(id);
    }

    public boolean addCashier(UserCredentials user)
    {
        return UserAccess.insertCashier(user);
    }

    public String getCashierPassword(String username)
    {
        return UserAccess.getCashierPassword(username);
    }

    public void updateCashierUsername(String username, String newUsername)
    {
        UserAccess.updateCashierUsername(username, newUsername);
    }

    public void updateCashierPassword(String username, String newPassword)
    {
        UserAccess.updateCashierPassword(username, newPassword);
    }

    public boolean deleteCashier(String username)
    {
        return UserAccess.deleteCashier(username);
    }

    public boolean changeMaxCapacity(int newMaxCapacity)
    {
        int ticketsCount = TicketAccess.getNumberOfTickets();
        System.out.println(ticketsCount);
        if (ticketsCount < newMaxCapacity)
        {
            UserAccess.changeMaxCapacity(newMaxCapacity);
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<CashierCredentials> getNumberTicketsPerCashier()
    {
        return UserAccess.getNumberTicketsPerCashier();
    }

    public List<CashDayCredentials> getCashPerDay()
    {
        return UserAccess.getCashingPerDay();
    }

    public List<CashDayCredentials> getCashPerTicket()
    {
        return UserAccess.getCashPerTicket();
    }

    public String getTotal(List<CashDayCredentials> cashDayCredentials)
    {
        int total = 0;
        for (CashDayCredentials cashDayCredential: cashDayCredentials)
        {
            total += cashDayCredential.getCash();
        }

        return total + "RON";
    }
}
