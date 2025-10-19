package bci.request.rules;

import bci.user.User;
import bci.work.Work;

/**
 * Rule 5: Cannot request reference works.
 */
public final class Rule5 implements Rules {
    
    private final int _id = 5;
    
    @Override
    public boolean verify(User user, Work work) {
        // Check if the work is a reference work
        // This depends on the work category being "REFERENCE"
        return work.getCategory() == null || 
               !"REFERENCE".equals(work.getCategory().getName().toUpperCase());
    }
    
    public int getId() {
        return _id;
    }
}