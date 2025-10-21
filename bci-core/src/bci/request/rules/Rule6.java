package bci.request.rules;

import bci.user.User;
import bci.work.Work;


public final class Rule6 implements Rules {
    
    private final int _id = 6;
    
    @Override
    public boolean verify(User user, Work work) {
        if (user.canBorrowExpensive()) {
            return true;
        }
        
        return work.getPrice() <= 25;
    }
    
    public int getId() {
        return _id;
    }
}