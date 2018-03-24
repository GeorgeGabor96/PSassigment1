package DataAccess.TableAccess;

import DataAccess.Connection.ConnectionFactory;
import Logic.Model.Ticket;
import Logic.Interfaces.TicketCredentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class TicketAccess {
    private static final Logger LOGGER = Logger.getLogger(TicketAccess.class.getName());

    private static final String getTicketTypeCountQuery = "SELECT count(id) as count from ticketdetails inner join ticket on ticket_id = id where type = ?;"; // tipul biletului
    private static final String getCapacityPerDayQuery = "select capacity from constants;";
    private static final String insertTicketQuery = "insert into ticketdetails(time, User_id, Ticket_id) values(?, ?, (Select id from ticket where type = ?));"; // time, id cashier, tipul biletului
    private static final String ticketsSoldByCashierQuery = "SELECT time, price, type FROM electriccastle.ticketdetails inner join electriccastle.ticket on ticket_id = id where user_id = ? order by time desc;"; // id cashier
    private static final String numberOfTicketsQuery = "SELECT Count(*) as count FROM electriccastle.ticketdetails;";

    public static int getNumberOfTickets()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int ticketsCount = 0;

        try {
            statement = connection.prepareStatement(numberOfTicketsQuery);
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                ticketsCount = resultSet.getInt("count");
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"TicketAccess:getCapacityPerDay " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return ticketsCount;
    }


    public static List<TicketCredentials> getTicketsSoldByCashier(int id)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<TicketCredentials> ticketsDetails = new ArrayList<TicketCredentials>();

        try {
            statement = connection.prepareStatement(ticketsSoldByCashierQuery);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while(true == resultSet.next()) {
                String[] timeData = resultSet.getString("time").split(" ");
                String date = timeData[0];
                String time = timeData[1];
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                ticketsDetails.add(new Ticket(id, type, price, date, time));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"TicketAccess:getCapacityPerDay " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return ticketsDetails;
    }

    public static void insertTicket(TicketCredentials ticketCredentials)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertTicketQuery);
            statement.setString(1, ticketCredentials.getTimeDate());
            statement.setInt(2, ticketCredentials.getCashierID());
            statement.setString(3, ticketCredentials.getTicketType());

            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"TicketAccess:insertTicket " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    public static int getTicketsTypeSold(String type)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int ticketsCount = -1;

        try {
            statement = connection.prepareStatement(getTicketTypeCountQuery);
            statement.setString(1, type);
            resultSet = statement.executeQuery();

            resultSet.next();
            ticketsCount = resultSet.getInt("count");

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"TicketAccess:getTicketsTypeSold " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return ticketsCount;
    }

    public static int getCapacityPerDay()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int capacity = -1;

        try {
            statement = connection.prepareStatement(getCapacityPerDayQuery);
            resultSet = statement.executeQuery();

            resultSet.next();
            capacity = resultSet.getInt("capacity");

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"TicketAccess:getCapacityPerDay " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return capacity;
    }
}

