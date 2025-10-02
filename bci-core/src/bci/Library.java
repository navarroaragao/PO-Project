package bci;

import bci.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
//FIXME maybe import classes

/** Class that represents the library as a whole. */
public class Library implements Serializable {
    private boolean _changed = false;
    private int _currentDate = 0;

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    //FIXME maybe define attributes
    //FIXME maybe implement constructor
    //FIXME maybe implement methods

    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException
     * @throws IOException
     * FIXME maybe other exceptions
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException {
      //try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
    }

    
/** 
     * Set Library to changed. 
     */
    public void changed() {
        setChanged(true);
    }
    
    /** 
     * @return boolean changed status
     */
    public boolean getChanged() {
        return _changed;
    }

    /** 
     * @param changed
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Gets the current date.
     * @return current date
     */
    public int getCurrentDate() {
        return _currentDate;
    }

    /**
     * Advances the current date by the specified number of days.
     * @param days number of days to advance
     */
    public void advanceDate(int days) {
        if (days > 0) {
            _currentDate += days;
            setChanged(true);
        }
    }


}
