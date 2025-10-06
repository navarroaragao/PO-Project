package bci.user.behaviorInterface;

public class Normal implements UserBehavior {
    
    @Override
    public int getMaxAllowedWorks() {
        return 3;
    }
    
    @Override
    public int getMaxAllowedRequestDuration() {
        return 3;
    }
    
    @Override
    public boolean canBorrowExpensive() {
        return false;
    }
}