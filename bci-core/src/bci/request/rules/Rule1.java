package bci.request.rules;

import bci.user.User;
import bci.work.Work;

/**
 * Rule 1: User cannot request the same work twice (i.e., in two different and simultaneously open requests).
 */
public final class Rule1 implements Rules {
    
    private final int _id = 1;
    
    @Override
    public boolean verify(User user, Work work) {
        // Check whether the user already has an active request for this work
        if (user == null || work == null) return false;
        return !user.hasRequestedWork(work.getIdWork());
    }
    
    public int getId() {
        return _id;
    }
}