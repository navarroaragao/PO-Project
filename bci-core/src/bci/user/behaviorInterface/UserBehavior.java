package bci.user.behaviorInterface;

/**
 * Interface that defines user behavior patterns for library operations.
 */
public interface UserBehavior {
    
    /**
     * Returns the maximum number of works a user can borrow simultaneously.
     * @return maximum allowed works
     */
    int getMaxAllowedWorks();
    
    /**
     * Returns the maximum duration for a request based on user behavior.
     * @return maximum request duration in days
     */
    int getMaxAllowedRequestDuration();
    
    /**
     * Determines if the user can borrow expensive works.
     * @return true if user can borrow expensive works, false otherwise
     */
    boolean canBorrowExpensive();
}