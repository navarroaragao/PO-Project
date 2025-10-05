package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
        addStringField("name", Prompt.userName());
        addStringField("email", Prompt.userEMail());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            var user = _receiver.getLibrary().processUser("USER",
                stringField("name"), 
                stringField("email"));
            
            _display.addLine(Message.registrationSuccessful(user.getIdUser()));
        } catch (bci.exceptions.UserRegistrationFailedException e) {
            throw new bci.app.exceptions.UserRegistrationFailedException(stringField("name"), stringField("email"));
        }
    }
}