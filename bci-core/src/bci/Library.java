package bci;

import bci.exceptions.*;
import bci.user.*;
import bci.work.*;
import bci.work.workCategory.*;
import bci.work.workType.*;
import bci.creator.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Locale;

/**
 * The {@code Library} class represents a collection of users, works (such as books and DVDs), and creators.
 * It provides functionality for importing data, registering and retrieving users, works, and creators,
 * processing requests, and managing inventory and categories.
 * <p>
 * The library maintains internal state regarding changes, current date, and unique identifiers for users and works.
 * It supports importing data from files, processing entries, and provides methods for displaying users and works.
 * </p>
 * <p>
 * This class is serializable and is intended to be the core data structure for a library management system.
 * </p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Importing users, works, and requests from a file</li>
 *   <li>Registering and retrieving users, works, and creators</li>
 *   <li>Managing inventory and categories</li>
 *   <li>Tracking changes and current date</li>
 *   <li>Displaying sorted lists of users and works</li>
 * </ul>
 *
 * <p>
 * <b>Exceptions:</b> Many methods throw domain-specific exceptions such as
 * {@link UnrecognizedEntryException}, {@link NoSuchUserException}, {@link NoSuchWorkException},
 * {@link NoSuchCreatorException}, {@link UserRegistrationFailedException}, and
 * {@link BorrowingRuleFailedException} to indicate various error conditions.
 * </p>
 *

 * @version 202507171003L
 */
public class Library implements Serializable {
    private boolean _changed = false; //To check if it there is anything new to save
    private int _currentDate = 1;
    private Map<Integer, User> _users = new HashMap<>();
    private Map<Integer, Work> _works = new HashMap<>();
    private Map<String, Creator> _creators = new HashMap<>();

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /**
     * Imports data from a specified file and processes each entry.
     * 
     * Reads the file line by line, splits each line into fields using the colon (:) delimiter,
     * and processes the resulting fields. Marks the state as changed after processing.
     * 
     *
     * @param filename the path to the file to import
     * @throws UnrecognizedEntryException if an entry in the file is not recognized
     * @throws IOException if an I/O error occurs while reading the file
     * @throws NoSuchUserException if a referenced user does not exist
     * @throws NoSuchWorkException if a referenced work does not exist
     * @throws NoSuchCreatorException if a referenced creator does not exist
     * @throws UserRegistrationFailedException if user registration fails during import
     * @throws BorrowingRuleFailedException if a borrowing rule is violated during import
     * @throws WorkNotBorrowedByUserException if a work is not borrowed by the specified user
     * @throws UserIsActiveException if an operation is attempted on an active user
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException, 
                                           NoSuchUserException, NoSuchWorkException, NoSuchCreatorException,
                                           UserRegistrationFailedException, BorrowingRuleFailedException,
                                           WorkNotBorrowedByUserException, UserIsActiveException {
        var reader = new BufferedReader(new FileReader(filename));
        String line;
        //Read all the lines in the file
        while ((line = reader.readLine()) != null) {
            //Separate the lines into parameters every time there are ":"
            var fields = line.split(":");
            process(fields);
        }
        _changed = true;
        reader.close();
    }

    /**
     * Processes a line from the import file.
     * @param fields the fields from the line split by ":"
     * @throws UnrecognizedEntryException if the entry type is not recognized
     * @throws NoSuchUserException if a referenced user doesn't exist
     * @throws NoSuchWorkException if a referenced work doesn't exist
     * @throws NoSuchCreatorException if a referenced creator doesn't exist
     * @throws UserRegistrationFailedException if user registration fails
     * @throws BorrowingRuleFailedException if borrowing rules are violated
     * @throws WorkNotBorrowedByUserException if work is not borrowed by user
     * @throws UserIsActiveException if user is already active
     */
    private void process(String[] fields) throws UnrecognizedEntryException, NoSuchUserException, 
                                               NoSuchWorkException, NoSuchCreatorException,
                                               UserRegistrationFailedException, BorrowingRuleFailedException,
                                               WorkNotBorrowedByUserException, UserIsActiveException {
        switch (fields[0]) {
            case "USER":
                processUser(fields);
                break;
            case "BOOK":
            case "DVD":
                processWork(fields);
                break;
            case "REQUEST":
                processRequest(fields);
                break;
            default:
                throw new UnrecognizedEntryException(fields[0]);
        }
    }


