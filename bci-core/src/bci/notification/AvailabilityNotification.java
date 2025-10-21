package bci.notification;

import bci.work.Work;


public class AvailabilityNotification extends Notification {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171006L;
    
    private String _onTimeNotification;
    
    public AvailabilityNotification(int timestamp, Work work) {
        super(timestamp);
        _onTimeNotification = work.toString();
    }
    
    @Override
    public String getNotificationMessage() {
        return "DISPONIBILIDADE: " + _onTimeNotification;
    }
}