package bci.user.behaviorInterface;

/**
 * Dutiful user behavior - allows borrowing more works and expensive items.
 */
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