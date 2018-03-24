package Logic.Model;

import DataAccess.TableAccess.UserAccess;
import Logic.Interfaces.UserCredentials;

/**
 * Created by GEORGE on 18.03.2018.
 */
public class User implements UserCredentials
{
    protected int id;
    protected String username;
    protected String password;
    protected String type;


    public User()
    {

    }

    public User(int id)
    {
        this.id = id;
        this.username = null;
        this.password = null;
        this.type = null;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.id = -1;
        this.type = null;
    }

    public void verifyUser()
    {
        UserAccess.verifyUser(this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType()
    {
        return type;
    }

    public int getID()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString()
    {
        return id + " " + username + " " + password + " " + type;
    }

    public static void main(String[] args) {

    }
}
