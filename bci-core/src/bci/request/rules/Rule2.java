package bci.request.rules;

import bci.user.User;
import bci.work.Work;


public final class Rule2 implements Rules {
    
    private final int _id = 2;
    
    @Override
    public boolean verify(User user, Work work) {
        return !user.isSuspended();
    }
    
    public int getId() {
        return _id;
    }
}