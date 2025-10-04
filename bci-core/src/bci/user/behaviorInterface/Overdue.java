package bci.user.behaviorInterface;

/**
 * Overdue user behavior - restricted borrowing privileges.
 */
public class Overdue implements UserBehavior {
    
    @Override
    public int getMaxAllowedWorks() {
        return 1;
    }
    
    @Override
    public int getMaxAllowedRequestDuration() {
        return 1; // 1 day for overdue users
    }
    
    @Override
    public boolean canBorrowExpensive() {
        return false;
    }
}