package bci.request.rules;

import bci.user.User;
import bci.work.Work;


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