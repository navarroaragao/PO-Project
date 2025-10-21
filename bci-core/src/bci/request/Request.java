package bci.request;

import bci.user.User;
import bci.work.Work;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import bci.request.rules.*;

public class Request implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private User _user;
    private Work _work;
    private int _requestDate;
    private int _requestLimit;
    private int _devolutionDate;
    
    private static final List<Rules> BORROWING_RULES = Arrays.asList(
        new Rule1(),
        new Rule2(), 
        new Rule3(),
        new Rule4(),
        new Rule5(),
        new Rule6()
    );
    
    public Request(User user, Work work, int requestDate) {
        this._user = user;
        this._work = work;
        this._requestDate = requestDate;
        this._requestLimit = requestDate + calculateRequestDuration();
        this._devolutionDate = 0; 
    }
    
    public int calculateRequestDuration() {
        int totalCopies = _work.getTotalCopies();
        String userBehavior = _user.getBehavior();
        
        if (totalCopies == 1) {
            switch (userBehavior) {
                case "CUMPRIDOR": return 8;
                case "FALTOSO": return 2;
                default: return 3; 
            }
        } else if (totalCopies <= 5) {
            switch (userBehavior) {
                case "CUMPRIDOR": return 15;
                case "FALTOSO": return 2;
                default: return 8; 
            }
        } else {
            switch (userBehavior) {
                case "CUMPRIDOR": return 30;
                case "FALTOSO": return 2;
                default: return 15;
            }
        }
    }
    
    public int identifyRule() {
        for (Rules rule : BORROWING_RULES) {
            if (!rule.verify(_user, _work)) {
                return getRuleId(rule);
            }
        }
        return 0;
    }

    public int getRuleId(Rules rule) {
        if (rule instanceof Rule1) return 1;
        if (rule instanceof Rule2) return 2;
        if (rule instanceof Rule3) return 3;
        if (rule instanceof Rule4) return 4;
        if (rule instanceof Rule5) return 5;
        if (rule instanceof Rule6) return 6;
        return 0;
    }
    
    
    public int calculateFine(int currentDate) {
        int daysLate = daysOverdue(currentDate);
        return daysLate * 5; // €5 per day
    }
    

    public boolean isOverdue(int currentDate) {
        return _devolutionDate == 0 && currentDate > _requestLimit;
    }
    

    public int daysOverdue(int currentDate) {
        if (!isOverdue(currentDate)) {
            return 0;
        }
        return currentDate - _requestLimit;
    }
    

    public void returnWork(int returnDate) {
        this._devolutionDate = returnDate;
        _work.addCopy();
    }


    
    public User getUser() {
        return _user;
    }
    
    public Work getWork() {
        return _work;
    }
    
    public int getRequestDate() {
        return _requestDate;
    }
    
    public int getRequestLimit() {
        return _requestLimit;
    }
    
    public int getDevolutionDate() {
        return _devolutionDate;
    }


    
    public void setDevolutionDate(int devolutionDate) {
        this._devolutionDate = devolutionDate;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Request request = (Request) obj;
        return _user.equals(request._user) && 
               _work.equals(request._work) && 
               _requestDate == request._requestDate;
    }
    
    @Override
    public String toString() {
        String status = _devolutionDate > 0 ? "Returned on day " + _devolutionDate : "Active";
        return "Request: " + _user.getName() + " -> " + _work.getTitle() + 
               " (Day " + _requestDate + " - " + _requestLimit + ") [" + status + "]";
    }
}