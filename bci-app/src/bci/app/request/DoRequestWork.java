package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.BorrowingRuleFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        int workId = integerField("workId");
        
        try {
            // Use library's requestWork method for interactive requests
            int requestLimit = _receiver.getLibrary().requestWork(userId, workId);
            
            // Show return day message for successful requests
            _display.popup(Message.workReturnDay(workId, requestLimit));
            
        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(userId);

        } catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(workId);

        } catch (bci.exceptions.BorrowingRuleFailedException e) {
            if (e.getMessage().contains("3")) { // Rule 3
                Form.confirm(Prompt.returnNotificationPreference());
            }
            throw new BorrowingRuleFailedException(userId, workId, extractRuleIdFromMessage(e.getMessage()));
        }
    }
    
    /**
     * Extracts rule ID from exception message.
     */
    private int extractRuleIdFromMessage(String message) {
        if (message.contains("1")) return 1;
        if (message.contains("2")) return 2;
        if (message.contains("3")) return 3;
        if (message.contains("4")) return 4;
        if (message.contains("5")) return 5;
        if (message.contains("6")) return 6;
        return 1; // Default rule ID
    }

}