    /**
     * Processes user registration by creating a new User with an auto-generated ID.
     * 
     * The method expects an array of fields where:
     * 
     *   fields[1] - the user's name
     *   fields[2] - the user's email
     * 
     * If a user with the generated ID already exists, a UserRegistrationFailedException is thrown.
     * The new user is added to the internal user map and marked as changed.
     *
     * @param fields Variable number of String arguments containing user information.
     * @return The newly created User object.
     * @throws UserRegistrationFailedException if a user with the generated ID already exists.
     */
    public User processUser(String... fields) throws UserRegistrationFailedException {
        // Always auto-generate ID incrementally
        int id = getCurrentUserID();
        String name = fields[1];
        String email = fields[2];
        
        if (_users.containsKey(id)) throw new UserRegistrationFailedException(name, email);
        
        var user = new User(id, name, email);
        _users.put(id, user);
        _changed = true;
        return user;
    }


    /**
     * Processes the input fields to create and register a new Work instance in the library.
     * 
     * The method expects the following fields in order:
     * 
     *   workType: The type of the work (e.g., BOOK, DVD).</li>
     *   title: The title of the work.</li>
     *   creatorsString: A comma-separated list of creator names.</li>
     *   price: The price of the work as a string (will be parsed to int).</li>
     *   categoryName: The name of the category for the work.</li>
     *   additionalInfo: Additional information (ISBN for BOOK, IGAC for DVD).</li>
     *   quantity: The quantity of the work as a string (will be parsed to int).</li>
     * 
     * If a creator does not exist, it will be created and added to the library.
     * The method creates the work, registers it, and marks the library as changed.
     *
     * @param fields The fields required to create a Work, as described above.
     * @return The created Work instance.
     * @throws NoSuchCreatorException If a creator cannot be found and cannot be created.
     * @throws UnrecognizedEntryException If the work type or other entry is unrecognized.
     */
    public Work processWork(String... fields) throws NoSuchCreatorException, UnrecognizedEntryException {
        String workType = fields[0];
        String title = fields[1];
        String creatorsString = fields[2];
        int price = Integer.parseInt(fields[3]);
        String categoryName = fields[4];
        String additionalInfo = fields[5]; // ISBN for BOOK, IGAC for DVD, etc
        int quantity = Integer.parseInt(fields[6]);
        
        int id = getCurrentWorkID();
        
        // Process multiple creators (separated by comma for books)
        List<Creator> creators = new ArrayList<>();
        String[] creatorNames = creatorsString.split(","); //To split every time there is a ","
        
        for (String creatorName : creatorNames) {
            creatorName = creatorName.trim(); // Remove any extra spaces
            Creator creator;
            try {
                creator = creatorByKey(creatorName);
            } catch (NoSuchCreatorException e) {
                creator = new Creator(creatorName);
                _creators.put(creatorName, creator);
            }
            creators.add(creator);
        }
        
        Category category = getCategoryByName(categoryName);
        
        Work work = createWork(workType, id, title, price, category, additionalInfo, creators, quantity);
        _works.put(id, work);
        _changed = true;
        return work;
    }

    
    /**
     * Creates a new Work instance of the specified type (BOOK or DVD) with the given parameters.
     * <p>
     * For BOOK, all creators are used. For DVD, only the first creator is used as the director.
     * The initial inventory is set to the specified quantity (default is 1).
     * </p>
     *
     * @param workType        the type of work to create ("BOOK" or "DVD")
     * @param id              the unique identifier for the work
     * @param title           the title of the work
     * @param price           the price of the work
     * @param category        the category of the work
     * @param additionalInfo  additional information (e.g., ISBN for books, IGAC for DVDs)
     * @param creators        the list of creators (authors or director)
     * @param quantity        the initial quantity of the work in inventory
     * @return                the created Work instance
     * @throws UnrecognizedEntryException if the workType is not recognized
     */
    private Work createWork(String workType, int id, String title, int price, Category category, 
                           String additionalInfo, List<Creator> creators, int quantity) throws UnrecognizedEntryException {
        Work work;
        switch (workType.toUpperCase()) {
            case "BOOK":
                work = new Book(id, title, price, category, additionalInfo, creators);
                break;
            case "DVD":
                // DVDs have only one director, so we take the first creator
                Creator director = creators.isEmpty() ? null : creators.get(0);
                work = new DVD(id, title, price, category, additionalInfo, director);
                break;
            default:
                throw new UnrecognizedEntryException(workType);
        }
        
        // Set initial quantity
        if (quantity > 1) {
            work.changeInventory(quantity - 1); // -1 because work starts with 1 copy
        }
        
        return work;
    }
    

