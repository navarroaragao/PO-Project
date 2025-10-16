package bci.user.behaviorInterface;

import java.io.Serializable;

public class Overdue implements UserBehavior, Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    @Override
    public int getMaxAllowedWorks() {
        return 1;
    }
    
    @Override
    public int getMaxAllowedRequestDuration() {
        return 1;
    }
    
    @Override
    public boolean canBorrowExpensive() {
        return false;
    }
}