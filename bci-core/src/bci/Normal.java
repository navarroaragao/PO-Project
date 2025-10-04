package bci;

/**
 * Normal user behavior - standard borrowing privileges.
 */
public class Normal implements UserBehavior {
    
    @Override
    public int getMaxAllowedWorks() {
        return 3;
    }
    
    @Override
    public int getMaxAllowedRequestDuration() {
        return 3; // 3 days for normal users
    }
    
    @Override
    public boolean canBorrowExpensive() {
        return false;
    }
}