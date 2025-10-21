package bci.user;

import bci.user.behaviorInterface.*;
import bci.request.Request;
import bci.notification.Notification;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class User implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private int _idUser;
    private String _name;
    private String _email;
    private String _status;
    private UserBehavior _behavior; 
    private int _consecutiveLate; 
    private int _fines;
    private int _currentRequests;
    private int _consecutiveOnTime;
    private List<Integer> _interestWork; 
    private List<Notification> _notifications; 
    private List<Integer> _requestedWorks; 
    

    public User(int id, String name, String email) {
        _idUser = id;
        _name = name;
        _email = email;
        _status = "ACTIVO";
        _behavior = new Normal();
        _consecutiveLate = 0;
        _fines = 0;
        _currentRequests = 0;
        _consecutiveOnTime = 0;
        _interestWork = new ArrayList<>();
        _notifications = new ArrayList<>();
        _requestedWorks = new ArrayList<>();
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
    
    public void addLateRequest(Request request) {
        _consecutiveLate += 1;
    }

    public void addConsecutiveOnTime(Request request) {
        _consecutiveOnTime += 1;
    }
    
    public void addFine(int value) {
        _fines += value;
    }


    public void recordReturn(boolean wasOnTime) {
        if (wasOnTime) {
            _consecutiveOnTime++;
            _consecutiveLate = 0; 
        } else {
            _consecutiveLate++;
            _consecutiveOnTime = 0;
        }
        calculateAndUpdateBehavior();
    }
    
    public boolean canBorrow() {
        return "ACTIVO".equals(_status);
    }
    
    public boolean canBorrowExpensive() {
        return _behavior.canBorrowExpensive();
    }
    
    public void calculateAndUpdateBehavior() {
        if (_consecutiveOnTime >= 5) {
            _behavior = new Dutiful();
        }
        else if (_consecutiveLate >= 3) {
            _behavior = new Overdue();
        }
        else if (_behavior instanceof Overdue && _consecutiveOnTime >= 3) {
            _behavior = new Normal();
        }
        else if (_behavior instanceof Dutiful && _consecutiveOnTime == 0) {
            _behavior = new Normal();
        }
        else if (_consecutiveOnTime == 0 && _consecutiveLate == 0) {
            _behavior = new Normal();
        }
    }
    
    public void updateStatus() {
        if (_fines > 0) {
            this.suspend();
        }
    }
    
    public void zeroFine(int amount) {
        _fines = 0;
    }
    
    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }

    public void addRequestedWork(int workId) {
        if (!_requestedWorks.contains(workId)) {
            _requestedWorks.add(workId);
        }
    }

    public void removeRequestedWork(int workId) {
        _requestedWorks.remove(Integer.valueOf(workId));
    }

    public boolean hasRequestedWork(int workId) {
        return _requestedWorks.contains(workId);
    }
    
    public List<Notification> getAndClearNotifications() {
        List<Notification> notifications = new ArrayList<>(_notifications);
        _notifications.clear();
        return notifications;
    }
    
    public void addInterestWork(int workId) {
        if (!_interestWork.contains(workId)) {
            _interestWork.add(workId);
        }
    }
    
    public void removeInterestWork(int workId) {
        _interestWork.remove(Integer.valueOf(workId));
    }

    public boolean hasNotifications() {
        return !_notifications.isEmpty();
    }
    
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
    
    public int getFines() {
        return _fines;
    }
    
    public int getCurrentRequests() {
        return _currentRequests;
    }
    
    public int getMaxAllowedWorks() {
        return _behavior.getMaxAllowedWorks();
    }
    
    public int getMaxAllowedRequestDuration() {
        return _behavior.getMaxAllowedRequestDuration();
    }
    
    public int getRequestDuration() {
        return _behavior.getMaxAllowedRequestDuration();
    }

    public int getCurrentOnTime() {
        return _consecutiveOnTime;
    }
    
    public List<Integer> getInterestWorks() {
        return new ArrayList<>(_interestWork);
    }
    
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

    public void setBehavior(UserBehavior behavior) {
        this._behavior = behavior;
    }

    public void setFines(int fines) {
        this._fines = fines;
    }

    public void setCurrentRequests(int currentRequests) {
        this._currentRequests = currentRequests;
    }

    public void setConsecutiveOnTime(int consecutiveOnTime) {
        _consecutiveOnTime = consecutiveOnTime;
    } 
    
    @Override
    public String toString() {
        String behaviorName = getBehavior();
        if ("SUSPENSO".equals(_status)) {
            return _idUser + " - " + _name + " - " + _email + " - " + behaviorName + " - " + _status + " - EUR " + _fines;
        } else {
            return _idUser + " - " + _name + " - " + _email + " - " + behaviorName + " - " + _status;
        }
    }
}
