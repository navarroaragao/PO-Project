package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;


class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("creatorId", Prompt.creatorId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String creatorId = stringField("creatorId");
            List<String> works = _receiver.getLibrary().showWorksByCreator(creatorId);
            
            for (String work : works) {
                _display.addLine(work);
            }
        } catch (bci.exceptions.NoSuchCreatorException e) {
            throw new NoSuchCreatorException(stringField("creatorId"));
        }
    }

}
