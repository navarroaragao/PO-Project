package bci;

import bci.exceptions.*;
import java.io.*;
import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager {

  private Library _library = new Library(/**_defaultRules*/);

  private String _filename = "";

  /**
   * Saves the current state of the library to the associated file.
   * 
   * If there are no changes to save, the method returns immediately.
   * If the filename is not set or is blank, a {@code MissingFileAssociationException} is thrown.
   * The method serializes the library object and writes it to the specified file.
   * After saving, the library's changed flag is reset.
   *
   * @throws MissingFileAssociationException if the filename is not set or is blank
   * @throws IOException if an I/O error occurs during saving
   */
  public void save() throws MissingFileAssociationException, IOException {
    if (!hasChanged()) return;
    if (_filename == null || _filename.isBlank()) 
      throw new MissingFileAssociationException();
    try (ObjectOutputStream oos = new ObjectOutputStream(
                                  new BufferedOutputStream(
                                  new FileOutputStream(_filename)))) {
      oos.writeObject(_library);
      _library.setChanged(false);                    
    }
  }

  /**
   * Saves the current state of the library to a new file specified by the given filename.
   * Updates the internal filename reference and delegates the actual saving process to the {@code save()} method.
   *
   * @param filename the name of the file to save the library state to
   * @throws MissingFileAssociationException if there is no file associated with the library
   * @throws IOException if an I/O error occurs during the save operation
   */
  public void saveAs(String filename) throws MissingFileAssociationException, IOException {
    _filename = filename;
    save();
  }

  /**
   * Loads a Library object from the specified file.
   * 
   * This method attempts to deserialize a Library instance from the given filename.
   * If successful, it updates the internal state with the loaded library and marks it as unchanged.
   * If the file cannot be read or the class cannot be found during deserialization,
   * an UnavailableFileException is thrown.
   *
   * @param filename the path to the file from which the Library should be loaded
   * @throws UnavailableFileException if the file cannot be loaded or deserialized
   */
  public void load(String filename) throws UnavailableFileException {
    try {
      try (ObjectInputStream ois = new ObjectInputStream(
                                   new BufferedInputStream(  
                                   new FileInputStream(filename)))) {
        _filename = filename;
        _library = (Library)ois.readObject();
        _library.setChanged(false);
      }
    }
    catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * Read text input file and initializes the current library (which should be empty)
   * with the domain entities representeed in the import file.
   *
   * @param filename name of the text input file
   * @throws ImportFileException if some error happens during the processing of the
   * import file.
   */
  public void importFile(String filename) throws ImportFileException {
    try {
      _library.importFile(filename);
    } catch (UnrecognizedEntryException | IOException | NoSuchUserException | NoSuchWorkException |
             NoSuchCreatorException | UserRegistrationFailedException | BorrowingRuleFailedException |
             WorkNotBorrowedByUserException | UserIsActiveException e) {
      throw new ImportFileException(filename, e);
    }
  }

    /**
     * Advances the date in the library
     */
    public void advanceDate(int days){
        _library.advanceDate(days);
    }

    /**
     * Performs a search by term in the library.
     * 
     * @param term the search term
     * @return a list of works matching the search term
     */
    public List<String> searchWorks(String term) {
        return _library.searchWorks(term);
    }




  
  /**
   * Gets a user by ID.
   * @param userId the user ID
   * @return the user
   * @throws NoSuchUserException if user doesn't exist
   */
  public bci.user.User getUser(int userId) throws NoSuchUserException {
    return _library.userByKey(userId);
  }
  
  /**
   * Gets a work by ID.
   * @param workId the work ID
   * @return the work
   * @throws NoSuchWorkException if work doesn't exist
   */
  public bci.work.Work getWork(int workId) throws NoSuchWorkException {
    return _library.workByKey(workId);
  }
  
  /**
   * Processes a work request.
   */
  public int requestWork(int userId, int workId) throws NoSuchUserException, NoSuchWorkException, BorrowingRuleFailedException {
    return _library.requestWork(userId, workId);
  }
  
  /**
   * Processes a work return.
   */
  public int returnWork(int userId, int workId) throws NoSuchUserException, NoSuchWorkException, WorkNotBorrowedByUserException {
    return _library.returnWork(userId, workId);
  }
  
  /**
   * Processes fine payment.
   */
  public boolean payFine(int userId, int amount) throws NoSuchUserException, UserIsActiveException {
    return _library.payFine(userId, amount);
  }
  
  /**
   * Shows all users.
   */
  public java.util.List<String> showUsers() {
    return _library.showUsers();
  }
  
  /**
   * Shows all works.
   */
  public java.util.List<String> showWorks() {
    return _library.showWorks();
  }
  
  /**
   * Shows works by creator.
   */
  public java.util.List<String> showWorksByCreator(String creatorName) throws NoSuchCreatorException {
    return _library.showWorksByCreator(creatorName);
  }
  
  /**
   * Changes work inventory.
   */
  public void changeWorkInventory(int workId, int amount) throws NoSuchWorkException {
    _library.changeWorkInventory(workId, amount);
  }
  
  /**
   * Registers availability interest.
   */
  public void registerAvailabilityInterest(int userId, int workId) throws NoSuchUserException, NoSuchWorkException {
    _library.registerAvailabilityInterest(userId, workId);
  }
  
  /**
   * Registers borrowing interest.
   */
  public void registerBorrowingInterest(int userId, int workId) throws NoSuchUserException, NoSuchWorkException {
    _library.registerBorrowingInterest(userId, workId);
  }
  
  /**
   * Gets current date.
   */
  public int getCurrentDate() {
    return _library.getCurrentDate();
  }
  
  /**
   * Shows user notifications.
   */
  public java.util.List<String> showUserNotifications(int userId) throws NoSuchUserException {
    return _library.showUserNotifications(userId);
  }
  
  /**
   * Processes user registration.
   */
  public bci.user.User processUser(String... fields) throws UserRegistrationFailedException {
    return _library.processUser(fields);
  }




  /**
  * @return true if the library has changed since the last save.
  */
  public boolean hasChanged() { 
    return _library.getChanged();
  }

  /**
   * Gets the current library.
   * @return the current library
   */
  public Library getLibrary() {
    return _library;
  }


  

  /**
   * Sets the current library.
   * @param library the library to set
   */
  public void setLibrary(Library library) {
    _library = library;
  }

}
