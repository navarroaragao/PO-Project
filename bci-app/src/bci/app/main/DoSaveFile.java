package bci.app.main;

import bci.LibraryManager;
import bci.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

import java.io.IOException;


class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() {
        try {
			_receiver.save();
		} catch (MissingFileAssociationException eSave) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} catch (MissingFileAssociationException | IOException eSaveAs) {
				eSaveAs.printStackTrace();   
			}
		} catch (IOException eSaveAs) {
			eSaveAs.printStackTrace();   
		}
    }
	
}