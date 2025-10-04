package bci.exceptions;

import java.io.Serial;

public class UserIsActiveException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final int _userId;

    public UserIsActiveException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }

}