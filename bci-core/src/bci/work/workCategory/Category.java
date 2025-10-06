package bci.work.workCategory;

import bci.work.*;
import java.util.List;

public interface Category {
    
    List<Work> category(String name);
    
    List<Work> addWork(Work work);
    
    List<Work> removeWork(Work work);
    
    String getName();
}