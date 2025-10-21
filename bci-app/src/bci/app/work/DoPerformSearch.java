package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;
import java.util.List;


class DoPerformSearch extends Command<LibraryManager> {

    DoPerformSearch(LibraryManager receiver) {
        super(Label.PERFORM_SEARCH, receiver);
    }

    @Override
    protected final void execute() {
        String searchTerm = Form.requestString(Prompt.searchTerm());
        
        List<String> results = _receiver.searchWorks(searchTerm);
        
        for (String workDescription : results) {
            _display.popup(workDescription);
        }
    }

}
