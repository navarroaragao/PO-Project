package bci.exceptions;

import java.io.Serial;

public class WorkNotBorrowedByUserException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final int _workId;
    private final int _userId;

    public WorkNotBorrowedByUserException(int workId, int userId) {
        _workId = workId;
        _userId = userId;
    }

    public int getWorkId() {
        return _workId;
    }

    public int getUserId() {
        return _userId;
    }

}