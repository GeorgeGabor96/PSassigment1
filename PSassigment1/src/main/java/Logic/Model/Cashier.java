package Logic.Model;

import DataAccess.TableAccess.TicketAccess;
import Logic.Interfaces.CashierCredentials;
import Logic.Interfaces.TicketCredentials;

import java.util.List;

/**
 * Created by GEORGE on 18.03.2018.
 */
public class Cashier extends User implements CashierCredentials
{
    private int ticketsSold = 0;
    public Cashier(int id) {
        super(id);
    }

    public Cashier(String username, int ticketsSold)
    {
        super();
        this.username = username;
        this.ticketsSold = ticketsSold;
    }

    public List<TicketCredentials> getTicketsSold()
    {
        return TicketAccess.getTicketsSoldByCashier(id);
    }

    public boolean insertTicket(Ticket ticket)
    {
        return ticket.insertTicket();
    }


    public int getNumberSoldTickers() {
        return ticketsSold;
    }
}
