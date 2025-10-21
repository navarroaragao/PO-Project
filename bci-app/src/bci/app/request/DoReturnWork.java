package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        int workId = integerField("workId");
        
        try {
            int fine = _receiver.returnWork(userId, workId);
            
            if (fine > 0) {
                bci.user.User user = _receiver.getUser(userId);
                int totalFine = (user != null) ? user.getFines() : fine;

                _display.popup(Message.showFine(userId, totalFine));

                boolean wantsToPay = Form.confirm(Prompt.finePaymentChoice());

                if (wantsToPay) {
                    try {
                        _receiver.payFine(userId, totalFine);
                    } catch (bci.exceptions.UserIsActiveException e) {}
                }
            }
            
        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(userId);
            
        } catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(workId);
            
        } catch (bci.exceptions.WorkNotBorrowedByUserException e) {
            throw new WorkNotBorrowedByUserException(workId, userId);
        }
    }

}
