package bci;

import bci.user.User;
import bci.work.Work;

/**
 * Rule 1: User cannot request the same work twice (i.e., in two different and simultaneously open requests).
 */
public final class Rule1 implements Rules {
    
    private final int _id = 1;
    
    @Override
    public boolean verify(User user, Work work) {
        // TODO: Check if user already has an active request for this work
        // This would require tracking active requests per user
        // For now, return true as this functionality needs to be implemented
        // in the Library class to track active requests
        return true;
    }
    
    public int getId() {
        return _id;
    }
}