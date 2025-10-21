package bci.notification;

import bci.work.Work;


public class BorrowingNotification extends Notification {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171005L;
    
    private String _onTimeNotification;
    
    public BorrowingNotification(int timestamp, Work work) {
        super(timestamp);
        _onTimeNotification = work.toString();
    }
    
    @Override
    public String getNotificationMessage() {
        return "REQUISIÇÃO: " + _onTimeNotification;
    }
}