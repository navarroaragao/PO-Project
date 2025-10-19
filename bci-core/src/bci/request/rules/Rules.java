package bci.request.rules;

import bci.user.User;
import bci.work.Work;

/**
 * Interface for borrowing rules verification.
 * Each rule has an ID and can verify if a user can borrow a specific work.
 */
public interface Rules {
    
    /**
     * Verifies if the user can borrow the work according to this rule.
     * 
     * @param user the user attempting to borrow
     * @param work the work to be borrowed
     * @return true if the rule allows the borrowing, false otherwise
     */
    boolean verify(User user, Work work);
}