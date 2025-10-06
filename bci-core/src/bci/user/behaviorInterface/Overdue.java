package bci.user.behaviorInterface;

public class Overdue implements UserBehavior {
    
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