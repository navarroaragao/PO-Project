package bci.user.behaviorInterface;

public interface UserBehavior {
    
    int getMaxAllowedWorks();
    
    int getMaxAllowedRequestDuration();
    
    boolean canBorrowExpensive();
}