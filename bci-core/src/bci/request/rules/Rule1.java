package bci.request.rules;

import bci.user.User;
import bci.work.Work;


public final class Rule1 implements Rules {
    
    private final int _id = 1;
    
    @Override
    public boolean verify(User user, Work work) {
        if (user == null || work == null) return false;
        return !user.hasRequestedWork(work.getIdWork());
    }
    
    public int getId() {
        return _id;
    }
}