    /**
     * Processes a request using the provided fields.
     * <p>
     * Expects the fields array to contain at least three elements, where:
     * <ul>
     *   <li>fields[1]: String representation of the user ID</li>
     *   <li>fields[2]: String representation of the work ID</li>
     * </ul>
     * Validates the existence of the user and work by their IDs and marks the library as changed.
     *
     * @param fields Variable number of string arguments containing request data.
     * @throws NoSuchUserException If the user with the specified ID does not exist.
     * @throws NoSuchWorkException If the work with the specified ID does not exist.
     * @throws BorrowingRuleFailedException If borrowing rules are violated during the request.
     * @throws UnrecognizedEntryException If the entry is not recognized.
     */
    public void processRequest(String... fields) throws NoSuchUserException, NoSuchWorkException, 
                                                       BorrowingRuleFailedException, UnrecognizedEntryException {
        int userId = Integer.parseInt(fields[1]);
        int workId = Integer.parseInt(fields[2]);
        
        userByKey(userId);
        workByKey(workId);
        
        _changed = true;
    }
    /**
     * Gets a category by name, creating it if it doesn't exist.
     * @param categoryName the category name
     * @return the category instance
     */
    private Category getCategoryByName(String categoryName) {
        switch (categoryName.toUpperCase()) {
            case "FICTION":
                return new Fiction();
            case "TECHNICAL":
            case "SCITECH":
                return new Technical();
            case "REFERENCE":
                return new Reference();
            default:
                return null;
        }
    }


    /**
     * Gets a user by ID.
     * @param id the user ID
     * @return the user
     * @throws NoSuchUserException if user doesn't exist
     */
    public User userByKey(int id) throws NoSuchUserException {
        var user = _users.get(id);
        if (user == null) throw new NoSuchUserException(id);
        return user;
    }

    /**
     * Gets a work by ID.
     * @param id the work ID
     * @return the work
     * @throws NoSuchWorkException if work doesn't exist
     */
    public Work workByKey(int id) throws NoSuchWorkException {
        var work = _works.get(id);
        if (work == null) throw new NoSuchWorkException(id);
        return work;
    }

    /**
     * Gets a creator by name.
     * @param name the creator name
     * @return the creator
     * @throws NoSuchCreatorException if creator doesn't exist
     */
    public Creator creatorByKey(String name) throws NoSuchCreatorException {
        var creator = _creators.get(name);
        if (creator == null) throw new NoSuchCreatorException(name);
        return creator;
    }

    /**
     * Registers a new user in the library.
     * @param user the user to register
     */
    public void registerUser(User user) {
        _users.put(user.getIdUser(), user);
        setChanged(true);
    }

    /**
     * Registers a new work in the library.
     * @param work the work to register
     */
    public void registerWork(Work work) {
        _works.put(work.getIdWork(), work);
        setChanged(true);
    }

    /**
     * Registers a new creator in the library.
     * @param creator the creator to register
     */
    public void registerCreator(Creator creator) {
        _creators.put(creator.getName(), creator);
        setChanged(true);
    }

    /**
     * Marks the object as changed by setting its changed state to {@code true}.
     * This method should be called whenever the object's state is modified
     * and observers need to be notified of the change.
     */
    public void changed() {
        setChanged(true);
    }
    
