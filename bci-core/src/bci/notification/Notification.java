package bci.notification;

import java.io.Serializable;


public abstract class Notification implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171004L;
    
    private int _timestamp;
    
    public Notification(int timestamp) {
        _timestamp = timestamp;
    }
    
    public int getTimestamp() {
        return _timestamp;
    }
    
    public abstract String getNotificationMessage();
}