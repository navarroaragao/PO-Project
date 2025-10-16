package bci.user.behaviorInterface;

import java.io.Serializable;

public class Dutiful implements UserBehavior, Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    @Override
    public int getMaxAllowedWorks() {
        return 5;
    }
    
    @Override
    public int getMaxAllowedRequestDuration() {
        return 8;
    }
    
    @Override
    public boolean canBorrowExpensive() {
        return true;
    }
}