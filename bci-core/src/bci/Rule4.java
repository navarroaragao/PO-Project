package bci;

import bci.user.User;
import bci.work.Work;

/**
 * Rule 4: Cannot have more than n requested works at any moment.
 * Base value: 3; dutiful users: 5; defaulting users: 1.
 */
public final class Rule4 implements Rules {
    
    private final int _id = 4;
    
    @Override
    public boolean verify(User user, Work work) {
        return user.getCurrentRequests() < user.getMaxAllowedWorks();
    }
    
    public int getId() {
        return _id;
    }
}