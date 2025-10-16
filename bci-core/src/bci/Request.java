package bci;

import bci.user.User;
import bci.work.Work;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Request implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private User _user;
    private Work _work;
    private int _requestDate;
    private int _requestLimit;
    private int _devolutionDate;
    
    // Rules to be checked in order
    private static final List<Rules> BORROWING_RULES = Arrays.asList(
        new Rule1(),
        new Rule2(), 
        new Rule3(),
        new Rule4(),
        new Rule5(),
        new Rule6()
    );
    
    /**
     * Creates a new Request for the given user and work with the specified request date.
     * The request limit is calculated based on the user's behavior and the devolution date
     * is initially set to 0 (not returned yet).
     * 
     * @param user the user making the request
     * @param work the work being requested
     * @param requestDate the date when the request was made
     */
    public Request(User user, Work work, int requestDate) {
        this._user = user;
        this._work = work;
        this._requestDate = requestDate;
        this._requestLimit = requestDate + calculateRequestDuration();
        this._devolutionDate = 0; // Not returned yet
    }
    
    /**
     * Calculates the request duration based on work total copies and user behavior.
     * 
     * @return the number of days for the request duration
     */
    private int calculateRequestDuration() {
        int totalCopies = _work.getTotalCopies();
        String userBehavior = _user.getBehavior();
        
        if (totalCopies == 1) {
            // Obras com apenas um exemplar
            switch (userBehavior) {
                case "CUMPRIDOR": return 8;
                case "FALTOSO": return 2;
                default: return 3; // NORMAL
            }
        } else if (totalCopies <= 5) {
            // Obras com 5 exemplares ou menos
            switch (userBehavior) {
                case "CUMPRIDOR": return 15;
                case "FALTOSO": return 2;
                default: return 8; // NORMAL
            }
        } else {
            // Obras com mais de 5 exemplares
            switch (userBehavior) {
                case "CUMPRIDOR": return 30;
                case "FALTOSO": return 2;
                default: return 15; // NORMAL
            }
        }
    }
    
    /**
     * Verifies if the borrowing rules are satisfied for this request.
     * Rules are checked in order (1-6).
     * 
     * @return 0 if all rules pass, or the ID of the first rule that fails
     */
    public int identifyRule() {
        for (Rules rule : BORROWING_RULES) {
            if (!rule.verify(_user, _work)) {
                // Get rule ID using reflection or direct casting
                return getRuleId(rule);
            }
        }
        return 0; // All rules passed
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
    
    
    /**
     * Calculates the fine amount for this request based on the current date.
     * 
     * @param currentDate the current date
     * @return the fine amount (0 if not overdue, or calculated fine if overdue)
     */
    public int calculateFine(int currentDate) {
        if (!isOverdue(currentDate)) {
            return 0;
        }
        
        int daysLate = daysOverdue(currentDate);
        int basePrice = _work.getPrice();
        
        // Fine calculation: 5 EUR + 2 EUR per day overdue + 10% of work price per day
        int baseFine = 5;
        int dailyFine = 2 * daysLate;
        int percentageFine = (int) Math.ceil((basePrice * 0.1) * daysLate);
        
        return baseFine + dailyFine + percentageFine;
    }
    
    /**
     * Checks if this is a reference request (non-returnable).
     * For now, this returns false as the implementation depends on work type specifics.
     * 
     * @return true if this is a reference request, false otherwise
     */
    public boolean isReference() {
        // This would depend on the specific work type or category
        // For now, return false - could be extended based on work category
        return false;
    }
    
    /**
     * Checks if this request is overdue based on the current date.
     * 
     * @param currentDate the current date
     * @return true if the request is overdue, false otherwise
     */
    public boolean isOverdue(int currentDate) {
        // Only overdue if work has been borrowed but not returned and past limit
        return _devolutionDate == 0 && currentDate > _requestLimit;
    }
    
    /**
     * Calculates the number of days this request is overdue.
     * 
     * @param currentDate the current date
     * @return the number of days overdue (0 if not overdue)
     */
    public int daysOverdue(int currentDate) {
        if (!isOverdue(currentDate)) {
            return 0;
        }
        return currentDate - _requestLimit;
    }
    
    // Getters and setters
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
    
    /**
     * Returns the request - sets the devolution date and updates work availability
     * 
     * @param returnDate the date when the work is returned
     */
    public void returnWork(int returnDate) {
        this._devolutionDate = returnDate;
        _work.addCopy(); // Make the work available again
    }
    
    @Override
    public String toString() {
        String status = _devolutionDate > 0 ? "Returned on day " + _devolutionDate : "Active";
        return "Request: " + _user.getName() + " -> " + _work.getTitle() + 
               " (Day " + _requestDate + " - " + _requestLimit + ") [" + status + "]";
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
}