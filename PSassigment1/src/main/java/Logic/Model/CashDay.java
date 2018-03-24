package Logic.Model;

import Logic.Interfaces.CashDayCredentials;

/**
 * Created by GEORGE on 18.03.2018.
 */
public class CashDay implements CashDayCredentials {
    private String info;
    private int cash;
    private int count;

    public CashDay(String date, int cash)
    {
        this.info = date;
        this.cash = cash;
        this.count = 0;
    }

    public CashDay(String date, int count, int cash)
    {
        this.info = date;
        this.cash = cash;
        this.count = count;
    }

    public String getDate() {
        return info;
    }

    public int getCash() {
        return cash;
    }

    public int getCount()
    {
        return count;
    }

    public String getType()
    {
        return info;
    }
}
