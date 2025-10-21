package bci.request.rules;

import bci.user.User;
import bci.work.Work;


public interface Rules {
    
    boolean verify(User user, Work work);
}