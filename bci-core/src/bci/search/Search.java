package bci.search;

import java.util.List;
import bci.work.Work;


public interface Search {
    
    List<Work> search(String term, List<Work> works);
}