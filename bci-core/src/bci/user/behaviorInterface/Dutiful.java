package bci.user.behaviorInterface;

public class Dutiful implements UserBehavior {
    
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