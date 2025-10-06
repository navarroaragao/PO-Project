package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;


class DoDisplayDate extends Command<LibraryManager> {

    DoDisplayDate(LibraryManager receiver) {
        super(Label.DISPLAY_DATE, receiver);
    }

    @Override
    protected final void execute() {
        int currentDate = _receiver.getLibrary().getCurrentDate();
    _display.addLine(Message.currentDate(currentDate));
    }
    
}