package bci;

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
        return 8; // 8 days for dutiful users
    }
    
    @Override
    public boolean canBorrowExpensive() {
        return true;
    }
}