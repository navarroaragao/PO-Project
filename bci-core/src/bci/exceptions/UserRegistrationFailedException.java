package bci.exceptions;

import java.io.Serial;

public class UserRegistrationFailedException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _name;
    private final String _email;

    public UserRegistrationFailedException(String name, String email) {
        _name = name;
        _email = email;
    }

    public String getName() {
        return _name;
    }

    public String getEmail() {
        return _email;
    }

}