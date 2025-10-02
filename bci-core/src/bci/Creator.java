package bci;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a creator (author) in the library system.
 */
public class Creator implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    // Attributes from UML
    private String _name;
    private List<Object> _works; // TODO: Change to List<Work> when Work class is created
    
    /**
     * Constructor for Creator.
     * @param name the creator's name
     */
    public Creator(String name) {
        _name = name;
        _works = new ArrayList<>();
    }
    
    /**
     * Gets the creator's name.
     * @return the creator's name
     */
    public String getName() {
        return _name;
    }
    
    /**
     * Gets the list of works by this creator.
     * @return the list of works
     */
    public List<Object> getWorks() { // TODO: Change to List<Work> when Work class is created
        return _works;
    }
    
    /**
     * Adds a work to this creator's collection.
     * @param work the work to add
     */
    public void addWork(Object work) { // TODO: Change to Work when Work class is created
        if (work != null && !_works.contains(work)) {
            _works.add(work);
        }
    }
    
    /**
     * Removes a work from this creator's collection.
     * @param work the work to remove
     */
    public void removeWork(Object work) { // TODO: Change to Work when Work class is created
        _works.remove(work);
    }
    
    /**
     * Checks if this creator has a specific work.
     * @return true if the creator has the work, false otherwise
     */
    public boolean hasWork() {
        return !_works.isEmpty();
    }
    
    @Override
    public String toString() {
        return _name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Creator creator = (Creator) obj;
        return _name != null ? _name.equals(creator._name) : creator._name == null;
    }
    
    @Override
    public int hashCode() {
        return _name != null ? _name.hashCode() : 0;
    }
}