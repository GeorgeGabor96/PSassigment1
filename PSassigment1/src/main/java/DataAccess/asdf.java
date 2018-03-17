package DataAccess;

import DataAccess.Connection.ConnectionFactory;
import DataAccess.TableAccess.TicketAccess;

import java.sql.Connection;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class asdf
{
    public static void main(String[] args) {
        Connection connection = ConnectionFactory.getConnection();
        TicketAccess ticketAccess = new TicketAccess();
        ticketAccess.insertTicket();
        ConnectionFactory.close(connection);
    }
}
