package bci.exceptions;

import java.io.Serial;

public class NoSuchWorkException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final int _workId;

    public NoSuchWorkException(int workId) {
        _workId = workId;
    }

    public int getWorkId() {
        return _workId;
    }

}