    /**
     * Returns whether the state has changed.
     *
     * @return {@code true} if the state has changed; {@code false} otherwise.
     */
    public boolean getChanged() {
        return _changed;
    }

    /**
     * Sets the changed state of the object.
     *
     * @param changed {@code true} if the object has been changed; {@code false} otherwise.
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Returns the current date value.
     *
     * @return the current date as an integer
     */
    public int getCurrentDate() {
        return _currentDate;
    }

    /**
     * Advances the current date by the specified number of days.
     * If the number of days is greater than zero, the current date is incremented
     * and the changed state is set to true.
     *
     * @param days the number of days to advance the current date; must be positive
     */
    public void advanceDate(int days) {
        if (days > 0) {
            _currentDate += days;
            setChanged(true);
        }
    }

    /**
     * Returns the current user ID, which is calculated as the size of the user list plus one.
     *
     * @return the current user ID
     */
    public int getCurrentUserID() {
        return _users.size() + 1;
    }

    /**
     * Registers a new user with the specified name and email.
     * <p>
     * If a user with the current user ID already exists, the method returns without making changes.
     * Otherwise, it creates a new {@link User} instance and adds it to the user map.
     * The method also marks the state as changed.
     *
     * @param name  the name of the user to register
     * @param email the email address of the user to register
     */
    public void registerUser(String name, String email) {
        int id = getCurrentUserID();
        if (_users.containsKey(id)) {
            return;
        }
        User user = new User(id, name, email);
        _users.put(id, user);
        setChanged(true);
    }

    /**
     * Retrieves the {@link User} object associated with the specified user ID.
     *
     * @param userId the unique identifier of the user to retrieve
     * @return the {@link User} corresponding to the given userId, or {@code null} if no such user exists
     */
    public User getUser(int userId) {
        return _users.get(userId);
    }

    /**
     * Returns a list of all users in the library, sorted first by user name and then by user ID.
     * Each user is represented as a string using their {@code toString()} method.
     *
     * @return a list of string representations of users, sorted by name and ID
     */
    public List<String> showUsers() {
        return _users.values().stream()
            .sorted(Comparator.comparing(User::getName).thenComparing(User::getIdUser))
            .map(User::toString)
            .collect(Collectors.toList());
    }

    /**
     * Returns a list of string representations of all works, sorted by their work ID.
     *
     * @return a {@code List<String>} containing the string representations of the works,
     *         sorted in ascending order by their IDs.
     */
    public List<String> showWorks() {
        return _works.values().stream()
            .sorted(Comparator.comparing(Work::getIdWork))
            .map(Work::toString)
            .collect(Collectors.toList());
    }

    /**
     * Shows all works by a specific creator, ordered by title (case-insensitive).
     * @param creatorName the name of the creator
     * @return list of works by the creator formatted as strings
     * @throws NoSuchCreatorException if the creator doesn't exist
     */
    public List<String> showWorksByCreator(String creatorName) throws NoSuchCreatorException {
        Creator creator = creatorByKey(creatorName);
        return creator.getWorks().stream()
            .sorted(Comparator.comparing(work -> work.getTitle().toLowerCase(Locale.getDefault())))
            .map(Work::toString)
            .collect(Collectors.toList());
    }

    /**
     * Returns the next available work ID based on the current number of works.
     * The ID is calculated as the size of the works collection plus one.
     *
     * @return the next work ID to be assigned
     */
    public int getCurrentWorkID() {
        return _works.size() + 1;
    }

    /**
     * Retrieves a {@link Work} object by its unique identifier.
     *
     * @param WorkId the unique identifier of the work to retrieve
     * @return the {@link Work} associated with the specified ID, or {@code null} if not found
     */
    public Work getWork(int WorkId) {
        return _works.get(WorkId);
    }
    
    /**
     * Changes the inventory of a work by the specified amount.
     * @param workId the work identifier
     * @param amount the amount to change (positive to add, negative to remove)
     * @return true if successful, false if work doesn't exist or not enough inventory
     */
    public void changeWorkInventory(int workId, int amount) {
        Work work = _works.get(workId);
        if (work != null) {
            work.changeInventory(amount);
        if (amount>0) {
            setChanged(true);
        }
        }
    }
}

