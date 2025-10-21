package bci.request.rules;

import bci.user.User;
import bci.work.Work;


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