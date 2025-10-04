package bci;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a library user.
 */
public class User implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    // Attributes from UML
    private int _idUser;
    private String _name;
    private String _email;
    private String _status;
    private UserBehavior _behavior; // UserBehavior
    private List<Object> _lateRequests; // List<Requests>
    private int _fines;
    private int _currentRequests;
    private List<Object> _interestWork; // List<Work>
    private List<Object> _notifications; // List<Notifications>
    
    /**
     * Constructor for User.
     * @param id user identifier
     * @param name user name
     * @param email user email
     */
    public User(int id, String name, String email) {
        _idUser = id;
        _name = name;
        _email = email;
        _status = "ACTIVO";
        _behavior = new Normal();
        _lateRequests = new ArrayList<>();
        _fines = 0;
        _currentRequests = 0;
        _interestWork = new ArrayList<>();
        _notifications = new ArrayList<>();
    }
    
    // Getters
    public int getIdUser() {
        return _idUser;
    }
    
    public String getName() {
        return _name;
    }
    
    public String getEmail() {
        return _email;
    }
    
    public String getStatus() {
        return _status;
    }

    public String getBehavior() {
        String className = _behavior.getClass().getSimpleName();
        switch (className) {
            case "Dutiful":
                return "CUMPRIDOR";
            case "Overdue":
                return "FALTOSO";
            case "Normal":
            default:
                return "NORMAL";
        }
    }
    
    public UserBehavior getBehaviorObject() {
        return _behavior;
    }
    
    public void setBehavior(UserBehavior behavior) {
        this._behavior = behavior;
    }
    
    public int getFines() {
        return _fines;
    }
    
    public int getCurrentRequests() {
        return _currentRequests;
    }

    // Setters
    public void setIdUser(int idUser) {
        this._idUser = idUser;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public void setStatus(String status) {
        this._status = status;
    }

    public void setFines(int fines) {
        this._fines = fines;
    }

    public void setCurrentRequests(int currentRequests) {
        this._currentRequests = currentRequests;
    }


    public void suspend() {
        _status = "SUSPENSO";
    }
    
    public void activate() {
        _status = "ACTIVO";
    }

    public boolean isSuspended() {
        return "SUSPENSO".equals(_status);
    }
    
    public void addLateRequest(Object request) {
        _lateRequests.add(request);
    }
    
    public void removeLateRequest(Object request) {
        _lateRequests.remove(request);
    }
    
    public void addFine(int value) {
        _fines += value;
    }
    
    public boolean canBorrow() {
        return "ACTIVO".equals(_status);
    }
    
    public int getMaxAllowedWorks() {
        return _behavior.getMaxAllowedWorks();
    }
    
    public int getMaxAllowedRequestDuration() {
        return _behavior.getMaxAllowedRequestDuration();
    }
    
    public boolean canBorrowExpensive() {
        return _behavior.canBorrowExpensive();
    }
    
    public int getRequestDuration() {
        // Implementation depends on user behavior
        return _behavior.getMaxAllowedRequestDuration();
    }
    
    public void calculateAndUpdateBehavior() {
        // Calculate behavior based on user history
        if (_lateRequests.size() == 0 && _fines == 0) {
            _behavior = new Dutiful();
        } else if (_lateRequests.size() > 0 || _fines > 0) {
            _behavior = new Overdue();
        } else {
            _behavior = new Normal();
        }
    }
    
    public void updateStatus() {
        // Update status based on fines and behavior
        if (_fines > 0) {
            _status = "SUSPENSO";
        }
    }
    
    /**
     * Pays the user's fine completely and activates the user.
     * @param amount amount to pay (must be >= total fine)
     */
    public void payFine(int amount) {
        if (amount >= _fines) {
            _fines = 0;
            _status = "ACTIVO";
        }
    }
    
    
    @Override
    public String toString() {
        String behaviorName = getBehavior();
        if ("SUSPENSO".equals(_status) && _fines > 0) {
            return _idUser + " - " + _name + " - " + _email + " - " + behaviorName + " - " + _status + " - EUR " + _fines;
        } else {
            return _idUser + " - " + _name + " - " + _email + " - " + behaviorName + " - " + _status;
        }
    }
}
