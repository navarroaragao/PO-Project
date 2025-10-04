package bci.exceptions;

import java.io.Serial;

public class NoSuchUserException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final int _userId;

    public NoSuchUserException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }

}