package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.BorrowingRuleFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


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
            int requestLimit = _receiver.requestWork(userId, workId);
            
            _display.popup(Message.workReturnDay(workId, requestLimit));
            
        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(userId);

        } catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(workId);

        } catch (bci.exceptions.BorrowingRuleFailedException e) {
            if (e.getRuleId() == 3) {
                boolean wantsNotification = Form.confirm(Prompt.returnNotificationPreference());
                if (wantsNotification) {
                    try {
                        _receiver.registerAvailabilityInterest(userId, workId);
                    } catch (bci.exceptions.NoSuchUserException | bci.exceptions.NoSuchWorkException ex) {}
                }
            }
            else {
                throw new BorrowingRuleFailedException(userId, workId, e.getRuleId());
            }
        }
    }

}
