package DataAccess.TableAccess;

import DataAccess.Connection.ConnectionFactory;
import Logic.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class UserAccess
{
    private static final Logger LOGGER = Logger.getLogger(TicketAccess.class.getName());
    private static final String VERIFY_USER = "select id, type from user where username = ? and password = ?;";
    private static final String insertCashierQuery = "insert into user(username, password, type) values(?, ?, \"Cashier\");";
    private static final String getCashierPassQuery = "select password from user where username = ? and type = \"Cashier\";";
    private static final String updateCashierUsernameQuery = "update user set username = ? where username = ? and type = \"Cashier\";";
    private static final String updateCashierPasswordQuery = "update user set password = ? where username = ? and type = \"Cashier\";";
    private static final String deleteCashierQuery = "delete from user where username = ? and type = \"Cashier\";";
    private static final String getMaxCapacityQuery = "select capacity from constants";
    private static final String updateCapacityQuery = "update constants set capacity = ?;";
    private static final String getTicketsPerCashierQuery = "select username, newTable.tickets from electriccastle.user inner join " +
            "(SELECT user_id as newId, count(*) as tickets from electriccastle.ticket inner join electriccastle.ticketdetails where id = ticket_id group by user_id) as newTable " +
            "on id = newTable.newId";
    private static final String cashingPerDayQuery = "select date(time) as dates, sum(price) as money from electriccastle.ticketdetails inner join electriccastle.ticket on ticket_id = id group by date(time);";
    private static final String cashingPerTicketQuery = "select type, count(*) as ticketsSold, sum(price) as money from electriccastle.ticketdetails inner join electriccastle.ticket on ticket_id = id group by type;";


    public static void verifyUser(UserCredentials userCredentials)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(VERIFY_USER);
            statement.setString(1, userCredentials.getUsername());
            statement.setString(2, userCredentials.getPassword());
            resultSet = statement.executeQuery();

            if (resultSet.next() == true)
            {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");

                userCredentials.setId(id);
                userCredentials.setType(type);
            }

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:verifyUser " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public static boolean insertCashier(UserCredentials user)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean ok = true;
        try {
            statement = connection.prepareStatement(insertCashierQuery);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();

        } catch (SQLException e){
            ok = false;
            LOGGER.log(Level.WARNING,"UserAccess:insertCashier " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return ok;
    }

    public static String getCashierPassword(String username)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String password = null;

        try {
            statement = connection.prepareStatement(getCashierPassQuery);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                password = resultSet.getString("password");
            }

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:getCashierPassword " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return password;
    }

    public static void updateCashierUsername(String username, String newUsername)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(updateCashierUsernameQuery);
            statement.setString(1, newUsername);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:updateCashierUsername " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public static void updateCashierPassword(String username, String newPassword)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(updateCashierPasswordQuery);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:updateCashierPassword " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public static boolean deleteCashier(String username)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean ok = true;
        try {
            statement = connection.prepareStatement(deleteCashierQuery);
            statement.setString(1, username);
            statement.executeUpdate();

        } catch (SQLException e){
            ok = false;
            LOGGER.log(Level.WARNING,"UserAccess:deleteCashier " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return ok;
    }

    public static int getCurrentCapacity()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int capacity = 0;

        try {
            statement = connection.prepareStatement(getMaxCapacityQuery);
            resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                capacity = resultSet.getInt("capacity");
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:getCurrentCapacity " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return capacity;
    }

    public static void changeMaxCapacity(int capacity)
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(updateCapacityQuery);
            statement.setInt(1, capacity);
            statement.executeUpdate();

        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:changeMaxCapacity " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public static List<CashierCredentials> getNumberTicketsPerCashier()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<CashierCredentials> ticketsSoldPerCashier = new ArrayList<CashierCredentials>();

        try {
            statement = connection.prepareStatement(getTicketsPerCashierQuery);
            resultSet = statement.executeQuery();

            while(true == resultSet.next()) {
                String username = resultSet.getString("username");
                int ticketsSold = resultSet.getInt("tickets");
                ticketsSoldPerCashier.add(new Cashier(username, ticketsSold));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:getNumberTicketsPerCashier " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return ticketsSoldPerCashier;
    }

    public static List<CashDayCredentials> getCashingPerDay()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<CashDayCredentials> cashPerDay = new ArrayList<CashDayCredentials>();

        try {
            statement = connection.prepareStatement(cashingPerDayQuery);
            resultSet = statement.executeQuery();

            while(true == resultSet.next()) {
                String date = resultSet.getString("dates");
                int cash = resultSet.getInt("money");
                cashPerDay.add(new CashDay(date, cash));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:getCashingPerDay " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return cashPerDay;
    }

    public static List<CashDayCredentials> getCashPerTicket()
    {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<CashDayCredentials> cashPerTicket = new ArrayList<CashDayCredentials>();

        try {
            statement = connection.prepareStatement(cashingPerTicketQuery);
            resultSet = statement.executeQuery();

            while(true == resultSet.next()) {
                String type = resultSet.getString("type");
                int count = resultSet.getInt("ticketsSold");
                int cash = resultSet.getInt("money");
                cashPerTicket.add(new CashDay(type, count, cash));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserAccess:getCashPerTicket " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return cashPerTicket;
    }
}
