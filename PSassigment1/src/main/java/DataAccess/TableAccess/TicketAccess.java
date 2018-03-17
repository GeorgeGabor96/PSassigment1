package DataAccess.TableAccess;

import DataAccess.Connection.ConnectionFactory;
import sun.security.krb5.internal.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class TicketAccess {
    private static final String getTicketsTypeQuery = "Select * from Ticket where id = ?";
    private static final Logger LOGGER = Logger.getLogger(TicketAccess.class.getName());
    private static final String insertTicketDetails = "insert into electriccastle.ticketDetails(Cashier_id, Ticket_id) values(?, ?)";

    public void printTicketsType() {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        try {
            statement = connection.prepareStatement(getTicketsTypeQuery);
            statement.setInt(1, 2);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String price = resultSet.getString("price");
                String type = resultSet.getString("type");
                System.out.println(id + " " + price + " " + type);
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void insertTicket()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        try {
            statement = connection.prepareStatement(insertTicketDetails);
            statement.setInt(1, 1);
            statement.setInt(2, 1);
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}

