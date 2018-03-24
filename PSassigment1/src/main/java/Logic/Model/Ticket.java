package Logic.Model;

import DataAccess.TableAccess.TicketAccess;
import Logic.Interfaces.TicketCredentials;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GEORGE on 18.03.2018.
 */
public class Ticket implements TicketCredentials {
    private int cashierID;
    private String type;
    private String date;
    private String time;
    private int price;
    private static int ticketsInserted = 0;

    public Ticket(int cashierID, String type, int price, String date, String time)
    {
        this.cashierID  = cashierID;
        this.type       = type;
        this.price      = price;
        this.date       = date;
        this.time       = time;
    }

    public Ticket(int cashierID, String type, int price)
    {
        this.cashierID = cashierID;
        this.type = type;
        this.price = price;
    }


    public int getCashierID() {
        return cashierID;
    }

    public String getTicketType() {
        return type;
    }

    public String getTimeDate()
    {
        return date + " " + time;
    }


    public boolean insertTicket()
    {

        int allDaysTicketsCount =  TicketAccess.getTicketsTypeSold("AllDays");
        int capacityPerDay = TicketAccess.getCapacityPerDay();
        boolean isOk = false;

        if ("AllDays" == type) {
            int day1TicketsCount = TicketAccess.getTicketsTypeSold("Day1");
            int day2TicketsCount = TicketAccess.getTicketsTypeSold("Day2");
            int day3TicketsCount = TicketAccess.getTicketsTypeSold("Day3");

            if (day1TicketsCount + allDaysTicketsCount + 1 < capacityPerDay &&
                    day2TicketsCount + allDaysTicketsCount + 1 < capacityPerDay &&
                    day3TicketsCount + allDaysTicketsCount + 1 < capacityPerDay) {
                isOk = true;
            }
        }
        else{
            int ticketInDay = TicketAccess.getTicketsTypeSold(type);
            if (ticketInDay + allDaysTicketsCount + 1 < capacityPerDay)
            {
                isOk = true;
            }
        }

        if (true == isOk)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String[] data = dateFormat.format(date).split(" ");
            this.date = data[0];
            this.time = data[1];

            ticketsInserted++;
            TicketAccess.insertTicket(this);
            writeTicketFieOnDisk();
        }

        return isOk;
    }

    private void writeTicketFieOnDisk()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ticket with local number " + ticketsInserted + "\r\n");
        stringBuilder.append("Type " + type + "\r\n");
        stringBuilder.append("Price " + price + " RON\r\n");
        stringBuilder.append("Date " + date + "\r\n");
        stringBuilder.append("Time " + time + "\r\n");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("Tickets Sold//ticket" + ticketsInserted + ".txt");
            out.write(stringBuilder.toString().getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getType()
    {
        return type;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }

    public int getPrice()
    {
        return price;
    }

    public String toString()
    {
        return cashierID + " " + type + " " + price + " " + date + " " + time;
    }

    public static void main(String[] args) {
    }
}
