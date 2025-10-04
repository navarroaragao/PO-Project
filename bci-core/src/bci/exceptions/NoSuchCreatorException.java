package bci.exceptions;

import java.io.Serial;

public class NoSuchCreatorException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _creatorId;

    public NoSuchCreatorException(String creatorId) {
        _creatorId = creatorId;
    }

    public String getCreatorId() {
        return _creatorId;
    }

}