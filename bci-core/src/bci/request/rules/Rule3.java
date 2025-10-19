package bci.request.rules;

import bci.user.User;
import bci.work.Work;

/**
 * Rule 3: Cannot request works whose copies have all been requested.
 */
public final class Rule3 implements Rules {
    
    private final int _id = 3;
    
    @Override
    public boolean verify(User user, Work work) {
        return work.isAvailable();
    }
    
    public int getId() {
        return _id;
    }
}