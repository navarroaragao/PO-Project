package bci;

import java.io.Serializable;

/** Class that represents a library user. */
public class User implements Serializable {
    private int _id;
    private String _name;
    private String _email;

    @java.io.Serial
    private static final long serialVersionUID = 202507171004L;

    /**
     * Constructor for User.
     * @param id user identifier
     * @param name user name
     * @param email user email
     */
    public User(int id, String name, String email) {
        _id = id;
        _name = name;
        _email = email;
    }

    /**
     * Gets the user ID.
     * @return user ID
     */
    public int getId() {
        return _id;
    }

    /**
     * Gets the user name.
     * @return user name
     */
    public String getName() {
        return _name;
    }

    /**
     * Gets the user email.
     * @return user email
     */
    public String getEmail() {
        return _email;
    }

    /**
     * Sets the user name.
     * @param name new user name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Sets the user email.
     * @param email new user email
     */
    public void setEmail(String email) {
        _email = email;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name=%s, email=%s]", _id, _name, _email);
    }
}