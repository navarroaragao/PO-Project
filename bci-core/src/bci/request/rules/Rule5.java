package bci.request.rules;

import bci.user.User;
import bci.work.Work;


public final class Rule5 implements Rules {
    
    private final int _id = 5;
    
    @Override
    public boolean verify(User user, Work work) {
        return work.getCategory() == null || 
               !"REFERENCE".equals(work.getCategory().getName().toUpperCase());
    }
    
    public int getId() {
        return _id;
    }
}