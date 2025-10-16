package bci;

import bci.user.User;
import bci.work.Work;

/**
 * Rule 6: Cannot request works with a price above €25.00 (not applicable to dutiful users).
 */
public final class Rule6 implements Rules {
    
    private final int _id = 6;
    
    @Override
    public boolean verify(User user, Work work) {
        // If user is dutiful, this rule doesn't apply
        if (user.canBorrowExpensive()) {
            return true;
        }
        
        // For other users, check if work price is <= 25
        return work.getPrice() <= 25;
    }
    
    public int getId() {
        return _id;
    }
}